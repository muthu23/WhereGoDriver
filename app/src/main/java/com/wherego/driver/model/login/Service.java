
package com.wherego.driver.model.login;


import com.google.gson.annotations.SerializedName;

public class Service {

    @SerializedName("full")
    private String mFull;
    @SerializedName("id")
    private Long mId;
    @SerializedName("provider_id")
    private String mProviderId;
    @SerializedName("service_model")
    private String mServiceModel;
    @SerializedName("service_number")
    private String mServiceNumber;
    @SerializedName("service_type_id")
    private String mServiceTypeId;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("updated_at")
    private String mUpdatedAt;

    public String getFull() {
        return mFull;
    }

    public void setFull(String full) {
        mFull = full;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public String getProviderId() {
        return mProviderId;
    }

    public void setProviderId(String providerId) {
        mProviderId = providerId;
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

    public String getServiceTypeId() {
        return mServiceTypeId;
    }

    public void setServiceTypeId(String serviceTypeId) {
        mServiceTypeId = serviceTypeId;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public String getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        mUpdatedAt = updatedAt;
    }

}
