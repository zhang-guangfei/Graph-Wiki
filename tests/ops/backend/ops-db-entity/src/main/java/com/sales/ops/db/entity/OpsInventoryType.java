package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class OpsInventoryType implements Serializable {
    private String inventoryTypeCode;

    private String description;

    private Integer delflag;

    private Boolean matchCustomerNo;

    private Boolean matchProjectCode;

    private Boolean mathchPpl;

    private Boolean matchGroupCustomerNo;

    private String matchGroupCustomerType;

    private String creator;

    private Date creTime;

    private String modifier;

    private Date modifyTime;

    private Boolean mathchQbNo;

    private static final long serialVersionUID = 1L;

    public String getInventoryTypeCode() {
        return inventoryTypeCode;
    }

    public void setInventoryTypeCode(String inventoryTypeCode) {
        this.inventoryTypeCode = inventoryTypeCode == null ? null : inventoryTypeCode.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Integer getDelflag() {
        return delflag;
    }

    public void setDelflag(Integer delflag) {
        this.delflag = delflag;
    }

    public Boolean getMatchCustomerNo() {
        return matchCustomerNo;
    }

    public void setMatchCustomerNo(Boolean matchCustomerNo) {
        this.matchCustomerNo = matchCustomerNo;
    }

    public Boolean getMatchProjectCode() {
        return matchProjectCode;
    }

    public void setMatchProjectCode(Boolean matchProjectCode) {
        this.matchProjectCode = matchProjectCode;
    }

    public Boolean getMathchPpl() {
        return mathchPpl;
    }

    public void setMathchPpl(Boolean mathchPpl) {
        this.mathchPpl = mathchPpl;
    }

    public Boolean getMatchGroupCustomerNo() {
        return matchGroupCustomerNo;
    }

    public void setMatchGroupCustomerNo(Boolean matchGroupCustomerNo) {
        this.matchGroupCustomerNo = matchGroupCustomerNo;
    }

    public String getMatchGroupCustomerType() {
        return matchGroupCustomerType;
    }

    public void setMatchGroupCustomerType(String matchGroupCustomerType) {
        this.matchGroupCustomerType = matchGroupCustomerType == null ? null : matchGroupCustomerType.trim();
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public Date getCreTime() {
        return creTime;
    }

    public void setCreTime(Date creTime) {
        this.creTime = creTime;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier == null ? null : modifier.trim();
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Boolean getMathchQbNo() {
        return mathchQbNo;
    }

    public void setMathchQbNo(Boolean mathchQbNo) {
        this.mathchQbNo = mathchQbNo;
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
        OpsInventoryType other = (OpsInventoryType) that;
        return (this.getInventoryTypeCode() == null ? other.getInventoryTypeCode() == null : this.getInventoryTypeCode().equals(other.getInventoryTypeCode()))
            && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()))
            && (this.getDelflag() == null ? other.getDelflag() == null : this.getDelflag().equals(other.getDelflag()))
            && (this.getMatchCustomerNo() == null ? other.getMatchCustomerNo() == null : this.getMatchCustomerNo().equals(other.getMatchCustomerNo()))
            && (this.getMatchProjectCode() == null ? other.getMatchProjectCode() == null : this.getMatchProjectCode().equals(other.getMatchProjectCode()))
            && (this.getMathchPpl() == null ? other.getMathchPpl() == null : this.getMathchPpl().equals(other.getMathchPpl()))
            && (this.getMatchGroupCustomerNo() == null ? other.getMatchGroupCustomerNo() == null : this.getMatchGroupCustomerNo().equals(other.getMatchGroupCustomerNo()))
            && (this.getMatchGroupCustomerType() == null ? other.getMatchGroupCustomerType() == null : this.getMatchGroupCustomerType().equals(other.getMatchGroupCustomerType()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
            && (this.getCreTime() == null ? other.getCreTime() == null : this.getCreTime().equals(other.getCreTime()))
            && (this.getModifier() == null ? other.getModifier() == null : this.getModifier().equals(other.getModifier()))
            && (this.getModifyTime() == null ? other.getModifyTime() == null : this.getModifyTime().equals(other.getModifyTime()))
            && (this.getMathchQbNo() == null ? other.getMathchQbNo() == null : this.getMathchQbNo().equals(other.getMathchQbNo()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getInventoryTypeCode() == null) ? 0 : getInventoryTypeCode().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        result = prime * result + ((getDelflag() == null) ? 0 : getDelflag().hashCode());
        result = prime * result + ((getMatchCustomerNo() == null) ? 0 : getMatchCustomerNo().hashCode());
        result = prime * result + ((getMatchProjectCode() == null) ? 0 : getMatchProjectCode().hashCode());
        result = prime * result + ((getMathchPpl() == null) ? 0 : getMathchPpl().hashCode());
        result = prime * result + ((getMatchGroupCustomerNo() == null) ? 0 : getMatchGroupCustomerNo().hashCode());
        result = prime * result + ((getMatchGroupCustomerType() == null) ? 0 : getMatchGroupCustomerType().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getCreTime() == null) ? 0 : getCreTime().hashCode());
        result = prime * result + ((getModifier() == null) ? 0 : getModifier().hashCode());
        result = prime * result + ((getModifyTime() == null) ? 0 : getModifyTime().hashCode());
        result = prime * result + ((getMathchQbNo() == null) ? 0 : getMathchQbNo().hashCode());
        return result;
    }
}