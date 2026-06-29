package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class TempMergePco implements Serializable {
    private Long id;

    private String invoiceno;

    private Integer invoiceQty;

    private String orderno;

    private Integer orderitem;

    private Integer orderitemsplit;

    private String pcoId;

    private Integer pcoItem;

    private String subModelno;

    private Integer subQty;

    private Long invId;

    private Long inventoryId;

    private String inventoryTableType;

    private Integer useQty;

    private Integer outQty;

    private Integer delflag;

    private String inventoryStatus;

    private Integer quantity;

    private Integer prepareQuantity;

    private Integer delflag2;

    private Integer handleStatus;

    private String remark;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInvoiceno() {
        return invoiceno;
    }

    public void setInvoiceno(String invoiceno) {
        this.invoiceno = invoiceno == null ? null : invoiceno.trim();
    }

    public Integer getInvoiceQty() {
        return invoiceQty;
    }

    public void setInvoiceQty(Integer invoiceQty) {
        this.invoiceQty = invoiceQty;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno == null ? null : orderno.trim();
    }

    public Integer getOrderitem() {
        return orderitem;
    }

    public void setOrderitem(Integer orderitem) {
        this.orderitem = orderitem;
    }

    public Integer getOrderitemsplit() {
        return orderitemsplit;
    }

    public void setOrderitemsplit(Integer orderitemsplit) {
        this.orderitemsplit = orderitemsplit;
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

    public Long getInvId() {
        return invId;
    }

    public void setInvId(Long invId) {
        this.invId = invId;
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

    public Integer getDelflag() {
        return delflag;
    }

    public void setDelflag(Integer delflag) {
        this.delflag = delflag;
    }

    public String getInventoryStatus() {
        return inventoryStatus;
    }

    public void setInventoryStatus(String inventoryStatus) {
        this.inventoryStatus = inventoryStatus == null ? null : inventoryStatus.trim();
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

    public Integer getDelflag2() {
        return delflag2;
    }

    public void setDelflag2(Integer delflag2) {
        this.delflag2 = delflag2;
    }

    public Integer getHandleStatus() {
        return handleStatus;
    }

    public void setHandleStatus(Integer handleStatus) {
        this.handleStatus = handleStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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
        TempMergePco other = (TempMergePco) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getInvoiceno() == null ? other.getInvoiceno() == null : this.getInvoiceno().equals(other.getInvoiceno()))
            && (this.getInvoiceQty() == null ? other.getInvoiceQty() == null : this.getInvoiceQty().equals(other.getInvoiceQty()))
            && (this.getOrderno() == null ? other.getOrderno() == null : this.getOrderno().equals(other.getOrderno()))
            && (this.getOrderitem() == null ? other.getOrderitem() == null : this.getOrderitem().equals(other.getOrderitem()))
            && (this.getOrderitemsplit() == null ? other.getOrderitemsplit() == null : this.getOrderitemsplit().equals(other.getOrderitemsplit()))
            && (this.getPcoId() == null ? other.getPcoId() == null : this.getPcoId().equals(other.getPcoId()))
            && (this.getPcoItem() == null ? other.getPcoItem() == null : this.getPcoItem().equals(other.getPcoItem()))
            && (this.getSubModelno() == null ? other.getSubModelno() == null : this.getSubModelno().equals(other.getSubModelno()))
            && (this.getSubQty() == null ? other.getSubQty() == null : this.getSubQty().equals(other.getSubQty()))
            && (this.getInvId() == null ? other.getInvId() == null : this.getInvId().equals(other.getInvId()))
            && (this.getInventoryId() == null ? other.getInventoryId() == null : this.getInventoryId().equals(other.getInventoryId()))
            && (this.getInventoryTableType() == null ? other.getInventoryTableType() == null : this.getInventoryTableType().equals(other.getInventoryTableType()))
            && (this.getUseQty() == null ? other.getUseQty() == null : this.getUseQty().equals(other.getUseQty()))
            && (this.getOutQty() == null ? other.getOutQty() == null : this.getOutQty().equals(other.getOutQty()))
            && (this.getDelflag() == null ? other.getDelflag() == null : this.getDelflag().equals(other.getDelflag()))
            && (this.getInventoryStatus() == null ? other.getInventoryStatus() == null : this.getInventoryStatus().equals(other.getInventoryStatus()))
            && (this.getQuantity() == null ? other.getQuantity() == null : this.getQuantity().equals(other.getQuantity()))
            && (this.getPrepareQuantity() == null ? other.getPrepareQuantity() == null : this.getPrepareQuantity().equals(other.getPrepareQuantity()))
            && (this.getDelflag2() == null ? other.getDelflag2() == null : this.getDelflag2().equals(other.getDelflag2()))
            && (this.getHandleStatus() == null ? other.getHandleStatus() == null : this.getHandleStatus().equals(other.getHandleStatus()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getInvoiceno() == null) ? 0 : getInvoiceno().hashCode());
        result = prime * result + ((getInvoiceQty() == null) ? 0 : getInvoiceQty().hashCode());
        result = prime * result + ((getOrderno() == null) ? 0 : getOrderno().hashCode());
        result = prime * result + ((getOrderitem() == null) ? 0 : getOrderitem().hashCode());
        result = prime * result + ((getOrderitemsplit() == null) ? 0 : getOrderitemsplit().hashCode());
        result = prime * result + ((getPcoId() == null) ? 0 : getPcoId().hashCode());
        result = prime * result + ((getPcoItem() == null) ? 0 : getPcoItem().hashCode());
        result = prime * result + ((getSubModelno() == null) ? 0 : getSubModelno().hashCode());
        result = prime * result + ((getSubQty() == null) ? 0 : getSubQty().hashCode());
        result = prime * result + ((getInvId() == null) ? 0 : getInvId().hashCode());
        result = prime * result + ((getInventoryId() == null) ? 0 : getInventoryId().hashCode());
        result = prime * result + ((getInventoryTableType() == null) ? 0 : getInventoryTableType().hashCode());
        result = prime * result + ((getUseQty() == null) ? 0 : getUseQty().hashCode());
        result = prime * result + ((getOutQty() == null) ? 0 : getOutQty().hashCode());
        result = prime * result + ((getDelflag() == null) ? 0 : getDelflag().hashCode());
        result = prime * result + ((getInventoryStatus() == null) ? 0 : getInventoryStatus().hashCode());
        result = prime * result + ((getQuantity() == null) ? 0 : getQuantity().hashCode());
        result = prime * result + ((getPrepareQuantity() == null) ? 0 : getPrepareQuantity().hashCode());
        result = prime * result + ((getDelflag2() == null) ? 0 : getDelflag2().hashCode());
        result = prime * result + ((getHandleStatus() == null) ? 0 : getHandleStatus().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }
}