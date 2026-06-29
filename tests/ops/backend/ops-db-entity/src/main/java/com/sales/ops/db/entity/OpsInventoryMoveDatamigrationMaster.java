package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class OpsInventoryMoveDatamigrationMaster implements Serializable {
    private Long id;

    private String inventoryTypeCode;

    private String customerNo;

    private String ppl;

    private String projectCode;

    private String groupCustomerNo;

    private String salesInfoNo;

    private String warehouseCode;

    private String inventoryStatus;

    private Integer sourceType;

    private Integer quantity;

    private Integer prepareQuantity;

    private String modelno;

    private String orderno;

    private Integer itemno;

    private Integer splititemno;

    private String supplierid;

    private Date prereceivedate;

    private String invoiceNo;

    private String associateNo;

    private Integer associateNoItem;

    private Integer associateNoSplitno;

    private String signWarehouseCode;

    private Integer status;

    private Date createtime;

    private Date updatetime;

    private Integer invoiceId;

    private String ordtype;

    private String greencode;

    private String creator;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInventoryTypeCode() {
        return inventoryTypeCode;
    }

    public void setInventoryTypeCode(String inventoryTypeCode) {
        this.inventoryTypeCode = inventoryTypeCode == null ? null : inventoryTypeCode.trim();
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo == null ? null : customerNo.trim();
    }

    public String getPpl() {
        return ppl;
    }

    public void setPpl(String ppl) {
        this.ppl = ppl == null ? null : ppl.trim();
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode == null ? null : projectCode.trim();
    }

    public String getGroupCustomerNo() {
        return groupCustomerNo;
    }

    public void setGroupCustomerNo(String groupCustomerNo) {
        this.groupCustomerNo = groupCustomerNo == null ? null : groupCustomerNo.trim();
    }

    public String getSalesInfoNo() {
        return salesInfoNo;
    }

    public void setSalesInfoNo(String salesInfoNo) {
        this.salesInfoNo = salesInfoNo == null ? null : salesInfoNo.trim();
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

    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
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

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo == null ? null : invoiceNo.trim();
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Integer getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getOrdtype() {
        return ordtype;
    }

    public void setOrdtype(String ordtype) {
        this.ordtype = ordtype == null ? null : ordtype.trim();
    }

    public String getGreencode() {
        return greencode;
    }

    public void setGreencode(String greencode) {
        this.greencode = greencode == null ? null : greencode.trim();
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
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
        OpsInventoryMoveDatamigrationMaster other = (OpsInventoryMoveDatamigrationMaster) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getInventoryTypeCode() == null ? other.getInventoryTypeCode() == null : this.getInventoryTypeCode().equals(other.getInventoryTypeCode()))
            && (this.getCustomerNo() == null ? other.getCustomerNo() == null : this.getCustomerNo().equals(other.getCustomerNo()))
            && (this.getPpl() == null ? other.getPpl() == null : this.getPpl().equals(other.getPpl()))
            && (this.getProjectCode() == null ? other.getProjectCode() == null : this.getProjectCode().equals(other.getProjectCode()))
            && (this.getGroupCustomerNo() == null ? other.getGroupCustomerNo() == null : this.getGroupCustomerNo().equals(other.getGroupCustomerNo()))
            && (this.getSalesInfoNo() == null ? other.getSalesInfoNo() == null : this.getSalesInfoNo().equals(other.getSalesInfoNo()))
            && (this.getWarehouseCode() == null ? other.getWarehouseCode() == null : this.getWarehouseCode().equals(other.getWarehouseCode()))
            && (this.getInventoryStatus() == null ? other.getInventoryStatus() == null : this.getInventoryStatus().equals(other.getInventoryStatus()))
            && (this.getSourceType() == null ? other.getSourceType() == null : this.getSourceType().equals(other.getSourceType()))
            && (this.getQuantity() == null ? other.getQuantity() == null : this.getQuantity().equals(other.getQuantity()))
            && (this.getPrepareQuantity() == null ? other.getPrepareQuantity() == null : this.getPrepareQuantity().equals(other.getPrepareQuantity()))
            && (this.getModelno() == null ? other.getModelno() == null : this.getModelno().equals(other.getModelno()))
            && (this.getOrderno() == null ? other.getOrderno() == null : this.getOrderno().equals(other.getOrderno()))
            && (this.getItemno() == null ? other.getItemno() == null : this.getItemno().equals(other.getItemno()))
            && (this.getSplititemno() == null ? other.getSplititemno() == null : this.getSplititemno().equals(other.getSplititemno()))
            && (this.getSupplierid() == null ? other.getSupplierid() == null : this.getSupplierid().equals(other.getSupplierid()))
            && (this.getPrereceivedate() == null ? other.getPrereceivedate() == null : this.getPrereceivedate().equals(other.getPrereceivedate()))
            && (this.getInvoiceNo() == null ? other.getInvoiceNo() == null : this.getInvoiceNo().equals(other.getInvoiceNo()))
            && (this.getAssociateNo() == null ? other.getAssociateNo() == null : this.getAssociateNo().equals(other.getAssociateNo()))
            && (this.getAssociateNoItem() == null ? other.getAssociateNoItem() == null : this.getAssociateNoItem().equals(other.getAssociateNoItem()))
            && (this.getAssociateNoSplitno() == null ? other.getAssociateNoSplitno() == null : this.getAssociateNoSplitno().equals(other.getAssociateNoSplitno()))
            && (this.getSignWarehouseCode() == null ? other.getSignWarehouseCode() == null : this.getSignWarehouseCode().equals(other.getSignWarehouseCode()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getCreatetime() == null ? other.getCreatetime() == null : this.getCreatetime().equals(other.getCreatetime()))
            && (this.getUpdatetime() == null ? other.getUpdatetime() == null : this.getUpdatetime().equals(other.getUpdatetime()))
            && (this.getInvoiceId() == null ? other.getInvoiceId() == null : this.getInvoiceId().equals(other.getInvoiceId()))
            && (this.getOrdtype() == null ? other.getOrdtype() == null : this.getOrdtype().equals(other.getOrdtype()))
            && (this.getGreencode() == null ? other.getGreencode() == null : this.getGreencode().equals(other.getGreencode()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getInventoryTypeCode() == null) ? 0 : getInventoryTypeCode().hashCode());
        result = prime * result + ((getCustomerNo() == null) ? 0 : getCustomerNo().hashCode());
        result = prime * result + ((getPpl() == null) ? 0 : getPpl().hashCode());
        result = prime * result + ((getProjectCode() == null) ? 0 : getProjectCode().hashCode());
        result = prime * result + ((getGroupCustomerNo() == null) ? 0 : getGroupCustomerNo().hashCode());
        result = prime * result + ((getSalesInfoNo() == null) ? 0 : getSalesInfoNo().hashCode());
        result = prime * result + ((getWarehouseCode() == null) ? 0 : getWarehouseCode().hashCode());
        result = prime * result + ((getInventoryStatus() == null) ? 0 : getInventoryStatus().hashCode());
        result = prime * result + ((getSourceType() == null) ? 0 : getSourceType().hashCode());
        result = prime * result + ((getQuantity() == null) ? 0 : getQuantity().hashCode());
        result = prime * result + ((getPrepareQuantity() == null) ? 0 : getPrepareQuantity().hashCode());
        result = prime * result + ((getModelno() == null) ? 0 : getModelno().hashCode());
        result = prime * result + ((getOrderno() == null) ? 0 : getOrderno().hashCode());
        result = prime * result + ((getItemno() == null) ? 0 : getItemno().hashCode());
        result = prime * result + ((getSplititemno() == null) ? 0 : getSplititemno().hashCode());
        result = prime * result + ((getSupplierid() == null) ? 0 : getSupplierid().hashCode());
        result = prime * result + ((getPrereceivedate() == null) ? 0 : getPrereceivedate().hashCode());
        result = prime * result + ((getInvoiceNo() == null) ? 0 : getInvoiceNo().hashCode());
        result = prime * result + ((getAssociateNo() == null) ? 0 : getAssociateNo().hashCode());
        result = prime * result + ((getAssociateNoItem() == null) ? 0 : getAssociateNoItem().hashCode());
        result = prime * result + ((getAssociateNoSplitno() == null) ? 0 : getAssociateNoSplitno().hashCode());
        result = prime * result + ((getSignWarehouseCode() == null) ? 0 : getSignWarehouseCode().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getCreatetime() == null) ? 0 : getCreatetime().hashCode());
        result = prime * result + ((getUpdatetime() == null) ? 0 : getUpdatetime().hashCode());
        result = prime * result + ((getInvoiceId() == null) ? 0 : getInvoiceId().hashCode());
        result = prime * result + ((getOrdtype() == null) ? 0 : getOrdtype().hashCode());
        result = prime * result + ((getGreencode() == null) ? 0 : getGreencode().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        return result;
    }
}