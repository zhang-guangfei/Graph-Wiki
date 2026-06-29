package com.sales.ops.dto.tms;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class TmsTrackingResultItem implements Serializable {

    // 路由节点操作码
    private Integer stateCode;
    // 路由节点操作码
    private String stateDesc;
    // 路由节点具体描述
    private String trackingDescr;
    // 路由节点产生的时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date trackingTime;

    public Integer getStateCode() {
        return stateCode;
    }

    public void setStateCode(Integer stateCode) {
        this.stateCode = stateCode;
    }

    public String getStateDesc() {
        return stateDesc;
    }

    public void setStateDesc(String stateDesc) {
        this.stateDesc = stateDesc;
    }

    public String getTrackingDescr() {
        return trackingDescr;
    }

    public void setTrackingDescr(String trackingDescr) {
        this.trackingDescr = trackingDescr;
    }

    public Date getTrackingTime() {
        return trackingTime;
    }

    public void setTrackingTime(Date trackingTime) {
        this.trackingTime = trackingTime;
    }
}
