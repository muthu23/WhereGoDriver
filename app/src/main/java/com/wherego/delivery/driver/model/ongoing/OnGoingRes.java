
package com.wherego.delivery.driver.model.ongoing;

import com.google.gson.annotations.SerializedName;

public class OnGoingRes {

    @SerializedName("amount_customer")
    private String mAmountCustomer;
    @SerializedName("assigned_at")
    private String mAssignedAt;
    @SerializedName("booking_id")
    private String mBookingId;
    @SerializedName("cancel_reason")
    private String mCancelReason;
    @SerializedName("cancelled_by")
    private String mCancelledBy;
    @SerializedName("created_at")
    private String mCreatedAt;
    @SerializedName("current_provider_id")
    private String mCurrentProviderId;
    @SerializedName("d_address")
    private String mDAddress;
    @SerializedName("d_latitude")
    private String mDLatitude;
    @SerializedName("d_longitude")
    private String mDLongitude;
    @SerializedName("deleted_at")
    private String mDeletedAt;
    @SerializedName("distance")
    private String mDistance;
    @SerializedName("finished_at")
    private String mFinishedAt;
    @SerializedName("id")
    private Long mId;
    @SerializedName("invoice")
    private String mInvoice;
    @SerializedName("item")
    private Item mItem;
    @SerializedName("item_id")
    private String mItemId;
    @SerializedName("paid")
    private String mPaid;
    @SerializedName("payment_mode")
    private String mPaymentMode;
    @SerializedName("provider_id")
    private String mProviderId;
    @SerializedName("provider_rated")
    private String mProviderRated;
    @SerializedName("request_ip")
    private String mRequestIp;
    @SerializedName("route_key")
    private String mRouteKey;
    @SerializedName("s_address")
    private String mSAddress;
    @SerializedName("s_latitude")
    private String mSLatitude;
    @SerializedName("s_longitude")
    private String mSLongitude;
    @SerializedName("schedule_at")
    private String mScheduleAt;
    @SerializedName("service_type")
    private ServiceType mServiceType;
    @SerializedName("service_type_id")
    private String mServiceTypeId;
    @SerializedName("special_note")
    private String mSpecialNote;
    @SerializedName("started_at")
    private String mStartedAt;
    @SerializedName("static_map")
    private String mStaticMap;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("surge")
    private String mSurge;
    @SerializedName("updated_at")
    private String mUpdatedAt;
    @SerializedName("use_wallet")
    private String mUseWallet;
    @SerializedName("user")
    private User mUser;
    @SerializedName("user_id")
    private String mUserId;
    @SerializedName("user_rated")
    private String mUserRated;
    @SerializedName("verification_code")
    private String mVerificationCode;

    public String getAmountCustomer() {
        return mAmountCustomer;
    }

    public void setAmountCustomer(String amountCustomer) {
        mAmountCustomer = amountCustomer;
    }

    public String getAssignedAt() {
        return mAssignedAt;
    }

    public void setAssignedAt(String assignedAt) {
        mAssignedAt = assignedAt;
    }

    public String getBookingId() {
        return mBookingId;
    }

    public void setBookingId(String bookingId) {
        mBookingId = bookingId;
    }

    public String getCancelReason() {
        return mCancelReason;
    }

    public void setCancelReason(String cancelReason) {
        mCancelReason = cancelReason;
    }

    public String getCancelledBy() {
        return mCancelledBy;
    }

    public void setCancelledBy(String cancelledBy) {
        mCancelledBy = cancelledBy;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
    }

    public String getCurrentProviderId() {
        return mCurrentProviderId;
    }

    public void setCurrentProviderId(String currentProviderId) {
        mCurrentProviderId = currentProviderId;
    }

    public String getDAddress() {
        return mDAddress;
    }

    public void setDAddress(String dAddress) {
        mDAddress = dAddress;
    }

    public String getDLatitude() {
        return mDLatitude;
    }

