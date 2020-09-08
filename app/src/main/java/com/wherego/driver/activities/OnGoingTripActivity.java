package com.wherego.driver.activities;

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
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wherego.driver.App;
import com.wherego.driver.DriverMainActivity;
import com.wherego.driver.R;
import com.wherego.driver.helpers.ConnectionHelper;
import com.wherego.driver.helpers.CustomDialog;
import com.wherego.driver.helpers.RestInterface;
import com.wherego.driver.helpers.ServiceGenerator;
import com.wherego.driver.helpers.SharedHelper;
import com.wherego.driver.helpers.URLHelper;
import com.wherego.driver.model.ongoing.OnGoingRes;
import com.wherego.driver.utills.Utils;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class OnGoingTripActivity extends AppCompatActivity {

    @OnClick(R.id.backArrow)
            void backArrow(){
        onBackPressed();
    }
    RestInterface restInterface;
    OnGoingAdapter adapter;
    @BindView(R.id.rvHistory)
    RecyclerView rvHistory;
    @BindView(R.id.errorLayout)
    RelativeLayout errorLayout;

    Boolean isInternet;
    ConnectionHelper helper;
    CustomDialog customDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_going_trip);
        restInterface = ServiceGenerator.createService(RestInterface.class);
        ButterKnife.bind(this);
        errorLayout.setVisibility(View.GONE);
        helper = new ConnectionHelper(this);
        isInternet = helper.isConnectingToInternet();
        if (isInternet) {
            getUpcomingList();
        }
    }


    private void getUpcomingList() {
        String auth = "Bearer" + " " + SharedHelper.getKey(App.getContext(), "access_token");
        customDialog = new CustomDialog(this);
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
                        rvHistory.setLayoutManager(new LinearLayoutManager(OnGoingTripActivity.this));
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

    private void displayMessage(String toastString) {
        Toasty.info(App.getContext(), toastString, Toasty.LENGTH_SHORT, true).show();
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
                AlertDialog.Builder builder = new AlertDialog.Builder(OnGoingTripActivity.this);
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
                Intent intent = new Intent(OnGoingTripActivity.this,
                        UpcomingDetailsActivity.class);
                intent.putExtra("requestID", String.valueOf(oRS.getId()));
                startActivity(intent);
                finish();
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
        customDialog = new CustomDialog(this);
        customDialog.setCancelable(false);
        customDialog.show();
        Call<ResponseBody> call = restInterface.acceptTrip(URLHelper.REQUEST_WITH, auth, id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                customDialog.dismiss();
                if (response.code() == 200) {
                    displayMessage("Order accepted successfully");
                    Intent i = new Intent(OnGoingTripActivity.this, DriverMainActivity.class);
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
        customDialog = new CustomDialog(this);
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
