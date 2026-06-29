package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class OrderStatedetail extends OrderStatedetailKey implements Serializable {
    private Long id;

    private String dateName;

    private Integer stateCode;

    private Integer changeTimes;

    private Date stateDate;

    private String stateDes;

    private Date createTime;

    private Date updateTime;

    private Date mindate;

    private Date maxdate;

    private String provider;

    private Integer delayday;

    private String remark;

    private Date firstdate;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDateName() {
        return dateName;
    }

    public void setDateName(String dateName) {
        this.dateName = dateName == null ? null : dateName.trim();
    }

    public Integer getStateCode() {
        return stateCode;
    }

    public void setStateCode(Integer stateCode) {
        this.stateCode = stateCode;
    }

    public Integer getChangeTimes() {
        return changeTimes;
    }

    public void setChangeTimes(Integer changeTimes) {
        this.changeTimes = changeTimes;
    }

    public Date getStateDate() {
        return stateDate;
    }

    public void setStateDate(Date stateDate) {
        this.stateDate = stateDate;
    }

    public String getStateDes() {
        return stateDes;
    }

    public void setStateDes(String stateDes) {
        this.stateDes = stateDes == null ? null : stateDes.trim();
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

    public Date getMindate() {
        return mindate;
    }

    public void setMindate(Date mindate) {
        this.mindate = mindate;
    }

    public Date getMaxdate() {
        return maxdate;
    }

    public void setMaxdate(Date maxdate) {
        this.maxdate = maxdate;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider == null ? null : provider.trim();
    }

    public Integer getDelayday() {
        return delayday;
    }

    public void setDelayday(Integer delayday) {
        this.delayday = delayday;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getFirstdate() {
        return firstdate;
    }

    public void setFirstdate(Date firstdate) {
        this.firstdate = firstdate;
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
        OrderStatedetail other = (OrderStatedetail) that;
        return (this.getOrderNo() == null ? other.getOrderNo() == null : this.getOrderNo().equals(other.getOrderNo()))
            && (this.getDateType() == null ? other.getDateType() == null : this.getDateType().equals(other.getDateType()))
            && (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getDateName() == null ? other.getDateName() == null : this.getDateName().equals(other.getDateName()))
            && (this.getStateCode() == null ? other.getStateCode() == null : this.getStateCode().equals(other.getStateCode()))
            && (this.getChangeTimes() == null ? other.getChangeTimes() == null : this.getChangeTimes().equals(other.getChangeTimes()))
            && (this.getStateDate() == null ? other.getStateDate() == null : this.getStateDate().equals(other.getStateDate()))
            && (this.getStateDes() == null ? other.getStateDes() == null : this.getStateDes().equals(other.getStateDes()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getMindate() == null ? other.getMindate() == null : this.getMindate().equals(other.getMindate()))
            && (this.getMaxdate() == null ? other.getMaxdate() == null : this.getMaxdate().equals(other.getMaxdate()))
            && (this.getProvider() == null ? other.getProvider() == null : this.getProvider().equals(other.getProvider()))
            && (this.getDelayday() == null ? other.getDelayday() == null : this.getDelayday().equals(other.getDelayday()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getFirstdate() == null ? other.getFirstdate() == null : this.getFirstdate().equals(other.getFirstdate()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getOrderNo() == null) ? 0 : getOrderNo().hashCode());
        result = prime * result + ((getDateType() == null) ? 0 : getDateType().hashCode());
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getDateName() == null) ? 0 : getDateName().hashCode());
        result = prime * result + ((getStateCode() == null) ? 0 : getStateCode().hashCode());
        result = prime * result + ((getChangeTimes() == null) ? 0 : getChangeTimes().hashCode());
        result = prime * result + ((getStateDate() == null) ? 0 : getStateDate().hashCode());
        result = prime * result + ((getStateDes() == null) ? 0 : getStateDes().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getMindate() == null) ? 0 : getMindate().hashCode());
        result = prime * result + ((getMaxdate() == null) ? 0 : getMaxdate().hashCode());
        result = prime * result + ((getProvider() == null) ? 0 : getProvider().hashCode());
        result = prime * result + ((getDelayday() == null) ? 0 : getDelayday().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getFirstdate() == null) ? 0 : getFirstdate().hashCode());
        return result;
    }
}