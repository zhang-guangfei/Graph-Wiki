package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class OpsDrawupinventory implements Serializable {
    private String bathid;

    private String warehouseCode;

    private String customerNo;

    private Integer quantity;

    private Integer practicalityqty;

    private Integer differenceqty;

    private String unit;

    private Integer qaStatus;

    private String modelno;

    private Date creTime;

    private static final long serialVersionUID = 1L;

    public String getBathid() {
        return bathid;
    }

    public void setBathid(String bathid) {
        this.bathid = bathid == null ? null : bathid.trim();
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode == null ? null : warehouseCode.trim();
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo == null ? null : customerNo.trim();
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getPracticalityqty() {
        return practicalityqty;
    }

    public void setPracticalityqty(Integer practicalityqty) {
        this.practicalityqty = practicalityqty;
    }

    public Integer getDifferenceqty() {
        return differenceqty;
    }

    public void setDifferenceqty(Integer differenceqty) {
        this.differenceqty = differenceqty;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public Integer getQaStatus() {
        return qaStatus;
    }

    public void setQaStatus(Integer qaStatus) {
        this.qaStatus = qaStatus;
    }

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno == null ? null : modelno.trim();
    }

    public Date getCreTime() {
        return creTime;
    }

    public void setCreTime(Date creTime) {
        this.creTime = creTime;
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
        OpsDrawupinventory other = (OpsDrawupinventory) that;
        return (this.getBathid() == null ? other.getBathid() == null : this.getBathid().equals(other.getBathid()))
            && (this.getWarehouseCode() == null ? other.getWarehouseCode() == null : this.getWarehouseCode().equals(other.getWarehouseCode()))
            && (this.getCustomerNo() == null ? other.getCustomerNo() == null : this.getCustomerNo().equals(other.getCustomerNo()))
            && (this.getQuantity() == null ? other.getQuantity() == null : this.getQuantity().equals(other.getQuantity()))
            && (this.getPracticalityqty() == null ? other.getPracticalityqty() == null : this.getPracticalityqty().equals(other.getPracticalityqty()))
            && (this.getDifferenceqty() == null ? other.getDifferenceqty() == null : this.getDifferenceqty().equals(other.getDifferenceqty()))
            && (this.getUnit() == null ? other.getUnit() == null : this.getUnit().equals(other.getUnit()))
            && (this.getQaStatus() == null ? other.getQaStatus() == null : this.getQaStatus().equals(other.getQaStatus()))
            && (this.getModelno() == null ? other.getModelno() == null : this.getModelno().equals(other.getModelno()))
            && (this.getCreTime() == null ? other.getCreTime() == null : this.getCreTime().equals(other.getCreTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getBathid() == null) ? 0 : getBathid().hashCode());
        result = prime * result + ((getWarehouseCode() == null) ? 0 : getWarehouseCode().hashCode());
        result = prime * result + ((getCustomerNo() == null) ? 0 : getCustomerNo().hashCode());
        result = prime * result + ((getQuantity() == null) ? 0 : getQuantity().hashCode());
        result = prime * result + ((getPracticalityqty() == null) ? 0 : getPracticalityqty().hashCode());
        result = prime * result + ((getDifferenceqty() == null) ? 0 : getDifferenceqty().hashCode());
        result = prime * result + ((getUnit() == null) ? 0 : getUnit().hashCode());
        result = prime * result + ((getQaStatus() == null) ? 0 : getQaStatus().hashCode());
        result = prime * result + ((getModelno() == null) ? 0 : getModelno().hashCode());
        result = prime * result + ((getCreTime() == null) ? 0 : getCreTime().hashCode());
        return result;
    }
}