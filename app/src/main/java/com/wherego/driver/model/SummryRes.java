package com.wherego.driver.model;

public class SummryRes {

    private int rides;
    private String revenue;
    private int cancel_rides;
    private int scheduled_rides;

    public int getRides() {
        return rides;
    }

    public void setRides(int rides) {
        this.rides = rides;
    }

    public String getRevenue() {
        return revenue;
    }

    public void setRevenue(String revenue) {
        this.revenue = revenue;
    }

    public int getCancel_rides() {
        return cancel_rides;
    }

    public void setCancel_rides(int cancel_rides) {
        this.cancel_rides = cancel_rides;
    }

    public int getScheduled_rides() {
        return scheduled_rides;
    }

    public void setScheduled_rides(int scheduled_rides) {
        this.scheduled_rides = scheduled_rides;
    }
}
