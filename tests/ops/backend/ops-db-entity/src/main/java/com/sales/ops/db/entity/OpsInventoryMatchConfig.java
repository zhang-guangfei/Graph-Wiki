package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class OpsInventoryMatchConfig implements Serializable {
    private String inventoryMatchCode;

    private String description;

    private String matchWarehouseTypeCode;

    private String matchInventoryStatus;

    private Boolean mathchPreRecDate;

    private String inventoryTypeCode;

    private Integer delflag;

    private Date creTime;

    private String creator;

    private Date modifyTime;

    private String modifier;

    private Boolean matchInventoryRisk;

    private static final long serialVersionUID = 1L;

    public String getInventoryMatchCode() {
        return inventoryMatchCode;
    }

    public void setInventoryMatchCode(String inventoryMatchCode) {
        this.inventoryMatchCode = inventoryMatchCode == null ? null : inventoryMatchCode.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getMatchWarehouseTypeCode() {
        return matchWarehouseTypeCode;
    }

    public void setMatchWarehouseTypeCode(String matchWarehouseTypeCode) {
        this.matchWarehouseTypeCode = matchWarehouseTypeCode == null ? null : matchWarehouseTypeCode.trim();
    }

    public String getMatchInventoryStatus() {
        return matchInventoryStatus;
    }

    public void setMatchInventoryStatus(String matchInventoryStatus) {
        this.matchInventoryStatus = matchInventoryStatus == null ? null : matchInventoryStatus.trim();
    }

    public Boolean getMathchPreRecDate() {
        return mathchPreRecDate;
    }

    public void setMathchPreRecDate(Boolean mathchPreRecDate) {
        this.mathchPreRecDate = mathchPreRecDate;
    }

    public String getInventoryTypeCode() {
        return inventoryTypeCode;
    }

    public void setInventoryTypeCode(String inventoryTypeCode) {
        this.inventoryTypeCode = inventoryTypeCode == null ? null : inventoryTypeCode.trim();
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

    public Boolean getMatchInventoryRisk() {
        return matchInventoryRisk;
    }

    public void setMatchInventoryRisk(Boolean matchInventoryRisk) {
        this.matchInventoryRisk = matchInventoryRisk;
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
        OpsInventoryMatchConfig other = (OpsInventoryMatchConfig) that;
        return (this.getInventoryMatchCode() == null ? other.getInventoryMatchCode() == null : this.getInventoryMatchCode().equals(other.getInventoryMatchCode()))
            && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()))
            && (this.getMatchWarehouseTypeCode() == null ? other.getMatchWarehouseTypeCode() == null : this.getMatchWarehouseTypeCode().equals(other.getMatchWarehouseTypeCode()))
            && (this.getMatchInventoryStatus() == null ? other.getMatchInventoryStatus() == null : this.getMatchInventoryStatus().equals(other.getMatchInventoryStatus()))
            && (this.getMathchPreRecDate() == null ? other.getMathchPreRecDate() == null : this.getMathchPreRecDate().equals(other.getMathchPreRecDate()))
            && (this.getInventoryTypeCode() == null ? other.getInventoryTypeCode() == null : this.getInventoryTypeCode().equals(other.getInventoryTypeCode()))
            && (this.getDelflag() == null ? other.getDelflag() == null : this.getDelflag().equals(other.getDelflag()))
            && (this.getCreTime() == null ? other.getCreTime() == null : this.getCreTime().equals(other.getCreTime()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
            && (this.getModifyTime() == null ? other.getModifyTime() == null : this.getModifyTime().equals(other.getModifyTime()))
            && (this.getModifier() == null ? other.getModifier() == null : this.getModifier().equals(other.getModifier()))
            && (this.getMatchInventoryRisk() == null ? other.getMatchInventoryRisk() == null : this.getMatchInventoryRisk().equals(other.getMatchInventoryRisk()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getInventoryMatchCode() == null) ? 0 : getInventoryMatchCode().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        result = prime * result + ((getMatchWarehouseTypeCode() == null) ? 0 : getMatchWarehouseTypeCode().hashCode());
        result = prime * result + ((getMatchInventoryStatus() == null) ? 0 : getMatchInventoryStatus().hashCode());
        result = prime * result + ((getMathchPreRecDate() == null) ? 0 : getMathchPreRecDate().hashCode());
        result = prime * result + ((getInventoryTypeCode() == null) ? 0 : getInventoryTypeCode().hashCode());
        result = prime * result + ((getDelflag() == null) ? 0 : getDelflag().hashCode());
        result = prime * result + ((getCreTime() == null) ? 0 : getCreTime().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getModifyTime() == null) ? 0 : getModifyTime().hashCode());
        result = prime * result + ((getModifier() == null) ? 0 : getModifier().hashCode());
        result = prime * result + ((getMatchInventoryRisk() == null) ? 0 : getMatchInventoryRisk().hashCode());
        return result;
    }
}