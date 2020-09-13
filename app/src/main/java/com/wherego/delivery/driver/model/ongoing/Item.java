
package com.wherego.delivery.driver.model.ongoing;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Item {

    @SerializedName("discription")
    private String mDiscription;
    @SerializedName("document_type")
    private String mDocumentType;
    @SerializedName("height")
    private String mHeight;
    @SerializedName("id")
    private Long mId;
    @SerializedName("image")
    private String mImage;
    @SerializedName("instructions")
    private String mInstructions;
    @SerializedName("item_image")
    private List<ItemImage> mItemImage;
    @SerializedName("name")
    private String mName;
    @SerializedName("qty")
    private String mQty;
    @SerializedName("rec_address")
    private String mRecAddress;
    @SerializedName("rec_email")
    private String mRecEmail;
    @SerializedName("rec_mobile")
    private String mRecMobile;
    @SerializedName("rec_name")
    private String mRecName;
    @SerializedName("request_id")
    private String mRequestId;
    @SerializedName("special_notes")
    private String mSpecialNotes;
    @SerializedName("user_id")
    private String mUserId;
    @SerializedName("weight")
    private String mWeight;
    @SerializedName("width")
    private String mWidth;

    public String getDiscription() {
        return mDiscription;
    }

    public void setDiscription(String discription) {
        mDiscription = discription;
    }

    public String getDocumentType() {
        return mDocumentType;
    }

    public void setDocumentType(String documentType) {
        mDocumentType = documentType;
    }

    public String getHeight() {
        return mHeight;
    }

    public void setHeight(String height) {
        mHeight = height;
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

    public String getInstructions() {
        return mInstructions;
    }

    public void setInstructions(String instructions) {
        mInstructions = instructions;
    }

    public List<ItemImage> getItemImage() {
        return mItemImage;
    }

    public void setItemImage(List<ItemImage> itemImage) {
        mItemImage = itemImage;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getQty() {
        return mQty;
    }

    public void setQty(String qty) {
        mQty = qty;
    }

    public String getRecAddress() {
        return mRecAddress;
    }

    public void setRecAddress(String recAddress) {
        mRecAddress = recAddress;
    }

    public String getRecEmail() {
        return mRecEmail;
    }

    public void setRecEmail(String recEmail) {
        mRecEmail = recEmail;
    }

    public String getRecMobile() {
        return mRecMobile;
    }

    public void setRecMobile(String recMobile) {
        mRecMobile = recMobile;
    }

    public String getRecName() {
        return mRecName;
    }

    public void setRecName(String recName) {
        mRecName = recName;
    }

    public String getRequestId() {
        return mRequestId;
    }

    public void setRequestId(String requestId) {
        mRequestId = requestId;
    }

    public String getSpecialNotes() {
        return mSpecialNotes;
    }

    public void setSpecialNotes(String specialNotes) {
        mSpecialNotes = specialNotes;
    }

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String userId) {
        mUserId = userId;
    }

    public String getWeight() {
        return mWeight;
    }

    public void setWeight(String weight) {
        mWeight = weight;
    }

    public String getWidth() {
        return mWidth;
    }

    public void setWidth(String width) {
        mWidth = width;
    }

}
