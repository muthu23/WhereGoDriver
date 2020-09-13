
package com.wherego.delivery.driver.model.addbank;

import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("id")
    private Long mId;
    @SerializedName("provider_id")
    private Long mProviderId;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("type")
    private String mType;

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public Long getProviderId() {
        return mProviderId;
    }

    public void setProviderId(Long providerId) {
        mProviderId = providerId;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

}
