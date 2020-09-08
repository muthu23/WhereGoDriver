package com.wherego.driver.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.daasuu.cat.CountAnimationTextView;
import com.wherego.driver.App;
import com.wherego.driver.R;
import com.wherego.driver.helpers.CustomDialog;
import com.wherego.driver.helpers.RestInterface;
import com.wherego.driver.helpers.ServiceGenerator;
import com.wherego.driver.helpers.SharedHelper;
import com.wherego.driver.helpers.URLHelper;
import com.wherego.driver.model.SummryRes;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SummaryActivity extends AppCompatActivity {

    RestInterface restInterface;

    @OnClick(R.id.backArrow)
    void backArrow() {
        onBackPressed();
    }

    @OnClick(R.id.rides_card)
    void rides_card() {
        startActivity(new Intent(SummaryActivity.this, HistoryActivity.class));
    }

    @OnClick(R.id.revenue_card)
    void revenue_card() {
        startActivity(new Intent(SummaryActivity.this, EarningActivity.class));
    }

    @OnClick(R.id.schedule_card)
    void schedule_card() {
        startActivity(new Intent(SummaryActivity.this, OnGoingTripActivity.class));
    }

    @OnClick(R.id.cancel_card)
    void cancel_card() {

    }

    @BindView(R.id.card_layout)
    LinearLayout card_layout;
    @BindView(R.id.currency_txt)
    TextView currency_txt;
    @BindView(R.id.no_of_rides_txt)
    CountAnimationTextView no_of_rides_txt;
    @BindView(R.id.revenue_txt)
    CountAnimationTextView revenue_txt;
    @BindView(R.id.schedule_txt)
    CountAnimationTextView schedule_txt;

    @BindView(R.id.cancel_txt)
    CountAnimationTextView cancel_txt;

    int rides = 0, revenue = 0, schedule = 0, cancel = 0;
    Double doubleRevenue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        ButterKnife.bind(this);
        restInterface = ServiceGenerator.createService(RestInterface.class);
        providerSummary();
    }


    private void setDetails() {
        Animation txtAnim = AnimationUtils.loadAnimation(SummaryActivity.this, R.anim.txt_size);
        if (schedule > 0) {
            schedule_txt.setAnimationDuration(500)
                    .countAnimation(0, schedule);
        } else {
            schedule_txt.setText("0");
        }
        if (revenue > 0) {
            revenue_txt.setAnimationDuration(500)
                    .countAnimation(0, revenue);
        } else {
            revenue_txt.setText("0");
        }
        if (rides > 0) {
            no_of_rides_txt.setAnimationDuration(500)
                    .countAnimation(0, rides);

        } else {
            no_of_rides_txt.setText("0");
        }
        if (cancel > 0) {
            cancel_txt.setAnimationDuration(500)
                    .countAnimation(0, cancel);
        } else {
            cancel_txt.setText("0");
        }
        schedule_txt.startAnimation(txtAnim);
        revenue_txt.startAnimation(txtAnim);
        no_of_rides_txt.startAnimation(txtAnim);
        cancel_txt.startAnimation(txtAnim);
        currency_txt.setText(SharedHelper.getKey(SummaryActivity.this, "currency"));
    }

    CustomDialog customDialog;

    private void providerSummary() {
        String auth = "Bearer" + " " + SharedHelper.getKey(App.getContext(), "access_token");
        customDialog = new CustomDialog(SummaryActivity.this);
        customDialog.setCancelable(false);
        customDialog.show();
        Call<SummryRes> call = restInterface.rideSummary(URLHelper.REQUEST_WITH, auth);
        call.enqueue(new Callback<SummryRes>() {
            @Override
            public void onResponse(Call<SummryRes> call, Response<SummryRes> response) {
                customDialog.dismiss();
                if (response.code() == 200) {
                    card_layout.setVisibility(View.VISIBLE);
                    Animation slideUp = AnimationUtils.loadAnimation(SummaryActivity.this, R.anim.slide_up);
                    card_layout.startAnimation(slideUp);
                    if (response.body().getRides() != 0) {
                        rides = response.body().getRides();
                    }
                    if (response.body().getScheduled_rides() != 0) {
                        schedule = response.body().getScheduled_rides();
                    }
                    if (response.body().getCancel_rides() != 0) {
                        cancel = response.body().getCancel_rides();
                    }
                    if (response.body().getRevenue() != null) {
                        doubleRevenue = Double.parseDouble(response.body().getRevenue());
                        revenue = doubleRevenue.intValue();
                    }


                    slideUp.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            setDetails();
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<SummryRes> call, Throwable t) {
                customDialog.dismiss();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
