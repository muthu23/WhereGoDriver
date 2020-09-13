
package com.wherego.delivery.driver.model.historylist;

import com.google.gson.annotations.SerializedName;

public class Payment {

    @SerializedName("base_earn")
    private String mBaseEarn;
    @SerializedName("commision")
    private String mCommision;
    @SerializedName("discount")
    private String mDiscount;
    @SerializedName("distance")
    private String mDistance;
    @SerializedName("earning_money")
    private String mEarningMoney;
    @SerializedName("fixed")
    private String mFixed;
    @SerializedName("id")
    private Long mId;
    @SerializedName("insurance")
    private String mInsurance;
    @SerializedName("payment_id")
    private String mPaymentId;
    @SerializedName("payment_mode")
    private String mPaymentMode;
    @SerializedName("per_price")
    private String mPerPrice;
    @SerializedName("promocode_id")
    private String mPromocodeId;
    @SerializedName("provider_base_amount")
    private String mProviderBaseAmount;
    @SerializedName("request_id")
    private String mRequestId;
    @SerializedName("service_type")
    private String mServiceType;
    @SerializedName("surge")
    private String mSurge;
    @SerializedName("tax")
    private String mTax;
    @SerializedName("total")
    private String mTotal;
    @SerializedName("total_minute")
    private String mTotalMinute;
    @SerializedName("user_service_hour")
    private String mUserServiceHour;
    @SerializedName("wallet")
    private String mWallet;

    public String getBaseEarn() {
        return mBaseEarn;
    }

    public void setBaseEarn(String baseEarn) {
        mBaseEarn = baseEarn;
    }

    public String getCommision() {
        return mCommision;
    }

    public void setCommision(String commision) {
        mCommision = commision;
    }

    public String getDiscount() {
        return mDiscount;
    }

    public void setDiscount(String discount) {
        mDiscount = discount;
    }

    public String getDistance() {
        return mDistance;
    }

    public void setDistance(String distance) {
        mDistance = distance;
    }

    public String getEarningMoney() {
        return mEarningMoney;
    }

    public void setEarningMoney(String earningMoney) {
        mEarningMoney = earningMoney;
    }

    public String getFixed() {
        return mFixed;
    }

    public void setFixed(String fixed) {
        mFixed = fixed;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public String getInsurance() {
        return mInsurance;
    }

    public void setInsurance(String insurance) {
        mInsurance = insurance;
    }

    public String getPaymentId() {
        return mPaymentId;
    }

    public void setPaymentId(String paymentId) {
        mPaymentId = paymentId;
    }

    public String getPaymentMode() {
        return mPaymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        mPaymentMode = paymentMode;
    }

    public String getPerPrice() {
        return mPerPrice;
    }

    public void setPerPrice(String perPrice) {
        mPerPrice = perPrice;
    }

    public String getPromocodeId() {
        return mPromocodeId;
    }

    public void setPromocodeId(String promocodeId) {
        mPromocodeId = promocodeId;
    }

    public String getProviderBaseAmount() {
        return mProviderBaseAmount;
    }

    public void setProviderBaseAmount(String providerBaseAmount) {
        mProviderBaseAmount = providerBaseAmount;
    }

    public String getRequestId() {
        return mRequestId;
    }

    public void setRequestId(String requestId) {
        mRequestId = requestId;
    }

    public String getServiceType() {
        return mServiceType;
    }

    public void setServiceType(String serviceType) {
        mServiceType = serviceType;
    }

    public String getSurge() {
        return mSurge;
    }

    public void setSurge(String surge) {
        mSurge = surge;
    }

    public String getTax() {
        return mTax;
    }

    public void setTax(String tax) {
        mTax = tax;
    }

    public String getTotal() {
        return mTotal;
    }

    public void setTotal(String total) {
        mTotal = total;
    }

    public String getTotalMinute() {
        return mTotalMinute;
    }

    public void setTotalMinute(String totalMinute) {
        mTotalMinute = totalMinute;
    }

    public String getUserServiceHour() {
        return mUserServiceHour;
    }

    public void setUserServiceHour(String userServiceHour) {
        mUserServiceHour = userServiceHour;
    }

    public String getWallet() {
        return mWallet;
    }

    public void setWallet(String wallet) {
        mWallet = wallet;
    }

}
