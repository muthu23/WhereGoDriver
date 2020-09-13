package com.wherego.delivery.driver.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.wherego.delivery.driver.App;
import com.wherego.delivery.driver.R;
import com.wherego.delivery.driver.helpers.CustomDialog;
import com.wherego.delivery.driver.helpers.RestInterface;
import com.wherego.delivery.driver.helpers.ServiceGenerator;
import com.wherego.delivery.driver.helpers.SharedHelper;
import com.wherego.delivery.driver.helpers.URLHelper;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profile extends AppCompatActivity {

    RestInterface restInterface;

    @BindView(R.id.img_profile)
    CircleImageView img_profile;
    @BindView(R.id.txtuserName)
    TextView txtuserName;
    @BindView(R.id.img_car)
    ImageView img_car;
    @BindView(R.id.txtVehiclename)
    TextView txtVehiclename;

    @OnClick(R.id.backArrow)
    void backArrow() {
        onBackPressed();
    }

    @OnClick(R.id.btnLogout)
    void btnLogout() {
        showLogoutDialog();
    }

    @OnClick(R.id.txtEarning)
    void txtEarning() {
        startActivity(new Intent(Profile.this, EarningActivity.class));
    }

    @OnClick(R.id.txtDocuments)
    void txtDocuments() {
        startActivity(new Intent(Profile.this, UploadDocumentActivity.class));
    }

    @OnClick(R.id.txtPassword)
    void txtPassword() {
        startActivity(new Intent(Profile.this, ChangePassword.class));
    }

    @OnClick(R.id.txtEdituser)
    void txtEdituser() {
        startActivity(new Intent(Profile.this, EditProfile.class));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        restInterface = ServiceGenerator.createService(RestInterface.class);
        txtuserName.setText(SharedHelper.getKey(Profile.this, "first_name"));
        txtVehiclename.setText(SharedHelper.getKey(Profile.this, "service"));

        if (SharedHelper.getKey(App.getContext(), "picture") != null
                && !SharedHelper.getKey(App.getContext(), "picture").isEmpty()) {
            Picasso.get().load(URLHelper.IMAGE_BASE +
                    SharedHelper.getKey(App.getContext(), "picture"))
                    .error(R.drawable.ic_dummy_user)
                    .into(img_profile);
        }

        if (!SharedHelper.getKey(Profile.this, "service_image").isEmpty()) {
            Picasso.get().load(URLHelper.IMAGE_BASE +
                    SharedHelper.getKey(Profile.this, "service_image"))
                    .error(R.drawable.dummy_parcel)
                    .into(img_car);
        }
    }


    private void showLogoutDialog() {
        if (!isFinishing()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getString(R.string.logout));
            builder.setMessage(getString(R.string.exit_confirm));
            builder.setPositiveButton(R.string.yes,
                    (dialog, which) -> logoutUser());
            builder.setNegativeButton(R.string.no, (dialog, which) -> dialog.dismiss());
            builder.setCancelable(false);
            final AlertDialog dialog = builder.create();
            dialog.setOnShowListener(arg -> {
                dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                        .setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                        .setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));
            });
            dialog.show();
        }
    }


    CustomDialog customDialog;

    private void logoutUser() {
        FirebaseAuth.getInstance().signOut();
        String auth = "Bearer" + " " + SharedHelper.getKey(App.getContext(), "access_token");
        customDialog = new CustomDialog(this);
        customDialog.setCancelable(false);
        customDialog.show();
        Call<ResponseBody> call = restInterface.logout(URLHelper.REQUEST_WITH, auth);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                customDialog.dismiss();
                if (response.code() == 200) {
                    SharedHelper.clearSharedPreferences(Profile.this);
                    Intent goToLogin = new Intent(Profile.this, LoginActivity.class);
                    goToLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(goToLogin);
                    finish();
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

    public void displayMessage(String toastString) {
        Toasty.info(App.getContext(), toastString, Toasty.LENGTH_SHORT, true).show();
    }


}

