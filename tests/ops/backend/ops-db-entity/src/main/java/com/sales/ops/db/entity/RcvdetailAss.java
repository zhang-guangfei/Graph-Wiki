package com.sales.ops.db.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class RcvdetailAss implements Serializable {
    private Long id;

    private String orderNo;

    private Integer itemNo;

    private String modelNo;

    private Integer status;

    private Integer unitQty;

    private Integer quantity;

    private Integer allocatedQty;

    private String stockNo;

    private String stockType;

    private Integer orderType;

    private Long parentRorderId;

    private String parentOrderNo;

    private String parentModelno;

    private Integer expDlvtype;

    private Short assType;

    private BigDecimal price;

    private BigDecimal agentSalesprice;

    private BigDecimal eprice;

    private BigDecimal taxrate;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo == null ? null : modelNo.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getUnitQty() {
        return unitQty;
    }

    public void setUnitQty(Integer unitQty) {
        this.unitQty = unitQty;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getAllocatedQty() {
        return allocatedQty;
    }

    public void setAllocatedQty(Integer allocatedQty) {
        this.allocatedQty = allocatedQty;
    }

    public String getStockNo() {
        return stockNo;
    }

    public void setStockNo(String stockNo) {
        this.stockNo = stockNo == null ? null : stockNo.trim();
    }

    public String getStockType() {
        return stockType;
    }

    public void setStockType(String stockType) {
        this.stockType = stockType == null ? null : stockType.trim();
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public Long getParentRorderId() {
        return parentRorderId;
    }

    public void setParentRorderId(Long parentRorderId) {
        this.parentRorderId = parentRorderId;
    }

    public String getParentOrderNo() {
        return parentOrderNo;
    }

    public void setParentOrderNo(String parentOrderNo) {
        this.parentOrderNo = parentOrderNo == null ? null : parentOrderNo.trim();
    }

    public String getParentModelno() {
        return parentModelno;
    }

    public void setParentModelno(String parentModelno) {
        this.parentModelno = parentModelno == null ? null : parentModelno.trim();
    }

    public Integer getExpDlvtype() {
        return expDlvtype;
    }

    public void setExpDlvtype(Integer expDlvtype) {
        this.expDlvtype = expDlvtype;
    }

    public Short getAssType() {
        return assType;
    }

    public void setAssType(Short assType) {
        this.assType = assType;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getAgentSalesprice() {
        return agentSalesprice;
    }

    public void setAgentSalesprice(BigDecimal agentSalesprice) {
        this.agentSalesprice = agentSalesprice;
    }

    public BigDecimal getEprice() {
        return eprice;
    }

    public void setEprice(BigDecimal eprice) {
        this.eprice = eprice;
    }

    public BigDecimal getTaxrate() {
        return taxrate;
    }

    public void setTaxrate(BigDecimal taxrate) {
        this.taxrate = taxrate;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
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
        RcvdetailAss other = (RcvdetailAss) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getOrderNo() == null ? other.getOrderNo() == null : this.getOrderNo().equals(other.getOrderNo()))
            && (this.getItemNo() == null ? other.getItemNo() == null : this.getItemNo().equals(other.getItemNo()))
            && (this.getModelNo() == null ? other.getModelNo() == null : this.getModelNo().equals(other.getModelNo()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getUnitQty() == null ? other.getUnitQty() == null : this.getUnitQty().equals(other.getUnitQty()))
            && (this.getQuantity() == null ? other.getQuantity() == null : this.getQuantity().equals(other.getQuantity()))
            && (this.getAllocatedQty() == null ? other.getAllocatedQty() == null : this.getAllocatedQty().equals(other.getAllocatedQty()))
            && (this.getStockNo() == null ? other.getStockNo() == null : this.getStockNo().equals(other.getStockNo()))
            && (this.getStockType() == null ? other.getStockType() == null : this.getStockType().equals(other.getStockType()))
            && (this.getOrderType() == null ? other.getOrderType() == null : this.getOrderType().equals(other.getOrderType()))
            && (this.getParentRorderId() == null ? other.getParentRorderId() == null : this.getParentRorderId().equals(other.getParentRorderId()))
            && (this.getParentOrderNo() == null ? other.getParentOrderNo() == null : this.getParentOrderNo().equals(other.getParentOrderNo()))
            && (this.getParentModelno() == null ? other.getParentModelno() == null : this.getParentModelno().equals(other.getParentModelno()))
            && (this.getExpDlvtype() == null ? other.getExpDlvtype() == null : this.getExpDlvtype().equals(other.getExpDlvtype()))
            && (this.getAssType() == null ? other.getAssType() == null : this.getAssType().equals(other.getAssType()))
            && (this.getPrice() == null ? other.getPrice() == null : this.getPrice().equals(other.getPrice()))
            && (this.getAgentSalesprice() == null ? other.getAgentSalesprice() == null : this.getAgentSalesprice().equals(other.getAgentSalesprice()))
            && (this.getEprice() == null ? other.getEprice() == null : this.getEprice().equals(other.getEprice()))
            && (this.getTaxrate() == null ? other.getTaxrate() == null : this.getTaxrate().equals(other.getTaxrate()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getCreateUser() == null ? other.getCreateUser() == null : this.getCreateUser().equals(other.getCreateUser()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getUpdateUser() == null ? other.getUpdateUser() == null : this.getUpdateUser().equals(other.getUpdateUser()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getOrderNo() == null) ? 0 : getOrderNo().hashCode());
        result = prime * result + ((getItemNo() == null) ? 0 : getItemNo().hashCode());
        result = prime * result + ((getModelNo() == null) ? 0 : getModelNo().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getUnitQty() == null) ? 0 : getUnitQty().hashCode());
        result = prime * result + ((getQuantity() == null) ? 0 : getQuantity().hashCode());
        result = prime * result + ((getAllocatedQty() == null) ? 0 : getAllocatedQty().hashCode());
        result = prime * result + ((getStockNo() == null) ? 0 : getStockNo().hashCode());
        result = prime * result + ((getStockType() == null) ? 0 : getStockType().hashCode());
        result = prime * result + ((getOrderType() == null) ? 0 : getOrderType().hashCode());
        result = prime * result + ((getParentRorderId() == null) ? 0 : getParentRorderId().hashCode());
        result = prime * result + ((getParentOrderNo() == null) ? 0 : getParentOrderNo().hashCode());
        result = prime * result + ((getParentModelno() == null) ? 0 : getParentModelno().hashCode());
        result = prime * result + ((getExpDlvtype() == null) ? 0 : getExpDlvtype().hashCode());
        result = prime * result + ((getAssType() == null) ? 0 : getAssType().hashCode());
        result = prime * result + ((getPrice() == null) ? 0 : getPrice().hashCode());
        result = prime * result + ((getAgentSalesprice() == null) ? 0 : getAgentSalesprice().hashCode());
        result = prime * result + ((getEprice() == null) ? 0 : getEprice().hashCode());
        result = prime * result + ((getTaxrate() == null) ? 0 : getTaxrate().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getCreateUser() == null) ? 0 : getCreateUser().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getUpdateUser() == null) ? 0 : getUpdateUser().hashCode());
        return result;
    }
}