
package com.wherego.driver.model.login;


import com.google.gson.annotations.SerializedName;

public class Device {

    @SerializedName("id")
    private Long mId;
    @SerializedName("provider_id")
    private String mProviderId;
    @SerializedName("sns_arn")
    private String mSnsArn;
    @SerializedName("token")
    private String mToken;
    @SerializedName("type")
    private String mType;
    @SerializedName("udid")
    private String mUdid;

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

    public String getSnsArn() {
        return mSnsArn;
    }

    public void setSnsArn(String snsArn) {
        mSnsArn = snsArn;
    }

    public String getToken() {
        return mToken;
    }

    public void setToken(String token) {
        mToken = token;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public String getUdid() {
        return mUdid;
    }

    public void setUdid(String udid) {
        mUdid = udid;
    }

}
