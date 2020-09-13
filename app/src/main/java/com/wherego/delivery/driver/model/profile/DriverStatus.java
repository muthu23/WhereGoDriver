
package com.wherego.delivery.driver.model.profile;

import com.google.gson.annotations.SerializedName;

public class DriverStatus {

    @SerializedName("account_status")
    private String mAccountStatus;
    @SerializedName("msg")
    private String mError;
    @SerializedName("service_status")
    private String mServiceStatus;

    public String getAccountStatus() {
        return mAccountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        mAccountStatus = accountStatus;
    }

    public String getError() {
        return mError;
    }

    public void setError(String error) {
        mError = error;
    }

    public String getServiceStatus() {
        return mServiceStatus;
    }

    public void setServiceStatus(String serviceStatus) {
        mServiceStatus = serviceStatus;
    }

}
