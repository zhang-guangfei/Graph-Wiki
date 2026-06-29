package com.sales.ops.db.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OpsPcoItem implements Serializable {
    private Long id;

    private String pcoId;

    private Integer pcoItem;

    private String subModelno;

    private Integer subQty;

    private String remark;

    private Long version;

    private BigDecimal weight;

    private String waitType;

    private Integer delflag;

    private Boolean isCross;

    private Date creTime;

    private String creator;

    private Date modifyTime;

    private String modifier;

    private String relocation;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPcoId() {
        return pcoId;
    }

    public void setPcoId(String pcoId) {
        this.pcoId = pcoId == null ? null : pcoId.trim();
    }

    public Integer getPcoItem() {
        return pcoItem;
    }

    public void setPcoItem(Integer pcoItem) {
        this.pcoItem = pcoItem;
    }

    public String getSubModelno() {
        return subModelno;
    }

    public void setSubModelno(String subModelno) {
        this.subModelno = subModelno == null ? null : subModelno.trim();
    }

    public Integer getSubQty() {
        return subQty;
    }

    public void setSubQty(Integer subQty) {
        this.subQty = subQty;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public String getWaitType() {
        return waitType;
    }

    public void setWaitType(String waitType) {
        this.waitType = waitType == null ? null : waitType.trim();
    }

    public Integer getDelflag() {
        return delflag;
    }

    public void setDelflag(Integer delflag) {
        this.delflag = delflag;
    }

    public Boolean getIsCross() {
        return isCross;
    }

    public void setIsCross(Boolean isCross) {
        this.isCross = isCross;
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

    public String getRelocation() {
        return relocation;
    }

    public void setRelocation(String relocation) {
        this.relocation = relocation == null ? null : relocation.trim();
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
        OpsPcoItem other = (OpsPcoItem) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getPcoId() == null ? other.getPcoId() == null : this.getPcoId().equals(other.getPcoId()))
            && (this.getPcoItem() == null ? other.getPcoItem() == null : this.getPcoItem().equals(other.getPcoItem()))
            && (this.getSubModelno() == null ? other.getSubModelno() == null : this.getSubModelno().equals(other.getSubModelno()))
            && (this.getSubQty() == null ? other.getSubQty() == null : this.getSubQty().equals(other.getSubQty()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getVersion() == null ? other.getVersion() == null : this.getVersion().equals(other.getVersion()))
            && (this.getWeight() == null ? other.getWeight() == null : this.getWeight().equals(other.getWeight()))
            && (this.getWaitType() == null ? other.getWaitType() == null : this.getWaitType().equals(other.getWaitType()))
            && (this.getDelflag() == null ? other.getDelflag() == null : this.getDelflag().equals(other.getDelflag()))
            && (this.getIsCross() == null ? other.getIsCross() == null : this.getIsCross().equals(other.getIsCross()))
            && (this.getCreTime() == null ? other.getCreTime() == null : this.getCreTime().equals(other.getCreTime()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
            && (this.getModifyTime() == null ? other.getModifyTime() == null : this.getModifyTime().equals(other.getModifyTime()))
            && (this.getModifier() == null ? other.getModifier() == null : this.getModifier().equals(other.getModifier()))
            && (this.getRelocation() == null ? other.getRelocation() == null : this.getRelocation().equals(other.getRelocation()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getPcoId() == null) ? 0 : getPcoId().hashCode());
        result = prime * result + ((getPcoItem() == null) ? 0 : getPcoItem().hashCode());
        result = prime * result + ((getSubModelno() == null) ? 0 : getSubModelno().hashCode());
        result = prime * result + ((getSubQty() == null) ? 0 : getSubQty().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getVersion() == null) ? 0 : getVersion().hashCode());
        result = prime * result + ((getWeight() == null) ? 0 : getWeight().hashCode());
        result = prime * result + ((getWaitType() == null) ? 0 : getWaitType().hashCode());
        result = prime * result + ((getDelflag() == null) ? 0 : getDelflag().hashCode());
        result = prime * result + ((getIsCross() == null) ? 0 : getIsCross().hashCode());
        result = prime * result + ((getCreTime() == null) ? 0 : getCreTime().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getModifyTime() == null) ? 0 : getModifyTime().hashCode());
        result = prime * result + ((getModifier() == null) ? 0 : getModifier().hashCode());
        result = prime * result + ((getRelocation() == null) ? 0 : getRelocation().hashCode());
        return result;
    }
}