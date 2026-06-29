package com.sales.ops.db.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OpsRoItem implements Serializable {
    private Long id;

    private String roId;

    private Integer roItem;

    private Integer qty;

    private Integer recQty;

    private String modelno;

    private String remark;

    private Long fromInventoryId;

    private String fromInventoryTableType;

    private String qaStatus;

    private String greenCode;

    private Integer version;

    private Integer delflag;

    private Date creTime;

    private String creator;

    private Date modifyTime;

    private String modifier;

    private BigDecimal netweight;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoId() {
        return roId;
    }

    public void setRoId(String roId) {
        this.roId = roId == null ? null : roId.trim();
    }

    public Integer getRoItem() {
        return roItem;
    }

    public void setRoItem(Integer roItem) {
        this.roItem = roItem;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Integer getRecQty() {
        return recQty;
    }

    public void setRecQty(Integer recQty) {
        this.recQty = recQty;
    }

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno == null ? null : modelno.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Long getFromInventoryId() {
        return fromInventoryId;
    }

    public void setFromInventoryId(Long fromInventoryId) {
        this.fromInventoryId = fromInventoryId;
    }

    public String getFromInventoryTableType() {
        return fromInventoryTableType;
    }

    public void setFromInventoryTableType(String fromInventoryTableType) {
        this.fromInventoryTableType = fromInventoryTableType == null ? null : fromInventoryTableType.trim();
    }

    public String getQaStatus() {
        return qaStatus;
    }

    public void setQaStatus(String qaStatus) {
        this.qaStatus = qaStatus == null ? null : qaStatus.trim();
    }

    public String getGreenCode() {
        return greenCode;
    }

    public void setGreenCode(String greenCode) {
        this.greenCode = greenCode == null ? null : greenCode.trim();
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
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

    public BigDecimal getNetweight() {
        return netweight;
    }

    public void setNetweight(BigDecimal netweight) {
        this.netweight = netweight;
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
        OpsRoItem other = (OpsRoItem) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getRoId() == null ? other.getRoId() == null : this.getRoId().equals(other.getRoId()))
            && (this.getRoItem() == null ? other.getRoItem() == null : this.getRoItem().equals(other.getRoItem()))
            && (this.getQty() == null ? other.getQty() == null : this.getQty().equals(other.getQty()))
            && (this.getRecQty() == null ? other.getRecQty() == null : this.getRecQty().equals(other.getRecQty()))
            && (this.getModelno() == null ? other.getModelno() == null : this.getModelno().equals(other.getModelno()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getFromInventoryId() == null ? other.getFromInventoryId() == null : this.getFromInventoryId().equals(other.getFromInventoryId()))
            && (this.getFromInventoryTableType() == null ? other.getFromInventoryTableType() == null : this.getFromInventoryTableType().equals(other.getFromInventoryTableType()))
            && (this.getQaStatus() == null ? other.getQaStatus() == null : this.getQaStatus().equals(other.getQaStatus()))
            && (this.getGreenCode() == null ? other.getGreenCode() == null : this.getGreenCode().equals(other.getGreenCode()))
            && (this.getVersion() == null ? other.getVersion() == null : this.getVersion().equals(other.getVersion()))
            && (this.getDelflag() == null ? other.getDelflag() == null : this.getDelflag().equals(other.getDelflag()))
            && (this.getCreTime() == null ? other.getCreTime() == null : this.getCreTime().equals(other.getCreTime()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
            && (this.getModifyTime() == null ? other.getModifyTime() == null : this.getModifyTime().equals(other.getModifyTime()))
            && (this.getModifier() == null ? other.getModifier() == null : this.getModifier().equals(other.getModifier()))
            && (this.getNetweight() == null ? other.getNetweight() == null : this.getNetweight().equals(other.getNetweight()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getRoId() == null) ? 0 : getRoId().hashCode());
        result = prime * result + ((getRoItem() == null) ? 0 : getRoItem().hashCode());
        result = prime * result + ((getQty() == null) ? 0 : getQty().hashCode());
        result = prime * result + ((getRecQty() == null) ? 0 : getRecQty().hashCode());
        result = prime * result + ((getModelno() == null) ? 0 : getModelno().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getFromInventoryId() == null) ? 0 : getFromInventoryId().hashCode());
        result = prime * result + ((getFromInventoryTableType() == null) ? 0 : getFromInventoryTableType().hashCode());
        result = prime * result + ((getQaStatus() == null) ? 0 : getQaStatus().hashCode());
        result = prime * result + ((getGreenCode() == null) ? 0 : getGreenCode().hashCode());
        result = prime * result + ((getVersion() == null) ? 0 : getVersion().hashCode());
        result = prime * result + ((getDelflag() == null) ? 0 : getDelflag().hashCode());
        result = prime * result + ((getCreTime() == null) ? 0 : getCreTime().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getModifyTime() == null) ? 0 : getModifyTime().hashCode());
        result = prime * result + ((getModifier() == null) ? 0 : getModifier().hashCode());
        result = prime * result + ((getNetweight() == null) ? 0 : getNetweight().hashCode());
        return result;
    }
}