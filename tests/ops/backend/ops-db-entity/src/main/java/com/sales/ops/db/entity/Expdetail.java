package com.sales.ops.db.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Expdetail implements Serializable {
    private Long id;

    private String invoiceNo;

    private String deliveryNo;

    private String orderNo;

    private Integer itemNo;

    private String orderFno;

    private String modelNo;

    private Integer quantity;

    private String barcode;

    private String customerNo;

    private String userNo;

    private Date shipDate;

    private String expressNo;

    private String expressCompany;

    private String warehouseCode;

    private BigDecimal price;

    private Integer optCode;

    private String corderNo;

    private String cmodelNo;

    private String caseNo;

    private BigDecimal weight;

    private Integer orderType;

    private String invoiceFlag;

    private Date invoiceTime;

    private Date signTime;

    private String stockCode;

    private Date dlvDate;

    private String ororderno;

    private Short signStatus;

    private String sender;

    private String dlvSite;

    private String volume;

    private String boxType;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;

    private String deptNo;

    private String signOrderNo;

    private String dlvAddress;

    private String contactpsn;

    private String endUser;

    private String email;

    private Integer branchFlag;

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

    public String getDeliveryNo() {
        return deliveryNo;
    }

    public void setDeliveryNo(String deliveryNo) {
        this.deliveryNo = deliveryNo == null ? null : deliveryNo.trim();
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

    public String getOrderFno() {
        return orderFno;
    }

    public void setOrderFno(String orderFno) {
        this.orderFno = orderFno == null ? null : orderFno.trim();
    }

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo == null ? null : modelNo.trim();
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode == null ? null : barcode.trim();
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo == null ? null : customerNo.trim();
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo == null ? null : userNo.trim();
    }

    public Date getShipDate() {
        return shipDate;
    }

    public void setShipDate(Date shipDate) {
        this.shipDate = shipDate;
    }

    public String getExpressNo() {
        return expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo == null ? null : expressNo.trim();
    }

    public String getExpressCompany() {
        return expressCompany;
    }

    public void setExpressCompany(String expressCompany) {
        this.expressCompany = expressCompany == null ? null : expressCompany.trim();
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode == null ? null : warehouseCode.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getOptCode() {
        return optCode;
    }

    public void setOptCode(Integer optCode) {
        this.optCode = optCode;
    }

    public String getCorderNo() {
        return corderNo;
    }

    public void setCorderNo(String corderNo) {
        this.corderNo = corderNo == null ? null : corderNo.trim();
    }

    public String getCmodelNo() {
        return cmodelNo;
    }

    public void setCmodelNo(String cmodelNo) {
        this.cmodelNo = cmodelNo == null ? null : cmodelNo.trim();
    }

    public String getCaseNo() {
        return caseNo;
    }

    public void setCaseNo(String caseNo) {
        this.caseNo = caseNo == null ? null : caseNo.trim();
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public String getInvoiceFlag() {
        return invoiceFlag;
    }

    public void setInvoiceFlag(String invoiceFlag) {
        this.invoiceFlag = invoiceFlag == null ? null : invoiceFlag.trim();
    }

    public Date getInvoiceTime() {
        return invoiceTime;
    }

    public void setInvoiceTime(Date invoiceTime) {
        this.invoiceTime = invoiceTime;
    }

    public Date getSignTime() {
        return signTime;
    }

    public void setSignTime(Date signTime) {
        this.signTime = signTime;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode == null ? null : stockCode.trim();
    }

    public Date getDlvDate() {
        return dlvDate;
    }

    public void setDlvDate(Date dlvDate) {
        this.dlvDate = dlvDate;
    }

    public String getOrorderno() {
        return ororderno;
    }

    public void setOrorderno(String ororderno) {
        this.ororderno = ororderno == null ? null : ororderno.trim();
    }

    public Short getSignStatus() {
        return signStatus;
    }

    public void setSignStatus(Short signStatus) {
        this.signStatus = signStatus;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender == null ? null : sender.trim();
    }

    public String getDlvSite() {
        return dlvSite;
    }

    public void setDlvSite(String dlvSite) {
        this.dlvSite = dlvSite == null ? null : dlvSite.trim();
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume == null ? null : volume.trim();
    }

    public String getBoxType() {
        return boxType;
    }

    public void setBoxType(String boxType) {
        this.boxType = boxType == null ? null : boxType.trim();
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

    public String getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo == null ? null : deptNo.trim();
    }

    public String getSignOrderNo() {
        return signOrderNo;
    }

    public void setSignOrderNo(String signOrderNo) {
        this.signOrderNo = signOrderNo == null ? null : signOrderNo.trim();
    }

    public String getDlvAddress() {
        return dlvAddress;
    }

    public void setDlvAddress(String dlvAddress) {
        this.dlvAddress = dlvAddress == null ? null : dlvAddress.trim();
    }

    public String getContactpsn() {
        return contactpsn;
    }

    public void setContactpsn(String contactpsn) {
        this.contactpsn = contactpsn == null ? null : contactpsn.trim();
    }

    public String getEndUser() {
        return endUser;
    }

    public void setEndUser(String endUser) {
        this.endUser = endUser == null ? null : endUser.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Integer getBranchFlag() {
        return branchFlag;
    }

    public void setBranchFlag(Integer branchFlag) {
        this.branchFlag = branchFlag;
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
        Expdetail other = (Expdetail) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getInvoiceNo() == null ? other.getInvoiceNo() == null : this.getInvoiceNo().equals(other.getInvoiceNo()))
            && (this.getDeliveryNo() == null ? other.getDeliveryNo() == null : this.getDeliveryNo().equals(other.getDeliveryNo()))
            && (this.getOrderNo() == null ? other.getOrderNo() == null : this.getOrderNo().equals(other.getOrderNo()))
            && (this.getItemNo() == null ? other.getItemNo() == null : this.getItemNo().equals(other.getItemNo()))
            && (this.getOrderFno() == null ? other.getOrderFno() == null : this.getOrderFno().equals(other.getOrderFno()))
            && (this.getModelNo() == null ? other.getModelNo() == null : this.getModelNo().equals(other.getModelNo()))
            && (this.getQuantity() == null ? other.getQuantity() == null : this.getQuantity().equals(other.getQuantity()))
            && (this.getBarcode() == null ? other.getBarcode() == null : this.getBarcode().equals(other.getBarcode()))
            && (this.getCustomerNo() == null ? other.getCustomerNo() == null : this.getCustomerNo().equals(other.getCustomerNo()))
            && (this.getUserNo() == null ? other.getUserNo() == null : this.getUserNo().equals(other.getUserNo()))
            && (this.getShipDate() == null ? other.getShipDate() == null : this.getShipDate().equals(other.getShipDate()))
            && (this.getExpressNo() == null ? other.getExpressNo() == null : this.getExpressNo().equals(other.getExpressNo()))
            && (this.getExpressCompany() == null ? other.getExpressCompany() == null : this.getExpressCompany().equals(other.getExpressCompany()))
            && (this.getWarehouseCode() == null ? other.getWarehouseCode() == null : this.getWarehouseCode().equals(other.getWarehouseCode()))
            && (this.getPrice() == null ? other.getPrice() == null : this.getPrice().equals(other.getPrice()))
            && (this.getOptCode() == null ? other.getOptCode() == null : this.getOptCode().equals(other.getOptCode()))
            && (this.getCorderNo() == null ? other.getCorderNo() == null : this.getCorderNo().equals(other.getCorderNo()))
            && (this.getCmodelNo() == null ? other.getCmodelNo() == null : this.getCmodelNo().equals(other.getCmodelNo()))
            && (this.getCaseNo() == null ? other.getCaseNo() == null : this.getCaseNo().equals(other.getCaseNo()))
            && (this.getWeight() == null ? other.getWeight() == null : this.getWeight().equals(other.getWeight()))
            && (this.getOrderType() == null ? other.getOrderType() == null : this.getOrderType().equals(other.getOrderType()))
            && (this.getInvoiceFlag() == null ? other.getInvoiceFlag() == null : this.getInvoiceFlag().equals(other.getInvoiceFlag()))
            && (this.getInvoiceTime() == null ? other.getInvoiceTime() == null : this.getInvoiceTime().equals(other.getInvoiceTime()))
            && (this.getSignTime() == null ? other.getSignTime() == null : this.getSignTime().equals(other.getSignTime()))
            && (this.getStockCode() == null ? other.getStockCode() == null : this.getStockCode().equals(other.getStockCode()))
            && (this.getDlvDate() == null ? other.getDlvDate() == null : this.getDlvDate().equals(other.getDlvDate()))
            && (this.getOrorderno() == null ? other.getOrorderno() == null : this.getOrorderno().equals(other.getOrorderno()))
            && (this.getSignStatus() == null ? other.getSignStatus() == null : this.getSignStatus().equals(other.getSignStatus()))
            && (this.getSender() == null ? other.getSender() == null : this.getSender().equals(other.getSender()))
            && (this.getDlvSite() == null ? other.getDlvSite() == null : this.getDlvSite().equals(other.getDlvSite()))
            && (this.getVolume() == null ? other.getVolume() == null : this.getVolume().equals(other.getVolume()))
            && (this.getBoxType() == null ? other.getBoxType() == null : this.getBoxType().equals(other.getBoxType()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getCreateUser() == null ? other.getCreateUser() == null : this.getCreateUser().equals(other.getCreateUser()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getUpdateUser() == null ? other.getUpdateUser() == null : this.getUpdateUser().equals(other.getUpdateUser()))
            && (this.getDeptNo() == null ? other.getDeptNo() == null : this.getDeptNo().equals(other.getDeptNo()))
            && (this.getSignOrderNo() == null ? other.getSignOrderNo() == null : this.getSignOrderNo().equals(other.getSignOrderNo()))
            && (this.getDlvAddress() == null ? other.getDlvAddress() == null : this.getDlvAddress().equals(other.getDlvAddress()))
            && (this.getContactpsn() == null ? other.getContactpsn() == null : this.getContactpsn().equals(other.getContactpsn()))
            && (this.getEndUser() == null ? other.getEndUser() == null : this.getEndUser().equals(other.getEndUser()))
            && (this.getEmail() == null ? other.getEmail() == null : this.getEmail().equals(other.getEmail()))
            && (this.getBranchFlag() == null ? other.getBranchFlag() == null : this.getBranchFlag().equals(other.getBranchFlag()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getInvoiceNo() == null) ? 0 : getInvoiceNo().hashCode());
        result = prime * result + ((getDeliveryNo() == null) ? 0 : getDeliveryNo().hashCode());
        result = prime * result + ((getOrderNo() == null) ? 0 : getOrderNo().hashCode());
        result = prime * result + ((getItemNo() == null) ? 0 : getItemNo().hashCode());
        result = prime * result + ((getOrderFno() == null) ? 0 : getOrderFno().hashCode());
        result = prime * result + ((getModelNo() == null) ? 0 : getModelNo().hashCode());
        result = prime * result + ((getQuantity() == null) ? 0 : getQuantity().hashCode());
        result = prime * result + ((getBarcode() == null) ? 0 : getBarcode().hashCode());
        result = prime * result + ((getCustomerNo() == null) ? 0 : getCustomerNo().hashCode());
        result = prime * result + ((getUserNo() == null) ? 0 : getUserNo().hashCode());
        result = prime * result + ((getShipDate() == null) ? 0 : getShipDate().hashCode());
        result = prime * result + ((getExpressNo() == null) ? 0 : getExpressNo().hashCode());
        result = prime * result + ((getExpressCompany() == null) ? 0 : getExpressCompany().hashCode());
        result = prime * result + ((getWarehouseCode() == null) ? 0 : getWarehouseCode().hashCode());
        result = prime * result + ((getPrice() == null) ? 0 : getPrice().hashCode());
        result = prime * result + ((getOptCode() == null) ? 0 : getOptCode().hashCode());
        result = prime * result + ((getCorderNo() == null) ? 0 : getCorderNo().hashCode());
        result = prime * result + ((getCmodelNo() == null) ? 0 : getCmodelNo().hashCode());
        result = prime * result + ((getCaseNo() == null) ? 0 : getCaseNo().hashCode());
        result = prime * result + ((getWeight() == null) ? 0 : getWeight().hashCode());
        result = prime * result + ((getOrderType() == null) ? 0 : getOrderType().hashCode());
        result = prime * result + ((getInvoiceFlag() == null) ? 0 : getInvoiceFlag().hashCode());
        result = prime * result + ((getInvoiceTime() == null) ? 0 : getInvoiceTime().hashCode());
        result = prime * result + ((getSignTime() == null) ? 0 : getSignTime().hashCode());
        result = prime * result + ((getStockCode() == null) ? 0 : getStockCode().hashCode());
        result = prime * result + ((getDlvDate() == null) ? 0 : getDlvDate().hashCode());
        result = prime * result + ((getOrorderno() == null) ? 0 : getOrorderno().hashCode());
        result = prime * result + ((getSignStatus() == null) ? 0 : getSignStatus().hashCode());
        result = prime * result + ((getSender() == null) ? 0 : getSender().hashCode());
        result = prime * result + ((getDlvSite() == null) ? 0 : getDlvSite().hashCode());
        result = prime * result + ((getVolume() == null) ? 0 : getVolume().hashCode());
        result = prime * result + ((getBoxType() == null) ? 0 : getBoxType().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getCreateUser() == null) ? 0 : getCreateUser().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getUpdateUser() == null) ? 0 : getUpdateUser().hashCode());
        result = prime * result + ((getDeptNo() == null) ? 0 : getDeptNo().hashCode());
        result = prime * result + ((getSignOrderNo() == null) ? 0 : getSignOrderNo().hashCode());
        result = prime * result + ((getDlvAddress() == null) ? 0 : getDlvAddress().hashCode());
        result = prime * result + ((getContactpsn() == null) ? 0 : getContactpsn().hashCode());
        result = prime * result + ((getEndUser() == null) ? 0 : getEndUser().hashCode());
        result = prime * result + ((getEmail() == null) ? 0 : getEmail().hashCode());
        result = prime * result + ((getBranchFlag() == null) ? 0 : getBranchFlag().hashCode());
        return result;
    }
}