package com.wherego.delivery.driver.activities;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wherego.delivery.driver.App;
import com.wherego.delivery.driver.R;
import com.wherego.delivery.driver.adapter.ImageAdapter;
import com.wherego.delivery.driver.helpers.CustomDialog;
import com.wherego.delivery.driver.helpers.RestInterface;
import com.wherego.delivery.driver.helpers.ServiceGenerator;
import com.wherego.delivery.driver.helpers.SharedHelper;
import com.wherego.delivery.driver.helpers.URLHelper;
import com.wherego.delivery.driver.model.Image;
import com.wherego.delivery.driver.model.history.HistoryDetails;
import com.wherego.delivery.driver.model.history.Item;
import com.wherego.delivery.driver.model.history.User;
import com.wherego.delivery.driver.utills.Utils;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryDetailsActivity extends AppCompatActivity {

    ImageAdapter adapter;
    @OnClick(R.id.backArrow)
    void backArrow(){
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
//    @BindView(R.id.iv_upload)
//    ImageView iv_upload;
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
    @BindView(R.id.paymentTypeImg)
    ImageView paymentTypeImg;
    @BindView(R.id.paymentType)
    TextView paymentType;
    @BindView(R.id.tripAmount)
    TextView tripAmount;

    @BindView(R.id.tripComments)
    TextView tripComments;
    @BindView(R.id.imgSign)
    ImageView imgSign;

    RestInterface restInterface;
    String requestID;

    String signature;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_details);
        ButterKnife.bind(this);
        restInterface = ServiceGenerator.createService(RestInterface.class);
        requestID = getIntent().getStringExtra("requestID");
        if (requestID != null){
            orderDetail(requestID);
            RecyclerView recyclerView = findViewById(R.id.rvItem);
            adapter = new ImageAdapter(HistoryDetailsActivity.this);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(HistoryDetailsActivity.this,
                    LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        }
    }

    CustomDialog customDialog;
    private void orderDetail(String requestID){
        String auth = "Bearer" + " " + SharedHelper.getKey(App.getContext(), "access_token");
        customDialog = new CustomDialog(this);
        customDialog.setCancelable(false);
        customDialog.show();

        Call<HistoryDetails> call = restInterface.historyItemDetail(URLHelper.REQUEST_WITH, auth, requestID);
        call.enqueue(new Callback<HistoryDetails>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<HistoryDetails> call, Response<HistoryDetails> response) {
                customDialog.dismiss();
                if (response.code() == 200){
                    HistoryDetails hd = response.body();

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
                    if (hd.getMsignature() !=null)
                    {
                        byte[] decodedString = Base64.decode(hd.getMsignature(), Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        imgSign.setImageBitmap(decodedByte);
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
                    if (hd.getPaymentMode() != null){
                        paymentType.setText(hd.getPaymentMode());
                    }
                    if (hd.getPayment() != null){
                        if (hd.getPayment().getTotal() != null){
                            tripAmount.setText(SharedHelper.getKey(App.getContext(), "currency")
                                    + " " +hd.getPayment().getTotal());
                        }
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
                            tv_discription.setText(i.getWeight() + "Gm");
                        }
                        if (i.getDiscription() != null){
                            tv_short_notes.setText(i.getDiscription());
                        }
                        if (i.getItemImage() != null && !i.getItemImage().isEmpty()){
                            ArrayList<Image> images = new ArrayList<>();

                            for (int k = 0; k < i.getItemImage().size(); k++) {
                                String imagePath = URLHelper.BASE + i.getItemImage().get(k).getImagePath();
                                Image image = new Image(k, "a", imagePath);
                                images.add(image);
                            }
                            adapter.setData(images);

//                            if (i.getItemImage().get(0).getImagePath() != null){
//                                Picasso.get().load(URLHelper.BASE +
//                                        i.getItemImage().get(0).getImagePath()).into(iv_upload);
//                            }
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

                    if (hd.getRating() != null){
                        if (hd.getRating().getUserComment() != null){
                            tripComments.setText(hd.getRating().getUserComment());
                        }
                    }


                }else {
                    display(getString(R.string.something_went_wrong));
                }
            }

            @Override
            public void onFailure(Call<HistoryDetails> call, Throwable t) {
                customDialog.dismiss();
                display(getString(R.string.something_went_wrong));
            }
        });

    }

    private void display(String message){
        Toasty.info(App.getContext(), message, Toasty.LENGTH_SHORT, true).show();
    }

}
