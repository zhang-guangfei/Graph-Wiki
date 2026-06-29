package com.sales.ops.db.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OpsPcoBak implements Serializable {
    private String pcoId;

    private Integer pcoSource;

    private String subOrderId;

    private String bindOrderId;

    private Integer pcoType;

    private Integer prodType;

    private String warehouseCode;

    private Integer pcoStatus;

    private String remark;

    private Integer delflag;

    private Date creTime;

    private String creator;

    private Date modifyTime;

    private String modifier;

    private String modelNo;

    private Integer qty;

    private String customerNo;

    private BigDecimal weight;

    private String orderId;

    private static final long serialVersionUID = 1L;

    public String getPcoId() {
        return pcoId;
    }

    public void setPcoId(String pcoId) {
        this.pcoId = pcoId == null ? null : pcoId.trim();
    }

    public Integer getPcoSource() {
        return pcoSource;
    }

    public void setPcoSource(Integer pcoSource) {
        this.pcoSource = pcoSource;
    }

    public String getSubOrderId() {
        return subOrderId;
    }

    public void setSubOrderId(String subOrderId) {
        this.subOrderId = subOrderId == null ? null : subOrderId.trim();
    }

    public String getBindOrderId() {
        return bindOrderId;
    }

    public void setBindOrderId(String bindOrderId) {
        this.bindOrderId = bindOrderId == null ? null : bindOrderId.trim();
    }

    public Integer getPcoType() {
        return pcoType;
    }

    public void setPcoType(Integer pcoType) {
        this.pcoType = pcoType;
    }

    public Integer getProdType() {
        return prodType;
    }

    public void setProdType(Integer prodType) {
        this.prodType = prodType;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode == null ? null : warehouseCode.trim();
    }

    public Integer getPcoStatus() {
        return pcoStatus;
    }

    public void setPcoStatus(Integer pcoStatus) {
        this.pcoStatus = pcoStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getDelflag() {
        return delflag;
    }

    public void setDelflag(Integer delflag) {
        this.delflag = delflag;
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

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier == null ? null : modifier.trim();
    }

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo == null ? null : modelNo.trim();
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo == null ? null : customerNo.trim();
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
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
        OpsPcoBak other = (OpsPcoBak) that;
        return (this.getPcoId() == null ? other.getPcoId() == null : this.getPcoId().equals(other.getPcoId()))
            && (this.getPcoSource() == null ? other.getPcoSource() == null : this.getPcoSource().equals(other.getPcoSource()))
            && (this.getSubOrderId() == null ? other.getSubOrderId() == null : this.getSubOrderId().equals(other.getSubOrderId()))
            && (this.getBindOrderId() == null ? other.getBindOrderId() == null : this.getBindOrderId().equals(other.getBindOrderId()))
            && (this.getPcoType() == null ? other.getPcoType() == null : this.getPcoType().equals(other.getPcoType()))
            && (this.getProdType() == null ? other.getProdType() == null : this.getProdType().equals(other.getProdType()))
            && (this.getWarehouseCode() == null ? other.getWarehouseCode() == null : this.getWarehouseCode().equals(other.getWarehouseCode()))
            && (this.getPcoStatus() == null ? other.getPcoStatus() == null : this.getPcoStatus().equals(other.getPcoStatus()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getDelflag() == null ? other.getDelflag() == null : this.getDelflag().equals(other.getDelflag()))
            && (this.getCreTime() == null ? other.getCreTime() == null : this.getCreTime().equals(other.getCreTime()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
            && (this.getModifyTime() == null ? other.getModifyTime() == null : this.getModifyTime().equals(other.getModifyTime()))
            && (this.getModifier() == null ? other.getModifier() == null : this.getModifier().equals(other.getModifier()))
            && (this.getModelNo() == null ? other.getModelNo() == null : this.getModelNo().equals(other.getModelNo()))
            && (this.getQty() == null ? other.getQty() == null : this.getQty().equals(other.getQty()))
            && (this.getCustomerNo() == null ? other.getCustomerNo() == null : this.getCustomerNo().equals(other.getCustomerNo()))
            && (this.getWeight() == null ? other.getWeight() == null : this.getWeight().equals(other.getWeight()))
            && (this.getOrderId() == null ? other.getOrderId() == null : this.getOrderId().equals(other.getOrderId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getPcoId() == null) ? 0 : getPcoId().hashCode());
        result = prime * result + ((getPcoSource() == null) ? 0 : getPcoSource().hashCode());
        result = prime * result + ((getSubOrderId() == null) ? 0 : getSubOrderId().hashCode());
        result = prime * result + ((getBindOrderId() == null) ? 0 : getBindOrderId().hashCode());
        result = prime * result + ((getPcoType() == null) ? 0 : getPcoType().hashCode());
        result = prime * result + ((getProdType() == null) ? 0 : getProdType().hashCode());
        result = prime * result + ((getWarehouseCode() == null) ? 0 : getWarehouseCode().hashCode());
        result = prime * result + ((getPcoStatus() == null) ? 0 : getPcoStatus().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getDelflag() == null) ? 0 : getDelflag().hashCode());
        result = prime * result + ((getCreTime() == null) ? 0 : getCreTime().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getModifyTime() == null) ? 0 : getModifyTime().hashCode());
        result = prime * result + ((getModifier() == null) ? 0 : getModifier().hashCode());
        result = prime * result + ((getModelNo() == null) ? 0 : getModelNo().hashCode());
        result = prime * result + ((getQty() == null) ? 0 : getQty().hashCode());
        result = prime * result + ((getCustomerNo() == null) ? 0 : getCustomerNo().hashCode());
        result = prime * result + ((getWeight() == null) ? 0 : getWeight().hashCode());
        result = prime * result + ((getOrderId() == null) ? 0 : getOrderId().hashCode());
        return result;
    }
}