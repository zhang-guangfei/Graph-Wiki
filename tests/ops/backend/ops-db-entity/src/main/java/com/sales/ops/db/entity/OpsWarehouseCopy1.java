package com.sales.ops.db.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OpsWarehouseCopy1 implements Serializable {
    private String warehouseCode;

    private String warehouseType;

    private String warehouseName;

    private String province;

    private String city;

    private String district;

    private String adress;

    private String postCode;

    private String linkman;

    private String mail;

    private String customerNo;

    private String customerLinkman;

    private String customerPhone;

    private String customerMail;

    private String smcLinkman;

    private String smcPhone;

    private String smcMail;

    private Boolean disableFlag;

    private Boolean assemblyFlag;

    private Boolean centralizeFlag;

    private Boolean autodispatchFlag;

    private Boolean purchaseFlag;

    private String parentCode;

    private String deptNo;

    private BigDecimal maxEamount;

    private Boolean delflag;

    private Date creTime;

    private String creator;

    private Date modifyTime;

    private String modifier;

    private String linkPhone;

    private String linkMobile;

    private static final long serialVersionUID = 1L;

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode == null ? null : warehouseCode.trim();
    }

    public String getWarehouseType() {
        return warehouseType;
    }

    public void setWarehouseType(String warehouseType) {
        this.warehouseType = warehouseType == null ? null : warehouseType.trim();
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName == null ? null : warehouseName.trim();
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

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress == null ? null : adress.trim();
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode == null ? null : postCode.trim();
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman == null ? null : linkman.trim();
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail == null ? null : mail.trim();
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo == null ? null : customerNo.trim();
    }

    public String getCustomerLinkman() {
        return customerLinkman;
    }

    public void setCustomerLinkman(String customerLinkman) {
        this.customerLinkman = customerLinkman == null ? null : customerLinkman.trim();
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone == null ? null : customerPhone.trim();
    }

    public String getCustomerMail() {
        return customerMail;
    }

    public void setCustomerMail(String customerMail) {
        this.customerMail = customerMail == null ? null : customerMail.trim();
    }

    public String getSmcLinkman() {
        return smcLinkman;
    }

    public void setSmcLinkman(String smcLinkman) {
        this.smcLinkman = smcLinkman == null ? null : smcLinkman.trim();
    }

    public String getSmcPhone() {
        return smcPhone;
    }

    public void setSmcPhone(String smcPhone) {
        this.smcPhone = smcPhone == null ? null : smcPhone.trim();
    }

    public String getSmcMail() {
        return smcMail;
    }

    public void setSmcMail(String smcMail) {
        this.smcMail = smcMail == null ? null : smcMail.trim();
    }

    public Boolean getDisableFlag() {
        return disableFlag;
    }

    public void setDisableFlag(Boolean disableFlag) {
        this.disableFlag = disableFlag;
    }

    public Boolean getAssemblyFlag() {
        return assemblyFlag;
    }

    public void setAssemblyFlag(Boolean assemblyFlag) {
        this.assemblyFlag = assemblyFlag;
    }

    public Boolean getCentralizeFlag() {
        return centralizeFlag;
    }

    public void setCentralizeFlag(Boolean centralizeFlag) {
        this.centralizeFlag = centralizeFlag;
    }

    public Boolean getAutodispatchFlag() {
        return autodispatchFlag;
    }

    public void setAutodispatchFlag(Boolean autodispatchFlag) {
        this.autodispatchFlag = autodispatchFlag;
    }

    public Boolean getPurchaseFlag() {
        return purchaseFlag;
    }

    public void setPurchaseFlag(Boolean purchaseFlag) {
        this.purchaseFlag = purchaseFlag;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode == null ? null : parentCode.trim();
    }

    public String getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo == null ? null : deptNo.trim();
    }

    public BigDecimal getMaxEamount() {
        return maxEamount;
    }

    public void setMaxEamount(BigDecimal maxEamount) {
        this.maxEamount = maxEamount;
    }

    public Boolean getDelflag() {
        return delflag;
    }

    public void setDelflag(Boolean delflag) {
        this.delflag = delflag;
    }

    public Date getCreTime() {
        return creTime;
    }

    public void setCreTime(Date creTime) {
        this.creTime = creTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier == null ? null : modifier.trim();
    }

    public String getLinkPhone() {
        return linkPhone;
    }

    public void setLinkPhone(String linkPhone) {
        this.linkPhone = linkPhone == null ? null : linkPhone.trim();
    }

    public String getLinkMobile() {
        return linkMobile;
    }

    public void setLinkMobile(String linkMobile) {
        this.linkMobile = linkMobile == null ? null : linkMobile.trim();
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
        OpsWarehouseCopy1 other = (OpsWarehouseCopy1) that;
        return (this.getWarehouseCode() == null ? other.getWarehouseCode() == null : this.getWarehouseCode().equals(other.getWarehouseCode()))
            && (this.getWarehouseType() == null ? other.getWarehouseType() == null : this.getWarehouseType().equals(other.getWarehouseType()))
            && (this.getWarehouseName() == null ? other.getWarehouseName() == null : this.getWarehouseName().equals(other.getWarehouseName()))
            && (this.getProvince() == null ? other.getProvince() == null : this.getProvince().equals(other.getProvince()))
            && (this.getCity() == null ? other.getCity() == null : this.getCity().equals(other.getCity()))
            && (this.getDistrict() == null ? other.getDistrict() == null : this.getDistrict().equals(other.getDistrict()))
            && (this.getAdress() == null ? other.getAdress() == null : this.getAdress().equals(other.getAdress()))
            && (this.getPostCode() == null ? other.getPostCode() == null : this.getPostCode().equals(other.getPostCode()))
            && (this.getLinkman() == null ? other.getLinkman() == null : this.getLinkman().equals(other.getLinkman()))
            && (this.getMail() == null ? other.getMail() == null : this.getMail().equals(other.getMail()))
            && (this.getCustomerNo() == null ? other.getCustomerNo() == null : this.getCustomerNo().equals(other.getCustomerNo()))
            && (this.getCustomerLinkman() == null ? other.getCustomerLinkman() == null : this.getCustomerLinkman().equals(other.getCustomerLinkman()))
            && (this.getCustomerPhone() == null ? other.getCustomerPhone() == null : this.getCustomerPhone().equals(other.getCustomerPhone()))
            && (this.getCustomerMail() == null ? other.getCustomerMail() == null : this.getCustomerMail().equals(other.getCustomerMail()))
            && (this.getSmcLinkman() == null ? other.getSmcLinkman() == null : this.getSmcLinkman().equals(other.getSmcLinkman()))
            && (this.getSmcPhone() == null ? other.getSmcPhone() == null : this.getSmcPhone().equals(other.getSmcPhone()))
            && (this.getSmcMail() == null ? other.getSmcMail() == null : this.getSmcMail().equals(other.getSmcMail()))
            && (this.getDisableFlag() == null ? other.getDisableFlag() == null : this.getDisableFlag().equals(other.getDisableFlag()))
            && (this.getAssemblyFlag() == null ? other.getAssemblyFlag() == null : this.getAssemblyFlag().equals(other.getAssemblyFlag()))
            && (this.getCentralizeFlag() == null ? other.getCentralizeFlag() == null : this.getCentralizeFlag().equals(other.getCentralizeFlag()))
            && (this.getAutodispatchFlag() == null ? other.getAutodispatchFlag() == null : this.getAutodispatchFlag().equals(other.getAutodispatchFlag()))
            && (this.getPurchaseFlag() == null ? other.getPurchaseFlag() == null : this.getPurchaseFlag().equals(other.getPurchaseFlag()))
            && (this.getParentCode() == null ? other.getParentCode() == null : this.getParentCode().equals(other.getParentCode()))
            && (this.getDeptNo() == null ? other.getDeptNo() == null : this.getDeptNo().equals(other.getDeptNo()))
            && (this.getMaxEamount() == null ? other.getMaxEamount() == null : this.getMaxEamount().equals(other.getMaxEamount()))
            && (this.getDelflag() == null ? other.getDelflag() == null : this.getDelflag().equals(other.getDelflag()))
            && (this.getCreTime() == null ? other.getCreTime() == null : this.getCreTime().equals(other.getCreTime()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
            && (this.getModifyTime() == null ? other.getModifyTime() == null : this.getModifyTime().equals(other.getModifyTime()))
            && (this.getModifier() == null ? other.getModifier() == null : this.getModifier().equals(other.getModifier()))
            && (this.getLinkPhone() == null ? other.getLinkPhone() == null : this.getLinkPhone().equals(other.getLinkPhone()))
            && (this.getLinkMobile() == null ? other.getLinkMobile() == null : this.getLinkMobile().equals(other.getLinkMobile()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getWarehouseCode() == null) ? 0 : getWarehouseCode().hashCode());
        result = prime * result + ((getWarehouseType() == null) ? 0 : getWarehouseType().hashCode());
        result = prime * result + ((getWarehouseName() == null) ? 0 : getWarehouseName().hashCode());
        result = prime * result + ((getProvince() == null) ? 0 : getProvince().hashCode());
        result = prime * result + ((getCity() == null) ? 0 : getCity().hashCode());
        result = prime * result + ((getDistrict() == null) ? 0 : getDistrict().hashCode());
        result = prime * result + ((getAdress() == null) ? 0 : getAdress().hashCode());
        result = prime * result + ((getPostCode() == null) ? 0 : getPostCode().hashCode());
        result = prime * result + ((getLinkman() == null) ? 0 : getLinkman().hashCode());
        result = prime * result + ((getMail() == null) ? 0 : getMail().hashCode());
        result = prime * result + ((getCustomerNo() == null) ? 0 : getCustomerNo().hashCode());
        result = prime * result + ((getCustomerLinkman() == null) ? 0 : getCustomerLinkman().hashCode());
        result = prime * result + ((getCustomerPhone() == null) ? 0 : getCustomerPhone().hashCode());
        result = prime * result + ((getCustomerMail() == null) ? 0 : getCustomerMail().hashCode());
        result = prime * result + ((getSmcLinkman() == null) ? 0 : getSmcLinkman().hashCode());
        result = prime * result + ((getSmcPhone() == null) ? 0 : getSmcPhone().hashCode());
        result = prime * result + ((getSmcMail() == null) ? 0 : getSmcMail().hashCode());
        result = prime * result + ((getDisableFlag() == null) ? 0 : getDisableFlag().hashCode());
        result = prime * result + ((getAssemblyFlag() == null) ? 0 : getAssemblyFlag().hashCode());
        result = prime * result + ((getCentralizeFlag() == null) ? 0 : getCentralizeFlag().hashCode());
        result = prime * result + ((getAutodispatchFlag() == null) ? 0 : getAutodispatchFlag().hashCode());
        result = prime * result + ((getPurchaseFlag() == null) ? 0 : getPurchaseFlag().hashCode());
        result = prime * result + ((getParentCode() == null) ? 0 : getParentCode().hashCode());
        result = prime * result + ((getDeptNo() == null) ? 0 : getDeptNo().hashCode());
        result = prime * result + ((getMaxEamount() == null) ? 0 : getMaxEamount().hashCode());
        result = prime * result + ((getDelflag() == null) ? 0 : getDelflag().hashCode());
        result = prime * result + ((getCreTime() == null) ? 0 : getCreTime().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getModifyTime() == null) ? 0 : getModifyTime().hashCode());
        result = prime * result + ((getModifier() == null) ? 0 : getModifier().hashCode());
        result = prime * result + ((getLinkPhone() == null) ? 0 : getLinkPhone().hashCode());
        result = prime * result + ((getLinkMobile() == null) ? 0 : getLinkMobile().hashCode());
        return result;
    }
}