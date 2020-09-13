
package com.wherego.delivery.driver.model.history;

import com.google.gson.annotations.SerializedName;

public class Rating {

    @SerializedName("created_at")
    private String mCreatedAt;
    @SerializedName("id")
    private Long mId;
    @SerializedName("provider_comment")
    private String mProviderComment;
    @SerializedName("provider_id")
    private String mProviderId;
    @SerializedName("provider_rating")
    private String mProviderRating;
    @SerializedName("request_id")
    private String mRequestId;
    @SerializedName("user_comment")
    private String mUserComment;
    @SerializedName("user_id")
    private String mUserId;
    @SerializedName("user_rating")
    private String mUserRating;

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

    public String getProviderComment() {
        return mProviderComment;
    }

    public void setProviderComment(String providerComment) {
        mProviderComment = providerComment;
    }

    public String getProviderId() {
        return mProviderId;
    }

    public void setProviderId(String providerId) {
        mProviderId = providerId;
    }

    public String getProviderRating() {
        return mProviderRating;
    }

    public void setProviderRating(String providerRating) {
        mProviderRating = providerRating;
    }

    public String getRequestId() {
        return mRequestId;
    }

    public void setRequestId(String requestId) {
        mRequestId = requestId;
    }

    public String getUserComment() {
        return mUserComment;
    }

    public void setUserComment(String userComment) {
        mUserComment = userComment;
    }

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String userId) {
        mUserId = userId;
    }

    public String getUserRating() {
        return mUserRating;
    }

    public void setUserRating(String userRating) {
        mUserRating = userRating;
    }

}
