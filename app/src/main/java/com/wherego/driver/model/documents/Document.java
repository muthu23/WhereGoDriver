
package com.wherego.driver.model.documents;

import com.google.gson.annotations.SerializedName;

public class Document {

    @SerializedName("expire")
    private String mExpire;
    @SerializedName("id")
    private Long mId;
    @SerializedName("image")
    private String mImage;
    @SerializedName("name")
    private String mName;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("type")
    private String mType;

    public String getExpire() {
        return mExpire;
    }

    public void setExpire(String expire) {
        mExpire = expire;
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

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
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
