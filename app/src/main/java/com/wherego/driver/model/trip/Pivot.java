package com.wherego.driver.model.trip;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pivot {
    @SerializedName("item_id")
    @Expose
    private String itemId;
    @SerializedName("image_id")
    @Expose
    private String imageId;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }
}
