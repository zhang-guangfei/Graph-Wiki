package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class OpsPoDestinationConfig implements Serializable {
    private String ordertype;

    private Integer producttype;

    private String customerno;

    private String smccode;

    private Date updatetime;

    private String supplierid;

    private String warehouseid;

    private String manufactureaccepter;

    private String hopereceivewarehouse;

    private String wmtag;

    private String subCode;

    private String transType;

    private static final long serialVersionUID = 1L;

    public String getOrdertype() {
        return ordertype;
    }

    public void setOrdertype(String ordertype) {
        this.ordertype = ordertype == null ? null : ordertype.trim();
    }

    public Integer getProducttype() {
        return producttype;
    }

    public void setProducttype(Integer producttype) {
        this.producttype = producttype;
    }

    public String getCustomerno() {
        return customerno;
    }

    public void setCustomerno(String customerno) {
        this.customerno = customerno == null ? null : customerno.trim();
    }

    public String getSmccode() {
        return smccode;
    }

    public void setSmccode(String smccode) {
        this.smccode = smccode == null ? null : smccode.trim();
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getSupplierid() {
        return supplierid;
    }

    public void setSupplierid(String supplierid) {
        this.supplierid = supplierid == null ? null : supplierid.trim();
    }

    public String getWarehouseid() {
        return warehouseid;
    }

    public void setWarehouseid(String warehouseid) {
        this.warehouseid = warehouseid == null ? null : warehouseid.trim();
    }

    public String getManufactureaccepter() {
        return manufactureaccepter;
    }

    public void setManufactureaccepter(String manufactureaccepter) {
        this.manufactureaccepter = manufactureaccepter == null ? null : manufactureaccepter.trim();
    }

    public String getHopereceivewarehouse() {
        return hopereceivewarehouse;
    }

    public void setHopereceivewarehouse(String hopereceivewarehouse) {
        this.hopereceivewarehouse = hopereceivewarehouse == null ? null : hopereceivewarehouse.trim();
    }

    public String getWmtag() {
        return wmtag;
    }

    public void setWmtag(String wmtag) {
        this.wmtag = wmtag == null ? null : wmtag.trim();
    }

    public String getSubCode() {
        return subCode;
    }

    public void setSubCode(String subCode) {
        this.subCode = subCode == null ? null : subCode.trim();
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType == null ? null : transType.trim();
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
        OpsPoDestinationConfig other = (OpsPoDestinationConfig) that;
        return (this.getOrdertype() == null ? other.getOrdertype() == null : this.getOrdertype().equals(other.getOrdertype()))
            && (this.getProducttype() == null ? other.getProducttype() == null : this.getProducttype().equals(other.getProducttype()))
            && (this.getCustomerno() == null ? other.getCustomerno() == null : this.getCustomerno().equals(other.getCustomerno()))
            && (this.getSmccode() == null ? other.getSmccode() == null : this.getSmccode().equals(other.getSmccode()))
            && (this.getUpdatetime() == null ? other.getUpdatetime() == null : this.getUpdatetime().equals(other.getUpdatetime()))
            && (this.getSupplierid() == null ? other.getSupplierid() == null : this.getSupplierid().equals(other.getSupplierid()))
            && (this.getWarehouseid() == null ? other.getWarehouseid() == null : this.getWarehouseid().equals(other.getWarehouseid()))
            && (this.getManufactureaccepter() == null ? other.getManufactureaccepter() == null : this.getManufactureaccepter().equals(other.getManufactureaccepter()))
            && (this.getHopereceivewarehouse() == null ? other.getHopereceivewarehouse() == null : this.getHopereceivewarehouse().equals(other.getHopereceivewarehouse()))
            && (this.getWmtag() == null ? other.getWmtag() == null : this.getWmtag().equals(other.getWmtag()))
            && (this.getSubCode() == null ? other.getSubCode() == null : this.getSubCode().equals(other.getSubCode()))
            && (this.getTransType() == null ? other.getTransType() == null : this.getTransType().equals(other.getTransType()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getOrdertype() == null) ? 0 : getOrdertype().hashCode());
        result = prime * result + ((getProducttype() == null) ? 0 : getProducttype().hashCode());
        result = prime * result + ((getCustomerno() == null) ? 0 : getCustomerno().hashCode());
        result = prime * result + ((getSmccode() == null) ? 0 : getSmccode().hashCode());
        result = prime * result + ((getUpdatetime() == null) ? 0 : getUpdatetime().hashCode());
        result = prime * result + ((getSupplierid() == null) ? 0 : getSupplierid().hashCode());
        result = prime * result + ((getWarehouseid() == null) ? 0 : getWarehouseid().hashCode());
        result = prime * result + ((getManufactureaccepter() == null) ? 0 : getManufactureaccepter().hashCode());
        result = prime * result + ((getHopereceivewarehouse() == null) ? 0 : getHopereceivewarehouse().hashCode());
        result = prime * result + ((getWmtag() == null) ? 0 : getWmtag().hashCode());
        result = prime * result + ((getSubCode() == null) ? 0 : getSubCode().hashCode());
        result = prime * result + ((getTransType() == null) ? 0 : getTransType().hashCode());
        return result;
    }
}