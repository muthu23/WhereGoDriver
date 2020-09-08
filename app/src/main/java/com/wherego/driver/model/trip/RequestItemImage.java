package com.wherego.driver.model.trip;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestItemImage {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("image_path")
    @Expose
    private String imagePath;
    @SerializedName("deleted_at")
    @Expose
    private String deletedAt;
    @SerializedName("pivot")
    @Expose
    private Pivot pivot;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Pivot getPivot() {
        return pivot;
    }

    public void setPivot(Pivot pivot) {
        this.pivot = pivot;
    }
}
