package com.wherego.driver.model.trip;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestListItem {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("booking_id")
    @Expose
    private String bookingId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("provider_id")
    @Expose
    private String providerId;
    @SerializedName("current_provider_id")
    @Expose
    private String currentProviderId;
    @SerializedName("service_type_id")
    @Expose
    private String serviceTypeId;
    @SerializedName("item_id")
    @Expose
    private String itemId;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("cancelled_by")
    @Expose
    private String cancelledBy;
    @SerializedName("cancel_reason")
    @Expose
    private String cancelReason;
    @SerializedName("payment_mode")
    @Expose
    private String paymentMode;
    @SerializedName("paid")
    @Expose
    private String paid;
    @SerializedName("distance")
    @Expose
    private String distance;
    @SerializedName("s_address")
    @Expose
    private String sAddress;
    @SerializedName("s_latitude")
    @Expose
    private String sLatitude;
    @SerializedName("s_longitude")
    @Expose
    private String sLongitude;
    @SerializedName("d_address")
    @Expose
    private String dAddress;
    @SerializedName("d_latitude")
    @Expose
    private String dLatitude;
    @SerializedName("d_longitude")
    @Expose
    private String dLongitude;
    @SerializedName("assigned_at")
    @Expose
    private String assignedAt;
    @SerializedName("schedule_at")
    @Expose
    private String scheduleAt;
    @SerializedName("started_at")
    @Expose
    private String startedAt;
    @SerializedName("request_ip")
    @Expose
    private String requestIp;
    @SerializedName("finished_at")
    @Expose
    private String finishedAt;
    @SerializedName("user_rated")
    @Expose
    private String userRated;
    @SerializedName("provider_rated")
    @Expose
    private String providerRated;
    @SerializedName("use_wallet")
    @Expose
    private String useWallet;
    @SerializedName("surge")
    @Expose
    private String surge;
    @SerializedName("route_key")
    @Expose
    private String routeKey;
    @SerializedName("invoice")
    @Expose
    private String invoice;
    @SerializedName("special_note")
    @Expose
    private String specialNote;
    @SerializedName("verification_code")
    @Expose
    private String verificationCode;
    @SerializedName("amount_customer")
    @Expose
    private String amountCustomer;
    @SerializedName("deleted_at")
    @Expose
    private String deletedAt;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("user")
    @Expose
    private RequestUser user;
    @SerializedName("payment")
    @Expose
    private RequestItemPayment payment;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getCurrentProviderId() {
        return currentProviderId;
    }

    public void setCurrentProviderId(String currentProviderId) {
        this.currentProviderId = currentProviderId;
    }

    public String getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(String serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCancelledBy() {
        return cancelledBy;
    }

    public void setCancelledBy(String cancelledBy) {
        this.cancelledBy = cancelledBy;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getPaid() {
        return paid;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getSAddress() {
        return sAddress;
    }

    public void setSAddress(String sAddress) {
        this.sAddress = sAddress;
    }

    public String getSLatitude() {
        return sLatitude;
    }

    public void setSLatitude(String sLatitude) {
        this.sLatitude = sLatitude;
    }

    public String getSLongitude() {
        return sLongitude;
    }

    public void setSLongitude(String sLongitude) {
        this.sLongitude = sLongitude;
    }

    public String getDAddress() {
        return dAddress;
    }

    public void setDAddress(String dAddress) {
        this.dAddress = dAddress;
    }

    public String getDLatitude() {
        return dLatitude;
    }

    public void setDLatitude(String dLatitude) {
        this.dLatitude = dLatitude;
    }

    public String getDLongitude() {
        return dLongitude;
    }

    public void setDLongitude(String dLongitude) {
        this.dLongitude = dLongitude;
    }

    public String getAssignedAt() {
        return assignedAt;
    }

    public void setAssignedAt(String assignedAt) {
        this.assignedAt = assignedAt;
    }

    public String getScheduleAt() {
        return scheduleAt;
    }

    public void setScheduleAt(String scheduleAt) {
        this.scheduleAt = scheduleAt;
    }

    public String getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(String startedAt) {
        this.startedAt = startedAt;
    }

    public String getRequestIp() {
        return requestIp;
    }

    public void setRequestIp(String requestIp) {
        this.requestIp = requestIp;
    }

    public String getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(String finishedAt) {
        this.finishedAt = finishedAt;
    }

    public String getUserRated() {
        return userRated;
    }

    public void setUserRated(String userRated) {
        this.userRated = userRated;
    }

    public String getProviderRated() {
        return providerRated;
    }

    public void setProviderRated(String providerRated) {
        this.providerRated = providerRated;
    }

    public String getUseWallet() {
        return useWallet;
    }

    public void setUseWallet(String useWallet) {
        this.useWallet = useWallet;
    }

    public String getSurge() {
        return surge;
    }

    public void setSurge(String surge) {
        this.surge = surge;
    }

    public String getRouteKey() {
        return routeKey;
    }

    public void setRouteKey(String routeKey) {
        this.routeKey = routeKey;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public String getSpecialNote() {
        return specialNote;
    }

    public void setSpecialNote(String specialNote) {
        this.specialNote = specialNote;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public String getAmountCustomer() {
        return amountCustomer;
    }

    public void setAmountCustomer(String amountCustomer) {
        this.amountCustomer = amountCustomer;
    }

    public String getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public RequestUser getUser() {
        return user;
    }

    public void setUser(RequestUser user) {
        this.user = user;
    }

    public RequestItemPayment getPayment() {
        return payment;
    }

    public void setPayment(RequestItemPayment payment) {
        this.payment = payment;
    }

}
