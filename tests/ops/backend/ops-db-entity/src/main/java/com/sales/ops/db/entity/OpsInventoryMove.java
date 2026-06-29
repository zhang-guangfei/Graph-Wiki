package com.sales.ops.db.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OpsInventoryMove implements Serializable {
    private Long inventoryId;

    private String warehouseCode;

    private String inventoryStatus;

    private String sourceType;

    private Integer quantity;

    private String unit;

    private String qaStatus;

    private Integer prepareQuantity;

    private String modelno;

    private Long inventoryPropertyId;

    private BigDecimal price;

    private Date inTime;

    private String orderno;

    private Integer itemno;

    private Integer splititemno;

    private String supplierid;

    private Date prereceivedate;

    private String invoiceno;

    private String associateNo;

    private Integer associateNoItem;

    private Integer associateNoSplitno;

    private String signWarehouseCode;

    private Integer delflag;

    private String greencode;

    private Long version;

    private Date creTime;

    private String creator;

    private Date modifyTime;

    private String modifier;

    private Integer optStatus;

    private Date optTime;

    private Long invoiceid;

    private Date delTime;

    private Integer poQty;

    private Integer preOriginalQuantity;

    private Integer originalQuantity;

    private String remark;

    private Boolean associateNoSplitnoFlag;

    private static final long serialVersionUID = 1L;

    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode == null ? null : warehouseCode.trim();
    }

    public String getInventoryStatus() {
        return inventoryStatus;
    }

    public void setInventoryStatus(String inventoryStatus) {
        this.inventoryStatus = inventoryStatus == null ? null : inventoryStatus.trim();
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType == null ? null : sourceType.trim();
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public String getQaStatus() {
        return qaStatus;
    }

    public void setQaStatus(String qaStatus) {
        this.qaStatus = qaStatus == null ? null : qaStatus.trim();
    }

    public Integer getPrepareQuantity() {
        return prepareQuantity;
    }

    public void setPrepareQuantity(Integer prepareQuantity) {
        this.prepareQuantity = prepareQuantity;
    }

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno == null ? null : modelno.trim();
    }

    public Long getInventoryPropertyId() {
        return inventoryPropertyId;
    }

    public void setInventoryPropertyId(Long inventoryPropertyId) {
        this.inventoryPropertyId = inventoryPropertyId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getInTime() {
        return inTime;
    }

    public void setInTime(Date inTime) {
        this.inTime = inTime;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno == null ? null : orderno.trim();
    }

    public Integer getItemno() {
        return itemno;
    }

    public void setItemno(Integer itemno) {
        this.itemno = itemno;
    }

    public Integer getSplititemno() {
        return splititemno;
    }

    public void setSplititemno(Integer splititemno) {
        this.splititemno = splititemno;
    }

    public String getSupplierid() {
        return supplierid;
    }

    public void setSupplierid(String supplierid) {
        this.supplierid = supplierid == null ? null : supplierid.trim();
    }

    public Date getPrereceivedate() {
        return prereceivedate;
    }

    public void setPrereceivedate(Date prereceivedate) {
        this.prereceivedate = prereceivedate;
    }

    public String getInvoiceno() {
        return invoiceno;
    }

    public void setInvoiceno(String invoiceno) {
        this.invoiceno = invoiceno == null ? null : invoiceno.trim();
    }

    public String getAssociateNo() {
        return associateNo;
    }

    public void setAssociateNo(String associateNo) {
        this.associateNo = associateNo == null ? null : associateNo.trim();
    }

    public Integer getAssociateNoItem() {
        return associateNoItem;
    }

    public void setAssociateNoItem(Integer associateNoItem) {
        this.associateNoItem = associateNoItem;
    }

    public Integer getAssociateNoSplitno() {
        return associateNoSplitno;
    }

    public void setAssociateNoSplitno(Integer associateNoSplitno) {
        this.associateNoSplitno = associateNoSplitno;
    }

    public String getSignWarehouseCode() {
        return signWarehouseCode;
    }

    public void setSignWarehouseCode(String signWarehouseCode) {
        this.signWarehouseCode = signWarehouseCode == null ? null : signWarehouseCode.trim();
    }

    public Integer getDelflag() {
        return delflag;
    }

    public void setDelflag(Integer delflag) {
        this.delflag = delflag;
    }

    public String getGreencode() {
        return greencode;
    }

    public void setGreencode(String greencode) {
        this.greencode = greencode == null ? null : greencode.trim();
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
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

    public Integer getOptStatus() {
        return optStatus;
    }

    public void setOptStatus(Integer optStatus) {
        this.optStatus = optStatus;
    }

    public Date getOptTime() {
        return optTime;
    }

    public void setOptTime(Date optTime) {
        this.optTime = optTime;
    }

    public Long getInvoiceid() {
        return invoiceid;
    }

    public void setInvoiceid(Long invoiceid) {
        this.invoiceid = invoiceid;
    }

    public Date getDelTime() {
        return delTime;
    }

    public void setDelTime(Date delTime) {
        this.delTime = delTime;
    }

    public Integer getPoQty() {
        return poQty;
    }

    public void setPoQty(Integer poQty) {
        this.poQty = poQty;
    }

    public Integer getPreOriginalQuantity() {
        return preOriginalQuantity;
    }

    public void setPreOriginalQuantity(Integer preOriginalQuantity) {
        this.preOriginalQuantity = preOriginalQuantity;
    }

    public Integer getOriginalQuantity() {
        return originalQuantity;
    }

    public void setOriginalQuantity(Integer originalQuantity) {
        this.originalQuantity = originalQuantity;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Boolean getAssociateNoSplitnoFlag() {
        return associateNoSplitnoFlag;
    }

    public void setAssociateNoSplitnoFlag(Boolean associateNoSplitnoFlag) {
        this.associateNoSplitnoFlag = associateNoSplitnoFlag;
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
        OpsInventoryMove other = (OpsInventoryMove) that;
        return (this.getInventoryId() == null ? other.getInventoryId() == null : this.getInventoryId().equals(other.getInventoryId()))
            && (this.getWarehouseCode() == null ? other.getWarehouseCode() == null : this.getWarehouseCode().equals(other.getWarehouseCode()))
            && (this.getInventoryStatus() == null ? other.getInventoryStatus() == null : this.getInventoryStatus().equals(other.getInventoryStatus()))
            && (this.getSourceType() == null ? other.getSourceType() == null : this.getSourceType().equals(other.getSourceType()))
            && (this.getQuantity() == null ? other.getQuantity() == null : this.getQuantity().equals(other.getQuantity()))
            && (this.getUnit() == null ? other.getUnit() == null : this.getUnit().equals(other.getUnit()))
            && (this.getQaStatus() == null ? other.getQaStatus() == null : this.getQaStatus().equals(other.getQaStatus()))
            && (this.getPrepareQuantity() == null ? other.getPrepareQuantity() == null : this.getPrepareQuantity().equals(other.getPrepareQuantity()))
            && (this.getModelno() == null ? other.getModelno() == null : this.getModelno().equals(other.getModelno()))
            && (this.getInventoryPropertyId() == null ? other.getInventoryPropertyId() == null : this.getInventoryPropertyId().equals(other.getInventoryPropertyId()))
            && (this.getPrice() == null ? other.getPrice() == null : this.getPrice().equals(other.getPrice()))
            && (this.getInTime() == null ? other.getInTime() == null : this.getInTime().equals(other.getInTime()))
            && (this.getOrderno() == null ? other.getOrderno() == null : this.getOrderno().equals(other.getOrderno()))
            && (this.getItemno() == null ? other.getItemno() == null : this.getItemno().equals(other.getItemno()))
            && (this.getSplititemno() == null ? other.getSplititemno() == null : this.getSplititemno().equals(other.getSplititemno()))
            && (this.getSupplierid() == null ? other.getSupplierid() == null : this.getSupplierid().equals(other.getSupplierid()))
            && (this.getPrereceivedate() == null ? other.getPrereceivedate() == null : this.getPrereceivedate().equals(other.getPrereceivedate()))
            && (this.getInvoiceno() == null ? other.getInvoiceno() == null : this.getInvoiceno().equals(other.getInvoiceno()))
            && (this.getAssociateNo() == null ? other.getAssociateNo() == null : this.getAssociateNo().equals(other.getAssociateNo()))
            && (this.getAssociateNoItem() == null ? other.getAssociateNoItem() == null : this.getAssociateNoItem().equals(other.getAssociateNoItem()))
            && (this.getAssociateNoSplitno() == null ? other.getAssociateNoSplitno() == null : this.getAssociateNoSplitno().equals(other.getAssociateNoSplitno()))
            && (this.getSignWarehouseCode() == null ? other.getSignWarehouseCode() == null : this.getSignWarehouseCode().equals(other.getSignWarehouseCode()))
            && (this.getDelflag() == null ? other.getDelflag() == null : this.getDelflag().equals(other.getDelflag()))
            && (this.getGreencode() == null ? other.getGreencode() == null : this.getGreencode().equals(other.getGreencode()))
            && (this.getVersion() == null ? other.getVersion() == null : this.getVersion().equals(other.getVersion()))
            && (this.getCreTime() == null ? other.getCreTime() == null : this.getCreTime().equals(other.getCreTime()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
            && (this.getModifyTime() == null ? other.getModifyTime() == null : this.getModifyTime().equals(other.getModifyTime()))
            && (this.getModifier() == null ? other.getModifier() == null : this.getModifier().equals(other.getModifier()))
            && (this.getOptStatus() == null ? other.getOptStatus() == null : this.getOptStatus().equals(other.getOptStatus()))
            && (this.getOptTime() == null ? other.getOptTime() == null : this.getOptTime().equals(other.getOptTime()))
            && (this.getInvoiceid() == null ? other.getInvoiceid() == null : this.getInvoiceid().equals(other.getInvoiceid()))
            && (this.getDelTime() == null ? other.getDelTime() == null : this.getDelTime().equals(other.getDelTime()))
            && (this.getPoQty() == null ? other.getPoQty() == null : this.getPoQty().equals(other.getPoQty()))
            && (this.getPreOriginalQuantity() == null ? other.getPreOriginalQuantity() == null : this.getPreOriginalQuantity().equals(other.getPreOriginalQuantity()))
            && (this.getOriginalQuantity() == null ? other.getOriginalQuantity() == null : this.getOriginalQuantity().equals(other.getOriginalQuantity()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getAssociateNoSplitnoFlag() == null ? other.getAssociateNoSplitnoFlag() == null : this.getAssociateNoSplitnoFlag().equals(other.getAssociateNoSplitnoFlag()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getInventoryId() == null) ? 0 : getInventoryId().hashCode());
        result = prime * result + ((getWarehouseCode() == null) ? 0 : getWarehouseCode().hashCode());
        result = prime * result + ((getInventoryStatus() == null) ? 0 : getInventoryStatus().hashCode());
        result = prime * result + ((getSourceType() == null) ? 0 : getSourceType().hashCode());
        result = prime * result + ((getQuantity() == null) ? 0 : getQuantity().hashCode());
        result = prime * result + ((getUnit() == null) ? 0 : getUnit().hashCode());
        result = prime * result + ((getQaStatus() == null) ? 0 : getQaStatus().hashCode());
        result = prime * result + ((getPrepareQuantity() == null) ? 0 : getPrepareQuantity().hashCode());
        result = prime * result + ((getModelno() == null) ? 0 : getModelno().hashCode());
        result = prime * result + ((getInventoryPropertyId() == null) ? 0 : getInventoryPropertyId().hashCode());
        result = prime * result + ((getPrice() == null) ? 0 : getPrice().hashCode());
        result = prime * result + ((getInTime() == null) ? 0 : getInTime().hashCode());
        result = prime * result + ((getOrderno() == null) ? 0 : getOrderno().hashCode());
        result = prime * result + ((getItemno() == null) ? 0 : getItemno().hashCode());
        result = prime * result + ((getSplititemno() == null) ? 0 : getSplititemno().hashCode());
        result = prime * result + ((getSupplierid() == null) ? 0 : getSupplierid().hashCode());
        result = prime * result + ((getPrereceivedate() == null) ? 0 : getPrereceivedate().hashCode());
        result = prime * result + ((getInvoiceno() == null) ? 0 : getInvoiceno().hashCode());
        result = prime * result + ((getAssociateNo() == null) ? 0 : getAssociateNo().hashCode());
        result = prime * result + ((getAssociateNoItem() == null) ? 0 : getAssociateNoItem().hashCode());
        result = prime * result + ((getAssociateNoSplitno() == null) ? 0 : getAssociateNoSplitno().hashCode());
        result = prime * result + ((getSignWarehouseCode() == null) ? 0 : getSignWarehouseCode().hashCode());
        result = prime * result + ((getDelflag() == null) ? 0 : getDelflag().hashCode());
        result = prime * result + ((getGreencode() == null) ? 0 : getGreencode().hashCode());
        result = prime * result + ((getVersion() == null) ? 0 : getVersion().hashCode());
        result = prime * result + ((getCreTime() == null) ? 0 : getCreTime().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getModifyTime() == null) ? 0 : getModifyTime().hashCode());
        result = prime * result + ((getModifier() == null) ? 0 : getModifier().hashCode());
        result = prime * result + ((getOptStatus() == null) ? 0 : getOptStatus().hashCode());
        result = prime * result + ((getOptTime() == null) ? 0 : getOptTime().hashCode());
        result = prime * result + ((getInvoiceid() == null) ? 0 : getInvoiceid().hashCode());
        result = prime * result + ((getDelTime() == null) ? 0 : getDelTime().hashCode());
        result = prime * result + ((getPoQty() == null) ? 0 : getPoQty().hashCode());
        result = prime * result + ((getPreOriginalQuantity() == null) ? 0 : getPreOriginalQuantity().hashCode());
        result = prime * result + ((getOriginalQuantity() == null) ? 0 : getOriginalQuantity().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getAssociateNoSplitnoFlag() == null) ? 0 : getAssociateNoSplitnoFlag().hashCode());
        return result;
    }
}