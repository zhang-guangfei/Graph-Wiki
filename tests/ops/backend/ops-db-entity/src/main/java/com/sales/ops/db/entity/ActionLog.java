package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class ActionLog implements Serializable {
    private Long id;

    private String userNo;

    private String clientIp;

    private String clientAgent;

    private String requestUrl;

    private String requestDescription;

    private String requestData;

    private Date requestTime;

    private String responseUrl;

    private Boolean responseResult;

    private String reponseData;

    private Date reponseTime;

    private Date createTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo == null ? null : userNo.trim();
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp == null ? null : clientIp.trim();
    }

    public String getClientAgent() {
        return clientAgent;
    }

    public void setClientAgent(String clientAgent) {
        this.clientAgent = clientAgent == null ? null : clientAgent.trim();
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl == null ? null : requestUrl.trim();
    }

    public String getRequestDescription() {
        return requestDescription;
    }

    public void setRequestDescription(String requestDescription) {
        this.requestDescription = requestDescription == null ? null : requestDescription.trim();
    }

    public String getRequestData() {
        return requestData;
    }

    public void setRequestData(String requestData) {
        this.requestData = requestData == null ? null : requestData.trim();
    }

    public Date getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Date requestTime) {
        this.requestTime = requestTime;
    }

    public String getResponseUrl() {
        return responseUrl;
    }

    public void setResponseUrl(String responseUrl) {
        this.responseUrl = responseUrl == null ? null : responseUrl.trim();
    }

    public Boolean getResponseResult() {
        return responseResult;
    }

    public void setResponseResult(Boolean responseResult) {
        this.responseResult = responseResult;
    }

    public String getReponseData() {
        return reponseData;
    }

    public void setReponseData(String reponseData) {
        this.reponseData = reponseData == null ? null : reponseData.trim();
    }

    public Date getReponseTime() {
        return reponseTime;
    }

    public void setReponseTime(Date reponseTime) {
        this.reponseTime = reponseTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        ActionLog other = (ActionLog) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUserNo() == null ? other.getUserNo() == null : this.getUserNo().equals(other.getUserNo()))
            && (this.getClientIp() == null ? other.getClientIp() == null : this.getClientIp().equals(other.getClientIp()))
            && (this.getClientAgent() == null ? other.getClientAgent() == null : this.getClientAgent().equals(other.getClientAgent()))
            && (this.getRequestUrl() == null ? other.getRequestUrl() == null : this.getRequestUrl().equals(other.getRequestUrl()))
            && (this.getRequestDescription() == null ? other.getRequestDescription() == null : this.getRequestDescription().equals(other.getRequestDescription()))
            && (this.getRequestData() == null ? other.getRequestData() == null : this.getRequestData().equals(other.getRequestData()))
            && (this.getRequestTime() == null ? other.getRequestTime() == null : this.getRequestTime().equals(other.getRequestTime()))
            && (this.getResponseUrl() == null ? other.getResponseUrl() == null : this.getResponseUrl().equals(other.getResponseUrl()))
            && (this.getResponseResult() == null ? other.getResponseResult() == null : this.getResponseResult().equals(other.getResponseResult()))
            && (this.getReponseData() == null ? other.getReponseData() == null : this.getReponseData().equals(other.getReponseData()))
            && (this.getReponseTime() == null ? other.getReponseTime() == null : this.getReponseTime().equals(other.getReponseTime()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUserNo() == null) ? 0 : getUserNo().hashCode());
        result = prime * result + ((getClientIp() == null) ? 0 : getClientIp().hashCode());
        result = prime * result + ((getClientAgent() == null) ? 0 : getClientAgent().hashCode());
        result = prime * result + ((getRequestUrl() == null) ? 0 : getRequestUrl().hashCode());
        result = prime * result + ((getRequestDescription() == null) ? 0 : getRequestDescription().hashCode());
        result = prime * result + ((getRequestData() == null) ? 0 : getRequestData().hashCode());
        result = prime * result + ((getRequestTime() == null) ? 0 : getRequestTime().hashCode());
        result = prime * result + ((getResponseUrl() == null) ? 0 : getResponseUrl().hashCode());
        result = prime * result + ((getResponseResult() == null) ? 0 : getResponseResult().hashCode());
        result = prime * result + ((getReponseData() == null) ? 0 : getReponseData().hashCode());
        result = prime * result + ((getReponseTime() == null) ? 0 : getReponseTime().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        return result;
    }
}