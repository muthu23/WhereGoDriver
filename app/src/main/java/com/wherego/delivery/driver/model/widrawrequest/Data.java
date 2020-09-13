
package com.wherego.delivery.driver.model.widrawrequest;


import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("amount")
    private Long mAmount;
    @SerializedName("bank_account_id")
    private Long mBankAccountId;
    @SerializedName("created_at")
    private String mCreatedAt;
    @SerializedName("id")
    private Long mId;
    @SerializedName("provider_id")
    private Long mProviderId;
    @SerializedName("status")
    private String mStatus;

    public Long getAmount() {
        return mAmount;
    }

    public void setAmount(Long amount) {
        mAmount = amount;
    }

    public Long getBankAccountId() {
        return mBankAccountId;
    }

    public void setBankAccountId(Long bankAccountId) {
        mBankAccountId = bankAccountId;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
    }

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

}
