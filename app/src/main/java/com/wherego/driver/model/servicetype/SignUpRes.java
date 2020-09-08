
package com.wherego.driver.model.servicetype;

import com.google.gson.annotations.SerializedName;

public class SignUpRes {

    @SerializedName("created_at")
    private String mCreatedAt;
    @SerializedName("device_id")
    private String mDeviceId;
    @SerializedName("device_token")
    private String mDeviceToken;
    @SerializedName("email")
    private String mEmail;
    @SerializedName("first_name")
    private String mFirstName;
    @SerializedName("id")
    private Long mId;
    @SerializedName("logged_in")
    private String mLoggedIn;
    @SerializedName("mobile")
    private String mMobile;

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
    }

    public String getDeviceId() {
        return mDeviceId;
    }

    public void setDeviceId(String deviceId) {
        mDeviceId = deviceId;
    }

    public String getDeviceToken() {
        return mDeviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        mDeviceToken = deviceToken;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public String getLoggedIn() {
        return mLoggedIn;
    }

    public void setLoggedIn(String loggedIn) {
        mLoggedIn = loggedIn;
    }

    public String getMobile() {
        return mMobile;
    }

    public void setMobile(String mobile) {
        mMobile = mobile;
    }

}
