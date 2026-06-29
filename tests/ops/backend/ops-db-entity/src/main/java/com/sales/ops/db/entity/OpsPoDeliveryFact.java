package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class OpsPoDeliveryFact implements Serializable {
    private Long id;

    private Long sourceId;

    private String orderNo;

    private Integer itemNo;

    private Integer splitNo;

    private String modelNo;

    private Integer quantity;

    private String transtypeCode;

    private Date deliveryTimeH;

    private Date deliveryTimeW;

    private Date deliveryTimeA;

    private Long invoiceId;

    private String invoiceNo;

    private Boolean delFlag;

    private Date createTime;

    private String creator;

    private Date updateTime;

    private String updator;

    private String remark;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public Integer getItemNo() {
        return itemNo;
    }

    public void setItemNo(Integer itemNo) {
        this.itemNo = itemNo;
    }

    public Integer getSplitNo() {
        return splitNo;
    }

    public void setSplitNo(Integer splitNo) {
        this.splitNo = splitNo;
    }

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo == null ? null : modelNo.trim();
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getTranstypeCode() {
        return transtypeCode;
    }

    public void setTranstypeCode(String transtypeCode) {
        this.transtypeCode = transtypeCode == null ? null : transtypeCode.trim();
    }

    public Date getDeliveryTimeH() {
        return deliveryTimeH;
    }

    public void setDeliveryTimeH(Date deliveryTimeH) {
        this.deliveryTimeH = deliveryTimeH;
    }

    public Date getDeliveryTimeW() {
        return deliveryTimeW;
    }

    public void setDeliveryTimeW(Date deliveryTimeW) {
        this.deliveryTimeW = deliveryTimeW;
    }

    public Date getDeliveryTimeA() {
        return deliveryTimeA;
    }

    public void setDeliveryTimeA(Date deliveryTimeA) {
        this.deliveryTimeA = deliveryTimeA;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo == null ? null : invoiceNo.trim();
    }

    public Boolean getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Boolean delFlag) {
        this.delFlag = delFlag;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdator() {
        return updator;
    }

    public void setUpdator(String updator) {
        this.updator = updator == null ? null : updator.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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
        OpsPoDeliveryFact other = (OpsPoDeliveryFact) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getSourceId() == null ? other.getSourceId() == null : this.getSourceId().equals(other.getSourceId()))
            && (this.getOrderNo() == null ? other.getOrderNo() == null : this.getOrderNo().equals(other.getOrderNo()))
            && (this.getItemNo() == null ? other.getItemNo() == null : this.getItemNo().equals(other.getItemNo()))
            && (this.getSplitNo() == null ? other.getSplitNo() == null : this.getSplitNo().equals(other.getSplitNo()))
            && (this.getModelNo() == null ? other.getModelNo() == null : this.getModelNo().equals(other.getModelNo()))
            && (this.getQuantity() == null ? other.getQuantity() == null : this.getQuantity().equals(other.getQuantity()))
            && (this.getTranstypeCode() == null ? other.getTranstypeCode() == null : this.getTranstypeCode().equals(other.getTranstypeCode()))
            && (this.getDeliveryTimeH() == null ? other.getDeliveryTimeH() == null : this.getDeliveryTimeH().equals(other.getDeliveryTimeH()))
            && (this.getDeliveryTimeW() == null ? other.getDeliveryTimeW() == null : this.getDeliveryTimeW().equals(other.getDeliveryTimeW()))
            && (this.getDeliveryTimeA() == null ? other.getDeliveryTimeA() == null : this.getDeliveryTimeA().equals(other.getDeliveryTimeA()))
            && (this.getInvoiceId() == null ? other.getInvoiceId() == null : this.getInvoiceId().equals(other.getInvoiceId()))
            && (this.getInvoiceNo() == null ? other.getInvoiceNo() == null : this.getInvoiceNo().equals(other.getInvoiceNo()))
            && (this.getDelFlag() == null ? other.getDelFlag() == null : this.getDelFlag().equals(other.getDelFlag()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getUpdator() == null ? other.getUpdator() == null : this.getUpdator().equals(other.getUpdator()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getSourceId() == null) ? 0 : getSourceId().hashCode());
        result = prime * result + ((getOrderNo() == null) ? 0 : getOrderNo().hashCode());
        result = prime * result + ((getItemNo() == null) ? 0 : getItemNo().hashCode());
        result = prime * result + ((getSplitNo() == null) ? 0 : getSplitNo().hashCode());
        result = prime * result + ((getModelNo() == null) ? 0 : getModelNo().hashCode());
        result = prime * result + ((getQuantity() == null) ? 0 : getQuantity().hashCode());
        result = prime * result + ((getTranstypeCode() == null) ? 0 : getTranstypeCode().hashCode());
        result = prime * result + ((getDeliveryTimeH() == null) ? 0 : getDeliveryTimeH().hashCode());
        result = prime * result + ((getDeliveryTimeW() == null) ? 0 : getDeliveryTimeW().hashCode());
        result = prime * result + ((getDeliveryTimeA() == null) ? 0 : getDeliveryTimeA().hashCode());
        result = prime * result + ((getInvoiceId() == null) ? 0 : getInvoiceId().hashCode());
        result = prime * result + ((getInvoiceNo() == null) ? 0 : getInvoiceNo().hashCode());
        result = prime * result + ((getDelFlag() == null) ? 0 : getDelFlag().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getUpdator() == null) ? 0 : getUpdator().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        return result;
    }
}