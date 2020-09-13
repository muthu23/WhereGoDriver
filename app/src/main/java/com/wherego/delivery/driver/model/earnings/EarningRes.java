
package com.wherego.delivery.driver.model.earnings;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class EarningRes {

    @SerializedName("rides")
    private List<Ride> mRides;
    @SerializedName("rides_count")
    private RidesCount mRidesCount;
    @SerializedName("target")
    private String mTarget;
    @SerializedName("user")
    private Long mUser;

    public List<Ride> getRides() {
        return mRides;
    }

    public void setRides(List<Ride> rides) {
        mRides = rides;
    }

    public RidesCount getRidesCount() {
        return mRidesCount;
    }

    public void setRidesCount(RidesCount ridesCount) {
        mRidesCount = ridesCount;
    }

    public String getTarget() {
        return mTarget;
    }

    public void setTarget(String target) {
        mTarget = target;
    }

    public Long getUser() {
        return mUser;
    }

    public void setUser(Long user) {
        mUser = user;
    }

}
