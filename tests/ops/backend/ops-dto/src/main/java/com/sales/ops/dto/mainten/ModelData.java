package com.sales.ops.dto.mainten;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class ModelData implements Serializable  {
    // 主键Id
    private Integer bomId;
    // 完整型号
    private String wholeModelNo;
    // 拆分型号
    private String splitModelNo;
    // 型号数量
    private Integer quantity;
    // 区分
    private Boolean classify;
    // 拆分型号
    private boolean priorityComplete;
    // 修改人
    private String updateUser;
    // 修改日期
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date updateTime;
    // 版本号
    private String versionNum;
    // 是否有效
    private boolean isValid;
    // 登录日期
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date loginDate;

    public Integer getBomId() {
        return bomId;
    }

    public void setBomId(Integer bomId) {
        this.bomId = bomId;
    }

    public String getWholeModelNo() {
        return wholeModelNo;
    }

    public void setWholeModelNo(String wholeModelNo) {
        this.wholeModelNo = wholeModelNo;
    }

    public String getSplitModelNo() {
        return splitModelNo;
    }

    public void setSplitModelNo(String splitModelNo) {
        this.splitModelNo = splitModelNo;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public boolean isClassify() {
        return classify;
    }

    public void setClassify(boolean classify) {
        this.classify = classify;
    }

    public boolean isPriorityComplete() {
        return priorityComplete;
    }

    public void setPriorityComplete(boolean priorityComplete) {
        this.priorityComplete = priorityComplete;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(String versionNum) {
        this.versionNum = versionNum;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    @Override
    public String toString() {
        return "ModelData{" +
                "bomId=" + bomId +
                ", wholeModelNo='" + wholeModelNo + '\'' +
                ", splitModelNo='" + splitModelNo + '\'' +
                ", quantity=" + quantity +
                ", classify='" + classify + '\'' +
                ", priorityComplete=" + priorityComplete +
                ", updateUser='" + updateUser + '\'' +
                ", updateTime=" + updateTime +
                ", versionNum='" + versionNum + '\'' +
                ", isValid=" + isValid +
                ", loginDate=" + loginDate +
                '}';
    }
}
