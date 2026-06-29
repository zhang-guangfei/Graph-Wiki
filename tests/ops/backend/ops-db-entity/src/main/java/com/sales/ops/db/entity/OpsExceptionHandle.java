package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class OpsExceptionHandle implements Serializable {
    private Long id;

    private String businessType;

    private String errType;

    private String apiName;

    private Integer status;

    private Integer handleStatus;

    private String msgId;

    private String inputMsg;

    private String outputMsg;

    private String parameter1;

    private String parameter2;

    private String parameter3;

    private String parameter4;

    private String parameter5;

    private String parameter6;

    private String parameter7;

    private String parameter8;

    private String parameter9;

    private String remark;

    private String createUser;

    private Date createTime;

    private String updateUser;

    private Date updateTime;

    private Integer readQty;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType == null ? null : businessType.trim();
    }

    public String getErrType() {
        return errType;
    }

    public void setErrType(String errType) {
        this.errType = errType == null ? null : errType.trim();
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName == null ? null : apiName.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getHandleStatus() {
        return handleStatus;
    }

    public void setHandleStatus(Integer handleStatus) {
        this.handleStatus = handleStatus;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId == null ? null : msgId.trim();
    }

    public String getInputMsg() {
        return inputMsg;
    }

    public void setInputMsg(String inputMsg) {
        this.inputMsg = inputMsg == null ? null : inputMsg.trim();
    }

    public String getOutputMsg() {
        return outputMsg;
    }

    public void setOutputMsg(String outputMsg) {
        this.outputMsg = outputMsg == null ? null : outputMsg.trim();
    }

    public String getParameter1() {
        return parameter1;
    }

    public void setParameter1(String parameter1) {
        this.parameter1 = parameter1 == null ? null : parameter1.trim();
    }

    public String getParameter2() {
        return parameter2;
    }

    public void setParameter2(String parameter2) {
        this.parameter2 = parameter2 == null ? null : parameter2.trim();
    }

    public String getParameter3() {
        return parameter3;
    }

    public void setParameter3(String parameter3) {
        this.parameter3 = parameter3 == null ? null : parameter3.trim();
    }

    public String getParameter4() {
        return parameter4;
    }

    public void setParameter4(String parameter4) {
        this.parameter4 = parameter4 == null ? null : parameter4.trim();
    }

    public String getParameter5() {
        return parameter5;
    }

    public void setParameter5(String parameter5) {
        this.parameter5 = parameter5 == null ? null : parameter5.trim();
    }

    public String getParameter6() {
        return parameter6;
    }

    public void setParameter6(String parameter6) {
        this.parameter6 = parameter6 == null ? null : parameter6.trim();
    }

    public String getParameter7() {
        return parameter7;
    }

    public void setParameter7(String parameter7) {
        this.parameter7 = parameter7 == null ? null : parameter7.trim();
    }

    public String getParameter8() {
        return parameter8;
    }

    public void setParameter8(String parameter8) {
        this.parameter8 = parameter8 == null ? null : parameter8.trim();
    }

    public String getParameter9() {
        return parameter9;
    }

    public void setParameter9(String parameter9) {
        this.parameter9 = parameter9 == null ? null : parameter9.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getReadQty() {
        return readQty;
    }

    public void setReadQty(Integer readQty) {
        this.readQty = readQty;
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
        OpsExceptionHandle other = (OpsExceptionHandle) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getBusinessType() == null ? other.getBusinessType() == null : this.getBusinessType().equals(other.getBusinessType()))
            && (this.getErrType() == null ? other.getErrType() == null : this.getErrType().equals(other.getErrType()))
            && (this.getApiName() == null ? other.getApiName() == null : this.getApiName().equals(other.getApiName()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getHandleStatus() == null ? other.getHandleStatus() == null : this.getHandleStatus().equals(other.getHandleStatus()))
            && (this.getMsgId() == null ? other.getMsgId() == null : this.getMsgId().equals(other.getMsgId()))
            && (this.getInputMsg() == null ? other.getInputMsg() == null : this.getInputMsg().equals(other.getInputMsg()))
            && (this.getOutputMsg() == null ? other.getOutputMsg() == null : this.getOutputMsg().equals(other.getOutputMsg()))
            && (this.getParameter1() == null ? other.getParameter1() == null : this.getParameter1().equals(other.getParameter1()))
            && (this.getParameter2() == null ? other.getParameter2() == null : this.getParameter2().equals(other.getParameter2()))
            && (this.getParameter3() == null ? other.getParameter3() == null : this.getParameter3().equals(other.getParameter3()))
            && (this.getParameter4() == null ? other.getParameter4() == null : this.getParameter4().equals(other.getParameter4()))
            && (this.getParameter5() == null ? other.getParameter5() == null : this.getParameter5().equals(other.getParameter5()))
            && (this.getParameter6() == null ? other.getParameter6() == null : this.getParameter6().equals(other.getParameter6()))
            && (this.getParameter7() == null ? other.getParameter7() == null : this.getParameter7().equals(other.getParameter7()))
            && (this.getParameter8() == null ? other.getParameter8() == null : this.getParameter8().equals(other.getParameter8()))
            && (this.getParameter9() == null ? other.getParameter9() == null : this.getParameter9().equals(other.getParameter9()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getCreateUser() == null ? other.getCreateUser() == null : this.getCreateUser().equals(other.getCreateUser()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateUser() == null ? other.getUpdateUser() == null : this.getUpdateUser().equals(other.getUpdateUser()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getReadQty() == null ? other.getReadQty() == null : this.getReadQty().equals(other.getReadQty()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getBusinessType() == null) ? 0 : getBusinessType().hashCode());
        result = prime * result + ((getErrType() == null) ? 0 : getErrType().hashCode());
        result = prime * result + ((getApiName() == null) ? 0 : getApiName().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getHandleStatus() == null) ? 0 : getHandleStatus().hashCode());
        result = prime * result + ((getMsgId() == null) ? 0 : getMsgId().hashCode());
        result = prime * result + ((getInputMsg() == null) ? 0 : getInputMsg().hashCode());
        result = prime * result + ((getOutputMsg() == null) ? 0 : getOutputMsg().hashCode());
        result = prime * result + ((getParameter1() == null) ? 0 : getParameter1().hashCode());
        result = prime * result + ((getParameter2() == null) ? 0 : getParameter2().hashCode());
        result = prime * result + ((getParameter3() == null) ? 0 : getParameter3().hashCode());
        result = prime * result + ((getParameter4() == null) ? 0 : getParameter4().hashCode());
        result = prime * result + ((getParameter5() == null) ? 0 : getParameter5().hashCode());
        result = prime * result + ((getParameter6() == null) ? 0 : getParameter6().hashCode());
        result = prime * result + ((getParameter7() == null) ? 0 : getParameter7().hashCode());
        result = prime * result + ((getParameter8() == null) ? 0 : getParameter8().hashCode());
        result = prime * result + ((getParameter9() == null) ? 0 : getParameter9().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getCreateUser() == null) ? 0 : getCreateUser().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateUser() == null) ? 0 : getUpdateUser().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getReadQty() == null) ? 0 : getReadQty().hashCode());
        return result;
    }
}