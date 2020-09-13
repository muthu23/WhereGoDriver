package com.wherego.delivery.driver.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wherego.delivery.driver.App;
import com.wherego.delivery.driver.R;
import com.wherego.delivery.driver.activities.HistoryDetailsActivity;
import com.wherego.delivery.driver.helpers.ConnectionHelper;
import com.wherego.delivery.driver.helpers.CustomDialog;
import com.wherego.delivery.driver.helpers.RestInterface;
import com.wherego.delivery.driver.helpers.ServiceGenerator;
import com.wherego.delivery.driver.helpers.SharedHelper;
import com.wherego.delivery.driver.helpers.URLHelper;
import com.wherego.delivery.driver.model.historylist.HistoryList;
import com.wherego.delivery.driver.utills.Utils;

import java.text.ParseException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;


public class PastTrips extends Fragment {

    RestInterface restInterface;
    HistoryListAdapter adapter;
    @BindView(R.id.rvHistory)
    RecyclerView rvHistory;
    @BindView(R.id.errorLayout)
    RelativeLayout errorLayout;

    Boolean isInternet;
    ConnectionHelper helper;
    CustomDialog customDialog;


    public PastTrips() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_past_trips, container, false);
        restInterface = ServiceGenerator.createService(RestInterface.class);
        ButterKnife.bind(this, view);
        errorLayout.setVisibility(View.GONE);
        helper = new ConnectionHelper(getActivity());
        isInternet = helper.isConnectingToInternet();
        if (isInternet) {
            historyList();
        }
        return view;
    }

    private void historyList() {
        String auth = "Bearer" + " " + SharedHelper.getKey(App.getContext(), "access_token");
        customDialog = new CustomDialog(getActivity());
        customDialog.setCancelable(false);
        customDialog.show();
        Call<List<HistoryList>> call = restInterface.historyList(URLHelper.REQUEST_WITH, auth);
        call.enqueue(new Callback<List<HistoryList>>() {
            @Override
            public void onResponse(Call<List<HistoryList>> call, retrofit2.Response<List<HistoryList>> response) {
                customDialog.dismiss();
                if (response.code() == 200) {
                    if (!response.body().isEmpty()) {
                        errorLayout.setVisibility(View.GONE);
                        adapter = new HistoryListAdapter(response.body());
                        rvHistory.setHasFixedSize(true);
                        rvHistory.setLayoutManager(new LinearLayoutManager(getActivity()));
                        rvHistory.setAdapter(adapter);
                    } else {
                        errorLayout.setVisibility(View.VISIBLE);
                    }
                }

            }

            @Override
            public void onFailure(Call<List<HistoryList>> call, Throwable t) {
                customDialog.dismiss();
            }
        });

    }

    private class HistoryListAdapter extends RecyclerView.Adapter<HistoryListAdapter.HistoryHolder> {

        List<HistoryList> historyLists;

        public HistoryListAdapter(List<HistoryList> historyLists) {
            this.historyLists = historyLists;
        }


        @Override
        public HistoryListAdapter.HistoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new HistoryHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.history_item, parent, false));
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(HistoryListAdapter.HistoryHolder holder, int position) {
            HistoryList hl = historyLists.get(position);
            if (hl.getSAddress() != null) {
                holder.txtdepartlocation.setText(hl.getSAddress());
            }
            if (hl.getDAddress() != null) {
                holder.destinationlocatio.setText(hl.getDAddress());
            }
            if (hl.getStatus() != null) {
                if (hl.getStatus().equalsIgnoreCase("COMPLETED")) {
                    holder.serviceType.setText("Delivered");
                } else {
                    holder.serviceType.setText(hl.getStatus());
                }
            }
            if (hl.getAssignedAt() != null) {
                try {
                    holder.journeydate.setText(Utils.getDate(hl.getAssignedAt()) + "th "
                            + Utils.getMonth(hl.getAssignedAt()) + " "
                            + Utils.getYear(hl.getAssignedAt()));
                } catch (ParseException e) {

                }

            }

            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), HistoryDetailsActivity.class);
                intent.putExtra("requestID", String.valueOf(hl.getId()));
                startActivity(intent);
            });

        }

        @Override
        public int getItemCount() {
            return historyLists.size();
        }


        public class HistoryHolder extends RecyclerView.ViewHolder {

            TextView txtdepartlocation, journeydate, destinationlocatio, txtTime;
            TextView serviceType;

            public HistoryHolder(View itemView) {
                super(itemView);
                txtdepartlocation = itemView.findViewById(R.id.txtdepartlocation);
                journeydate = itemView.findViewById(R.id.journeydate);
                destinationlocatio = itemView.findViewById(R.id.destinationlocatio);
                txtTime = itemView.findViewById(R.id.txtTime);
                serviceType = itemView.findViewById(R.id.serviceType);
            }
        }
    }
}
