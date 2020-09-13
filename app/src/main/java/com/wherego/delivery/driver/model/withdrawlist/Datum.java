
package com.wherego.delivery.driver.model.withdrawlist;

import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("amount")
    private String mAmount;
    @SerializedName("bank_account_id")
    private String mBankAccountId;
    @SerializedName("created_at")
    private String mCreatedAt;
    @SerializedName("id")
    private Long mId;
    @SerializedName("provider_id")
    private String mProviderId;
    @SerializedName("status")
    private String mStatus;

    public String getAmount() {
        return mAmount;
    }

    public void setAmount(String amount) {
        mAmount = amount;
    }

    public String getBankAccountId() {
        return mBankAccountId;
    }

    public void setBankAccountId(String bankAccountId) {
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

    public String getProviderId() {
        return mProviderId;
    }

    public void setProviderId(String providerId) {
        mProviderId = providerId;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

}
