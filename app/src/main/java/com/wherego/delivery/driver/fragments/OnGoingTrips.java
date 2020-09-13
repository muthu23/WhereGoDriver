package com.wherego.delivery.driver.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.wherego.delivery.driver.App;
import com.wherego.delivery.driver.DriverMainActivity;
import com.wherego.delivery.driver.R;
import com.wherego.delivery.driver.activities.UpcomingDetailsActivity;
import com.wherego.delivery.driver.helpers.ConnectionHelper;
import com.wherego.delivery.driver.helpers.CustomDialog;
import com.wherego.delivery.driver.helpers.RestInterface;
import com.wherego.delivery.driver.helpers.ServiceGenerator;
import com.wherego.delivery.driver.helpers.SharedHelper;
import com.wherego.delivery.driver.helpers.URLHelper;
import com.wherego.delivery.driver.model.ongoing.OnGoingRes;
import com.wherego.delivery.driver.utills.Utils;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class OnGoingTrips extends Fragment {

    RestInterface restInterface;
    OnGoingAdapter adapter;
    @BindView(R.id.rvHistory)
    RecyclerView rvHistory;
    @BindView(R.id.errorLayout)
    RelativeLayout errorLayout;

    Boolean isInternet;
    ConnectionHelper helper;
    CustomDialog customDialog;


    public OnGoingTrips() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_on_going_trips, container, false);
        restInterface = ServiceGenerator.createService(RestInterface.class);
        ButterKnife.bind(this, view);
        errorLayout.setVisibility(View.GONE);
        helper = new ConnectionHelper(getActivity());
        isInternet = helper.isConnectingToInternet();
        if (isInternet) {
            getUpcomingList();
        }
        return view;
    }


    private void getUpcomingList() {
        String auth = "Bearer" + " " + SharedHelper.getKey(App.getContext(), "access_token");
        customDialog = new CustomDialog(getActivity());
        customDialog.setCancelable(false);
        customDialog.show();
        Call<List<OnGoingRes>> call = restInterface.onGoingList(URLHelper.REQUEST_WITH, auth);
        call.enqueue(new Callback<List<OnGoingRes>>() {
            @Override
            public void onResponse(Call<List<OnGoingRes>> call, retrofit2.Response<List<OnGoingRes>> response) {
                customDialog.dismiss();
                if (response.code() == 200) {
                    if (!response.body().isEmpty()) {
                        errorLayout.setVisibility(View.GONE);
                        adapter = new OnGoingAdapter(response.body());
                        rvHistory.setHasFixedSize(true);
                        rvHistory.setLayoutManager(new LinearLayoutManager(getActivity()));
                        rvHistory.setAdapter(adapter);
                    } else {
                        errorLayout.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<OnGoingRes>> call, Throwable t) {
                customDialog.dismiss();
            }
        });
    }


    public void displayMessage(String toastString) {
        Snackbar.make(getView(), toastString, Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show();
    }

    private class OnGoingAdapter extends RecyclerView.Adapter<OnGoingAdapter.OnGoingHolder> {

        List<OnGoingRes> onGoingRes;

        public OnGoingAdapter(List<OnGoingRes> onGoingRes) {
            this.onGoingRes = onGoingRes;
        }


        @Override
        public OnGoingAdapter.OnGoingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new OnGoingAdapter.OnGoingHolder(LayoutInflater.from(parent.getContext())
            .inflate(R.layout.upcoming_list_item, parent, false));
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(OnGoingAdapter.OnGoingHolder holder, final int position) {
            OnGoingRes oRS = onGoingRes.get(position);
            if (oRS.getStaticMap() != null){
                Picasso.get().load(oRS.getStaticMap()).placeholder(R.drawable.placeholder).into(holder.tripImg);
            }
            if (oRS.getScheduleAt() != null){
                try {
                    holder.tripDate.setText(Utils.getDate(oRS.getScheduleAt()) + "th " +
                            Utils.getMonth(oRS.getScheduleAt()) + " " +
                            Utils.getYear(oRS.getScheduleAt()) + "at " +
                            Utils.getTime(oRS.getScheduleAt()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            if (oRS.getBookingId() != null){
                holder.tripId.setText(oRS.getBookingId());
            }
            if (oRS.getServiceType() != null){
                if (oRS.getServiceType().getName() != null){
                    holder.car_name.setText(oRS.getServiceType().getName());
                }
                if (oRS.getServiceType().getImage() != null){
                    Picasso.get().load(URLHelper.BASE + oRS.getServiceType().getImage());
                }
            }

            holder.btnCancel.setOnClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage(getString(R.string.cencel_request))
                        .setCancelable(false)
                        .setPositiveButton("YES", (dialog, id) -> {
                            dialog.dismiss();
                            cancelTrip(String.valueOf(oRS.getId()), "");
                        })
                        .setNegativeButton("NO", (dialog, id) -> dialog.dismiss());
                AlertDialog alert = builder.create();
                alert.show();
            });

            holder.btnStart.setOnClickListener(view -> acceptRequest(String.valueOf(oRS.getId())));

            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), UpcomingDetailsActivity.class);
                intent.putExtra("requestID", String.valueOf(oRS.getId()));
                startActivity(intent);
                getActivity().finish();
            });

        }

        @Override
        public int getItemCount() {
            return onGoingRes.size();
        }

        public class OnGoingHolder extends RecyclerView.ViewHolder {

            TextView tripTime, car_name;
            TextView tripDate, tripAmount, tripId;
            ImageView tripImg, driver_image;
            Button btnCancel, btnStart;

            public OnGoingHolder(View itemView) {
                super(itemView);
                tripDate = itemView.findViewById(R.id.tripDate);
                tripTime = itemView.findViewById(R.id.tripTime);
                tripAmount = itemView.findViewById(R.id.tripAmount);
                tripImg = itemView.findViewById(R.id.tripImg);
                car_name = itemView.findViewById(R.id.car_name);
                driver_image = itemView.findViewById(R.id.driver_image);
                btnCancel = itemView.findViewById(R.id.btnCancel);
                btnStart = itemView.findViewById(R.id.btnStart);
                tripId = itemView.findViewById(R.id.tripid);
            }
        }
    }

    private void acceptRequest(String id) {
        String auth = "Bearer" + " " + SharedHelper.getKey(App.getContext(), "access_token");
        customDialog = new CustomDialog(getActivity());
        customDialog.setCancelable(false);
        customDialog.show();
        Call<ResponseBody> call = restInterface.acceptTrip(URLHelper.REQUEST_WITH, auth, id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                customDialog.dismiss();
                if (response.code() == 200) {
                    displayMessage("Order accepted successfully");
                    Intent i = new Intent(getActivity(), DriverMainActivity.class);
                    i.putExtra("type", "SCHEDULED");
                    startActivity(i);
                } else {
                    displayMessage(getString(R.string.please_try_again));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                customDialog.dismiss();
                displayMessage(getString(R.string.something_went_wrong));
            }
        });
    }

    private void cancelTrip(String id, String cancelReason) {
        String auth = "Bearer" + " " + SharedHelper.getKey(App.getContext(), "access_token");
        customDialog = new CustomDialog(getActivity());
        customDialog.setCancelable(false);
        customDialog.show();
        Call<ResponseBody> call = restInterface.cancelTrip(URLHelper.REQUEST_WITH, auth, id, cancelReason);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                customDialog.dismiss();
                if (response.code() == 200) {
                    getUpcomingList();
                    displayMessage("You have cancelled the order");
                } else {
                    displayMessage(getString(R.string.please_try_again));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                customDialog.dismiss();
                displayMessage(getString(R.string.something_went_wrong));
            }
        });
    }

}
