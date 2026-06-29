package com.sales.ops.db.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class SpecOrder implements Serializable {
    private Long id;

    private String groupNo;

    private Integer orderType;

    private String customerNo;

    private String userNo;

    private String modelNo;

    private Integer quantity;

    private BigDecimal orgPrice;

    private String orgCurrency;

    private BigDecimal price;

    private String deptNo;

    private Date orderDate;

    private String remark;

    private Date dlvDate;

    private String corderNo;

    private String productName;

    private Short status;

    private String dlvtype;

    private String tradeCompanyid;

    private String orderNo;

    private Integer orderItem;

    private String fullOrderNo;

    private Date createTime;

    private Date updateTime;

    private String updateUser;

    private String createUser;

    private String cproductNo;

    private String exportType;

    private String receiverAddress;

    private String receiverPhone;

    private String receiverName;

    private String receiverCompany;

    private String exportWarehouse;

    private String complaintNo;

    private BigDecimal eprice;

    private String dlvEntire;

    private String province;

    private String city;

    private String district;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupNo() {
        return groupNo;
    }

    public void setGroupNo(String groupNo) {
        this.groupNo = groupNo == null ? null : groupNo.trim();
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
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

    public BigDecimal getOrgPrice() {
        return orgPrice;
    }

    public void setOrgPrice(BigDecimal orgPrice) {
        this.orgPrice = orgPrice;
    }

    public String getOrgCurrency() {
        return orgCurrency;
    }

    public void setOrgCurrency(String orgCurrency) {
        this.orgCurrency = orgCurrency == null ? null : orgCurrency.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo == null ? null : deptNo.trim();
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getDlvDate() {
        return dlvDate;
    }

    public void setDlvDate(Date dlvDate) {
        this.dlvDate = dlvDate;
    }

    public String getCorderNo() {
        return corderNo;
    }

    public void setCorderNo(String corderNo) {
        this.corderNo = corderNo == null ? null : corderNo.trim();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getDlvtype() {
        return dlvtype;
    }

    public void setDlvtype(String dlvtype) {
        this.dlvtype = dlvtype == null ? null : dlvtype.trim();
    }

    public String getTradeCompanyid() {
        return tradeCompanyid;
    }

    public void setTradeCompanyid(String tradeCompanyid) {
        this.tradeCompanyid = tradeCompanyid == null ? null : tradeCompanyid.trim();
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public Integer getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(Integer orderItem) {
        this.orderItem = orderItem;
    }

    public String getFullOrderNo() {
        return fullOrderNo;
    }

    public void setFullOrderNo(String fullOrderNo) {
        this.fullOrderNo = fullOrderNo == null ? null : fullOrderNo.trim();
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

    public String getCproductNo() {
        return cproductNo;
    }

    public void setCproductNo(String cproductNo) {
        this.cproductNo = cproductNo == null ? null : cproductNo.trim();
    }

    public String getExportType() {
        return exportType;
    }

    public void setExportType(String exportType) {
        this.exportType = exportType == null ? null : exportType.trim();
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress == null ? null : receiverAddress.trim();
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone == null ? null : receiverPhone.trim();
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName == null ? null : receiverName.trim();
    }

    public String getReceiverCompany() {
        return receiverCompany;
    }

    public void setReceiverCompany(String receiverCompany) {
        this.receiverCompany = receiverCompany == null ? null : receiverCompany.trim();
    }

    public String getExportWarehouse() {
        return exportWarehouse;
    }

    public void setExportWarehouse(String exportWarehouse) {
        this.exportWarehouse = exportWarehouse == null ? null : exportWarehouse.trim();
    }

    public String getComplaintNo() {
        return complaintNo;
    }

    public void setComplaintNo(String complaintNo) {
        this.complaintNo = complaintNo == null ? null : complaintNo.trim();
    }

    public BigDecimal getEprice() {
        return eprice;
    }

    public void setEprice(BigDecimal eprice) {
        this.eprice = eprice;
    }

    public String getDlvEntire() {
        return dlvEntire;
    }

    public void setDlvEntire(String dlvEntire) {
        this.dlvEntire = dlvEntire == null ? null : dlvEntire.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district == null ? null : district.trim();
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
        SpecOrder other = (SpecOrder) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getGroupNo() == null ? other.getGroupNo() == null : this.getGroupNo().equals(other.getGroupNo()))
            && (this.getOrderType() == null ? other.getOrderType() == null : this.getOrderType().equals(other.getOrderType()))
            && (this.getCustomerNo() == null ? other.getCustomerNo() == null : this.getCustomerNo().equals(other.getCustomerNo()))
            && (this.getUserNo() == null ? other.getUserNo() == null : this.getUserNo().equals(other.getUserNo()))
            && (this.getModelNo() == null ? other.getModelNo() == null : this.getModelNo().equals(other.getModelNo()))
            && (this.getQuantity() == null ? other.getQuantity() == null : this.getQuantity().equals(other.getQuantity()))
            && (this.getOrgPrice() == null ? other.getOrgPrice() == null : this.getOrgPrice().equals(other.getOrgPrice()))
            && (this.getOrgCurrency() == null ? other.getOrgCurrency() == null : this.getOrgCurrency().equals(other.getOrgCurrency()))
            && (this.getPrice() == null ? other.getPrice() == null : this.getPrice().equals(other.getPrice()))
            && (this.getDeptNo() == null ? other.getDeptNo() == null : this.getDeptNo().equals(other.getDeptNo()))
            && (this.getOrderDate() == null ? other.getOrderDate() == null : this.getOrderDate().equals(other.getOrderDate()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getDlvDate() == null ? other.getDlvDate() == null : this.getDlvDate().equals(other.getDlvDate()))
            && (this.getCorderNo() == null ? other.getCorderNo() == null : this.getCorderNo().equals(other.getCorderNo()))
            && (this.getProductName() == null ? other.getProductName() == null : this.getProductName().equals(other.getProductName()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getDlvtype() == null ? other.getDlvtype() == null : this.getDlvtype().equals(other.getDlvtype()))
            && (this.getTradeCompanyid() == null ? other.getTradeCompanyid() == null : this.getTradeCompanyid().equals(other.getTradeCompanyid()))
            && (this.getOrderNo() == null ? other.getOrderNo() == null : this.getOrderNo().equals(other.getOrderNo()))
            && (this.getOrderItem() == null ? other.getOrderItem() == null : this.getOrderItem().equals(other.getOrderItem()))
            && (this.getFullOrderNo() == null ? other.getFullOrderNo() == null : this.getFullOrderNo().equals(other.getFullOrderNo()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getUpdateUser() == null ? other.getUpdateUser() == null : this.getUpdateUser().equals(other.getUpdateUser()))
            && (this.getCreateUser() == null ? other.getCreateUser() == null : this.getCreateUser().equals(other.getCreateUser()))
            && (this.getCproductNo() == null ? other.getCproductNo() == null : this.getCproductNo().equals(other.getCproductNo()))
            && (this.getExportType() == null ? other.getExportType() == null : this.getExportType().equals(other.getExportType()))
            && (this.getReceiverAddress() == null ? other.getReceiverAddress() == null : this.getReceiverAddress().equals(other.getReceiverAddress()))
            && (this.getReceiverPhone() == null ? other.getReceiverPhone() == null : this.getReceiverPhone().equals(other.getReceiverPhone()))
            && (this.getReceiverName() == null ? other.getReceiverName() == null : this.getReceiverName().equals(other.getReceiverName()))
            && (this.getReceiverCompany() == null ? other.getReceiverCompany() == null : this.getReceiverCompany().equals(other.getReceiverCompany()))
            && (this.getExportWarehouse() == null ? other.getExportWarehouse() == null : this.getExportWarehouse().equals(other.getExportWarehouse()))
            && (this.getComplaintNo() == null ? other.getComplaintNo() == null : this.getComplaintNo().equals(other.getComplaintNo()))
            && (this.getEprice() == null ? other.getEprice() == null : this.getEprice().equals(other.getEprice()))
            && (this.getDlvEntire() == null ? other.getDlvEntire() == null : this.getDlvEntire().equals(other.getDlvEntire()))
            && (this.getProvince() == null ? other.getProvince() == null : this.getProvince().equals(other.getProvince()))
            && (this.getCity() == null ? other.getCity() == null : this.getCity().equals(other.getCity()))
            && (this.getDistrict() == null ? other.getDistrict() == null : this.getDistrict().equals(other.getDistrict()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getGroupNo() == null) ? 0 : getGroupNo().hashCode());
        result = prime * result + ((getOrderType() == null) ? 0 : getOrderType().hashCode());
        result = prime * result + ((getCustomerNo() == null) ? 0 : getCustomerNo().hashCode());
        result = prime * result + ((getUserNo() == null) ? 0 : getUserNo().hashCode());
        result = prime * result + ((getModelNo() == null) ? 0 : getModelNo().hashCode());
        result = prime * result + ((getQuantity() == null) ? 0 : getQuantity().hashCode());
        result = prime * result + ((getOrgPrice() == null) ? 0 : getOrgPrice().hashCode());
        result = prime * result + ((getOrgCurrency() == null) ? 0 : getOrgCurrency().hashCode());
        result = prime * result + ((getPrice() == null) ? 0 : getPrice().hashCode());
        result = prime * result + ((getDeptNo() == null) ? 0 : getDeptNo().hashCode());
        result = prime * result + ((getOrderDate() == null) ? 0 : getOrderDate().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getDlvDate() == null) ? 0 : getDlvDate().hashCode());
        result = prime * result + ((getCorderNo() == null) ? 0 : getCorderNo().hashCode());
        result = prime * result + ((getProductName() == null) ? 0 : getProductName().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getDlvtype() == null) ? 0 : getDlvtype().hashCode());
        result = prime * result + ((getTradeCompanyid() == null) ? 0 : getTradeCompanyid().hashCode());
        result = prime * result + ((getOrderNo() == null) ? 0 : getOrderNo().hashCode());
        result = prime * result + ((getOrderItem() == null) ? 0 : getOrderItem().hashCode());
        result = prime * result + ((getFullOrderNo() == null) ? 0 : getFullOrderNo().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getUpdateUser() == null) ? 0 : getUpdateUser().hashCode());
        result = prime * result + ((getCreateUser() == null) ? 0 : getCreateUser().hashCode());
        result = prime * result + ((getCproductNo() == null) ? 0 : getCproductNo().hashCode());
        result = prime * result + ((getExportType() == null) ? 0 : getExportType().hashCode());
        result = prime * result + ((getReceiverAddress() == null) ? 0 : getReceiverAddress().hashCode());
        result = prime * result + ((getReceiverPhone() == null) ? 0 : getReceiverPhone().hashCode());
        result = prime * result + ((getReceiverName() == null) ? 0 : getReceiverName().hashCode());
        result = prime * result + ((getReceiverCompany() == null) ? 0 : getReceiverCompany().hashCode());
        result = prime * result + ((getExportWarehouse() == null) ? 0 : getExportWarehouse().hashCode());
        result = prime * result + ((getComplaintNo() == null) ? 0 : getComplaintNo().hashCode());
        result = prime * result + ((getEprice() == null) ? 0 : getEprice().hashCode());
        result = prime * result + ((getDlvEntire() == null) ? 0 : getDlvEntire().hashCode());
        result = prime * result + ((getProvince() == null) ? 0 : getProvince().hashCode());
        result = prime * result + ((getCity() == null) ? 0 : getCity().hashCode());
        result = prime * result + ((getDistrict() == null) ? 0 : getDistrict().hashCode());
        return result;
    }
}