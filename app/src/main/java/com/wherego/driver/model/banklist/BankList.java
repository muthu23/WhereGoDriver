
package com.wherego.driver.model.banklist;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class BankList {

    @SerializedName("data")
    private List<Datum> mData;
    @SerializedName("status")
    private Long mStatus;

    public List<Datum> getData() {
        return mData;
    }

    public void setData(List<Datum> data) {
        mData = data;
    }

    public Long getStatus() {
        return mStatus;
    }

    public void setStatus(Long status) {
        mStatus = status;
    }

}
