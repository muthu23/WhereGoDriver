package com.wherego.delivery.driver.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.wherego.delivery.driver.App;
import com.wherego.delivery.driver.DriverMainActivity;
import com.wherego.delivery.driver.R;
import com.wherego.delivery.driver.helpers.ConnectionHelper;
import com.wherego.delivery.driver.helpers.CustomDialog;
import com.wherego.delivery.driver.helpers.RestInterface;
import com.wherego.delivery.driver.helpers.ServiceGenerator;
import com.wherego.delivery.driver.helpers.SharedHelper;
import com.wherego.delivery.driver.helpers.URLHelper;
import com.wherego.delivery.driver.model.login.LoginRequest;
import com.wherego.delivery.driver.model.login.LoginResponse;
import com.wherego.delivery.driver.model.profile.ProfileResponse;
import com.wherego.delivery.driver.utills.Utilities;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by jayakumar on 31/01/17.
 */

public class LoginActivity extends AppCompatActivity {

    private RestInterface restInterface;

    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.etPassword)
    EditText etPassword;

    @OnClick(R.id.txtSignUp)
    void txtSignUp() {
        Intent mainIntent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(mainIntent);
    }

    @OnClick(R.id.txtForget)
    void txtForget() {
        Intent mainIntent = new Intent(LoginActivity.this, ForgetPassword.class);
        startActivity(mainIntent);
    }

    @OnClick(R.id.btnLogin)
    void btnLogin() {
        if (etEmail.getText().toString().equals("")) {
            displayMessage(getString(R.string.email_validation));
        } else if (etPassword.getText().toString().equals("") ||
                etPassword.getText().toString().equalsIgnoreCase(getString(R.string.password_txt))) {
            displayMessage(getString(R.string.password_validation));
        } else if (etPassword.length() < 6) {
            displayMessage(getString(R.string.password_size));
        } else {
            SharedHelper.putKey(App.getContext(), "email", etEmail.getText().toString());
            SharedHelper.putKey(App.getContext(), "password", etPassword.getText().toString());
            LoginRequest request = new LoginRequest();
            request.setDeviceId(device_UDID);
            request.setDeviceToken(device_token);
            request.setDeviceType("android");
            request.setEmail(etEmail.getText().toString());
            request.setPassword(etPassword.getText().toString());
            login(request);
        }
    }


    String TAG = "LoginActivity";
    ConnectionHelper helper;
    Boolean isInternet;

    CustomDialog customDialog;
    String device_token, device_UDID;

    Utilities utils = new Utilities();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        helper = new ConnectionHelper(this);
        isInternet = helper.isConnectingToInternet();
        restInterface = ServiceGenerator.createService(RestInterface.class);
        ButterKnife.bind(this);
        GetToken();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

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
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                customDialog.dismiss();
            }
        });
    }


