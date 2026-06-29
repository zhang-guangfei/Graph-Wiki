package com.sales.ops.db.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class StockAdjust implements Serializable {
    private Integer id;

    private String invoiceno;

    private String fullorderno;

    private String orderno;

    private Integer itemno;

    private Integer splititemno;

    private String modelno;

    private Integer quantity;

    private Integer adjusttype;

    private Integer optcode;

    private Date createtime;

    private Date updatetime;

    private String createuser;

    private String updateuser;

    private BigDecimal price;

    private BigDecimal amount;

    private String warehousecode;

    private String pplno;

    private String projectno;

    private String customerno;

    private String groupcustomerno;

    private String inventorytypecode;

    private Long inventorypropertyid;

    private String reason;

    private Date adjustdate;

    private String suppliercode;

    private BigDecimal exchangerate;

    private String currency;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInvoiceno() {
        return invoiceno;
    }

    public void setInvoiceno(String invoiceno) {
        this.invoiceno = invoiceno == null ? null : invoiceno.trim();
    }

    public String getFullorderno() {
        return fullorderno;
    }

    public void setFullorderno(String fullorderno) {
        this.fullorderno = fullorderno == null ? null : fullorderno.trim();
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

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno == null ? null : modelno.trim();
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getAdjusttype() {
        return adjusttype;
    }

    public void setAdjusttype(Integer adjusttype) {
        this.adjusttype = adjusttype;
    }

    public Integer getOptcode() {
        return optcode;
    }

    public void setOptcode(Integer optcode) {
        this.optcode = optcode;
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

    public String getCreateuser() {
        return createuser;
    }

    public void setCreateuser(String createuser) {
        this.createuser = createuser == null ? null : createuser.trim();
    }

    public String getUpdateuser() {
        return updateuser;
    }

    public void setUpdateuser(String updateuser) {
        this.updateuser = updateuser == null ? null : updateuser.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getWarehousecode() {
        return warehousecode;
    }

    public void setWarehousecode(String warehousecode) {
        this.warehousecode = warehousecode == null ? null : warehousecode.trim();
    }

    public String getPplno() {
        return pplno;
    }

    public void setPplno(String pplno) {
        this.pplno = pplno == null ? null : pplno.trim();
    }

    public String getProjectno() {
        return projectno;
    }

    public void setProjectno(String projectno) {
        this.projectno = projectno == null ? null : projectno.trim();
    }

    public String getCustomerno() {
        return customerno;
    }

    public void setCustomerno(String customerno) {
        this.customerno = customerno == null ? null : customerno.trim();
    }

    public String getGroupcustomerno() {
        return groupcustomerno;
    }

    public void setGroupcustomerno(String groupcustomerno) {
        this.groupcustomerno = groupcustomerno == null ? null : groupcustomerno.trim();
    }

    public String getInventorytypecode() {
        return inventorytypecode;
    }

    public void setInventorytypecode(String inventorytypecode) {
        this.inventorytypecode = inventorytypecode == null ? null : inventorytypecode.trim();
    }

    public Long getInventorypropertyid() {
        return inventorypropertyid;
    }

    public void setInventorypropertyid(Long inventorypropertyid) {
        this.inventorypropertyid = inventorypropertyid;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    public Date getAdjustdate() {
        return adjustdate;
    }

    public void setAdjustdate(Date adjustdate) {
        this.adjustdate = adjustdate;
    }

    public String getSuppliercode() {
        return suppliercode;
    }

    public void setSuppliercode(String suppliercode) {
        this.suppliercode = suppliercode == null ? null : suppliercode.trim();
    }

    public BigDecimal getExchangerate() {
        return exchangerate;
    }

    public void setExchangerate(BigDecimal exchangerate) {
        this.exchangerate = exchangerate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency == null ? null : currency.trim();
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
        StockAdjust other = (StockAdjust) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getInvoiceno() == null ? other.getInvoiceno() == null : this.getInvoiceno().equals(other.getInvoiceno()))
            && (this.getFullorderno() == null ? other.getFullorderno() == null : this.getFullorderno().equals(other.getFullorderno()))
            && (this.getOrderno() == null ? other.getOrderno() == null : this.getOrderno().equals(other.getOrderno()))
            && (this.getItemno() == null ? other.getItemno() == null : this.getItemno().equals(other.getItemno()))
            && (this.getSplititemno() == null ? other.getSplititemno() == null : this.getSplititemno().equals(other.getSplititemno()))
            && (this.getModelno() == null ? other.getModelno() == null : this.getModelno().equals(other.getModelno()))
            && (this.getQuantity() == null ? other.getQuantity() == null : this.getQuantity().equals(other.getQuantity()))
            && (this.getAdjusttype() == null ? other.getAdjusttype() == null : this.getAdjusttype().equals(other.getAdjusttype()))
            && (this.getOptcode() == null ? other.getOptcode() == null : this.getOptcode().equals(other.getOptcode()))
            && (this.getCreatetime() == null ? other.getCreatetime() == null : this.getCreatetime().equals(other.getCreatetime()))
            && (this.getUpdatetime() == null ? other.getUpdatetime() == null : this.getUpdatetime().equals(other.getUpdatetime()))
            && (this.getCreateuser() == null ? other.getCreateuser() == null : this.getCreateuser().equals(other.getCreateuser()))
            && (this.getUpdateuser() == null ? other.getUpdateuser() == null : this.getUpdateuser().equals(other.getUpdateuser()))
            && (this.getPrice() == null ? other.getPrice() == null : this.getPrice().equals(other.getPrice()))
            && (this.getAmount() == null ? other.getAmount() == null : this.getAmount().equals(other.getAmount()))
            && (this.getWarehousecode() == null ? other.getWarehousecode() == null : this.getWarehousecode().equals(other.getWarehousecode()))
            && (this.getPplno() == null ? other.getPplno() == null : this.getPplno().equals(other.getPplno()))
            && (this.getProjectno() == null ? other.getProjectno() == null : this.getProjectno().equals(other.getProjectno()))
            && (this.getCustomerno() == null ? other.getCustomerno() == null : this.getCustomerno().equals(other.getCustomerno()))
            && (this.getGroupcustomerno() == null ? other.getGroupcustomerno() == null : this.getGroupcustomerno().equals(other.getGroupcustomerno()))
            && (this.getInventorytypecode() == null ? other.getInventorytypecode() == null : this.getInventorytypecode().equals(other.getInventorytypecode()))
            && (this.getInventorypropertyid() == null ? other.getInventorypropertyid() == null : this.getInventorypropertyid().equals(other.getInventorypropertyid()))
            && (this.getReason() == null ? other.getReason() == null : this.getReason().equals(other.getReason()))
            && (this.getAdjustdate() == null ? other.getAdjustdate() == null : this.getAdjustdate().equals(other.getAdjustdate()))
            && (this.getSuppliercode() == null ? other.getSuppliercode() == null : this.getSuppliercode().equals(other.getSuppliercode()))
            && (this.getExchangerate() == null ? other.getExchangerate() == null : this.getExchangerate().equals(other.getExchangerate()))
            && (this.getCurrency() == null ? other.getCurrency() == null : this.getCurrency().equals(other.getCurrency()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getInvoiceno() == null) ? 0 : getInvoiceno().hashCode());
        result = prime * result + ((getFullorderno() == null) ? 0 : getFullorderno().hashCode());
        result = prime * result + ((getOrderno() == null) ? 0 : getOrderno().hashCode());
        result = prime * result + ((getItemno() == null) ? 0 : getItemno().hashCode());
        result = prime * result + ((getSplititemno() == null) ? 0 : getSplititemno().hashCode());
        result = prime * result + ((getModelno() == null) ? 0 : getModelno().hashCode());
        result = prime * result + ((getQuantity() == null) ? 0 : getQuantity().hashCode());
        result = prime * result + ((getAdjusttype() == null) ? 0 : getAdjusttype().hashCode());
        result = prime * result + ((getOptcode() == null) ? 0 : getOptcode().hashCode());
        result = prime * result + ((getCreatetime() == null) ? 0 : getCreatetime().hashCode());
        result = prime * result + ((getUpdatetime() == null) ? 0 : getUpdatetime().hashCode());
        result = prime * result + ((getCreateuser() == null) ? 0 : getCreateuser().hashCode());
        result = prime * result + ((getUpdateuser() == null) ? 0 : getUpdateuser().hashCode());
        result = prime * result + ((getPrice() == null) ? 0 : getPrice().hashCode());
        result = prime * result + ((getAmount() == null) ? 0 : getAmount().hashCode());
        result = prime * result + ((getWarehousecode() == null) ? 0 : getWarehousecode().hashCode());
        result = prime * result + ((getPplno() == null) ? 0 : getPplno().hashCode());
        result = prime * result + ((getProjectno() == null) ? 0 : getProjectno().hashCode());
        result = prime * result + ((getCustomerno() == null) ? 0 : getCustomerno().hashCode());
        result = prime * result + ((getGroupcustomerno() == null) ? 0 : getGroupcustomerno().hashCode());
        result = prime * result + ((getInventorytypecode() == null) ? 0 : getInventorytypecode().hashCode());
        result = prime * result + ((getInventorypropertyid() == null) ? 0 : getInventorypropertyid().hashCode());
        result = prime * result + ((getReason() == null) ? 0 : getReason().hashCode());
        result = prime * result + ((getAdjustdate() == null) ? 0 : getAdjustdate().hashCode());
        result = prime * result + ((getSuppliercode() == null) ? 0 : getSuppliercode().hashCode());
        result = prime * result + ((getExchangerate() == null) ? 0 : getExchangerate().hashCode());
        result = prime * result + ((getCurrency() == null) ? 0 : getCurrency().hashCode());
        return result;
    }
}