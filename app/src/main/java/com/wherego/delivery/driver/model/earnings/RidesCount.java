
package com.wherego.delivery.driver.model.earnings;


import com.google.gson.annotations.SerializedName;

public class RidesCount {

    @SerializedName("commission")
    private Long mCommission;
    @SerializedName("overall")
    private float mOverall;

    public Long getCommission() {
        return mCommission;
    }

    public void setCommission(Long commission) {
        mCommission = commission;
    }

    public float getOverall() {
        return mOverall;
    }

    public void setOverall(float overall) {
        mOverall = overall;
    }

}