//    private void signIn() {
//        if (isInternet) {
//            customDialog = new CustomDialog(this);
//            customDialog.setCancelable(false);
//            customDialog.show();
//            JSONObject object = new JSONObject();
//            try {
//                object.put("device_type", "android");
//                object.put("device_id", device_UDID);
//                object.put("device_token", device_token);
//                object.put("player_id", player_id + "");
//                object.put("email", SharedHelper.getKey(App.getContext(), "email"));
//                object.put("password", SharedHelper.getKey(App.getContext(), "password"));
//                utils.print("InputToLoginAPI", "" + object);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            Log.e("login", URLHelper.login + "login");
//            Log.e("object", object + "login");
//
//            JsonObjectRequest jsonObjectRequest = new
//                    JsonObjectRequest(RequestList.Method.POST,
//                            URLHelper.login,
//                            object,
//                            response -> {
//                                customDialog.dismiss();
//                                utils.print("SignUpResponse", response.toString());
//                                SharedHelper.putKey(App.getContext(), "access_token",
//                                        response.optString("access_token"));
//                                if (!response.optString("currency").equalsIgnoreCase("")
//                                        && response.optString("currency") != null)
//                                    SharedHelper.putKey(App.getContext(), "currency",
//                                            response.optString("currency"));
//                                else
//                                    SharedHelper.putKey(App.getContext(), "currency", "$");
//                                getProfile();
//                            }, error -> {
//                        Log.e("errror", error.toString() + "error");
//                        customDialog.dismiss();
//                        String json = null;
//                        String Message;
//                        NetworkResponse response = error.networkResponse;
//                        utils.print("MyTest", "" + error);
//                        utils.print("MyTestError", "" + error.networkResponse);
//
//                        if (response != null && response.data != null) {
//                            utils.print("MyTestError1", "" + response.statusCode);
//                            Log.e("statuscode", response.statusCode + "");
//                            try {
//                                JSONObject errorObj = new JSONObject(new String(response.data));
//                                if (response.statusCode == 400 || response.statusCode == 405 ||
//                                        response.statusCode == 500) {
//                                    displayMessage(getString(R.string.something_went_wrong));
//                                } else if (response.statusCode == 401) {
//                                    displayMessage(getString(R.string.invalid_credentials));
//                                } else if (response.statusCode == 422) {
//                                    json = App.trimMessage(new String(response.data));
//                                    if (json != "" && json != null) {
//                                        displayMessage(json);
//                                    } else {
//                                        displayMessage(getString(R.string.please_try_again));
//                                    }
//
//                                } else {
//                                    displayMessage(getString(R.string.please_try_again));
//                                }
//
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                                displayMessage(getString(R.string.something_went_wrong));
//                            }
//
//                        } else {
//                            if (error instanceof NoConnectionError) {
//                                displayMessage(getString(R.string.oops_connect_your_internet));
//                            } else if (error instanceof NetworkError) {
//                                displayMessage(getString(R.string.oops_connect_your_internet));
//                            } else if (error instanceof TimeoutError) {
//                                signIn();
//                            }
//                        }
//
//                    }) {
//                        @Override
//                        public Map<String, String> getHeaders() throws AuthFailureError {
//                            HashMap<String, String> headers = new HashMap<String, String>();
//                            headers.put("X-Requested-With", "XMLHttpRequest");
//                            return headers;
//                        }
//                    };
//
//            App.getInstance().addToRequestQueue(jsonObjectRequest);
//
//        } else {
//            displayMessage(getString(R.string.something_went_wrong_net));
//        }
//
//    }

