package com.sales.ops.db.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ImpInvoiceMaster implements Serializable {
    private Long id;

    private String invoiceNo;

    private String supplierCode;

    private Integer totalQty;

    private Integer status;

    private BigDecimal amount;

    private Integer boxQty;

    private Integer orderQty;

    private Date shipDate;

    private Date customsDate;

    private BigDecimal grossWeight;

    private BigDecimal weight;

    private BigDecimal customsFee;

    private BigDecimal vatFee;

    private BigDecimal otherFee;

    private String currency;

    private BigDecimal exchangeRate;

    private BigDecimal amountRmb;

    private Date createTime;

    private Date updateTime;

    private String updateUser;

    private String createUser;

    private String transType;

    private Date prearriveDate;

    private String remark;

    private String shipment;

    private String cinvoiceNo;

    private BigDecimal transFee;

    private Date invoiceDate;

    private String arrivedWarehouseCode;

    private String receiveWarehouseCode;

    private Date arriveDate;

    private Integer dataType;

    private Date receiveDate;

    private String declarationNo;

    private String plantmark;

    private BigDecimal exciseTax;

    private String bargainType;

    private Date portArrivedate;

    private String gwState;

    private String gwStatecode;

    private Integer payDay;

    private Date customsShipdate;

    private BigDecimal shipAmount;

    private Integer invoiceType;

    private Date confirmDate;

    private String optStatus;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo == null ? null : invoiceNo.trim();
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode == null ? null : supplierCode.trim();
    }

    public Integer getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(Integer totalQty) {
        this.totalQty = totalQty;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getBoxQty() {
        return boxQty;
    }

    public void setBoxQty(Integer boxQty) {
        this.boxQty = boxQty;
    }

    public Integer getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(Integer orderQty) {
        this.orderQty = orderQty;
    }

    public Date getShipDate() {
        return shipDate;
    }

    public void setShipDate(Date shipDate) {
        this.shipDate = shipDate;
    }

    public Date getCustomsDate() {
        return customsDate;
    }

    public void setCustomsDate(Date customsDate) {
        this.customsDate = customsDate;
    }

    public BigDecimal getGrossWeight() {
        return grossWeight;
    }

    public void setGrossWeight(BigDecimal grossWeight) {
        this.grossWeight = grossWeight;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getCustomsFee() {
        return customsFee;
    }

    public void setCustomsFee(BigDecimal customsFee) {
        this.customsFee = customsFee;
    }

    public BigDecimal getVatFee() {
        return vatFee;
    }

    public void setVatFee(BigDecimal vatFee) {
        this.vatFee = vatFee;
    }

    public BigDecimal getOtherFee() {
        return otherFee;
    }

    public void setOtherFee(BigDecimal otherFee) {
        this.otherFee = otherFee;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency == null ? null : currency.trim();
    }

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public BigDecimal getAmountRmb() {
        return amountRmb;
    }

    public void setAmountRmb(BigDecimal amountRmb) {
        this.amountRmb = amountRmb;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType == null ? null : transType.trim();
    }

    public Date getPrearriveDate() {
        return prearriveDate;
    }

    public void setPrearriveDate(Date prearriveDate) {
        this.prearriveDate = prearriveDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getShipment() {
        return shipment;
    }

    public void setShipment(String shipment) {
        this.shipment = shipment == null ? null : shipment.trim();
    }

    public String getCinvoiceNo() {
        return cinvoiceNo;
    }

    public void setCinvoiceNo(String cinvoiceNo) {
        this.cinvoiceNo = cinvoiceNo == null ? null : cinvoiceNo.trim();
    }

    public BigDecimal getTransFee() {
        return transFee;
    }

    public void setTransFee(BigDecimal transFee) {
        this.transFee = transFee;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getArrivedWarehouseCode() {
        return arrivedWarehouseCode;
    }

    public void setArrivedWarehouseCode(String arrivedWarehouseCode) {
        this.arrivedWarehouseCode = arrivedWarehouseCode == null ? null : arrivedWarehouseCode.trim();
    }

    public String getReceiveWarehouseCode() {
        return receiveWarehouseCode;
    }

    public void setReceiveWarehouseCode(String receiveWarehouseCode) {
        this.receiveWarehouseCode = receiveWarehouseCode == null ? null : receiveWarehouseCode.trim();
    }

    public Date getArriveDate() {
        return arriveDate;
    }

    public void setArriveDate(Date arriveDate) {
        this.arriveDate = arriveDate;
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public Date getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    public String getDeclarationNo() {
        return declarationNo;
    }

    public void setDeclarationNo(String declarationNo) {
        this.declarationNo = declarationNo == null ? null : declarationNo.trim();
    }

    public String getPlantmark() {
        return plantmark;
    }

    public void setPlantmark(String plantmark) {
        this.plantmark = plantmark == null ? null : plantmark.trim();
    }

    public BigDecimal getExciseTax() {
        return exciseTax;
    }

    public void setExciseTax(BigDecimal exciseTax) {
        this.exciseTax = exciseTax;
    }

    public String getBargainType() {
        return bargainType;
    }

    public void setBargainType(String bargainType) {
        this.bargainType = bargainType == null ? null : bargainType.trim();
    }

    public Date getPortArrivedate() {
        return portArrivedate;
    }

    public void setPortArrivedate(Date portArrivedate) {
        this.portArrivedate = portArrivedate;
    }

    public String getGwState() {
        return gwState;
    }

    public void setGwState(String gwState) {
        this.gwState = gwState == null ? null : gwState.trim();
    }

    public String getGwStatecode() {
        return gwStatecode;
    }

    public void setGwStatecode(String gwStatecode) {
        this.gwStatecode = gwStatecode == null ? null : gwStatecode.trim();
    }

    public Integer getPayDay() {
        return payDay;
    }

    public void setPayDay(Integer payDay) {
        this.payDay = payDay;
    }

    public Date getCustomsShipdate() {
        return customsShipdate;
    }

    public void setCustomsShipdate(Date customsShipdate) {
        this.customsShipdate = customsShipdate;
    }

    public BigDecimal getShipAmount() {
        return shipAmount;
    }

    public void setShipAmount(BigDecimal shipAmount) {
        this.shipAmount = shipAmount;
    }

    public Integer getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(Integer invoiceType) {
        this.invoiceType = invoiceType;
    }

    public Date getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(Date confirmDate) {
        this.confirmDate = confirmDate;
    }

    public String getOptStatus() {
        return optStatus;
    }

    public void setOptStatus(String optStatus) {
        this.optStatus = optStatus == null ? null : optStatus.trim();
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
        ImpInvoiceMaster other = (ImpInvoiceMaster) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getInvoiceNo() == null ? other.getInvoiceNo() == null : this.getInvoiceNo().equals(other.getInvoiceNo()))
            && (this.getSupplierCode() == null ? other.getSupplierCode() == null : this.getSupplierCode().equals(other.getSupplierCode()))
            && (this.getTotalQty() == null ? other.getTotalQty() == null : this.getTotalQty().equals(other.getTotalQty()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getAmount() == null ? other.getAmount() == null : this.getAmount().equals(other.getAmount()))
            && (this.getBoxQty() == null ? other.getBoxQty() == null : this.getBoxQty().equals(other.getBoxQty()))
            && (this.getOrderQty() == null ? other.getOrderQty() == null : this.getOrderQty().equals(other.getOrderQty()))
            && (this.getShipDate() == null ? other.getShipDate() == null : this.getShipDate().equals(other.getShipDate()))
            && (this.getCustomsDate() == null ? other.getCustomsDate() == null : this.getCustomsDate().equals(other.getCustomsDate()))
            && (this.getGrossWeight() == null ? other.getGrossWeight() == null : this.getGrossWeight().equals(other.getGrossWeight()))
            && (this.getWeight() == null ? other.getWeight() == null : this.getWeight().equals(other.getWeight()))
            && (this.getCustomsFee() == null ? other.getCustomsFee() == null : this.getCustomsFee().equals(other.getCustomsFee()))
            && (this.getVatFee() == null ? other.getVatFee() == null : this.getVatFee().equals(other.getVatFee()))
            && (this.getOtherFee() == null ? other.getOtherFee() == null : this.getOtherFee().equals(other.getOtherFee()))
            && (this.getCurrency() == null ? other.getCurrency() == null : this.getCurrency().equals(other.getCurrency()))
            && (this.getExchangeRate() == null ? other.getExchangeRate() == null : this.getExchangeRate().equals(other.getExchangeRate()))
            && (this.getAmountRmb() == null ? other.getAmountRmb() == null : this.getAmountRmb().equals(other.getAmountRmb()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getUpdateUser() == null ? other.getUpdateUser() == null : this.getUpdateUser().equals(other.getUpdateUser()))
            && (this.getCreateUser() == null ? other.getCreateUser() == null : this.getCreateUser().equals(other.getCreateUser()))
            && (this.getTransType() == null ? other.getTransType() == null : this.getTransType().equals(other.getTransType()))
            && (this.getPrearriveDate() == null ? other.getPrearriveDate() == null : this.getPrearriveDate().equals(other.getPrearriveDate()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getShipment() == null ? other.getShipment() == null : this.getShipment().equals(other.getShipment()))
            && (this.getCinvoiceNo() == null ? other.getCinvoiceNo() == null : this.getCinvoiceNo().equals(other.getCinvoiceNo()))
            && (this.getTransFee() == null ? other.getTransFee() == null : this.getTransFee().equals(other.getTransFee()))
            && (this.getInvoiceDate() == null ? other.getInvoiceDate() == null : this.getInvoiceDate().equals(other.getInvoiceDate()))
            && (this.getArrivedWarehouseCode() == null ? other.getArrivedWarehouseCode() == null : this.getArrivedWarehouseCode().equals(other.getArrivedWarehouseCode()))
            && (this.getReceiveWarehouseCode() == null ? other.getReceiveWarehouseCode() == null : this.getReceiveWarehouseCode().equals(other.getReceiveWarehouseCode()))
            && (this.getArriveDate() == null ? other.getArriveDate() == null : this.getArriveDate().equals(other.getArriveDate()))
            && (this.getDataType() == null ? other.getDataType() == null : this.getDataType().equals(other.getDataType()))
            && (this.getReceiveDate() == null ? other.getReceiveDate() == null : this.getReceiveDate().equals(other.getReceiveDate()))
            && (this.getDeclarationNo() == null ? other.getDeclarationNo() == null : this.getDeclarationNo().equals(other.getDeclarationNo()))
            && (this.getPlantmark() == null ? other.getPlantmark() == null : this.getPlantmark().equals(other.getPlantmark()))
            && (this.getExciseTax() == null ? other.getExciseTax() == null : this.getExciseTax().equals(other.getExciseTax()))
            && (this.getBargainType() == null ? other.getBargainType() == null : this.getBargainType().equals(other.getBargainType()))
            && (this.getPortArrivedate() == null ? other.getPortArrivedate() == null : this.getPortArrivedate().equals(other.getPortArrivedate()))
            && (this.getGwState() == null ? other.getGwState() == null : this.getGwState().equals(other.getGwState()))
            && (this.getGwStatecode() == null ? other.getGwStatecode() == null : this.getGwStatecode().equals(other.getGwStatecode()))
            && (this.getPayDay() == null ? other.getPayDay() == null : this.getPayDay().equals(other.getPayDay()))
            && (this.getCustomsShipdate() == null ? other.getCustomsShipdate() == null : this.getCustomsShipdate().equals(other.getCustomsShipdate()))
            && (this.getShipAmount() == null ? other.getShipAmount() == null : this.getShipAmount().equals(other.getShipAmount()))
            && (this.getInvoiceType() == null ? other.getInvoiceType() == null : this.getInvoiceType().equals(other.getInvoiceType()))
            && (this.getConfirmDate() == null ? other.getConfirmDate() == null : this.getConfirmDate().equals(other.getConfirmDate()))
            && (this.getOptStatus() == null ? other.getOptStatus() == null : this.getOptStatus().equals(other.getOptStatus()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getInvoiceNo() == null) ? 0 : getInvoiceNo().hashCode());
        result = prime * result + ((getSupplierCode() == null) ? 0 : getSupplierCode().hashCode());
        result = prime * result + ((getTotalQty() == null) ? 0 : getTotalQty().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getAmount() == null) ? 0 : getAmount().hashCode());
        result = prime * result + ((getBoxQty() == null) ? 0 : getBoxQty().hashCode());
        result = prime * result + ((getOrderQty() == null) ? 0 : getOrderQty().hashCode());
        result = prime * result + ((getShipDate() == null) ? 0 : getShipDate().hashCode());
        result = prime * result + ((getCustomsDate() == null) ? 0 : getCustomsDate().hashCode());
        result = prime * result + ((getGrossWeight() == null) ? 0 : getGrossWeight().hashCode());
        result = prime * result + ((getWeight() == null) ? 0 : getWeight().hashCode());
        result = prime * result + ((getCustomsFee() == null) ? 0 : getCustomsFee().hashCode());
        result = prime * result + ((getVatFee() == null) ? 0 : getVatFee().hashCode());
        result = prime * result + ((getOtherFee() == null) ? 0 : getOtherFee().hashCode());
        result = prime * result + ((getCurrency() == null) ? 0 : getCurrency().hashCode());
        result = prime * result + ((getExchangeRate() == null) ? 0 : getExchangeRate().hashCode());
        result = prime * result + ((getAmountRmb() == null) ? 0 : getAmountRmb().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getUpdateUser() == null) ? 0 : getUpdateUser().hashCode());
        result = prime * result + ((getCreateUser() == null) ? 0 : getCreateUser().hashCode());
        result = prime * result + ((getTransType() == null) ? 0 : getTransType().hashCode());
        result = prime * result + ((getPrearriveDate() == null) ? 0 : getPrearriveDate().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getShipment() == null) ? 0 : getShipment().hashCode());
        result = prime * result + ((getCinvoiceNo() == null) ? 0 : getCinvoiceNo().hashCode());
        result = prime * result + ((getTransFee() == null) ? 0 : getTransFee().hashCode());
        result = prime * result + ((getInvoiceDate() == null) ? 0 : getInvoiceDate().hashCode());
        result = prime * result + ((getArrivedWarehouseCode() == null) ? 0 : getArrivedWarehouseCode().hashCode());
        result = prime * result + ((getReceiveWarehouseCode() == null) ? 0 : getReceiveWarehouseCode().hashCode());
        result = prime * result + ((getArriveDate() == null) ? 0 : getArriveDate().hashCode());
        result = prime * result + ((getDataType() == null) ? 0 : getDataType().hashCode());
        result = prime * result + ((getReceiveDate() == null) ? 0 : getReceiveDate().hashCode());
        result = prime * result + ((getDeclarationNo() == null) ? 0 : getDeclarationNo().hashCode());
        result = prime * result + ((getPlantmark() == null) ? 0 : getPlantmark().hashCode());
        result = prime * result + ((getExciseTax() == null) ? 0 : getExciseTax().hashCode());
        result = prime * result + ((getBargainType() == null) ? 0 : getBargainType().hashCode());
        result = prime * result + ((getPortArrivedate() == null) ? 0 : getPortArrivedate().hashCode());
        result = prime * result + ((getGwState() == null) ? 0 : getGwState().hashCode());
        result = prime * result + ((getGwStatecode() == null) ? 0 : getGwStatecode().hashCode());
        result = prime * result + ((getPayDay() == null) ? 0 : getPayDay().hashCode());
        result = prime * result + ((getCustomsShipdate() == null) ? 0 : getCustomsShipdate().hashCode());
        result = prime * result + ((getShipAmount() == null) ? 0 : getShipAmount().hashCode());
        result = prime * result + ((getInvoiceType() == null) ? 0 : getInvoiceType().hashCode());
        result = prime * result + ((getConfirmDate() == null) ? 0 : getConfirmDate().hashCode());
        result = prime * result + ((getOptStatus() == null) ? 0 : getOptStatus().hashCode());
        return result;
    }
}