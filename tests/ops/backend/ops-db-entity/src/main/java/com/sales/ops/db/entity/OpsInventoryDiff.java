package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class OpsInventoryDiff implements Serializable {
    private Long id;

    private Date diffDate;

    private String modelno;

    private String warehouseCode;

    private Integer quantityOps;

    private Integer quantityWms;

    private Integer quantityDiff;

    private Integer status;

    private String msg;

    private Date createTime;

    private String creator;

    private Date modifyTime;

    private String modifier;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDiffDate() {
        return diffDate;
    }

    public void setDiffDate(Date diffDate) {
        this.diffDate = diffDate;
    }

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno == null ? null : modelno.trim();
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode == null ? null : warehouseCode.trim();
    }

    public Integer getQuantityOps() {
        return quantityOps;
    }

    public void setQuantityOps(Integer quantityOps) {
        this.quantityOps = quantityOps;
    }

    public Integer getQuantityWms() {
        return quantityWms;
    }

    public void setQuantityWms(Integer quantityWms) {
        this.quantityWms = quantityWms;
    }

    public Integer getQuantityDiff() {
        return quantityDiff;
    }

    public void setQuantityDiff(Integer quantityDiff) {
        this.quantityDiff = quantityDiff;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg == null ? null : msg.trim();
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
        OpsInventoryDiff other = (OpsInventoryDiff) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getDiffDate() == null ? other.getDiffDate() == null : this.getDiffDate().equals(other.getDiffDate()))
            && (this.getModelno() == null ? other.getModelno() == null : this.getModelno().equals(other.getModelno()))
            && (this.getWarehouseCode() == null ? other.getWarehouseCode() == null : this.getWarehouseCode().equals(other.getWarehouseCode()))
            && (this.getQuantityOps() == null ? other.getQuantityOps() == null : this.getQuantityOps().equals(other.getQuantityOps()))
            && (this.getQuantityWms() == null ? other.getQuantityWms() == null : this.getQuantityWms().equals(other.getQuantityWms()))
            && (this.getQuantityDiff() == null ? other.getQuantityDiff() == null : this.getQuantityDiff().equals(other.getQuantityDiff()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getMsg() == null ? other.getMsg() == null : this.getMsg().equals(other.getMsg()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
            && (this.getModifyTime() == null ? other.getModifyTime() == null : this.getModifyTime().equals(other.getModifyTime()))
            && (this.getModifier() == null ? other.getModifier() == null : this.getModifier().equals(other.getModifier()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getDiffDate() == null) ? 0 : getDiffDate().hashCode());
        result = prime * result + ((getModelno() == null) ? 0 : getModelno().hashCode());
        result = prime * result + ((getWarehouseCode() == null) ? 0 : getWarehouseCode().hashCode());
        result = prime * result + ((getQuantityOps() == null) ? 0 : getQuantityOps().hashCode());
        result = prime * result + ((getQuantityWms() == null) ? 0 : getQuantityWms().hashCode());
        result = prime * result + ((getQuantityDiff() == null) ? 0 : getQuantityDiff().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getMsg() == null) ? 0 : getMsg().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getModifyTime() == null) ? 0 : getModifyTime().hashCode());
        result = prime * result + ((getModifier() == null) ? 0 : getModifier().hashCode());
        return result;
    }
}