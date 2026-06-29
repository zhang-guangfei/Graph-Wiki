package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class StockTransferPlanItem implements Serializable {
    private Long id;

    private String planNo;

    private String modelno;

    private Integer finishQty;

    private String transferOrderNo;

    private String transferOrderItem;

    private Integer delflag;

    private Date createTime;

    private String creator;

    private Date updateTime;

    private String updator;

    private String warehouseCode;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlanNo() {
        return planNo;
    }

    public void setPlanNo(String planNo) {
        this.planNo = planNo == null ? null : planNo.trim();
    }

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno == null ? null : modelno.trim();
    }

    public Integer getFinishQty() {
        return finishQty;
    }

    public void setFinishQty(Integer finishQty) {
        this.finishQty = finishQty;
    }

    public String getTransferOrderNo() {
        return transferOrderNo;
    }

    public void setTransferOrderNo(String transferOrderNo) {
        this.transferOrderNo = transferOrderNo == null ? null : transferOrderNo.trim();
    }

    public String getTransferOrderItem() {
        return transferOrderItem;
    }

    public void setTransferOrderItem(String transferOrderItem) {
        this.transferOrderItem = transferOrderItem == null ? null : transferOrderItem.trim();
    }

    public Integer getDelflag() {
        return delflag;
    }

    public void setDelflag(Integer delflag) {
        this.delflag = delflag;
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

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode == null ? null : warehouseCode.trim();
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
        StockTransferPlanItem other = (StockTransferPlanItem) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getPlanNo() == null ? other.getPlanNo() == null : this.getPlanNo().equals(other.getPlanNo()))
            && (this.getModelno() == null ? other.getModelno() == null : this.getModelno().equals(other.getModelno()))
            && (this.getFinishQty() == null ? other.getFinishQty() == null : this.getFinishQty().equals(other.getFinishQty()))
            && (this.getTransferOrderNo() == null ? other.getTransferOrderNo() == null : this.getTransferOrderNo().equals(other.getTransferOrderNo()))
            && (this.getTransferOrderItem() == null ? other.getTransferOrderItem() == null : this.getTransferOrderItem().equals(other.getTransferOrderItem()))
            && (this.getDelflag() == null ? other.getDelflag() == null : this.getDelflag().equals(other.getDelflag()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getUpdator() == null ? other.getUpdator() == null : this.getUpdator().equals(other.getUpdator()))
            && (this.getWarehouseCode() == null ? other.getWarehouseCode() == null : this.getWarehouseCode().equals(other.getWarehouseCode()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getPlanNo() == null) ? 0 : getPlanNo().hashCode());
        result = prime * result + ((getModelno() == null) ? 0 : getModelno().hashCode());
        result = prime * result + ((getFinishQty() == null) ? 0 : getFinishQty().hashCode());
        result = prime * result + ((getTransferOrderNo() == null) ? 0 : getTransferOrderNo().hashCode());
        result = prime * result + ((getTransferOrderItem() == null) ? 0 : getTransferOrderItem().hashCode());
        result = prime * result + ((getDelflag() == null) ? 0 : getDelflag().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getUpdator() == null) ? 0 : getUpdator().hashCode());
        result = prime * result + ((getWarehouseCode() == null) ? 0 : getWarehouseCode().hashCode());
        return result;
    }
}