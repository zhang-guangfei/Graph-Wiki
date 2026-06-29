package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class OpsCoordinate implements Serializable {
    private Long id;

    private String remark;

    private String message;

    private Date usetime;

    private Boolean issuccess;

    private String apiname;

    private String type;

    private String returnMessage;

    private Long costTime;

    private Date createTime;

    private Date updateTime;

    private String createUser;

    private String updateUser;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message == null ? null : message.trim();
    }

    public Date getUsetime() {
        return usetime;
    }

    public void setUsetime(Date usetime) {
        this.usetime = usetime;
    }

    public Boolean getIssuccess() {
        return issuccess;
    }

    public void setIssuccess(Boolean issuccess) {
        this.issuccess = issuccess;
    }

    public String getApiname() {
        return apiname;
    }

    public void setApiname(String apiname) {
        this.apiname = apiname == null ? null : apiname.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getReturnMessage() {
        return returnMessage;
    }

    public void setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage == null ? null : returnMessage.trim();
    }

    public Long getCostTime() {
        return costTime;
    }

    public void setCostTime(Long costTime) {
        this.costTime = costTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
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
        OpsCoordinate other = (OpsCoordinate) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getMessage() == null ? other.getMessage() == null : this.getMessage().equals(other.getMessage()))
            && (this.getUsetime() == null ? other.getUsetime() == null : this.getUsetime().equals(other.getUsetime()))
            && (this.getIssuccess() == null ? other.getIssuccess() == null : this.getIssuccess().equals(other.getIssuccess()))
            && (this.getApiname() == null ? other.getApiname() == null : this.getApiname().equals(other.getApiname()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getReturnMessage() == null ? other.getReturnMessage() == null : this.getReturnMessage().equals(other.getReturnMessage()))
            && (this.getCostTime() == null ? other.getCostTime() == null : this.getCostTime().equals(other.getCostTime()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getCreateUser() == null ? other.getCreateUser() == null : this.getCreateUser().equals(other.getCreateUser()))
            && (this.getUpdateUser() == null ? other.getUpdateUser() == null : this.getUpdateUser().equals(other.getUpdateUser()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getMessage() == null) ? 0 : getMessage().hashCode());
        result = prime * result + ((getUsetime() == null) ? 0 : getUsetime().hashCode());
        result = prime * result + ((getIssuccess() == null) ? 0 : getIssuccess().hashCode());
        result = prime * result + ((getApiname() == null) ? 0 : getApiname().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getReturnMessage() == null) ? 0 : getReturnMessage().hashCode());
        result = prime * result + ((getCostTime() == null) ? 0 : getCostTime().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getCreateUser() == null) ? 0 : getCreateUser().hashCode());
        result = prime * result + ((getUpdateUser() == null) ? 0 : getUpdateUser().hashCode());
        return result;
    }
}