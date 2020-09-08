package com.wherego.driver.activities;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hbb20.CountryCodePicker;
import com.wherego.driver.App;
import com.wherego.driver.DriverMainActivity;
import com.wherego.driver.R;
import com.wherego.driver.adapter.ServiceTypeAdapter;
import com.wherego.driver.helpers.CustomDialog;
import com.wherego.driver.helpers.RestInterface;
import com.wherego.driver.helpers.ServiceGenerator;
import com.wherego.driver.helpers.SharedHelper;
import com.wherego.driver.helpers.URLHelper;
import com.wherego.driver.model.login.LoginRequest;
import com.wherego.driver.model.login.LoginResponse;
import com.wherego.driver.model.profile.ProfileResponse;
import com.wherego.driver.model.servicetype.ServiceType;
import com.wherego.driver.model.servicetype.SignUpRequest;
import com.wherego.driver.model.servicetype.SignUpRes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterActivity extends AppCompatActivity {

    private RestInterface restInterface;
    private ArrayList<ServiceType> serviceTypes = new ArrayList<>();
    private ServiceTypeAdapter adapter;
    private SignUpRequest request;
    final int APP_REQUEST_CODE = 99;
    private String device_UDID, device_token;

    @BindView(R.id.tvName)
    EditText tvName;
    @BindView(R.id.tvEmail)

    EditText tvEmail;
    @BindView(R.id.tvPassword)
    EditText tvPassword;
    @BindView(R.id.ccp)
    CountryCodePicker ccp;
    @BindView(R.id.tvMobile)
    EditText tvMobile;
    @BindView(R.id.tvVehicleNo)
    EditText tvVehicleNo;
    @BindView(R.id.tvNoPlate)
    EditText tvNoPlate;
    @BindView(R.id.rvServiceType)
    RecyclerView rvServiceType;

    @OnClick(R.id.tvLogin)
    void tvLogin() {

    }

    @OnClick(R.id.tvSignUp)
    void tvSignUp() {
        String[] add = adapter.CheckData().toArray(new String[0]);
        String addOn = Arrays.toString(add);
        String addOnId = addOn.substring(1, addOn.length() - 1).replace(" ", "");

        if (tvName.getText().toString().isEmpty()) {
            displayMessage(getString(R.string.first_name_empty));
        } else if (tvEmail.getText().toString().isEmpty()) {
            displayMessage(getString(R.string.email_validation));
        } else if (tvPassword.getText().toString().equals("") ||
                tvPassword.getText().toString().equalsIgnoreCase(getString(R.string.password_txt))) {
            displayMessage(getString(R.string.password_validation));
        } else if (tvPassword.length() < 6) {
            displayMessage(getString(R.string.password_size));
        } else if (tvMobile.getText().toString().isEmpty()) {
            displayMessage(getString(R.string.mobile_number_validation));
        } else if (tvMobile.length() < 10) {
            displayMessage(getString(R.string.mobile_number_validation));
        } else if (tvVehicleNo.getText().toString().isEmpty()) {
            displayMessage(getString(R.string.vehicle_model));
        } else if (tvNoPlate.getText().toString().isEmpty()) {
            displayMessage(getString(R.string.vehicle_plate_number));
        } else if (addOnId.equals("") || addOnId.isEmpty()) {
            displayMessage(getString(R.string.service_type));
        } else if (addOnId == "[]") {
            displayMessage(getString(R.string.service_type));
        } else {
            {
                String phone = ccp.getSelectedCountryCodeWithPlus() + tvMobile.getText().toString();
                request.setDeviceId(device_UDID);
                request.setDeviceToken(device_token);
                request.setDeviceType("android");
                request.setEmail(tvEmail.getText().toString());
                request.setFirstName(tvName.getText().toString());
                request.setMobile(phone);
                request.setPassword(tvPassword.getText().toString());
                request.setServiceModel(tvVehicleNo.getText().toString());
                request.setServiceNumber(tvNoPlate.getText().toString());
                request.setServiceTypeIds(addOnId);

                SharedHelper.putKey(getApplicationContext(), "mobile", phone);
                Intent intent = new Intent(RegisterActivity.this, OtpVerification.class);
                intent.putExtra("phonenumber", phone);
                startActivityForResult(intent, APP_REQUEST_CODE);
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        GetToken();
        restInterface = ServiceGenerator.createService(RestInterface.class);
        serviceRecycler();
        getServiceType();
        request = new SignUpRequest();
    }


    @Override
    protected void onActivityResult(
            final int requestCode,
            final int resultCode,
            final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == APP_REQUEST_CODE) {
            if (SharedHelper.getKey(this, "isotpVerified").equalsIgnoreCase("true"))
                signUp();
            else displayMessage("OTP verification not successful");

        }
    }

    @SuppressLint("HardwareIds")
    public void GetToken() {
        try {
            if (!SharedHelper.getKey(this, "device_token").equals("")
                    && SharedHelper.getKey(this, "device_token") != null) {
                device_token = SharedHelper.getKey(this, "device_token");
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

    public void GoToMainActivity() {
        Intent mainIntent = new Intent(RegisterActivity.this, DriverMainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mainIntent);
        RegisterActivity.this.finish();
    }

    public void displayMessage(String toastString) {
        Toasty.info(App.getContext(), toastString, Toasty.LENGTH_SHORT, true).show();
    }

    public void GoToBeginActivity() {
        SharedHelper.putKey(this, "loggedIn", getString(R.string.False));
        Intent mainIntent = new Intent(this, LoginActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mainIntent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent mainIntent = new Intent(RegisterActivity.this, LoginActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mainIntent);
        RegisterActivity.this.finish();
    }

    private void getServiceType() {
        CustomDialog customDialog = new CustomDialog(this);
        customDialog.setCancelable(false);
        customDialog.show();
        Call<List<ServiceType>> call = restInterface.driverServiceType(URLHelper.REQUEST_WITH);
        call.enqueue(new Callback<List<ServiceType>>() {
            @Override
            public void onResponse(Call<List<ServiceType>> call, Response<List<ServiceType>> response) {
                serviceTypes.clear();
                customDialog.dismiss();
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (!response.body().isEmpty()) {
                        serviceTypes.addAll(response.body());
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ServiceType>> call, Throwable t) {
                customDialog.dismiss();
            }
        });
    }

    private void serviceRecycler() {
        @SuppressLint("WrongConstant")
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        if (adapter == null) {
            adapter = new ServiceTypeAdapter(serviceTypes, RegisterActivity.this);
            rvServiceType.setLayoutManager(layoutManager);
            rvServiceType.setAdapter(adapter);
            rvServiceType.setHasFixedSize(true);
            rvServiceType.setItemAnimator(new DefaultItemAnimator());
            rvServiceType.setNestedScrollingEnabled(true);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    private void signUp() {
        CustomDialog customDialog = new CustomDialog(this);
        customDialog.setCancelable(false);
        customDialog.show();
        Call<SignUpRes> call = restInterface.signUp(URLHelper.REQUEST_WITH, request);
        call.enqueue(new Callback<SignUpRes>() {
            @Override
            public void onResponse(Call<SignUpRes> call, Response<SignUpRes> response) {
                customDialog.dismiss();
                if (response.code() == 200) {
                    LoginRequest loginRequest = new LoginRequest();
                    loginRequest.setDeviceId(device_UDID);
                    loginRequest.setDeviceToken(device_token);
                    loginRequest.setDeviceType("android");
                    assert response.body() != null;
                    loginRequest.setEmail(response.body().getEmail());
                    loginRequest.setPassword(request.getPassword());
                    login(loginRequest);
                } else {
                    displayMessage(getString(R.string.please_try_again));
                    GoToBeginActivity();
                }
            }

            @Override
            public void onFailure(Call<SignUpRes> call, Throwable t) {
                customDialog.dismiss();
                displayMessage(getString(R.string.please_try_again));
                GoToBeginActivity();
            }
        });
    }

    private void login(LoginRequest loginRequest) {
        CustomDialog customDialog = new CustomDialog(this);
        customDialog.setCancelable(false);
        customDialog.show();
        Call<LoginResponse> call = restInterface.login(URLHelper.REQUEST_WITH, loginRequest);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                customDialog.dismiss();
                if (response.code() == 200) {
                    if (response.body() != null) {
                        LoginResponse rs = response.body();
                        if (rs.getAccessToken() != null) {
                            SharedHelper.putKey(App.getContext(),
                                    "access_token", rs.getAccessToken() + "");
                        }
                        if (rs.getCurrency() != null) {
                            SharedHelper.putKey(App.getContext(), "currency",
                                    rs.getCurrency() + "");
                        }
                        getProfile();
                    }
                } else {
                    displayMessage(getString(R.string.something_went_wrong));
                    GoToBeginActivity();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                customDialog.dismiss();
                displayMessage(getString(R.string.something_went_wrong));
                GoToBeginActivity();
            }
        });
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
                customDialog.dismiss();
                if (response.code() == 200) {
                    ProfileResponse pr = response.body();
                    assert pr != null;
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
                            Intent intent = new Intent(RegisterActivity.this, WaitingForApproval.class);
                            startActivity(intent);
                        } else {
                            GoToMainActivity();
                        }
                    }
                } else {
                    displayMessage(getString(R.string.something_went_wrong));
                    GoToBeginActivity();
                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                customDialog.dismiss();
                displayMessage(getString(R.string.something_went_wrong));
                GoToBeginActivity();
            }
        });
    }


}
