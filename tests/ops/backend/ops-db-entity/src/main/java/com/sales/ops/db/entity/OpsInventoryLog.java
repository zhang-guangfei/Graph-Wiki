package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class OpsInventoryLog implements Serializable {
    private Long inventoryLogId;

    private String merInventoryLogId;

    private Long inventoryId;

    private Integer quantity;

    private String voucherCode;

    private String voucherType;

    private String orderNo;

    private Integer itemNo;

    private String modelno;

    private String invoiceNo;

    private String warehouseCode;

    private Integer version;

    private Integer delflag;

    private Date creTime;

    private String creator;

    private Date modifyTime;

    private String modifier;

    private Boolean type;

    private Long propertyId;

    private String inventoryTableType;

    public String getMerInventoryLogId() {
        return merInventoryLogId;
    }

    public void setMerInventoryLogId(String merInventoryLogId) {
        this.merInventoryLogId = merInventoryLogId;
    }

    private static final long serialVersionUID = 1L;

    public Long getInventoryLogId() {
        return inventoryLogId;
    }

    public void setInventoryLogId(Long inventoryLogId) {
        this.inventoryLogId = inventoryLogId;
    }

    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode == null ? null : voucherCode.trim();
    }

    public String getVoucherType() {
        return voucherType;
    }

    public void setVoucherType(String voucherType) {
        this.voucherType = voucherType == null ? null : voucherType.trim();
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public Integer getItemNo() {
        return itemNo;
    }

    public void setItemNo(Integer itemNo) {
        this.itemNo = itemNo;
    }

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno == null ? null : modelno.trim();
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo == null ? null : invoiceNo.trim();
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode == null ? null : warehouseCode.trim();
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

    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }

    public Long getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Long propertyId) {
        this.propertyId = propertyId;
    }

    public String getInventoryTableType() {
        return inventoryTableType;
    }

    public void setInventoryTableType(String inventoryTableType) {
        this.inventoryTableType = inventoryTableType == null ? null : inventoryTableType.trim();
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
        OpsInventoryLog other = (OpsInventoryLog) that;
        return (this.getInventoryLogId() == null ? other.getInventoryLogId() == null : this.getInventoryLogId().equals(other.getInventoryLogId()))
            && (this.getInventoryId() == null ? other.getInventoryId() == null : this.getInventoryId().equals(other.getInventoryId()))
            && (this.getQuantity() == null ? other.getQuantity() == null : this.getQuantity().equals(other.getQuantity()))
            && (this.getVoucherCode() == null ? other.getVoucherCode() == null : this.getVoucherCode().equals(other.getVoucherCode()))
            && (this.getVoucherType() == null ? other.getVoucherType() == null : this.getVoucherType().equals(other.getVoucherType()))
            && (this.getOrderNo() == null ? other.getOrderNo() == null : this.getOrderNo().equals(other.getOrderNo()))
            && (this.getItemNo() == null ? other.getItemNo() == null : this.getItemNo().equals(other.getItemNo()))
            && (this.getModelno() == null ? other.getModelno() == null : this.getModelno().equals(other.getModelno()))
            && (this.getInvoiceNo() == null ? other.getInvoiceNo() == null : this.getInvoiceNo().equals(other.getInvoiceNo()))
            && (this.getWarehouseCode() == null ? other.getWarehouseCode() == null : this.getWarehouseCode().equals(other.getWarehouseCode()))
            && (this.getVersion() == null ? other.getVersion() == null : this.getVersion().equals(other.getVersion()))
            && (this.getDelflag() == null ? other.getDelflag() == null : this.getDelflag().equals(other.getDelflag()))
            && (this.getCreTime() == null ? other.getCreTime() == null : this.getCreTime().equals(other.getCreTime()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
            && (this.getModifyTime() == null ? other.getModifyTime() == null : this.getModifyTime().equals(other.getModifyTime()))
            && (this.getModifier() == null ? other.getModifier() == null : this.getModifier().equals(other.getModifier()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getPropertyId() == null ? other.getPropertyId() == null : this.getPropertyId().equals(other.getPropertyId()))
            && (this.getInventoryTableType() == null ? other.getInventoryTableType() == null : this.getInventoryTableType().equals(other.getInventoryTableType()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getInventoryLogId() == null) ? 0 : getInventoryLogId().hashCode());
        result = prime * result + ((getInventoryId() == null) ? 0 : getInventoryId().hashCode());
        result = prime * result + ((getQuantity() == null) ? 0 : getQuantity().hashCode());
        result = prime * result + ((getVoucherCode() == null) ? 0 : getVoucherCode().hashCode());
        result = prime * result + ((getVoucherType() == null) ? 0 : getVoucherType().hashCode());
        result = prime * result + ((getOrderNo() == null) ? 0 : getOrderNo().hashCode());
        result = prime * result + ((getItemNo() == null) ? 0 : getItemNo().hashCode());
        result = prime * result + ((getModelno() == null) ? 0 : getModelno().hashCode());
        result = prime * result + ((getInvoiceNo() == null) ? 0 : getInvoiceNo().hashCode());
        result = prime * result + ((getWarehouseCode() == null) ? 0 : getWarehouseCode().hashCode());
        result = prime * result + ((getVersion() == null) ? 0 : getVersion().hashCode());
        result = prime * result + ((getDelflag() == null) ? 0 : getDelflag().hashCode());
        result = prime * result + ((getCreTime() == null) ? 0 : getCreTime().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getModifyTime() == null) ? 0 : getModifyTime().hashCode());
        result = prime * result + ((getModifier() == null) ? 0 : getModifier().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getPropertyId() == null) ? 0 : getPropertyId().hashCode());
        result = prime * result + ((getInventoryTableType() == null) ? 0 : getInventoryTableType().hashCode());
        return result;
    }
}