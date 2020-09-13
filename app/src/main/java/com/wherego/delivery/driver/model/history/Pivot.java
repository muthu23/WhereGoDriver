
package com.wherego.delivery.driver.model.history;

import com.google.gson.annotations.SerializedName;

public class Pivot {

    @SerializedName("image_id")
    private String mImageId;
    @SerializedName("item_id")
    private String mItemId;

    public String getImageId() {
        return mImageId;
    }

    public void setImageId(String imageId) {
        mImageId = imageId;
    }

    public String getItemId() {
        return mItemId;
    }

    public void setItemId(String itemId) {
        mItemId = itemId;
    }

}
