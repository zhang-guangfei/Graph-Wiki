package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class ExpdetailBarcodeSend implements Serializable {
    private Long id;

    private String orderFno;

    private String modelNo;

    private Integer quantity;

    private String barcodeW;

    private String barcodeN;

    private String customerNo;

    private String userNo;

    private Date shipDate;

    private String expressNo;

    private String expressCompany;

    private String warehouseCode;

    private String corderNo;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;

    private String doId;

    private String userName;

    private String cproductNo;

    private String deptName;

    private String address;

    private String linkman;

    private String dlvEntire;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getBarcodeW() {
        return barcodeW;
    }

    public void setBarcodeW(String barcodeW) {
        this.barcodeW = barcodeW == null ? null : barcodeW.trim();
    }

    public String getBarcodeN() {
        return barcodeN;
    }

    public void setBarcodeN(String barcodeN) {
        this.barcodeN = barcodeN == null ? null : barcodeN.trim();
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

    public String getCorderNo() {
        return corderNo;
    }

    public void setCorderNo(String corderNo) {
        this.corderNo = corderNo == null ? null : corderNo.trim();
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

    public String getDoId() {
        return doId;
    }

    public void setDoId(String doId) {
        this.doId = doId == null ? null : doId.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getCproductNo() {
        return cproductNo;
    }

    public void setCproductNo(String cproductNo) {
        this.cproductNo = cproductNo == null ? null : cproductNo.trim();
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName == null ? null : deptName.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman == null ? null : linkman.trim();
    }

    public String getDlvEntire() {
        return dlvEntire;
    }

    public void setDlvEntire(String dlvEntire) {
        this.dlvEntire = dlvEntire == null ? null : dlvEntire.trim();
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
        ExpdetailBarcodeSend other = (ExpdetailBarcodeSend) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getOrderFno() == null ? other.getOrderFno() == null : this.getOrderFno().equals(other.getOrderFno()))
            && (this.getModelNo() == null ? other.getModelNo() == null : this.getModelNo().equals(other.getModelNo()))
            && (this.getQuantity() == null ? other.getQuantity() == null : this.getQuantity().equals(other.getQuantity()))
            && (this.getBarcodeW() == null ? other.getBarcodeW() == null : this.getBarcodeW().equals(other.getBarcodeW()))
            && (this.getBarcodeN() == null ? other.getBarcodeN() == null : this.getBarcodeN().equals(other.getBarcodeN()))
            && (this.getCustomerNo() == null ? other.getCustomerNo() == null : this.getCustomerNo().equals(other.getCustomerNo()))
            && (this.getUserNo() == null ? other.getUserNo() == null : this.getUserNo().equals(other.getUserNo()))
            && (this.getShipDate() == null ? other.getShipDate() == null : this.getShipDate().equals(other.getShipDate()))
            && (this.getExpressNo() == null ? other.getExpressNo() == null : this.getExpressNo().equals(other.getExpressNo()))
            && (this.getExpressCompany() == null ? other.getExpressCompany() == null : this.getExpressCompany().equals(other.getExpressCompany()))
            && (this.getWarehouseCode() == null ? other.getWarehouseCode() == null : this.getWarehouseCode().equals(other.getWarehouseCode()))
            && (this.getCorderNo() == null ? other.getCorderNo() == null : this.getCorderNo().equals(other.getCorderNo()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getCreateUser() == null ? other.getCreateUser() == null : this.getCreateUser().equals(other.getCreateUser()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getUpdateUser() == null ? other.getUpdateUser() == null : this.getUpdateUser().equals(other.getUpdateUser()))
            && (this.getDoId() == null ? other.getDoId() == null : this.getDoId().equals(other.getDoId()))
            && (this.getUserName() == null ? other.getUserName() == null : this.getUserName().equals(other.getUserName()))
            && (this.getCproductNo() == null ? other.getCproductNo() == null : this.getCproductNo().equals(other.getCproductNo()))
            && (this.getDeptName() == null ? other.getDeptName() == null : this.getDeptName().equals(other.getDeptName()))
            && (this.getAddress() == null ? other.getAddress() == null : this.getAddress().equals(other.getAddress()))
            && (this.getLinkman() == null ? other.getLinkman() == null : this.getLinkman().equals(other.getLinkman()))
            && (this.getDlvEntire() == null ? other.getDlvEntire() == null : this.getDlvEntire().equals(other.getDlvEntire()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getOrderFno() == null) ? 0 : getOrderFno().hashCode());
        result = prime * result + ((getModelNo() == null) ? 0 : getModelNo().hashCode());
        result = prime * result + ((getQuantity() == null) ? 0 : getQuantity().hashCode());
        result = prime * result + ((getBarcodeW() == null) ? 0 : getBarcodeW().hashCode());
        result = prime * result + ((getBarcodeN() == null) ? 0 : getBarcodeN().hashCode());
        result = prime * result + ((getCustomerNo() == null) ? 0 : getCustomerNo().hashCode());
        result = prime * result + ((getUserNo() == null) ? 0 : getUserNo().hashCode());
        result = prime * result + ((getShipDate() == null) ? 0 : getShipDate().hashCode());
        result = prime * result + ((getExpressNo() == null) ? 0 : getExpressNo().hashCode());
        result = prime * result + ((getExpressCompany() == null) ? 0 : getExpressCompany().hashCode());
        result = prime * result + ((getWarehouseCode() == null) ? 0 : getWarehouseCode().hashCode());
        result = prime * result + ((getCorderNo() == null) ? 0 : getCorderNo().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getCreateUser() == null) ? 0 : getCreateUser().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getUpdateUser() == null) ? 0 : getUpdateUser().hashCode());
        result = prime * result + ((getDoId() == null) ? 0 : getDoId().hashCode());
        result = prime * result + ((getUserName() == null) ? 0 : getUserName().hashCode());
        result = prime * result + ((getCproductNo() == null) ? 0 : getCproductNo().hashCode());
        result = prime * result + ((getDeptName() == null) ? 0 : getDeptName().hashCode());
        result = prime * result + ((getAddress() == null) ? 0 : getAddress().hashCode());
        result = prime * result + ((getLinkman() == null) ? 0 : getLinkman().hashCode());
        result = prime * result + ((getDlvEntire() == null) ? 0 : getDlvEntire().hashCode());
        return result;
    }
}