    public void setDLatitude(String dLatitude) {
        mDLatitude = dLatitude;
    }

    public String getDLongitude() {
        return mDLongitude;
    }

    public void setDLongitude(String dLongitude) {
        mDLongitude = dLongitude;
    }

    public String getDeletedAt() {
        return mDeletedAt;
    }

    public void setDeletedAt(String deletedAt) {
        mDeletedAt = deletedAt;
    }

    public String getDistance() {
        return mDistance;
    }

    public void setDistance(String distance) {
        mDistance = distance;
    }

    public String getFinishedAt() {
        return mFinishedAt;
    }

    public void setFinishedAt(String finishedAt) {
        mFinishedAt = finishedAt;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public String getInvoice() {
        return mInvoice;
    }

    public void setInvoice(String invoice) {
        mInvoice = invoice;
    }

    public Item getItem() {
        return mItem;
    }

    public void setItem(Item item) {
        mItem = item;
    }

    public String getItemId() {
        return mItemId;
    }

    public void setItemId(String itemId) {
        mItemId = itemId;
    }

    public String getPaid() {
        return mPaid;
    }

    public void setPaid(String paid) {
        mPaid = paid;
    }

    public String getPaymentMode() {
        return mPaymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        mPaymentMode = paymentMode;
    }

    public String getProviderId() {
        return mProviderId;
    }

    public void setProviderId(String providerId) {
        mProviderId = providerId;
    }

    public String getProviderRated() {
        return mProviderRated;
    }

    public void setProviderRated(String providerRated) {
        mProviderRated = providerRated;
    }

    public String getRequestIp() {
        return mRequestIp;
    }

    public void setRequestIp(String requestIp) {
        mRequestIp = requestIp;
    }

    public String getRouteKey() {
        return mRouteKey;
    }

    public void setRouteKey(String routeKey) {
        mRouteKey = routeKey;
    }

    public String getSAddress() {
        return mSAddress;
    }

    public void setSAddress(String sAddress) {
        mSAddress = sAddress;
    }

    public String getSLatitude() {
        return mSLatitude;
    }

    public void setSLatitude(String sLatitude) {
        mSLatitude = sLatitude;
    }

    public String getSLongitude() {
        return mSLongitude;
    }

    public void setSLongitude(String sLongitude) {
        mSLongitude = sLongitude;
    }

    public String getScheduleAt() {
        return mScheduleAt;
    }

    public void setScheduleAt(String scheduleAt) {
        mScheduleAt = scheduleAt;
    }

    public ServiceType getServiceType() {
        return mServiceType;
    }

    public void setServiceType(ServiceType serviceType) {
        mServiceType = serviceType;
    }

    public String getServiceTypeId() {
        return mServiceTypeId;
    }

    public void setServiceTypeId(String serviceTypeId) {
        mServiceTypeId = serviceTypeId;
    }

    public String getSpecialNote() {
        return mSpecialNote;
    }

    public void setSpecialNote(String specialNote) {
        mSpecialNote = specialNote;
    }

    public String getStartedAt() {
        return mStartedAt;
    }

    public void setStartedAt(String startedAt) {
        mStartedAt = startedAt;
    }

    public String getStaticMap() {
        return mStaticMap;
    }

    public void setStaticMap(String staticMap) {
        mStaticMap = staticMap;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public String getSurge() {
        return mSurge;
    }

    public void setSurge(String surge) {
        mSurge = surge;
    }

    public String getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        mUpdatedAt = updatedAt;
    }

    public String getUseWallet() {
        return mUseWallet;
    }

    public void setUseWallet(String useWallet) {
        mUseWallet = useWallet;
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String userId) {
        mUserId = userId;
    }

    public String getUserRated() {
        return mUserRated;
    }

    public void setUserRated(String userRated) {
        mUserRated = userRated;
    }

    public String getVerificationCode() {
        return mVerificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        mVerificationCode = verificationCode;
    }

}
