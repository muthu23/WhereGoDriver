package com.wherego.driver.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wherego.driver.App;
import com.wherego.driver.R;
import com.wherego.driver.helpers.CustomDialog;
import com.wherego.driver.helpers.RestInterface;
import com.wherego.driver.helpers.ServiceGenerator;
import com.wherego.driver.helpers.SharedHelper;
import com.wherego.driver.helpers.URLHelper;
import com.wherego.driver.model.withdrawlist.Datum;
import com.wherego.driver.model.withdrawlist.WithDrawsList;
import com.wherego.driver.utills.Utils;


import java.text.ParseException;
import java.util.ArrayList;

import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;

public class WithdrawActivity extends AppCompatActivity implements View.OnClickListener {

    @OnClick(R.id.backArrow)
    void backArrow() {
        onBackPressed();
    }

    RestInterface restInterface;
    ImageView backArrow;
    String locale;
    WithdrawAdapter withdrawAdapter;
//    ArrayList<WithdrawAmount> withdrawAmountArrayList;
    private Button addAccountDetailsBtn;
//    private Stripe stripe;
    private CustomDialog customDialog;
//    private LinearLayout layoutMainId;
//    private String bankAccount;
//    private String totalAmountTransfer;
//    private Button addAmountBtn;
//    private EditText amountEditText;
    private TextView earnedMoneyTxtView;
    private String providerId;
    private RecyclerView recyclerWithdraw;
    private LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);

        restInterface = ServiceGenerator.createService(RestInterface.class);
//
//        stripe = new Stripe(this);
//        stripe.setDefaultPublishableKey(URLHelper.STRIPE_TOKEN);
        initViews();

        //SharedHelper.getKey(WithdrawActivity.this,"user_provider_id");
        providerId = SharedHelper.getKey(WithdrawActivity.this, "id");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = this.getResources().getConfiguration().getLocales().get(0).getCountry();
        } else {
            locale = this.getResources().getConfiguration().locale.getCountry();
        }
        withdrawsList();
    }

    private void initViews() {
        backArrow = findViewById(R.id.backArrow);
        addAccountDetailsBtn = findViewById(R.id.addAccountDetailsBtn);
        backArrow = findViewById(R.id.backArrow);
        recyclerWithdraw = findViewById(R.id.recyclerWithdraw);
//        layoutMainId = findViewById(R.id.layoutMainId);
        earnedMoneyTxtView = findViewById(R.id.earnedMoneyTxtView);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerWithdraw.setLayoutManager(mLayoutManager);
        recyclerWithdraw.setItemAnimator(new DefaultItemAnimator());
        addAccountDetailsBtn.setOnClickListener(this);

        backArrow.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backArrow:
                onBackPressed();
                break;
            case R.id.addAccountDetailsBtn:
                Intent intent = new Intent(this, WithdrawAmountActivity.class);
                startActivity(intent);
                break;
        }

    }

    private void withdrawsList() {
        String auth = "Bearer" + " " + SharedHelper.getKey(App.getContext(), "access_token");
        customDialog = new CustomDialog(this);
        customDialog.setCancelable(false);
        customDialog.show();
        Call<WithDrawsList> call = restInterface.withdrawsList(URLHelper.REQUEST_WITH, auth);
        call.enqueue(new Callback<WithDrawsList>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<WithDrawsList> call, retrofit2.Response<WithDrawsList> response) {
                customDialog.dismiss();
                if (response.code() == 200) {
                    if (response.body().getTotalEarn() != null) {
                        earnedMoneyTxtView.setText(SharedHelper.getKey(App.getContext(), "currency") +
                                response.body().getTotalEarn().toString());
                    }
                    if (response.body().getData() != null && !response.body().getData().isEmpty()) {
                        recyclerWithdraw.setLayoutManager(new LinearLayoutManager(WithdrawActivity.this));
                        withdrawAdapter = new WithdrawAdapter(response.body().getData());
                        recyclerWithdraw.setAdapter(withdrawAdapter);
                    } else {
                        earnedMoneyTxtView.setText(SharedHelper.getKey(App.getContext(), "currency") + " 0");
                    }
                }
            }

            @Override
            public void onFailure(Call<WithDrawsList> call, Throwable t) {
                customDialog.dismiss();
            }
        });

    }

    public void displayMessage(String toastString) {
        Toasty.info(App.getContext(), toastString, Toasty.LENGTH_SHORT, true).show();
    }

    private class WithdrawAdapter extends RecyclerView.Adapter<WithdrawAdapter.MyViewHolder> {

        ArrayList<Datum> data;

        public WithdrawAdapter(ArrayList<Datum> data) {
            this.data = data;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.withdraw_item_adapter, parent, false));
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            Datum d = data.get(position);
            if (d.getAmount() != null) {
                holder.lblWithdrawAmount.setText(SharedHelper.getKey(App.getContext(), "currency")
                        + d.getAmount());
            }
            if (d.getStatus() != null) {
                holder.lblWithdrawStatus.setText(d.getStatus());
            }
            if (d.getCreatedAt() != null) {
                try {
                    holder.lblWithdrawDateTime.setText(Utils.getDate(d.getCreatedAt()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView lblWithdrawAmount, lblWithdrawDateTime, lblWithdrawStatus;

            public MyViewHolder(View itemView) {
                super(itemView);
                lblWithdrawAmount = itemView.findViewById(R.id.lblWithdrawAmount);
                lblWithdrawDateTime = itemView.findViewById(R.id.lblWithdrawDateTime);
                lblWithdrawStatus = itemView.findViewById(R.id.lblWithdrawStatus);
            }
        }
    }


}
