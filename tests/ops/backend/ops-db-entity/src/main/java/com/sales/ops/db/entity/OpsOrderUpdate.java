package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class OpsOrderUpdate implements Serializable {
    private Long id;

    private String orderid;

    private String orderItem;

    private String orderType;

    private Integer range;

    private String sCkType;

    private String tCkType;

    private String sPackageType;

    private String tPackageType;

    private Date sDeliveryDate;

    private Date tDeliveryDate;

    private String sAddress;

    private String tAddress;

    private String reason;

    private String success;

    private Date creTime;

    private String creator;

    private String sSpecialflag;

    private Integer tSpecialflag;

    private String sCarried;

    private String tCarried;

    private Boolean assmodel;

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

    public String getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(String orderItem) {
        this.orderItem = orderItem == null ? null : orderItem.trim();
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType == null ? null : orderType.trim();
    }

    public Integer getRange() {
        return range;
    }

    public void setRange(Integer range) {
        this.range = range;
    }

    public String getsCkType() {
        return sCkType;
    }

    public void setsCkType(String sCkType) {
        this.sCkType = sCkType == null ? null : sCkType.trim();
    }

    public String gettCkType() {
        return tCkType;
    }

    public void settCkType(String tCkType) {
        this.tCkType = tCkType == null ? null : tCkType.trim();
    }

    public String getsPackageType() {
        return sPackageType;
    }

    public void setsPackageType(String sPackageType) {
        this.sPackageType = sPackageType == null ? null : sPackageType.trim();
    }

    public String gettPackageType() {
        return tPackageType;
    }

    public void settPackageType(String tPackageType) {
        this.tPackageType = tPackageType == null ? null : tPackageType.trim();
    }

    public Date getsDeliveryDate() {
        return sDeliveryDate;
    }

    public void setsDeliveryDate(Date sDeliveryDate) {
        this.sDeliveryDate = sDeliveryDate;
    }

    public Date gettDeliveryDate() {
        return tDeliveryDate;
    }

    public void settDeliveryDate(Date tDeliveryDate) {
        this.tDeliveryDate = tDeliveryDate;
    }

    public String getsAddress() {
        return sAddress;
    }

    public void setsAddress(String sAddress) {
        this.sAddress = sAddress == null ? null : sAddress.trim();
    }

    public String gettAddress() {
        return tAddress;
    }

    public void settAddress(String tAddress) {
        this.tAddress = tAddress == null ? null : tAddress.trim();
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success == null ? null : success.trim();
    }

    public Date getCreTime() {
        return creTime;
    }

    public void setCreTime(Date creTime) {
        this.creTime = creTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public String getsSpecialflag() {
        return sSpecialflag;
    }

    public void setsSpecialflag(String sSpecialflag) {
        this.sSpecialflag = sSpecialflag == null ? null : sSpecialflag.trim();
    }

    public Integer gettSpecialflag() {
        return tSpecialflag;
    }

    public void settSpecialflag(Integer tSpecialflag) {
        this.tSpecialflag = tSpecialflag;
    }

    public String getsCarried() {
        return sCarried;
    }

    public void setsCarried(String sCarried) {
        this.sCarried = sCarried == null ? null : sCarried.trim();
    }

    public String gettCarried() {
        return tCarried;
    }

    public void settCarried(String tCarried) {
        this.tCarried = tCarried == null ? null : tCarried.trim();
    }

    public Boolean getAssmodel() {
        return assmodel;
    }

    public void setAssmodel(Boolean assmodel) {
        this.assmodel = assmodel;
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
        OpsOrderUpdate other = (OpsOrderUpdate) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getOrderid() == null ? other.getOrderid() == null : this.getOrderid().equals(other.getOrderid()))
            && (this.getOrderItem() == null ? other.getOrderItem() == null : this.getOrderItem().equals(other.getOrderItem()))
            && (this.getOrderType() == null ? other.getOrderType() == null : this.getOrderType().equals(other.getOrderType()))
            && (this.getRange() == null ? other.getRange() == null : this.getRange().equals(other.getRange()))
            && (this.getsCkType() == null ? other.getsCkType() == null : this.getsCkType().equals(other.getsCkType()))
            && (this.gettCkType() == null ? other.gettCkType() == null : this.gettCkType().equals(other.gettCkType()))
            && (this.getsPackageType() == null ? other.getsPackageType() == null : this.getsPackageType().equals(other.getsPackageType()))
            && (this.gettPackageType() == null ? other.gettPackageType() == null : this.gettPackageType().equals(other.gettPackageType()))
            && (this.getsDeliveryDate() == null ? other.getsDeliveryDate() == null : this.getsDeliveryDate().equals(other.getsDeliveryDate()))
            && (this.gettDeliveryDate() == null ? other.gettDeliveryDate() == null : this.gettDeliveryDate().equals(other.gettDeliveryDate()))
            && (this.getsAddress() == null ? other.getsAddress() == null : this.getsAddress().equals(other.getsAddress()))
            && (this.gettAddress() == null ? other.gettAddress() == null : this.gettAddress().equals(other.gettAddress()))
            && (this.getReason() == null ? other.getReason() == null : this.getReason().equals(other.getReason()))
            && (this.getSuccess() == null ? other.getSuccess() == null : this.getSuccess().equals(other.getSuccess()))
            && (this.getCreTime() == null ? other.getCreTime() == null : this.getCreTime().equals(other.getCreTime()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
            && (this.getsSpecialflag() == null ? other.getsSpecialflag() == null : this.getsSpecialflag().equals(other.getsSpecialflag()))
            && (this.gettSpecialflag() == null ? other.gettSpecialflag() == null : this.gettSpecialflag().equals(other.gettSpecialflag()))
            && (this.getsCarried() == null ? other.getsCarried() == null : this.getsCarried().equals(other.getsCarried()))
            && (this.gettCarried() == null ? other.gettCarried() == null : this.gettCarried().equals(other.gettCarried()))
            && (this.getAssmodel() == null ? other.getAssmodel() == null : this.getAssmodel().equals(other.getAssmodel()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getOrderid() == null) ? 0 : getOrderid().hashCode());
        result = prime * result + ((getOrderItem() == null) ? 0 : getOrderItem().hashCode());
        result = prime * result + ((getOrderType() == null) ? 0 : getOrderType().hashCode());
        result = prime * result + ((getRange() == null) ? 0 : getRange().hashCode());
        result = prime * result + ((getsCkType() == null) ? 0 : getsCkType().hashCode());
        result = prime * result + ((gettCkType() == null) ? 0 : gettCkType().hashCode());
        result = prime * result + ((getsPackageType() == null) ? 0 : getsPackageType().hashCode());
        result = prime * result + ((gettPackageType() == null) ? 0 : gettPackageType().hashCode());
        result = prime * result + ((getsDeliveryDate() == null) ? 0 : getsDeliveryDate().hashCode());
        result = prime * result + ((gettDeliveryDate() == null) ? 0 : gettDeliveryDate().hashCode());
        result = prime * result + ((getsAddress() == null) ? 0 : getsAddress().hashCode());
        result = prime * result + ((gettAddress() == null) ? 0 : gettAddress().hashCode());
        result = prime * result + ((getReason() == null) ? 0 : getReason().hashCode());
        result = prime * result + ((getSuccess() == null) ? 0 : getSuccess().hashCode());
        result = prime * result + ((getCreTime() == null) ? 0 : getCreTime().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getsSpecialflag() == null) ? 0 : getsSpecialflag().hashCode());
        result = prime * result + ((gettSpecialflag() == null) ? 0 : gettSpecialflag().hashCode());
        result = prime * result + ((getsCarried() == null) ? 0 : getsCarried().hashCode());
        result = prime * result + ((gettCarried() == null) ? 0 : gettCarried().hashCode());
        result = prime * result + ((getAssmodel() == null) ? 0 : getAssmodel().hashCode());
        return result;
    }
}