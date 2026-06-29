package com.sales.ops.db.entity;

import java.io.Serializable;

public class InvNormal implements Serializable {
    private String warehouseCode;

    private String warehouseName;

    private String warehouseType;

    private String modelno;

    private String inventoryTypeCode;

    private String customerNo;

    private String groupCustomerNo;

    private String ppl;

    private String projectCode;

    private String salesInfoNo;

    private Long inventoryPropertyId;

    private Integer qtyNormal;

    private Integer preqtyNormal;

    private Integer qtyW;

    private Integer preqtyW;

    private Integer qtyProduct;

    private Integer preqtyProduct;

    private Integer qtyDb;

    private Integer preqtyDb;

    private Integer qtyCg;

    private Integer preqtyCg;

    private static final long serialVersionUID = 1L;

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode == null ? null : warehouseCode.trim();
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName == null ? null : warehouseName.trim();
    }

    public String getWarehouseType() {
        return warehouseType;
    }

    public void setWarehouseType(String warehouseType) {
        this.warehouseType = warehouseType == null ? null : warehouseType.trim();
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

    public String getGroupCustomerNo() {
        return groupCustomerNo;
    }

    public void setGroupCustomerNo(String groupCustomerNo) {
        this.groupCustomerNo = groupCustomerNo == null ? null : groupCustomerNo.trim();
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

    public String getSalesInfoNo() {
        return salesInfoNo;
    }

    public void setSalesInfoNo(String salesInfoNo) {
        this.salesInfoNo = salesInfoNo == null ? null : salesInfoNo.trim();
    }

    public Long getInventoryPropertyId() {
        return inventoryPropertyId;
    }

    public void setInventoryPropertyId(Long inventoryPropertyId) {
        this.inventoryPropertyId = inventoryPropertyId;
    }

    public Integer getQtyNormal() {
        return qtyNormal;
    }

    public void setQtyNormal(Integer qtyNormal) {
        this.qtyNormal = qtyNormal;
    }

    public Integer getPreqtyNormal() {
        return preqtyNormal;
    }

    public void setPreqtyNormal(Integer preqtyNormal) {
        this.preqtyNormal = preqtyNormal;
    }

    public Integer getQtyW() {
        return qtyW;
    }

    public void setQtyW(Integer qtyW) {
        this.qtyW = qtyW;
    }

    public Integer getPreqtyW() {
        return preqtyW;
    }

    public void setPreqtyW(Integer preqtyW) {
        this.preqtyW = preqtyW;
    }

    public Integer getQtyProduct() {
        return qtyProduct;
    }

    public void setQtyProduct(Integer qtyProduct) {
        this.qtyProduct = qtyProduct;
    }

    public Integer getPreqtyProduct() {
        return preqtyProduct;
    }

    public void setPreqtyProduct(Integer preqtyProduct) {
        this.preqtyProduct = preqtyProduct;
    }

    public Integer getQtyDb() {
        return qtyDb;
    }

    public void setQtyDb(Integer qtyDb) {
        this.qtyDb = qtyDb;
    }

    public Integer getPreqtyDb() {
        return preqtyDb;
    }

    public void setPreqtyDb(Integer preqtyDb) {
        this.preqtyDb = preqtyDb;
    }

    public Integer getQtyCg() {
        return qtyCg;
    }

    public void setQtyCg(Integer qtyCg) {
        this.qtyCg = qtyCg;
    }

    public Integer getPreqtyCg() {
        return preqtyCg;
    }

    public void setPreqtyCg(Integer preqtyCg) {
        this.preqtyCg = preqtyCg;
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
        InvNormal other = (InvNormal) that;
        return (this.getWarehouseCode() == null ? other.getWarehouseCode() == null : this.getWarehouseCode().equals(other.getWarehouseCode()))
            && (this.getWarehouseName() == null ? other.getWarehouseName() == null : this.getWarehouseName().equals(other.getWarehouseName()))
            && (this.getWarehouseType() == null ? other.getWarehouseType() == null : this.getWarehouseType().equals(other.getWarehouseType()))
            && (this.getModelno() == null ? other.getModelno() == null : this.getModelno().equals(other.getModelno()))
            && (this.getInventoryTypeCode() == null ? other.getInventoryTypeCode() == null : this.getInventoryTypeCode().equals(other.getInventoryTypeCode()))
            && (this.getCustomerNo() == null ? other.getCustomerNo() == null : this.getCustomerNo().equals(other.getCustomerNo()))
            && (this.getGroupCustomerNo() == null ? other.getGroupCustomerNo() == null : this.getGroupCustomerNo().equals(other.getGroupCustomerNo()))
            && (this.getPpl() == null ? other.getPpl() == null : this.getPpl().equals(other.getPpl()))
            && (this.getProjectCode() == null ? other.getProjectCode() == null : this.getProjectCode().equals(other.getProjectCode()))
            && (this.getSalesInfoNo() == null ? other.getSalesInfoNo() == null : this.getSalesInfoNo().equals(other.getSalesInfoNo()))
            && (this.getInventoryPropertyId() == null ? other.getInventoryPropertyId() == null : this.getInventoryPropertyId().equals(other.getInventoryPropertyId()))
            && (this.getQtyNormal() == null ? other.getQtyNormal() == null : this.getQtyNormal().equals(other.getQtyNormal()))
            && (this.getPreqtyNormal() == null ? other.getPreqtyNormal() == null : this.getPreqtyNormal().equals(other.getPreqtyNormal()))
            && (this.getQtyW() == null ? other.getQtyW() == null : this.getQtyW().equals(other.getQtyW()))
            && (this.getPreqtyW() == null ? other.getPreqtyW() == null : this.getPreqtyW().equals(other.getPreqtyW()))
            && (this.getQtyProduct() == null ? other.getQtyProduct() == null : this.getQtyProduct().equals(other.getQtyProduct()))
            && (this.getPreqtyProduct() == null ? other.getPreqtyProduct() == null : this.getPreqtyProduct().equals(other.getPreqtyProduct()))
            && (this.getQtyDb() == null ? other.getQtyDb() == null : this.getQtyDb().equals(other.getQtyDb()))
            && (this.getPreqtyDb() == null ? other.getPreqtyDb() == null : this.getPreqtyDb().equals(other.getPreqtyDb()))
            && (this.getQtyCg() == null ? other.getQtyCg() == null : this.getQtyCg().equals(other.getQtyCg()))
            && (this.getPreqtyCg() == null ? other.getPreqtyCg() == null : this.getPreqtyCg().equals(other.getPreqtyCg()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getWarehouseCode() == null) ? 0 : getWarehouseCode().hashCode());
        result = prime * result + ((getWarehouseName() == null) ? 0 : getWarehouseName().hashCode());
        result = prime * result + ((getWarehouseType() == null) ? 0 : getWarehouseType().hashCode());
        result = prime * result + ((getModelno() == null) ? 0 : getModelno().hashCode());
        result = prime * result + ((getInventoryTypeCode() == null) ? 0 : getInventoryTypeCode().hashCode());
        result = prime * result + ((getCustomerNo() == null) ? 0 : getCustomerNo().hashCode());
        result = prime * result + ((getGroupCustomerNo() == null) ? 0 : getGroupCustomerNo().hashCode());
        result = prime * result + ((getPpl() == null) ? 0 : getPpl().hashCode());
        result = prime * result + ((getProjectCode() == null) ? 0 : getProjectCode().hashCode());
        result = prime * result + ((getSalesInfoNo() == null) ? 0 : getSalesInfoNo().hashCode());
        result = prime * result + ((getInventoryPropertyId() == null) ? 0 : getInventoryPropertyId().hashCode());
        result = prime * result + ((getQtyNormal() == null) ? 0 : getQtyNormal().hashCode());
        result = prime * result + ((getPreqtyNormal() == null) ? 0 : getPreqtyNormal().hashCode());
        result = prime * result + ((getQtyW() == null) ? 0 : getQtyW().hashCode());
        result = prime * result + ((getPreqtyW() == null) ? 0 : getPreqtyW().hashCode());
        result = prime * result + ((getQtyProduct() == null) ? 0 : getQtyProduct().hashCode());
        result = prime * result + ((getPreqtyProduct() == null) ? 0 : getPreqtyProduct().hashCode());
        result = prime * result + ((getQtyDb() == null) ? 0 : getQtyDb().hashCode());
        result = prime * result + ((getPreqtyDb() == null) ? 0 : getPreqtyDb().hashCode());
        result = prime * result + ((getQtyCg() == null) ? 0 : getQtyCg().hashCode());
        result = prime * result + ((getPreqtyCg() == null) ? 0 : getPreqtyCg().hashCode());
        return result;
    }
}