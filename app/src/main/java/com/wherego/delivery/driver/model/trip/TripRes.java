package com.wherego.delivery.driver.model.trip;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TripRes {

    @SerializedName("account_status")
    @Expose
    private String accountStatus;
    @SerializedName("service_status")
    @Expose
    private String serviceStatus;
    @SerializedName("trips")
    @Expose
    private Integer trips;
    @SerializedName("earnings")
    @Expose
    private String earnings;
    @SerializedName("commision")
    @Expose
    private String commision;
    @SerializedName("requests")
    @Expose
    private ArrayList<RequestList> requests = null;
    @SerializedName("item")
    @Expose
    private RequestItem item;

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(String serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    public Integer getTrips() {
        return trips;
    }

    public void setTrips(Integer trips) {
        this.trips = trips;
    }

    public String getEarnings() {
        return earnings;
    }

    public void setEarnings(String earnings) {
        this.earnings = earnings;
    }

    public String getCommision() {
        return commision;
    }

    public void setCommision(String commision) {
        this.commision = commision;
    }

    public ArrayList<RequestList> getRequests() {
        return requests;
    }

    public void setRequests(ArrayList<RequestList> requests) {
        this.requests = requests;
    }

    public RequestItem getItem() {
        return item;
    }

    public void setItem(RequestItem item) {
        this.item = item;
    }

}
