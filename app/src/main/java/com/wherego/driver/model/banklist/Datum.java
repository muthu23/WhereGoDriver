
package com.wherego.driver.model.banklist;

import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("account_name")
    private String mAccountName;
    @SerializedName("account_number")
    private String mAccountNumber;
    @SerializedName("bank_name")
    private String mBankName;
    @SerializedName("country")
    private String mCountry;
    @SerializedName("currency")
    private String mCurrency;
    @SerializedName("id")
    private Long mId;
    @SerializedName("paypal_id")
    private String mPaypalId;
    @SerializedName("provider_id")
    private String mProviderId;
    @SerializedName("routing_number")
    private String mRoutingNumber;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("type")
    private String mType;

    public String getAccountName() {
        return mAccountName;
    }

    public void setAccountName(String accountName) {
        mAccountName = accountName;
    }

    public String getAccountNumber() {
        return mAccountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        mAccountNumber = accountNumber;
    }

    public String getBankName() {
        return mBankName;
    }

    public void setBankName(String bankName) {
        mBankName = bankName;
    }

    public String getCountry() {
        return mCountry;
    }

    public void setCountry(String country) {
        mCountry = country;
    }

    public String getCurrency() {
        return mCurrency;
    }

    public void setCurrency(String currency) {
        mCurrency = currency;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public String getPaypalId() {
        return mPaypalId;
    }

    public void setPaypalId(String paypalId) {
        mPaypalId = paypalId;
    }

    public String getProviderId() {
        return mProviderId;
    }

    public void setProviderId(String providerId) {
        mProviderId = providerId;
    }

    public String getRoutingNumber() {
        return mRoutingNumber;
    }

    public void setRoutingNumber(String routingNumber) {
        mRoutingNumber = routingNumber;
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
