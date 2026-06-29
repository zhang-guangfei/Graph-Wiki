package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class OpsOrderUpdateLog implements Serializable {
    private Long id;

    private String orderid;

    private Integer orderItem;

    private String params;

    private String result;

    private Boolean master;

    private String dlvEntire;

    private String dlvType;

    private Date dlvDate;

    private Date wmsDlvData;

    private Boolean dlvSpecial;

    private Boolean dlvSelf;

    private Boolean dlvSplit;

    private String dlvAddress;

    private Date createTime;

    private Boolean changeFlag;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid == null ? null : orderid.trim();
    }

    public Integer getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(Integer orderItem) {
        this.orderItem = orderItem;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params == null ? null : params.trim();
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result == null ? null : result.trim();
    }

    public Boolean getMaster() {
        return master;
    }

    public void setMaster(Boolean master) {
        this.master = master;
    }

    public String getDlvEntire() {
        return dlvEntire;
    }

    public void setDlvEntire(String dlvEntire) {
        this.dlvEntire = dlvEntire == null ? null : dlvEntire.trim();
    }

    public String getDlvType() {
        return dlvType;
    }

    public void setDlvType(String dlvType) {
        this.dlvType = dlvType == null ? null : dlvType.trim();
    }

    public Date getDlvDate() {
        return dlvDate;
    }

    public void setDlvDate(Date dlvDate) {
        this.dlvDate = dlvDate;
    }

    public Date getWmsDlvData() {
        return wmsDlvData;
    }

    public void setWmsDlvData(Date wmsDlvData) {
        this.wmsDlvData = wmsDlvData;
    }

    public Boolean getDlvSpecial() {
        return dlvSpecial;
    }

    public void setDlvSpecial(Boolean dlvSpecial) {
        this.dlvSpecial = dlvSpecial;
    }

    public Boolean getDlvSelf() {
        return dlvSelf;
    }

    public void setDlvSelf(Boolean dlvSelf) {
        this.dlvSelf = dlvSelf;
    }

    public Boolean getDlvSplit() {
        return dlvSplit;
    }

    public void setDlvSplit(Boolean dlvSplit) {
        this.dlvSplit = dlvSplit;
    }

    public String getDlvAddress() {
        return dlvAddress;
    }

    public void setDlvAddress(String dlvAddress) {
        this.dlvAddress = dlvAddress == null ? null : dlvAddress.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Boolean getChangeFlag() {
        return changeFlag;
    }

    public void setChangeFlag(Boolean changeFlag) {
        this.changeFlag = changeFlag;
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
        OpsOrderUpdateLog other = (OpsOrderUpdateLog) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getOrderid() == null ? other.getOrderid() == null : this.getOrderid().equals(other.getOrderid()))
            && (this.getOrderItem() == null ? other.getOrderItem() == null : this.getOrderItem().equals(other.getOrderItem()))
            && (this.getParams() == null ? other.getParams() == null : this.getParams().equals(other.getParams()))
            && (this.getResult() == null ? other.getResult() == null : this.getResult().equals(other.getResult()))
            && (this.getMaster() == null ? other.getMaster() == null : this.getMaster().equals(other.getMaster()))
            && (this.getDlvEntire() == null ? other.getDlvEntire() == null : this.getDlvEntire().equals(other.getDlvEntire()))
            && (this.getDlvType() == null ? other.getDlvType() == null : this.getDlvType().equals(other.getDlvType()))
            && (this.getDlvDate() == null ? other.getDlvDate() == null : this.getDlvDate().equals(other.getDlvDate()))
            && (this.getWmsDlvData() == null ? other.getWmsDlvData() == null : this.getWmsDlvData().equals(other.getWmsDlvData()))
            && (this.getDlvSpecial() == null ? other.getDlvSpecial() == null : this.getDlvSpecial().equals(other.getDlvSpecial()))
            && (this.getDlvSelf() == null ? other.getDlvSelf() == null : this.getDlvSelf().equals(other.getDlvSelf()))
            && (this.getDlvSplit() == null ? other.getDlvSplit() == null : this.getDlvSplit().equals(other.getDlvSplit()))
            && (this.getDlvAddress() == null ? other.getDlvAddress() == null : this.getDlvAddress().equals(other.getDlvAddress()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getChangeFlag() == null ? other.getChangeFlag() == null : this.getChangeFlag().equals(other.getChangeFlag()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getOrderid() == null) ? 0 : getOrderid().hashCode());
        result = prime * result + ((getOrderItem() == null) ? 0 : getOrderItem().hashCode());
        result = prime * result + ((getParams() == null) ? 0 : getParams().hashCode());
        result = prime * result + ((getResult() == null) ? 0 : getResult().hashCode());
        result = prime * result + ((getMaster() == null) ? 0 : getMaster().hashCode());
        result = prime * result + ((getDlvEntire() == null) ? 0 : getDlvEntire().hashCode());
        result = prime * result + ((getDlvType() == null) ? 0 : getDlvType().hashCode());
        result = prime * result + ((getDlvDate() == null) ? 0 : getDlvDate().hashCode());
        result = prime * result + ((getWmsDlvData() == null) ? 0 : getWmsDlvData().hashCode());
        result = prime * result + ((getDlvSpecial() == null) ? 0 : getDlvSpecial().hashCode());
        result = prime * result + ((getDlvSelf() == null) ? 0 : getDlvSelf().hashCode());
        result = prime * result + ((getDlvSplit() == null) ? 0 : getDlvSplit().hashCode());
        result = prime * result + ((getDlvAddress() == null) ? 0 : getDlvAddress().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getChangeFlag() == null) ? 0 : getChangeFlag().hashCode());
        return result;
    }
}