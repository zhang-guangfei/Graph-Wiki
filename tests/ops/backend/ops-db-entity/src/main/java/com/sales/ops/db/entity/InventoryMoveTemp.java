package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class InventoryMoveTemp implements Serializable {
    private String warehouseCode;

    private Integer quantity;

    private String modelno;

    private String inventoryTypeCode;

    private String customerNo;

    private String ppl;

    private String projectCode;

    private String groupCustomerNo;

    private String inventoryStatus;

    private String orderNo;

    private Integer itemno;

    private String supplierid;

    private Date prereceivedate;

    private String invoiceno;

    private static final long serialVersionUID = 1L;

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode == null ? null : warehouseCode.trim();
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno == null ? null : modelno.trim();
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

    public String getInventoryStatus() {
        return inventoryStatus;
    }

    public void setInventoryStatus(String inventoryStatus) {
        this.inventoryStatus = inventoryStatus == null ? null : inventoryStatus.trim();
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public Integer getItemno() {
        return itemno;
    }

    public void setItemno(Integer itemno) {
        this.itemno = itemno;
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
        InventoryMoveTemp other = (InventoryMoveTemp) that;
        return (this.getWarehouseCode() == null ? other.getWarehouseCode() == null : this.getWarehouseCode().equals(other.getWarehouseCode()))
            && (this.getQuantity() == null ? other.getQuantity() == null : this.getQuantity().equals(other.getQuantity()))
            && (this.getModelno() == null ? other.getModelno() == null : this.getModelno().equals(other.getModelno()))
            && (this.getInventoryTypeCode() == null ? other.getInventoryTypeCode() == null : this.getInventoryTypeCode().equals(other.getInventoryTypeCode()))
            && (this.getCustomerNo() == null ? other.getCustomerNo() == null : this.getCustomerNo().equals(other.getCustomerNo()))
            && (this.getPpl() == null ? other.getPpl() == null : this.getPpl().equals(other.getPpl()))
            && (this.getProjectCode() == null ? other.getProjectCode() == null : this.getProjectCode().equals(other.getProjectCode()))
            && (this.getGroupCustomerNo() == null ? other.getGroupCustomerNo() == null : this.getGroupCustomerNo().equals(other.getGroupCustomerNo()))
            && (this.getInventoryStatus() == null ? other.getInventoryStatus() == null : this.getInventoryStatus().equals(other.getInventoryStatus()))
            && (this.getOrderNo() == null ? other.getOrderNo() == null : this.getOrderNo().equals(other.getOrderNo()))
            && (this.getItemno() == null ? other.getItemno() == null : this.getItemno().equals(other.getItemno()))
            && (this.getSupplierid() == null ? other.getSupplierid() == null : this.getSupplierid().equals(other.getSupplierid()))
            && (this.getPrereceivedate() == null ? other.getPrereceivedate() == null : this.getPrereceivedate().equals(other.getPrereceivedate()))
            && (this.getInvoiceno() == null ? other.getInvoiceno() == null : this.getInvoiceno().equals(other.getInvoiceno()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getWarehouseCode() == null) ? 0 : getWarehouseCode().hashCode());
        result = prime * result + ((getQuantity() == null) ? 0 : getQuantity().hashCode());
        result = prime * result + ((getModelno() == null) ? 0 : getModelno().hashCode());
        result = prime * result + ((getInventoryTypeCode() == null) ? 0 : getInventoryTypeCode().hashCode());
        result = prime * result + ((getCustomerNo() == null) ? 0 : getCustomerNo().hashCode());
        result = prime * result + ((getPpl() == null) ? 0 : getPpl().hashCode());
        result = prime * result + ((getProjectCode() == null) ? 0 : getProjectCode().hashCode());
        result = prime * result + ((getGroupCustomerNo() == null) ? 0 : getGroupCustomerNo().hashCode());
        result = prime * result + ((getInventoryStatus() == null) ? 0 : getInventoryStatus().hashCode());
        result = prime * result + ((getOrderNo() == null) ? 0 : getOrderNo().hashCode());
        result = prime * result + ((getItemno() == null) ? 0 : getItemno().hashCode());
        result = prime * result + ((getSupplierid() == null) ? 0 : getSupplierid().hashCode());
        result = prime * result + ((getPrereceivedate() == null) ? 0 : getPrereceivedate().hashCode());
        result = prime * result + ((getInvoiceno() == null) ? 0 : getInvoiceno().hashCode());
        return result;
    }
}