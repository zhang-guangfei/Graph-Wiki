package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class OpsPoDeliveryPlan implements Serializable {
    private Long id;

    private Long sourceId;

    private String orderNo;

    private Integer itemNo;

    private Integer splitNo;

    private String modelNo;

    private Integer quantity;

    private Date latestDeliveryTime;

    private String srcDeliveryTime;

    private String transtypeCode;

    private String reasonCode;

    private String remark;

    private Boolean newest;

    private Boolean delFlag;

    private Date createTime;

    private String creatUser;

    private Date updateTime;

    private String updator;

    private Date deliveryH;

    private Date deliveryW;

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

    public Date getLatestDeliveryTime() {
        return latestDeliveryTime;
    }

    public void setLatestDeliveryTime(Date latestDeliveryTime) {
        this.latestDeliveryTime = latestDeliveryTime;
    }

    public String getSrcDeliveryTime() {
        return srcDeliveryTime;
    }

    public void setSrcDeliveryTime(String srcDeliveryTime) {
        this.srcDeliveryTime = srcDeliveryTime == null ? null : srcDeliveryTime.trim();
    }

    public String getTranstypeCode() {
        return transtypeCode;
    }

    public void setTranstypeCode(String transtypeCode) {
        this.transtypeCode = transtypeCode == null ? null : transtypeCode.trim();
    }

    public String getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode == null ? null : reasonCode.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Boolean getNewest() {
        return newest;
    }

    public void setNewest(Boolean newest) {
        this.newest = newest;
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

    public String getCreatUser() {
        return creatUser;
    }

    public void setCreatUser(String creatUser) {
        this.creatUser = creatUser == null ? null : creatUser.trim();
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

    public Date getDeliveryH() {
        return deliveryH;
    }

    public void setDeliveryH(Date deliveryH) {
        this.deliveryH = deliveryH;
    }

    public Date getDeliveryW() {
        return deliveryW;
    }

    public void setDeliveryW(Date deliveryW) {
        this.deliveryW = deliveryW;
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
        OpsPoDeliveryPlan other = (OpsPoDeliveryPlan) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getSourceId() == null ? other.getSourceId() == null : this.getSourceId().equals(other.getSourceId()))
            && (this.getOrderNo() == null ? other.getOrderNo() == null : this.getOrderNo().equals(other.getOrderNo()))
            && (this.getItemNo() == null ? other.getItemNo() == null : this.getItemNo().equals(other.getItemNo()))
            && (this.getSplitNo() == null ? other.getSplitNo() == null : this.getSplitNo().equals(other.getSplitNo()))
            && (this.getModelNo() == null ? other.getModelNo() == null : this.getModelNo().equals(other.getModelNo()))
            && (this.getQuantity() == null ? other.getQuantity() == null : this.getQuantity().equals(other.getQuantity()))
            && (this.getLatestDeliveryTime() == null ? other.getLatestDeliveryTime() == null : this.getLatestDeliveryTime().equals(other.getLatestDeliveryTime()))
            && (this.getSrcDeliveryTime() == null ? other.getSrcDeliveryTime() == null : this.getSrcDeliveryTime().equals(other.getSrcDeliveryTime()))
            && (this.getTranstypeCode() == null ? other.getTranstypeCode() == null : this.getTranstypeCode().equals(other.getTranstypeCode()))
            && (this.getReasonCode() == null ? other.getReasonCode() == null : this.getReasonCode().equals(other.getReasonCode()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getNewest() == null ? other.getNewest() == null : this.getNewest().equals(other.getNewest()))
            && (this.getDelFlag() == null ? other.getDelFlag() == null : this.getDelFlag().equals(other.getDelFlag()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getCreatUser() == null ? other.getCreatUser() == null : this.getCreatUser().equals(other.getCreatUser()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getUpdator() == null ? other.getUpdator() == null : this.getUpdator().equals(other.getUpdator()))
            && (this.getDeliveryH() == null ? other.getDeliveryH() == null : this.getDeliveryH().equals(other.getDeliveryH()))
            && (this.getDeliveryW() == null ? other.getDeliveryW() == null : this.getDeliveryW().equals(other.getDeliveryW()));
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
        result = prime * result + ((getLatestDeliveryTime() == null) ? 0 : getLatestDeliveryTime().hashCode());
        result = prime * result + ((getSrcDeliveryTime() == null) ? 0 : getSrcDeliveryTime().hashCode());
        result = prime * result + ((getTranstypeCode() == null) ? 0 : getTranstypeCode().hashCode());
        result = prime * result + ((getReasonCode() == null) ? 0 : getReasonCode().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getNewest() == null) ? 0 : getNewest().hashCode());
        result = prime * result + ((getDelFlag() == null) ? 0 : getDelFlag().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getCreatUser() == null) ? 0 : getCreatUser().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getUpdator() == null) ? 0 : getUpdator().hashCode());
        result = prime * result + ((getDeliveryH() == null) ? 0 : getDeliveryH().hashCode());
        result = prime * result + ((getDeliveryW() == null) ? 0 : getDeliveryW().hashCode());
        return result;
    }
}