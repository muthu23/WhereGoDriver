
package com.wherego.delivery.driver.model.withdrawlist;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class WithDrawsList {

    @SerializedName("data")
    private ArrayList<Datum> mData;
    @SerializedName("status")
    private Long mStatus;
    @SerializedName("totalEarn")
    private Double mTotalEarn;

    public ArrayList<Datum> getData() {
        return mData;
    }

    public void setData(ArrayList<Datum> data) {
        mData = data;
    }

    public Long getStatus() {
        return mStatus;
    }

    public void setStatus(Long status) {
        mStatus = status;
    }

    public Double getTotalEarn() {
        return mTotalEarn;
    }

    public void setTotalEarn(Double totalEarn) {
        mTotalEarn = totalEarn;
    }

}
