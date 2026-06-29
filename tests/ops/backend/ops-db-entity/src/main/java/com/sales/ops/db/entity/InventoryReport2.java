package com.sales.ops.db.entity;

import java.io.Serializable;

public class InventoryReport2 implements Serializable {
    private String warehouseCode;

    private String warehouseName;

    private String warehouseType;

    private String modelno;

    private String inventoryTypeCode;

    private String customerNo;

    private String ppl;

    private String projectCode;

    private String groupCustomerNo;

    private String salesInfoNo;

    private Integer qtyn;

    private Integer pqtyn;

    private Integer qtyw;

    private Integer pqtyw;

    private Integer qtyd;

    private Integer pqtyd;

    private Integer qtyc;

    private Integer pqtyc;

    private Integer qtyp;

    private Integer pqtyp;

    private String name;

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

    public Integer getQtyn() {
        return qtyn;
    }

    public void setQtyn(Integer qtyn) {
        this.qtyn = qtyn;
    }

    public Integer getPqtyn() {
        return pqtyn;
    }

    public void setPqtyn(Integer pqtyn) {
        this.pqtyn = pqtyn;
    }

    public Integer getQtyw() {
        return qtyw;
    }

    public void setQtyw(Integer qtyw) {
        this.qtyw = qtyw;
    }

    public Integer getPqtyw() {
        return pqtyw;
    }

    public void setPqtyw(Integer pqtyw) {
        this.pqtyw = pqtyw;
    }

    public Integer getQtyd() {
        return qtyd;
    }

    public void setQtyd(Integer qtyd) {
        this.qtyd = qtyd;
    }

    public Integer getPqtyd() {
        return pqtyd;
    }

    public void setPqtyd(Integer pqtyd) {
        this.pqtyd = pqtyd;
    }

    public Integer getQtyc() {
        return qtyc;
    }

    public void setQtyc(Integer qtyc) {
        this.qtyc = qtyc;
    }

    public Integer getPqtyc() {
        return pqtyc;
    }

    public void setPqtyc(Integer pqtyc) {
        this.pqtyc = pqtyc;
    }

    public Integer getQtyp() {
        return qtyp;
    }

    public void setQtyp(Integer qtyp) {
        this.qtyp = qtyp;
    }

    public Integer getPqtyp() {
        return pqtyp;
    }

    public void setPqtyp(Integer pqtyp) {
        this.pqtyp = pqtyp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
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
        InventoryReport2 other = (InventoryReport2) that;
        return (this.getWarehouseCode() == null ? other.getWarehouseCode() == null : this.getWarehouseCode().equals(other.getWarehouseCode()))
            && (this.getWarehouseName() == null ? other.getWarehouseName() == null : this.getWarehouseName().equals(other.getWarehouseName()))
            && (this.getWarehouseType() == null ? other.getWarehouseType() == null : this.getWarehouseType().equals(other.getWarehouseType()))
            && (this.getModelno() == null ? other.getModelno() == null : this.getModelno().equals(other.getModelno()))
            && (this.getInventoryTypeCode() == null ? other.getInventoryTypeCode() == null : this.getInventoryTypeCode().equals(other.getInventoryTypeCode()))
            && (this.getCustomerNo() == null ? other.getCustomerNo() == null : this.getCustomerNo().equals(other.getCustomerNo()))
            && (this.getPpl() == null ? other.getPpl() == null : this.getPpl().equals(other.getPpl()))
            && (this.getProjectCode() == null ? other.getProjectCode() == null : this.getProjectCode().equals(other.getProjectCode()))
            && (this.getGroupCustomerNo() == null ? other.getGroupCustomerNo() == null : this.getGroupCustomerNo().equals(other.getGroupCustomerNo()))
            && (this.getSalesInfoNo() == null ? other.getSalesInfoNo() == null : this.getSalesInfoNo().equals(other.getSalesInfoNo()))
            && (this.getQtyn() == null ? other.getQtyn() == null : this.getQtyn().equals(other.getQtyn()))
            && (this.getPqtyn() == null ? other.getPqtyn() == null : this.getPqtyn().equals(other.getPqtyn()))
            && (this.getQtyw() == null ? other.getQtyw() == null : this.getQtyw().equals(other.getQtyw()))
            && (this.getPqtyw() == null ? other.getPqtyw() == null : this.getPqtyw().equals(other.getPqtyw()))
            && (this.getQtyd() == null ? other.getQtyd() == null : this.getQtyd().equals(other.getQtyd()))
            && (this.getPqtyd() == null ? other.getPqtyd() == null : this.getPqtyd().equals(other.getPqtyd()))
            && (this.getQtyc() == null ? other.getQtyc() == null : this.getQtyc().equals(other.getQtyc()))
            && (this.getPqtyc() == null ? other.getPqtyc() == null : this.getPqtyc().equals(other.getPqtyc()))
            && (this.getQtyp() == null ? other.getQtyp() == null : this.getQtyp().equals(other.getQtyp()))
            && (this.getPqtyp() == null ? other.getPqtyp() == null : this.getPqtyp().equals(other.getPqtyp()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()));
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
        result = prime * result + ((getPpl() == null) ? 0 : getPpl().hashCode());
        result = prime * result + ((getProjectCode() == null) ? 0 : getProjectCode().hashCode());
        result = prime * result + ((getGroupCustomerNo() == null) ? 0 : getGroupCustomerNo().hashCode());
        result = prime * result + ((getSalesInfoNo() == null) ? 0 : getSalesInfoNo().hashCode());
        result = prime * result + ((getQtyn() == null) ? 0 : getQtyn().hashCode());
        result = prime * result + ((getPqtyn() == null) ? 0 : getPqtyn().hashCode());
        result = prime * result + ((getQtyw() == null) ? 0 : getQtyw().hashCode());
        result = prime * result + ((getPqtyw() == null) ? 0 : getPqtyw().hashCode());
        result = prime * result + ((getQtyd() == null) ? 0 : getQtyd().hashCode());
        result = prime * result + ((getPqtyd() == null) ? 0 : getPqtyd().hashCode());
        result = prime * result + ((getQtyc() == null) ? 0 : getQtyc().hashCode());
        result = prime * result + ((getPqtyc() == null) ? 0 : getPqtyc().hashCode());
        result = prime * result + ((getQtyp() == null) ? 0 : getQtyp().hashCode());
        result = prime * result + ((getPqtyp() == null) ? 0 : getPqtyp().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        return result;
    }
}