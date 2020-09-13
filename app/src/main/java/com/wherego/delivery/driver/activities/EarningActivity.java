package com.wherego.delivery.driver.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wherego.delivery.driver.App;
import com.wherego.delivery.driver.R;
import com.wherego.delivery.driver.helpers.ConnectionHelper;
import com.wherego.delivery.driver.helpers.CustomDialog;
import com.wherego.delivery.driver.helpers.RestInterface;
import com.wherego.delivery.driver.helpers.ServiceGenerator;
import com.wherego.delivery.driver.helpers.SharedHelper;
import com.wherego.delivery.driver.helpers.URLHelper;
import com.wherego.delivery.driver.model.earnings.EarningRes;
import com.wherego.delivery.driver.model.earnings.Ride;
import com.wherego.delivery.driver.utills.Utils;

import java.text.ParseException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;

public class EarningActivity extends AppCompatActivity {

    @BindView(R.id.errorLayout)
    RelativeLayout errorLayout;
    @BindView(R.id.rcvRides)
    RecyclerView rcvRides;
    @BindView(R.id.lblEarnings)
    TextView lblEarnings;
    @OnClick(R.id.backArrow)
            void backArrow(){
        onBackPressed();
    }
    RestInterface restInterface;

    Boolean isInternet;
    EarningsAdapter earningsAdapter;
    ConnectionHelper helper;
    CustomDialog customDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earning);
        ButterKnife.bind(this);
        restInterface = ServiceGenerator.createService(RestInterface.class);
        helper = new ConnectionHelper(EarningActivity.this);
        isInternet = helper.isConnectingToInternet();
        errorLayout.setVisibility(View.GONE);
        getEarningsList();

    }


    @Override
    public void onResume() {
        super.onResume();
    }

    public void getEarningsList() {
        String auth = "Bearer" + " " + SharedHelper.getKey(App.getContext(), "access_token");
        customDialog = new CustomDialog(EarningActivity.this);
        customDialog.setCancelable(false);
        customDialog.show();
        Call<EarningRes> call = restInterface.earnings(URLHelper.REQUEST_WITH, auth);
        call.enqueue(new Callback<EarningRes>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<EarningRes> call, retrofit2.Response<EarningRes> response) {
                customDialog.dismiss();
                if (response.code() == 200){
                    EarningRes res = response.body();
                    lblEarnings.setText(SharedHelper.getKey(App.getContext(), "currency") + " " +
                            res.getRidesCount().getOverall() + "");
                    if (res.getRides() != null && !res.getRides().isEmpty()){
                        earningsAdapter = new EarningsAdapter(res.getRides());
                        rcvRides.setLayoutManager(new LinearLayoutManager(EarningActivity.this));
                        rcvRides.setItemAnimator(new DefaultItemAnimator());
                        rcvRides.setVisibility(View.VISIBLE);
                        errorLayout.setVisibility(View.GONE);
                        rcvRides.setAdapter(earningsAdapter);
                    }else {
                        lblEarnings.setText(SharedHelper.getKey(App.getContext(), "currency") + " 0");
                        errorLayout.setVisibility(View.VISIBLE);
                        rcvRides.setVisibility(View.GONE);
                    }
                }else {
                    errorLayout.setVisibility(View.VISIBLE);
                    rcvRides.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<EarningRes> call, Throwable t) {
                customDialog.dismiss();
                errorLayout.setVisibility(View.VISIBLE);
                rcvRides.setVisibility(View.GONE);
            }
        });


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private class EarningsAdapter extends RecyclerView.Adapter<EarningsAdapter.MyViewHolder> {

        List<Ride> rides;

        public EarningsAdapter(List<Ride> rides) {
            this.rides = rides;
        }

        @Override
        public EarningsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new EarningsAdapter.MyViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.earnings_item, parent, false));
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(EarningsAdapter.MyViewHolder holder, final int position) {
            Ride r = rides.get(position);
            if (r.getAssignedAt() != null){
                try {
                    holder.lblTime.setText(Utils.getTime(r.getAssignedAt()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            if (r.getDistance() != null){
                holder.lblDistance.setText(r.getDistance() + " Km");
            }
            if (r.getPayment() != null){
                if (r.getPayment().getTotal() != null){
                    holder.lblAmount.setText(SharedHelper.getKey(EarningActivity.this, "currency")
                            + r.getPayment().getTotal());
                }
            }
        }

        @Override
        public int getItemCount() {
            return rides.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView lblTime, lblDistance, lblAmount;

            public MyViewHolder(View itemView) {
                super(itemView);
                lblTime = itemView.findViewById(R.id.lblTime);
                lblDistance = itemView.findViewById(R.id.lblDistance);
                lblAmount = itemView.findViewById(R.id.lblAmount);
            }
        }
    }
}
