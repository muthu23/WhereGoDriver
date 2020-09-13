package com.wherego.delivery.driver.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.wherego.delivery.driver.App;
import com.wherego.delivery.driver.DriverMainActivity;
import com.wherego.delivery.driver.R;
import com.wherego.delivery.driver.helpers.CustomDialog;
import com.wherego.delivery.driver.helpers.RestInterface;
import com.wherego.delivery.driver.helpers.ServiceGenerator;
import com.wherego.delivery.driver.helpers.SharedHelper;
import com.wherego.delivery.driver.helpers.URLHelper;
import com.wherego.delivery.driver.model.ongoing.Item;
import com.wherego.delivery.driver.model.ongoing.OnGoingRes;
import com.wherego.delivery.driver.model.ongoing.User;
import com.wherego.delivery.driver.utills.Utils;
import com.squareup.picasso.Picasso;

import java.text.ParseException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpcomingDetailsActivity extends AppCompatActivity {

    @OnClick(R.id.backArrow)
    void backArrow() {
        onBackPressed();
    }

    @BindView(R.id.tripImg)
    ImageView tripImg;
    @BindView(R.id.tripProviderImg)
    CircleImageView tripProviderImg;
    @BindView(R.id.tripProviderName)
    TextView tripProviderName;
    @BindView(R.id.tripProviderRating)
    RatingBar tripProviderRating;
    @BindView(R.id.tripDate)
    TextView tripDate;
    @BindView(R.id.tripSource)
    TextView tripSource;
    @BindView(R.id.tripDestination)
    TextView tripDestination;


    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_qty)
    TextView tv_qty;
    @BindView(R.id.tv_discription)
    TextView tv_discription;
    @BindView(R.id.tv_short_notes)
    TextView tv_short_notes;
    @BindView(R.id.iv_upload)
    ImageView iv_upload;
    @BindView(R.id.tvWidth)
    TextView tvWidth;
    @BindView(R.id.tvHeight)
    TextView tvHeight;

    @BindView(R.id.rv_name)
    TextView rv_name;
    @BindView(R.id.rv_mob)
    TextView rv_mob;
    @BindView(R.id.rv_email)
    TextView rv_email;
    @BindView(R.id.rv_address)
    TextView rv_address;

    @BindView(R.id.trip_id)
    TextView trip_id;


    @BindView(R.id.btnStartRide)
    Button btnStartRide;
    @BindView(R.id.btnCancelRide)
    Button btnCancelRide;

    RestInterface restInterface;
    String requestID;
    CustomDialog customDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_details);
        ButterKnife.bind(this);
        restInterface = ServiceGenerator.createService(RestInterface.class);
        requestID = getIntent().getStringExtra("requestID");
        if (requestID != null) {
            orderDetail(requestID);
        }
    }

    private void orderDetail(String requestID) {
        String auth = "Bearer" + " " + SharedHelper.getKey(App.getContext(), "access_token");
        customDialog = new CustomDialog(this);
        customDialog.setCancelable(false);
        customDialog.show();
        Call<OnGoingRes> call = restInterface.onGoingItemDetail(URLHelper.REQUEST_WITH, auth, requestID);
        call.enqueue(new Callback<OnGoingRes>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<OnGoingRes> call, Response<OnGoingRes> response) {
                customDialog.dismiss();
                if (response.code() == 200){
                    OnGoingRes hd = response.body();

                    if (hd.getStaticMap() != null){
                        Picasso.get().load(hd.getStaticMap()).placeholder(R.drawable.placeholder).into(tripImg);
                    }
                    if (hd.getAssignedAt() != null){
                        try {
                            tripDate.setText(Utils.getDate(hd.getAssignedAt()) + "th "
                                    + Utils.getMonth(hd.getAssignedAt()) + " "
                                    + Utils.getYear(hd.getAssignedAt()) + "\n"
                                    + Utils.getTime(hd.getAssignedAt()));
                        }catch (ParseException e){

                        }
                    }
                    if (hd.getSAddress() != null){
                        tripSource.setText(hd.getSAddress());
                    }
                    if (hd.getDAddress() != null){
                        tripDestination.setText(hd.getDAddress());
                    }
                    if (hd.getBookingId() != null){
                        trip_id.setText(hd.getBookingId());
                    }

                    if (hd.getUser() != null){
                        User u = hd.getUser();
                        if (u.getPicture() != null){
                            Picasso.get().load(URLHelper.IMAGE_BASE + u.getPicture()).into(tripProviderImg);
                        }
                        if (u.getFirstName() != null){
                            tripProviderName.setText(u.getFirstName());
                        }
                        if (u.getRating() != null){
                            tripProviderRating.setRating(Float.parseFloat(u.getRating()));
                        }
                    }

                    if (hd.getItem() != null){
                        Item i = hd.getItem();
                        if (i.getName() != null){
                            tv_name.setText(i.getName());
                        }
                        if (i.getQty() != null){
                            tv_qty.setText(i.getQty());
                        }
                        if (i.getWeight() != null){
                            tv_discription.setText(i.getWeight());
                        }
                        if (i.getDiscription() != null){
                            tv_short_notes.setText(i.getDiscription());
                        }
                        if (i.getItemImage() != null && !i.getItemImage().isEmpty()){
                            if (i.getItemImage().get(0).getImagePath() != null){
                                Picasso.get().load(URLHelper.IMAGE_BASE +
                                        i.getItemImage().get(0).getImagePath()).into(iv_upload);
                            }
                        }
                        if (i.getRecName() != null){
                            rv_name.setText(i.getRecName());
                        }
                        if (i.getRecMobile() != null){
                            rv_mob.setText(i.getRecMobile());
                        }
                        if (i.getRecEmail() != null){
                            rv_email.setText(i.getRecEmail());
                        }
                        if (i.getRecAddress() !=  null){
                            rv_address.setText(i.getRecAddress());
                        }
                        if (i.getHeight() != null){
                            tvHeight.setText(i.getHeight() + " Feet");
                        }
                        if (i.getWidth() != null){
                            tvWidth.setText(i.getWidth() + " Feet");
                        }
                    }
                    if (hd.getId() != null){
                        btnStartRide.setOnClickListener(v -> acceptRequest(String.valueOf(hd.getId())));
                        btnCancelRide.setOnClickListener(v -> cancelTrip(String.valueOf(hd.getId()), ""));
                    }




                }else {
                    display(getString(R.string.something_went_wrong));
                }
            }

            @Override
            public void onFailure(Call<OnGoingRes> call, Throwable t) {
                customDialog.dismiss();
            }
        });
    }

    private void display(String message){
        Toasty.info(App.getContext(), message, Toasty.LENGTH_SHORT, true).show();
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
                    display("Order accepted successfully");
                    Intent i = new Intent(UpcomingDetailsActivity.this,
                            DriverMainActivity.class);
                    i.putExtra("type", "SCHEDULED");
                    startActivity(i);
                } else {
                    display(getString(R.string.please_try_again));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                customDialog.dismiss();
                display(getString(R.string.something_went_wrong));
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
                    display("You have cancelled the order");
                } else {
                    display(getString(R.string.please_try_again));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                customDialog.dismiss();
                display(getString(R.string.something_went_wrong));
            }
        });
    }


}
