
package com.wherego.delivery.driver.model.addbank;

import com.google.gson.annotations.SerializedName;

public class AddBank {

    @SerializedName("data")
    private Data mData;
    @SerializedName("status")
    private Long mStatus;

    public Data getData() {
        return mData;
    }

    public void setData(Data data) {
        mData = data;
    }

    public Long getStatus() {
        return mStatus;
    }

    public void setStatus(Long status) {
        mStatus = status;
    }

}
