package com.wherego.driver.model.trip;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestItemPayment {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_service_hour")
    @Expose
    private String userServiceHour;
    @SerializedName("earning_money")
    @Expose
    private String earningMoney;
    @SerializedName("provider_base_amount")
    @Expose
    private String providerBaseAmount;
    @SerializedName("service_type")
    @Expose
    private String serviceType;
    @SerializedName("request_id")
    @Expose
    private String requestId;
    @SerializedName("promocode_id")
    @Expose
    private String promocodeId;
    @SerializedName("payment_id")
    @Expose
    private String paymentId;
    @SerializedName("payment_mode")
    @Expose
    private String paymentMode;
    @SerializedName("fixed")
    @Expose
    private String fixed;
    @SerializedName("total_minute")
    @Expose
    private String totalMinute;
    @SerializedName("per_price")
    @Expose
    private String perPrice;
    @SerializedName("distance")
    @Expose
    private String distance;
    @SerializedName("commision")
    @Expose
    private String commision;
    @SerializedName("discount")
    @Expose
    private String discount;
    @SerializedName("tax")
    @Expose
    private String tax;
    @SerializedName("wallet")
    @Expose
    private String wallet;
    @SerializedName("surge")
    @Expose
    private String surge;
    @SerializedName("insurance")
    @Expose
    private String insurance;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("base_earn")
    @Expose
    private String baseEarn;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserServiceHour() {
        return userServiceHour;
    }

    public void setUserServiceHour(String userServiceHour) {
        this.userServiceHour = userServiceHour;
    }

    public String getEarningMoney() {
        return earningMoney;
    }

    public void setEarningMoney(String earningMoney) {
        this.earningMoney = earningMoney;
    }

    public String getProviderBaseAmount() {
        return providerBaseAmount;
    }

    public void setProviderBaseAmount(String providerBaseAmount) {
        this.providerBaseAmount = providerBaseAmount;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getPromocodeId() {
        return promocodeId;
    }

    public void setPromocodeId(String promocodeId) {
        this.promocodeId = promocodeId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getFixed() {
        return fixed;
    }

    public void setFixed(String fixed) {
        this.fixed = fixed;
    }

    public String getTotalMinute() {
        return totalMinute;
    }

    public void setTotalMinute(String totalMinute) {
        this.totalMinute = totalMinute;
    }

    public String getPerPrice() {
        return perPrice;
    }

    public void setPerPrice(String perPrice) {
        this.perPrice = perPrice;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getCommision() {
        return commision;
    }

    public void setCommision(String commision) {
        this.commision = commision;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getWallet() {
        return wallet;
    }

    public void setWallet(String wallet) {
        this.wallet = wallet;
    }

    public String getSurge() {
        return surge;
    }

    public void setSurge(String surge) {
        this.surge = surge;
    }

    public String getInsurance() {
        return insurance;
    }

    public void setInsurance(String insurance) {
        this.insurance = insurance;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getBaseEarn() {
        return baseEarn;
    }

    public void setBaseEarn(String baseEarn) {
        this.baseEarn = baseEarn;
    }

}
