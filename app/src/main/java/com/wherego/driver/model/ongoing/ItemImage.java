
package com.wherego.driver.model.ongoing;

import com.google.gson.annotations.SerializedName;

public class ItemImage {

    @SerializedName("deleted_at")
    private String mDeletedAt;
    @SerializedName("id")
    private Long mId;
    @SerializedName("image_path")
    private String mImagePath;
    @SerializedName("pivot")
    private Pivot mPivot;

    public String getDeletedAt() {
        return mDeletedAt;
    }

    public void setDeletedAt(String deletedAt) {
        mDeletedAt = deletedAt;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public String getImagePath() {
        return mImagePath;
    }

    public void setImagePath(String imagePath) {
        mImagePath = imagePath;
    }

    public Pivot getPivot() {
        return mPivot;
    }

    public void setPivot(Pivot pivot) {
        mPivot = pivot;
    }

}
