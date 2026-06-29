package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class OpsWarehouseAddress implements Serializable {
    private Long id;

    private String warehouseCode;

    private String warehouseName;

    private String province;

    private String city;

    private String district;

    private String adress;

    private String postCode;

    private String linkman;

    private String linkPhone;

    private String linkMobile;

    private String englishAddress;

    private String englishLinkman;

    private String mail;

    private Boolean delflag;

    private Date createTime;

    private String creator;

    private Date updateTime;

    private String updator;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getEnglishAddress() {
        return englishAddress;
    }

    public void setEnglishAddress(String englishAddress) {
        this.englishAddress = englishAddress == null ? null : englishAddress.trim();
    }

    public String getEnglishLinkman() {
        return englishLinkman;
    }

    public void setEnglishLinkman(String englishLinkman) {
        this.englishLinkman = englishLinkman == null ? null : englishLinkman.trim();
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail == null ? null : mail.trim();
    }

    public Boolean getDelflag() {
        return delflag;
    }

    public void setDelflag(Boolean delflag) {
        this.delflag = delflag;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdator() {
        return updator;
    }

    public void setUpdator(String updator) {
        this.updator = updator == null ? null : updator.trim();
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
        OpsWarehouseAddress other = (OpsWarehouseAddress) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getWarehouseCode() == null ? other.getWarehouseCode() == null : this.getWarehouseCode().equals(other.getWarehouseCode()))
            && (this.getWarehouseName() == null ? other.getWarehouseName() == null : this.getWarehouseName().equals(other.getWarehouseName()))
            && (this.getProvince() == null ? other.getProvince() == null : this.getProvince().equals(other.getProvince()))
            && (this.getCity() == null ? other.getCity() == null : this.getCity().equals(other.getCity()))
            && (this.getDistrict() == null ? other.getDistrict() == null : this.getDistrict().equals(other.getDistrict()))
            && (this.getAdress() == null ? other.getAdress() == null : this.getAdress().equals(other.getAdress()))
            && (this.getPostCode() == null ? other.getPostCode() == null : this.getPostCode().equals(other.getPostCode()))
            && (this.getLinkman() == null ? other.getLinkman() == null : this.getLinkman().equals(other.getLinkman()))
            && (this.getLinkPhone() == null ? other.getLinkPhone() == null : this.getLinkPhone().equals(other.getLinkPhone()))
            && (this.getLinkMobile() == null ? other.getLinkMobile() == null : this.getLinkMobile().equals(other.getLinkMobile()))
            && (this.getEnglishAddress() == null ? other.getEnglishAddress() == null : this.getEnglishAddress().equals(other.getEnglishAddress()))
            && (this.getEnglishLinkman() == null ? other.getEnglishLinkman() == null : this.getEnglishLinkman().equals(other.getEnglishLinkman()))
            && (this.getMail() == null ? other.getMail() == null : this.getMail().equals(other.getMail()))
            && (this.getDelflag() == null ? other.getDelflag() == null : this.getDelflag().equals(other.getDelflag()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getUpdator() == null ? other.getUpdator() == null : this.getUpdator().equals(other.getUpdator()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getWarehouseCode() == null) ? 0 : getWarehouseCode().hashCode());
        result = prime * result + ((getWarehouseName() == null) ? 0 : getWarehouseName().hashCode());
        result = prime * result + ((getProvince() == null) ? 0 : getProvince().hashCode());
        result = prime * result + ((getCity() == null) ? 0 : getCity().hashCode());
        result = prime * result + ((getDistrict() == null) ? 0 : getDistrict().hashCode());
        result = prime * result + ((getAdress() == null) ? 0 : getAdress().hashCode());
        result = prime * result + ((getPostCode() == null) ? 0 : getPostCode().hashCode());
        result = prime * result + ((getLinkman() == null) ? 0 : getLinkman().hashCode());
        result = prime * result + ((getLinkPhone() == null) ? 0 : getLinkPhone().hashCode());
        result = prime * result + ((getLinkMobile() == null) ? 0 : getLinkMobile().hashCode());
        result = prime * result + ((getEnglishAddress() == null) ? 0 : getEnglishAddress().hashCode());
        result = prime * result + ((getEnglishLinkman() == null) ? 0 : getEnglishLinkman().hashCode());
        result = prime * result + ((getMail() == null) ? 0 : getMail().hashCode());
        result = prime * result + ((getDelflag() == null) ? 0 : getDelflag().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getUpdator() == null) ? 0 : getUpdator().hashCode());
        return result;
    }
}