//    public void getProfile() {
//
//        if (isInternet) {
//
//            customDialog = new CustomDialog(this);
//            customDialog.setCancelable(false);
//            customDialog.show();
//            JSONObject object = new JSONObject();
//            JsonObjectRequest jsonObjectRequest = new
//                    JsonObjectRequest(RequestList.Method.GET,
//                            URLHelper.USER_PROFILE_API,
//                            object,
//                            response -> {
//                                customDialog.dismiss();
//                                utils.print("GetProfile", response.toString());
//                                SharedHelper.putKey(App.getContext(), "refer_code", response.optString("refer_code"));
//                                SharedHelper.putKey(App.getContext(), "id", response.optString("id"));
//                                SharedHelper.putKey(App.getContext(), "first_name", response.optString("first_name"));
//                                SharedHelper.putKey(App.getContext(), "last_name", response.optString("last_name"));
//                                SharedHelper.putKey(App.getContext(), "email", response.optString("email"));
//                                SharedHelper.putKey(App.getContext(), "gender", "" + response.optString("gender"));
//                                SharedHelper.putKey(App.getContext(), "mobile", response.optString("mobile"));
//                                SharedHelper.putKey(App.getContext(), "approval_status", response.optString("status"));
//                                SharedHelper.putKey(App.getContext(), "loggedIn", getString(R.string.True));
//                                if (response.optString("avatar").startsWith("http"))
//                                    SharedHelper.putKey(App.getContext(), "picture", response.optString("avatar"));
//                                else
//                                    SharedHelper.putKey(App.getContext(), "picture", URLHelper.BASE + "storage/app/public/" + response.optString("avatar"));
//
//                                SharedHelper.getKey(App.getContext(), "picture");
//
//                                if (response.optJSONObject("service") != null) {
//                                    try {
//                                        JSONObject service = response.optJSONObject("service");
//                                        if (service.optJSONObject("service_type") != null) {
//                                            JSONObject serviceType = service.optJSONObject("service_type");
//                                            SharedHelper.putKey(App.getContext(), "service", serviceType.optString("name"));
//                                            SharedHelper.putKey(App.getContext(), "service_image", serviceType.optString("image"));
//
//                                        }
//                                    } catch (Exception e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//                                SharedHelper.putKey(App.getContext(), "sos", response.optString("sos"));
//
//                                GoToMainActivity();
//
//                            }, error -> {
//                        customDialog.dismiss();
//                        String json = null;
//                        String Message;
//                        NetworkResponse response = error.networkResponse;
//                        if (response != null && response.data != null) {
//                            try {
//                                JSONObject errorObj = new JSONObject(new String(response.data));
//                                if (response.statusCode == 400 || response.statusCode == 405 ||
//                                        response.statusCode == 500) {
//                                    displayMessage(getString(R.string.something_went_wrong));
//                                } else if (response.statusCode == 401) {
//                                    SharedHelper.putKey(App.getContext(), "loggedIn", getString(R.string.False));
//                                } else if (response.statusCode == 422) {
//                                    json = App.trimMessage(new String(response.data));
//                                    if (json != "" && json != null) {
//                                        displayMessage(json);
//                                    } else {
//                                        displayMessage(getString(R.string.please_try_again));
//                                    }
//
//                                } else if (response.statusCode == 503) {
//                                    displayMessage(getString(R.string.server_down));
//                                } else {
//                                    displayMessage(getString(R.string.please_try_again));
//                                }
//
//                            } catch (Exception e) {
//                                displayMessage(getString(R.string.something_went_wrong));
//                            }
//
//                        } else {
//                            if (error instanceof NoConnectionError) {
//                                displayMessage(getString(R.string.oops_connect_your_internet));
//                            } else if (error instanceof NetworkError) {
//                                displayMessage(getString(R.string.oops_connect_your_internet));
//                            } else if (error instanceof TimeoutError) {
//                                getProfile();
//                            }
//                        }
//                    }) {
//                        @Override
//                        public Map<String, String> getHeaders() throws AuthFailureError {
//                            HashMap<String, String> headers = new HashMap<String, String>();
//                            headers.put("X-Requested-With", "XMLHttpRequest");
//                            headers.put("Authorization", "Bearer " + SharedHelper.getKey(App.getContext(), "access_token"));
//                            return headers;
//                        }
//                    };
//
//            App.getInstance().addToRequestQueue(jsonObjectRequest);
//        } else {
//            displayMessage(getString(R.string.something_went_wrong_net));
//        }
//
//    }


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
                            Intent intent = new Intent(LoginActivity.this, WaitingForApproval.class);
                            startActivity(intent);
                        } else {
                            GoToMainActivity();
                        }
                    }
                }else {
                    displayMessage(getString(R.string.please_try_again));
                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                customDialog.dismiss();
                displayMessage(getString(R.string.something_went_wrong));
            }
        });
    }

    public void displayMessage(String toastString) {
        utils.print("displayMessage", "" + toastString);
        Snackbar.make(getCurrentFocus(), toastString, Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show();
    }

    public void GoToMainActivity() {
        Intent mainIntent = new Intent(this, DriverMainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(mainIntent);
        finish();
    }

    @SuppressLint("HardwareIds")
    public void GetToken() {
        try {
            if (!SharedHelper.getKey(App.getContext(), "device_token").equals("")
                    && SharedHelper.getKey(App.getContext(), "device_token") != null) {
                device_token = SharedHelper.getKey(App.getContext(), "device_token");
                utils.print(TAG, "GCM Registration Token: " + device_token);
            } else {
                device_token = "COULD NOT GET FCM TOKEN";
                utils.print(TAG, "Failed to complete token refresh: " + device_token);
            }
        } catch (Exception e) {
            device_token = "COULD NOT GET FCM TOKEN";
            utils.print(TAG, "Failed to complete token refresh");
        }

        try {
            device_UDID = android.provider.Settings.Secure.getString(getContentResolver(),
                    android.provider.Settings.Secure.ANDROID_ID);
            utils.print(TAG, "Device UDID:" + device_UDID);
        } catch (Exception e) {
            device_UDID = "COULD NOT GET UDID";
            e.printStackTrace();
            utils.print(TAG, "Failed to complete device UDID");
        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
