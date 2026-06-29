package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class OpsChangeOrderLog implements Serializable {
    private Long id;

    private Integer trasferimentoType;

    private String dosourceEnum;

    private Long fromDoItemInventoryId;

    private String doid;

    private String pcoid;

    private Integer pcoitem;

    private Integer sortnum;

    private Long fromInventoryId;

    private String frominventoryTableType;

    private Long toinventoryId;

    private String toinventoryTableType;

    private String toWarehouseCode;

    private Integer toQty;

    private String creator;

    private Date creTime;

    private String doDbid;

    private String modelNo;

    private Integer splitNo;

    private Boolean dbFlag;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTrasferimentoType() {
        return trasferimentoType;
    }

    public void setTrasferimentoType(Integer trasferimentoType) {
        this.trasferimentoType = trasferimentoType;
    }

    public String getDosourceEnum() {
        return dosourceEnum;
    }

    public void setDosourceEnum(String dosourceEnum) {
        this.dosourceEnum = dosourceEnum == null ? null : dosourceEnum.trim();
    }

    public Long getFromDoItemInventoryId() {
        return fromDoItemInventoryId;
    }

    public void setFromDoItemInventoryId(Long fromDoItemInventoryId) {
        this.fromDoItemInventoryId = fromDoItemInventoryId;
    }

    public String getDoid() {
        return doid;
    }

    public void setDoid(String doid) {
        this.doid = doid == null ? null : doid.trim();
    }

    public String getPcoid() {
        return pcoid;
    }

    public void setPcoid(String pcoid) {
        this.pcoid = pcoid == null ? null : pcoid.trim();
    }

    public Integer getPcoitem() {
        return pcoitem;
    }

    public void setPcoitem(Integer pcoitem) {
        this.pcoitem = pcoitem;
    }

    public Integer getSortnum() {
        return sortnum;
    }

    public void setSortnum(Integer sortnum) {
        this.sortnum = sortnum;
    }

    public Long getFromInventoryId() {
        return fromInventoryId;
    }

    public void setFromInventoryId(Long fromInventoryId) {
        this.fromInventoryId = fromInventoryId;
    }

    public String getFrominventoryTableType() {
        return frominventoryTableType;
    }

    public void setFrominventoryTableType(String frominventoryTableType) {
        this.frominventoryTableType = frominventoryTableType == null ? null : frominventoryTableType.trim();
    }

    public Long getToinventoryId() {
        return toinventoryId;
    }

    public void setToinventoryId(Long toinventoryId) {
        this.toinventoryId = toinventoryId;
    }

    public String getToinventoryTableType() {
        return toinventoryTableType;
    }

    public void setToinventoryTableType(String toinventoryTableType) {
        this.toinventoryTableType = toinventoryTableType == null ? null : toinventoryTableType.trim();
    }

    public String getToWarehouseCode() {
        return toWarehouseCode;
    }

    public void setToWarehouseCode(String toWarehouseCode) {
        this.toWarehouseCode = toWarehouseCode == null ? null : toWarehouseCode.trim();
    }

    public Integer getToQty() {
        return toQty;
    }

    public void setToQty(Integer toQty) {
        this.toQty = toQty;
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

    public String getDoDbid() {
        return doDbid;
    }

    public void setDoDbid(String doDbid) {
        this.doDbid = doDbid == null ? null : doDbid.trim();
    }

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo == null ? null : modelNo.trim();
    }

    public Integer getSplitNo() {
        return splitNo;
    }

    public void setSplitNo(Integer splitNo) {
        this.splitNo = splitNo;
    }

    public Boolean getDbFlag() {
        return dbFlag;
    }

    public void setDbFlag(Boolean dbFlag) {
        this.dbFlag = dbFlag;
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
        OpsChangeOrderLog other = (OpsChangeOrderLog) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getTrasferimentoType() == null ? other.getTrasferimentoType() == null : this.getTrasferimentoType().equals(other.getTrasferimentoType()))
            && (this.getDosourceEnum() == null ? other.getDosourceEnum() == null : this.getDosourceEnum().equals(other.getDosourceEnum()))
            && (this.getFromDoItemInventoryId() == null ? other.getFromDoItemInventoryId() == null : this.getFromDoItemInventoryId().equals(other.getFromDoItemInventoryId()))
            && (this.getDoid() == null ? other.getDoid() == null : this.getDoid().equals(other.getDoid()))
            && (this.getPcoid() == null ? other.getPcoid() == null : this.getPcoid().equals(other.getPcoid()))
            && (this.getPcoitem() == null ? other.getPcoitem() == null : this.getPcoitem().equals(other.getPcoitem()))
            && (this.getSortnum() == null ? other.getSortnum() == null : this.getSortnum().equals(other.getSortnum()))
            && (this.getFromInventoryId() == null ? other.getFromInventoryId() == null : this.getFromInventoryId().equals(other.getFromInventoryId()))
            && (this.getFrominventoryTableType() == null ? other.getFrominventoryTableType() == null : this.getFrominventoryTableType().equals(other.getFrominventoryTableType()))
            && (this.getToinventoryId() == null ? other.getToinventoryId() == null : this.getToinventoryId().equals(other.getToinventoryId()))
            && (this.getToinventoryTableType() == null ? other.getToinventoryTableType() == null : this.getToinventoryTableType().equals(other.getToinventoryTableType()))
            && (this.getToWarehouseCode() == null ? other.getToWarehouseCode() == null : this.getToWarehouseCode().equals(other.getToWarehouseCode()))
            && (this.getToQty() == null ? other.getToQty() == null : this.getToQty().equals(other.getToQty()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
            && (this.getCreTime() == null ? other.getCreTime() == null : this.getCreTime().equals(other.getCreTime()))
            && (this.getDoDbid() == null ? other.getDoDbid() == null : this.getDoDbid().equals(other.getDoDbid()))
            && (this.getModelNo() == null ? other.getModelNo() == null : this.getModelNo().equals(other.getModelNo()))
            && (this.getSplitNo() == null ? other.getSplitNo() == null : this.getSplitNo().equals(other.getSplitNo()))
            && (this.getDbFlag() == null ? other.getDbFlag() == null : this.getDbFlag().equals(other.getDbFlag()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getTrasferimentoType() == null) ? 0 : getTrasferimentoType().hashCode());
        result = prime * result + ((getDosourceEnum() == null) ? 0 : getDosourceEnum().hashCode());
        result = prime * result + ((getFromDoItemInventoryId() == null) ? 0 : getFromDoItemInventoryId().hashCode());
        result = prime * result + ((getDoid() == null) ? 0 : getDoid().hashCode());
        result = prime * result + ((getPcoid() == null) ? 0 : getPcoid().hashCode());
        result = prime * result + ((getPcoitem() == null) ? 0 : getPcoitem().hashCode());
        result = prime * result + ((getSortnum() == null) ? 0 : getSortnum().hashCode());
        result = prime * result + ((getFromInventoryId() == null) ? 0 : getFromInventoryId().hashCode());
        result = prime * result + ((getFrominventoryTableType() == null) ? 0 : getFrominventoryTableType().hashCode());
        result = prime * result + ((getToinventoryId() == null) ? 0 : getToinventoryId().hashCode());
        result = prime * result + ((getToinventoryTableType() == null) ? 0 : getToinventoryTableType().hashCode());
        result = prime * result + ((getToWarehouseCode() == null) ? 0 : getToWarehouseCode().hashCode());
        result = prime * result + ((getToQty() == null) ? 0 : getToQty().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getCreTime() == null) ? 0 : getCreTime().hashCode());
        result = prime * result + ((getDoDbid() == null) ? 0 : getDoDbid().hashCode());
        result = prime * result + ((getModelNo() == null) ? 0 : getModelNo().hashCode());
        result = prime * result + ((getSplitNo() == null) ? 0 : getSplitNo().hashCode());
        result = prime * result + ((getDbFlag() == null) ? 0 : getDbFlag().hashCode());
        return result;
    }
}