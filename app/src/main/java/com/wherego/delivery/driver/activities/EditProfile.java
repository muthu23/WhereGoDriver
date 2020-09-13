package com.wherego.delivery.driver.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.wherego.delivery.driver.App;
import com.wherego.delivery.driver.R;
import com.wherego.delivery.driver.helpers.CustomDialog;
import com.wherego.delivery.driver.helpers.RestInterface;
import com.wherego.delivery.driver.helpers.ServiceGenerator;
import com.wherego.delivery.driver.helpers.SharedHelper;
import com.wherego.delivery.driver.helpers.URLHelper;
import com.wherego.delivery.driver.model.profile.ProfileResponse;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class EditProfile extends AppCompatActivity {

    RestInterface restInterface;

    @OnClick(R.id.backArrow)
    void backArrow() {
        onBackPressed();
    }

    @BindView(R.id.img_profile)
    CircleImageView img_profile;
    @BindView(R.id.first_name)
    TextView first_name;
    @BindView(R.id.mobile_no)
    TextView mobile_no;
    @BindView(R.id.email)
    TextView email;
    @BindView(R.id.service)
    TextView service;

    @BindView(R.id.txtHeaderName)
    TextView txtHeaderName;
    @BindView(R.id.txtHeaderMob)
    TextView txtHeaderMob;

    @OnClick(R.id.saveBTN)
    void saveBTN() {
        Pattern ps = Pattern.compile(".*[0-9].*");
        Matcher firstName = ps.matcher(first_name.getText().toString());
        if (email.getText().toString().equals("") || email.getText().toString().length() == 0) {
            displayMessage(getString(R.string.email_validation));
        } else if (mobile_no.getText().toString().equals("") || mobile_no.getText().toString().length() == 0) {
            displayMessage(getString(R.string.mobile_number_empty));
        } else if (mobile_no.getText().toString().length() < 10 || mobile_no.getText().toString().length() > 20) {
            displayMessage(getString(R.string.mobile_number_validation));
        } else if (first_name.getText().toString().equals("") || first_name.getText().toString().length() == 0) {
            displayMessage(getString(R.string.first_name_empty));
        } else if (firstName.matches()) {
            displayMessage(getString(R.string.first_name_no_number));
        } else {
            updateP();
        }
    }

    @OnClick(R.id.changePasswordTxt)
    void changePasswordTxt() {
        startActivity(new Intent(EditProfile.this, ChangePassword.class));
    }

    @OnClick(R.id.img_profile)
    void img_profile() {
        ImagePicker.Companion.with(EditProfile.this)
                .crop()
                .compress(1024)
                .maxResultSize(612, 816)
                .start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ButterKnife.bind(this);
        restInterface = ServiceGenerator.createService(RestInterface.class);
        getProfile();
    }

    private void getProfile() {
        String auth = "Bearer" + " " + SharedHelper.getKey(App.getContext(), "access_token");
        String token = SharedHelper.getKey(App.getContext(), "device_token");
        String device_UDID = android.provider.Settings.Secure.getString(getContentResolver(),
                android.provider.Settings.Secure.ANDROID_ID);
        CustomDialog customDialog = new CustomDialog(this);
        customDialog.setCancelable(false);
        customDialog.show();
        Call<ProfileResponse> call = restInterface.driverProfile(URLHelper.REQUEST_WITH, auth,
                "android", device_UDID, token);
        call.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, retrofit2.Response<ProfileResponse> response) {
                customDialog.dismiss();
                if (response.code() == 200) {
                    ProfileResponse pr = response.body();
                    if (pr.getId() != null) {
                        SharedHelper.putKey(App.getContext(), "id", String.valueOf(pr.getId()));
                    }
                    if (pr.getFirstName() != null) {
                        SharedHelper.putKey(App.getContext(), "first_name", pr.getFirstName());
                        first_name.setText(pr.getFirstName());
                        txtHeaderName.setText(pr.getFirstName());
                    }
                    if (pr.getEmail() != null) {
                        SharedHelper.putKey(App.getContext(), "email", pr.getEmail());
                        email.setText(pr.getEmail());
                    }
                    if (pr.getSos() != null) {
                        SharedHelper.putKey(App.getContext(), "sos", pr.getSos());
                    }
                    if (pr.getAvatar() != null) {
                        SharedHelper.putKey(App.getContext(), "picture", pr.getAvatar());
                        Picasso.get().load(URLHelper.IMAGE_BASE + pr.getAvatar()).into(img_profile);
                    }
                    if (pr.getMobile() != null) {
                        SharedHelper.putKey(App.getContext(), "mobile", pr.getMobile());
                        mobile_no.setText(pr.getMobile());
                        txtHeaderMob.setText(pr.getMobile());
                    }
                    if (pr.getAvatar() != null) {
                        SharedHelper.putKey(App.getContext(), "picture", pr.getAvatar());
                    }

                    if (pr.getService() != null) {
                        if (pr.getService().getServiceType() != null) {
                            if (pr.getService().getServiceType().getName() != null) {
                                SharedHelper.putKey(App.getContext(), "service",
                                        pr.getService().getServiceType().getName());
                                service.setText(pr.getService().getServiceType().getName());
                            }
                            if (pr.getService().getServiceType().getImage() != null) {
                                SharedHelper.putKey(App.getContext(), "service_image",
                                        pr.getService().getServiceType().getImage());
                            }


                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                customDialog.dismiss();
            }
        });
    }


    String imageType = "";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (data != null) {
                imageType = data.getData().getPath();
                File file = new File(data.getData().getPath());
                Picasso.get().load(file).into(img_profile);

                updateP();
            }
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            displayMessage(ImagePicker.Companion.getError(data));
        } else {
            displayMessage("Task Cancelled");
        }
    }

    public void updateP() {
        if (!imageType.isEmpty()) {
            updateProfileImage(imageType);
        } else {
            updateProfile();
        }
    }

    private void updateProfileImage(String imagePath) {
        String auth = "Bearer" + " " + SharedHelper.getKey(App.getContext(), "access_token");
        CustomDialog customDialog = new CustomDialog(this);
        customDialog.setCancelable(false);
        customDialog.show();

        MultipartBody.Part image = prepareImage("avatar", imagePath);
        Map<String, RequestBody> fields = new HashMap<>();
        fields.put("first_name", prepareText(first_name.getText().toString()));
        fields.put("email", prepareText(email.getText().toString()));
        fields.put("mobile", prepareText(mobile_no.getText().toString()));

        Call<ProfileResponse> call = restInterface.updateProfileImage(URLHelper.REQUEST_WITH, auth,
                fields, image);
        call.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, retrofit2.Response<ProfileResponse> response) {
                customDialog.dismiss();
                if (response.code() == 200) {
                    ProfileResponse pr = response.body();
                    if (pr.getId() != null) {
                        SharedHelper.putKey(App.getContext(), "id", String.valueOf(pr.getId()));
                    }
                    if (pr.getFirstName() != null) {
                        SharedHelper.putKey(App.getContext(), "first_name", pr.getFirstName());
                        first_name.setText(pr.getFirstName());
                    }
                    if (pr.getEmail() != null) {
                        SharedHelper.putKey(App.getContext(), "email", pr.getEmail());
                        email.setText(pr.getEmail());
                    }
                    if (pr.getSos() != null) {
                        SharedHelper.putKey(App.getContext(), "sos", pr.getSos());
                    }
                    if (pr.getAvatar() != null) {
                        SharedHelper.putKey(App.getContext(), "picture", pr.getAvatar());
                        Picasso.get().load(URLHelper.IMAGE_BASE + pr.getAvatar()).into(img_profile);
                    }
                    if (pr.getMobile() != null) {
                        SharedHelper.putKey(App.getContext(), "mobile", pr.getMobile());
                        mobile_no.setText(pr.getMobile());
                    }
                    if (pr.getAvatar() != null) {
                        SharedHelper.putKey(App.getContext(), "picture", pr.getAvatar());
                    }

                    if (pr.getService() != null) {
                        if (pr.getService().getServiceType() != null) {
                            if (pr.getService().getServiceType().getName() != null) {
                                SharedHelper.putKey(App.getContext(), "service",
                                        pr.getService().getServiceType().getName());
                                service.setText(pr.getService().getServiceType().getName());
                            }
                            if (pr.getService().getServiceType().getImage() != null) {
                                SharedHelper.putKey(App.getContext(), "service_image",
                                        pr.getService().getServiceType().getImage());
                            }


                        }
                    }
                } else {
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

    private void updateProfile() {
        String auth = "Bearer" + " " + SharedHelper.getKey(App.getContext(), "access_token");
        CustomDialog customDialog = new CustomDialog(this);
        customDialog.setCancelable(false);
        customDialog.show();

        Map<String, RequestBody> fields = new HashMap<>();
        fields.put("first_name", prepareText(first_name.getText().toString()));
        fields.put("email", prepareText(email.getText().toString()));
        fields.put("mobile", prepareText(mobile_no.getText().toString()));

        Call<ProfileResponse> call = restInterface.updateProfile(URLHelper.REQUEST_WITH, auth,
                fields);
        call.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, retrofit2.Response<ProfileResponse> response) {
                customDialog.dismiss();
                if (response.code() == 200) {
                    ProfileResponse pr = response.body();
                    if (pr.getId() != null) {
                        SharedHelper.putKey(App.getContext(), "id", String.valueOf(pr.getId()));
                    }
                    if (pr.getFirstName() != null) {
                        SharedHelper.putKey(App.getContext(), "first_name", pr.getFirstName());
                        first_name.setText(pr.getFirstName());
                    }
                    if (pr.getEmail() != null) {
                        SharedHelper.putKey(App.getContext(), "email", pr.getEmail());
                        email.setText(pr.getEmail());
                    }
                    if (pr.getSos() != null) {
                        SharedHelper.putKey(App.getContext(), "sos", pr.getSos());
                    }
                    if (pr.getAvatar() != null) {
                        SharedHelper.putKey(App.getContext(), "picture", pr.getAvatar());
                        Picasso.get().load(URLHelper.IMAGE_BASE + pr.getAvatar()).into(img_profile);
                    }
                    if (pr.getMobile() != null) {
                        SharedHelper.putKey(App.getContext(), "mobile", pr.getMobile());
                        mobile_no.setText(pr.getMobile());
                    }
                    if (pr.getAvatar() != null) {
                        SharedHelper.putKey(App.getContext(), "picture", pr.getAvatar());
                    }

                    if (pr.getService() != null) {
                        if (pr.getService().getServiceType() != null) {
                            if (pr.getService().getServiceType().getName() != null) {
                                SharedHelper.putKey(App.getContext(), "service",
                                        pr.getService().getServiceType().getName());
                                service.setText(pr.getService().getServiceType().getName());
                            }
                            if (pr.getService().getServiceType().getImage() != null) {
                                SharedHelper.putKey(App.getContext(), "service_image",
                                        pr.getService().getServiceType().getImage());
                            }


                        }
                    }
                } else {
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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @NonNull
    private MultipartBody.Part prepareImage(String keyName, String path) {
        File file = new File(path);
        int imageSize = Integer.parseInt(String.valueOf(file.length() / 1024));
        Log.d("imageSize", String.valueOf(imageSize));
        RequestBody requestFile = RequestBody.create(file, MediaType.parse("image/*"));
        return MultipartBody.Part.createFormData(keyName, file.getName(), requestFile);
    }

    @NonNull
    private RequestBody prepareText(String descString) {
        return RequestBody.create(descString, MultipartBody.FORM);
    }


    public void displayMessage(String toastString) {
        Toasty.info(App.getContext(), toastString, Toasty.LENGTH_SHORT, true).show();
    }

}
