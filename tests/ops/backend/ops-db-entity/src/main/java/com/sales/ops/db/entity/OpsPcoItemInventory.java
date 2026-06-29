package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class OpsPcoItemInventory implements Serializable {
    private Long id;

    private String pcoId;

    private Integer pcoItem;

    private Integer pcoType;

    private Integer useQty;

    private Integer outQty;

    private Long inventoryId;

    private String inventoryTableType;

    private Long srcInventoryId;

    private String srcInventoryTableType;

    private Integer sortnum;

    private Long version;

    private Integer delflag;

    private Date creTime;

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

    public Integer getPcoType() {
        return pcoType;
    }

    public void setPcoType(Integer pcoType) {
        this.pcoType = pcoType;
    }

    public Integer getUseQty() {
        return useQty;
    }

    public void setUseQty(Integer useQty) {
        this.useQty = useQty;
    }

    public Integer getOutQty() {
        return outQty;
    }

    public void setOutQty(Integer outQty) {
        this.outQty = outQty;
    }

    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

    public String getInventoryTableType() {
        return inventoryTableType;
    }

    public void setInventoryTableType(String inventoryTableType) {
        this.inventoryTableType = inventoryTableType == null ? null : inventoryTableType.trim();
    }

    public Long getSrcInventoryId() {
        return srcInventoryId;
    }

    public void setSrcInventoryId(Long srcInventoryId) {
        this.srcInventoryId = srcInventoryId;
    }

    public String getSrcInventoryTableType() {
        return srcInventoryTableType;
    }

    public void setSrcInventoryTableType(String srcInventoryTableType) {
        this.srcInventoryTableType = srcInventoryTableType == null ? null : srcInventoryTableType.trim();
    }

    public Integer getSortnum() {
        return sortnum;
    }

    public void setSortnum(Integer sortnum) {
        this.sortnum = sortnum;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
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
        OpsPcoItemInventory other = (OpsPcoItemInventory) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getPcoId() == null ? other.getPcoId() == null : this.getPcoId().equals(other.getPcoId()))
            && (this.getPcoItem() == null ? other.getPcoItem() == null : this.getPcoItem().equals(other.getPcoItem()))
            && (this.getPcoType() == null ? other.getPcoType() == null : this.getPcoType().equals(other.getPcoType()))
            && (this.getUseQty() == null ? other.getUseQty() == null : this.getUseQty().equals(other.getUseQty()))
            && (this.getOutQty() == null ? other.getOutQty() == null : this.getOutQty().equals(other.getOutQty()))
            && (this.getInventoryId() == null ? other.getInventoryId() == null : this.getInventoryId().equals(other.getInventoryId()))
            && (this.getInventoryTableType() == null ? other.getInventoryTableType() == null : this.getInventoryTableType().equals(other.getInventoryTableType()))
            && (this.getSrcInventoryId() == null ? other.getSrcInventoryId() == null : this.getSrcInventoryId().equals(other.getSrcInventoryId()))
            && (this.getSrcInventoryTableType() == null ? other.getSrcInventoryTableType() == null : this.getSrcInventoryTableType().equals(other.getSrcInventoryTableType()))
            && (this.getSortnum() == null ? other.getSortnum() == null : this.getSortnum().equals(other.getSortnum()))
            && (this.getVersion() == null ? other.getVersion() == null : this.getVersion().equals(other.getVersion()))
            && (this.getDelflag() == null ? other.getDelflag() == null : this.getDelflag().equals(other.getDelflag()))
            && (this.getCreTime() == null ? other.getCreTime() == null : this.getCreTime().equals(other.getCreTime()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
            && (this.getModifyTime() == null ? other.getModifyTime() == null : this.getModifyTime().equals(other.getModifyTime()))
            && (this.getModifier() == null ? other.getModifier() == null : this.getModifier().equals(other.getModifier()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getPcoId() == null) ? 0 : getPcoId().hashCode());
        result = prime * result + ((getPcoItem() == null) ? 0 : getPcoItem().hashCode());
        result = prime * result + ((getPcoType() == null) ? 0 : getPcoType().hashCode());
        result = prime * result + ((getUseQty() == null) ? 0 : getUseQty().hashCode());
        result = prime * result + ((getOutQty() == null) ? 0 : getOutQty().hashCode());
        result = prime * result + ((getInventoryId() == null) ? 0 : getInventoryId().hashCode());
        result = prime * result + ((getInventoryTableType() == null) ? 0 : getInventoryTableType().hashCode());
        result = prime * result + ((getSrcInventoryId() == null) ? 0 : getSrcInventoryId().hashCode());
        result = prime * result + ((getSrcInventoryTableType() == null) ? 0 : getSrcInventoryTableType().hashCode());
        result = prime * result + ((getSortnum() == null) ? 0 : getSortnum().hashCode());
        result = prime * result + ((getVersion() == null) ? 0 : getVersion().hashCode());
        result = prime * result + ((getDelflag() == null) ? 0 : getDelflag().hashCode());
        result = prime * result + ((getCreTime() == null) ? 0 : getCreTime().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getModifyTime() == null) ? 0 : getModifyTime().hashCode());
        result = prime * result + ((getModifier() == null) ? 0 : getModifier().hashCode());
        return result;
    }
}