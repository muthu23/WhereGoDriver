package com.wherego.delivery.driver.model.trip;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestList {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("request_id")
    @Expose
    private String requestId;
    @SerializedName("provider_id")
    @Expose
    private String providerId;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("time_left_to_respond")
    @Expose
    private Integer timeLeftToRespond;
    @SerializedName("request")
    @Expose
    private RequestListItem request;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getTimeLeftToRespond() {
        return timeLeftToRespond;
    }

    public void setTimeLeftToRespond(Integer timeLeftToRespond) {
        this.timeLeftToRespond = timeLeftToRespond;
    }

    public RequestListItem getRequest() {
        return request;
    }

    public void setRequest(RequestListItem request) {
        this.request = request;
    }

}
