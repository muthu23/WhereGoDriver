
package com.wherego.driver.model.servicetype;

import com.google.gson.annotations.SerializedName;

public class SignUpRequest {

    @SerializedName("device_id")
    private String mDeviceId;
    @SerializedName("device_token")
    private String mDeviceToken;
    @SerializedName("device_type")
    private String mDeviceType;
    @SerializedName("email")
    private String mEmail;
    @SerializedName("first_name")
    private String mFirstName;
    @SerializedName("mobile")
    private String mMobile;
    @SerializedName("password")
    private String mPassword;
    @SerializedName("service_model")
    private String mServiceModel;
    @SerializedName("service_number")
    private String mServiceNumber;
    @SerializedName("service_type_ids")
    private String mServiceTypeIds;

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

    public String getDeviceType() {
        return mDeviceType;
    }

    public void setDeviceType(String deviceType) {
        mDeviceType = deviceType;
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

    public String getMobile() {
        return mMobile;
    }

    public void setMobile(String mobile) {
        mMobile = mobile;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public String getServiceModel() {
        return mServiceModel;
    }

    public void setServiceModel(String serviceModel) {
        mServiceModel = serviceModel;
    }

    public String getServiceNumber() {
        return mServiceNumber;
    }

    public void setServiceNumber(String serviceNumber) {
        mServiceNumber = serviceNumber;
    }

    public String getServiceTypeIds() {
        return mServiceTypeIds;
    }

    public void setServiceTypeIds(String serviceTypeIds) {
        mServiceTypeIds = serviceTypeIds;
    }

}
