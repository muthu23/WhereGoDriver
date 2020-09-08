package com.wherego.driver.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.wherego.driver.App;
import com.wherego.driver.DriverMainActivity;
import com.wherego.driver.R;
import com.wherego.driver.helpers.ConnectionHelper;
import com.wherego.driver.helpers.CustomDialog;
import com.wherego.driver.helpers.RestInterface;
import com.wherego.driver.helpers.ServiceGenerator;
import com.wherego.driver.helpers.SharedHelper;
import com.wherego.driver.helpers.URLHelper;
import com.wherego.driver.model.profile.ProfileResponse;
import com.wherego.driver.services.GPSUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreen extends AppCompatActivity {

    private RestInterface restInterface;
    ConnectionHelper helper;
    Boolean isInternet;
    Handler handleCheckStatus;
    AlertDialog alert;
    String device_token, device_UDID;

    private boolean isGPS = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        GetToken();
        helper = new ConnectionHelper(this);
        isInternet = helper.isConnectingToInternet();
        restInterface = ServiceGenerator.createService(RestInterface.class);
        handleCheckStatus = new Handler();

        new GPSUtils(this).turnGPSOn(isGPSEnable -> isGPS = isGPSEnable);


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isGPS){
            handleCheckStatus.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (helper.isConnectingToInternet()) {
                        if (SharedHelper.getKey(App.getContext(), "loggedIn").equalsIgnoreCase(getString(R.string.True))) {
                            getProfile();
                        } else {
                            GoToBeginActivity();
                        }
                        if (alert != null && alert.isShowing()) {
                            alert.dismiss();
                        }
                    } else {
                        showDialog();
                        handleCheckStatus.postDelayed(this, 3000);
                    }
                }
            }, 5000);
        }
    }

    private void getProfile() {
        String auth = "Bearer" + " " + SharedHelper.getKey(App.getContext(), "access_token");
        CustomDialog customDialog = new CustomDialog(this);
        customDialog.setCancelable(false);
        customDialog.show();
        Call<ProfileResponse> call = restInterface.driverProfile(URLHelper.REQUEST_WITH, auth,
                "android", device_UDID, device_token);
        call.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {

                if ((customDialog != null) && (customDialog.isShowing()))
                    customDialog.dismiss();
                if (response.code() == 200) {
                    ProfileResponse pr = response.body();
                    if (pr.getId() != null) {
                        SharedHelper.putKey(App.getContext(), "id", String.valueOf(pr.getId()));
                    }
                    if (pr.getFirstName() != null) {
                        SharedHelper.putKey(App.getContext(), "first_name", pr.getFirstName());
                    }
                    if (pr.getEmail() != null) {
                        SharedHelper.putKey(App.getContext(), "email", pr.getEmail());
                    }
                    if (pr.getSos() != null) {
                        SharedHelper.putKey(App.getContext(), "sos", pr.getSos());
                    }
                    if (pr.getAvatar() != null) {
                        SharedHelper.putKey(App.getContext(), "picture", pr.getAvatar());
                    }
                    if (pr.getMobile() != null) {
                        SharedHelper.putKey(App.getContext(), "mobile", pr.getMobile());
                    }
                    if (pr.getAvatar() != null) {
                        SharedHelper.putKey(App.getContext(), "picture", pr.getAvatar());
                    }

                    if (pr.getService() != null) {
                        if (pr.getService().getServiceType() != null) {
                            if (pr.getService().getServiceType().getName() != null) {
                                SharedHelper.putKey(App.getContext(), "service",
                                        pr.getService().getServiceType().getName());
                            }
                            if (pr.getService().getServiceType().getImage() != null) {
                                SharedHelper.putKey(App.getContext(), "service_image",
                                        pr.getService().getServiceType().getImage());
                            }


                        }
                    }

                    SharedHelper.putKey(App.getContext(), "loggedIn", getString(R.string.True));
                    if (pr.getStatus() != null) {
                        SharedHelper.putKey(App.getContext(), "approval_status", pr.getStatus());
                        if (pr.getStatus().equalsIgnoreCase("new")) {
                            Intent intent = new Intent(SplashScreen.this, WaitingForApproval.class);
                            startActivity(intent);
                        } else {
                            GoToMainActivity();
                        }
                    }
                }else {
                    GoToBeginActivity();
                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {

                if ((customDialog != null) && (customDialog.isShowing()))
                    customDialog.dismiss();
                GoToBeginActivity();
            }
        });
    }

    public void GoToMainActivity() {
        Intent mainIntent = new Intent(SplashScreen.this, DriverMainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mainIntent);
        finish();
    }

    public void GoToBeginActivity() {
        SharedHelper.putKey(SplashScreen.this, "loggedIn", getString(R.string.False));
        Intent mainIntent = new Intent(SplashScreen.this, LoginActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mainIntent);
        finish();
    }


    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.connect_to_network))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.connect_to_wifi), (dialog, id) -> {
                    alert.dismiss();
                    startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                })
                .setNegativeButton(getString(R.string.quit), (dialog, id) -> {
                    alert.dismiss();
                    finish();
                });
        if (alert == null) {
            alert = builder.create();
            alert.show();
        }
    }


    @SuppressLint("HardwareIds")
    public void GetToken() {
        try {
            if (!SharedHelper.getKey(App.getContext(), "device_token").equals("") &&
                    SharedHelper.getKey(App.getContext(), "device_token") != null) {
                device_token = SharedHelper.getKey(App.getContext(), "device_token");
            } else {
                device_token = "COULD NOT GET FCM TOKEN";
            }
        } catch (Exception e) {
            device_token = "COULD NOT GET FCM TOKEN";
        }

        try {
            device_UDID = android.provider.Settings.Secure.getString(getContentResolver(),
                    android.provider.Settings.Secure.ANDROID_ID);
        } catch (Exception e) {
            device_UDID = "COULD NOT GET UDID";
            e.printStackTrace();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == URLHelper.GPS_REQUEST) {
                isGPS = true;
            }
        }
    }

}
