
package com.wherego.driver.model.ongoing;

import com.google.gson.annotations.SerializedName;

public class ServiceType {

    @SerializedName("calculator")
    private String mCalculator;
    @SerializedName("capacity")
    private String mCapacity;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("distance")
    private String mDistance;
    @SerializedName("fixed")
    private String mFixed;
    @SerializedName("id")
    private Long mId;
    @SerializedName("image")
    private String mImage;
    @SerializedName("minute")
    private String mMinute;
    @SerializedName("name")
    private String mName;
    @SerializedName("price")
    private String mPrice;
    @SerializedName("provider_name")
    private String mProviderName;
    @SerializedName("status")
    private String mStatus;

    public String getCalculator() {
        return mCalculator;
    }

    public void setCalculator(String calculator) {
        mCalculator = calculator;
    }

    public String getCapacity() {
        return mCapacity;
    }

    public void setCapacity(String capacity) {
        mCapacity = capacity;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getDistance() {
        return mDistance;
    }

    public void setDistance(String distance) {
        mDistance = distance;
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

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public String getMinute() {
        return mMinute;
    }

    public void setMinute(String minute) {
        mMinute = minute;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPrice() {
        return mPrice;
    }

    public void setPrice(String price) {
        mPrice = price;
    }

    public String getProviderName() {
        return mProviderName;
    }

    public void setProviderName(String providerName) {
        mProviderName = providerName;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

}
