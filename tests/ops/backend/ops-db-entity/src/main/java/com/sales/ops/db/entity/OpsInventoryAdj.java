package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class OpsInventoryAdj implements Serializable {
    private Long id;

    private Date adjDate;

    private Long diffId;

    private Long inventoryId;

    private Integer quantityBefore;

    private Integer quantityAdj;

    private Date createTime;

    private String creator;

    private Integer status;

    private String remark;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getAdjDate() {
        return adjDate;
    }

    public void setAdjDate(Date adjDate) {
        this.adjDate = adjDate;
    }

    public Long getDiffId() {
        return diffId;
    }

    public void setDiffId(Long diffId) {
        this.diffId = diffId;
    }

    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

    public Integer getQuantityBefore() {
        return quantityBefore;
    }

    public void setQuantityBefore(Integer quantityBefore) {
        this.quantityBefore = quantityBefore;
    }

    public Integer getQuantityAdj() {
        return quantityAdj;
    }

    public void setQuantityAdj(Integer quantityAdj) {
        this.quantityAdj = quantityAdj;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
        OpsInventoryAdj other = (OpsInventoryAdj) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getAdjDate() == null ? other.getAdjDate() == null : this.getAdjDate().equals(other.getAdjDate()))
            && (this.getDiffId() == null ? other.getDiffId() == null : this.getDiffId().equals(other.getDiffId()))
            && (this.getInventoryId() == null ? other.getInventoryId() == null : this.getInventoryId().equals(other.getInventoryId()))
            && (this.getQuantityBefore() == null ? other.getQuantityBefore() == null : this.getQuantityBefore().equals(other.getQuantityBefore()))
            && (this.getQuantityAdj() == null ? other.getQuantityAdj() == null : this.getQuantityAdj().equals(other.getQuantityAdj()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getAdjDate() == null) ? 0 : getAdjDate().hashCode());
        result = prime * result + ((getDiffId() == null) ? 0 : getDiffId().hashCode());
        result = prime * result + ((getInventoryId() == null) ? 0 : getInventoryId().hashCode());
        result = prime * result + ((getQuantityBefore() == null) ? 0 : getQuantityBefore().hashCode());
        result = prime * result + ((getQuantityAdj() == null) ? 0 : getQuantityAdj().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        return result;
    }
}