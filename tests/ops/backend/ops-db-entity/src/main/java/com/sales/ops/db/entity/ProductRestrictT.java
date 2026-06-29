package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class ProductRestrictT implements Serializable {
    private String modelno;

    private String customerno;

    private String industryCode;

    private String typeAsp;

    private String typePnc;

    private String typeSGroup;

    private String typeSPrice;

    private String typeSRoute;

    private String auth95012;

    private String desc;

    private Boolean isDeleted;

    private Date createdDate;

    private String createdUser;

    private Date updatedDate;

    private String updatedUser;

    private static final long serialVersionUID = 1L;

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno == null ? null : modelno.trim();
    }

    public String getCustomerno() {
        return customerno;
    }

    public void setCustomerno(String customerno) {
        this.customerno = customerno == null ? null : customerno.trim();
    }

    public String getIndustryCode() {
        return industryCode;
    }

    public void setIndustryCode(String industryCode) {
        this.industryCode = industryCode == null ? null : industryCode.trim();
    }

    public String getTypeAsp() {
        return typeAsp;
    }

    public void setTypeAsp(String typeAsp) {
        this.typeAsp = typeAsp == null ? null : typeAsp.trim();
    }

    public String getTypePnc() {
        return typePnc;
    }

    public void setTypePnc(String typePnc) {
        this.typePnc = typePnc == null ? null : typePnc.trim();
    }

    public String getTypeSGroup() {
        return typeSGroup;
    }

    public void setTypeSGroup(String typeSGroup) {
        this.typeSGroup = typeSGroup == null ? null : typeSGroup.trim();
    }

    public String getTypeSPrice() {
        return typeSPrice;
    }

    public void setTypeSPrice(String typeSPrice) {
        this.typeSPrice = typeSPrice == null ? null : typeSPrice.trim();
    }

    public String getTypeSRoute() {
        return typeSRoute;
    }

    public void setTypeSRoute(String typeSRoute) {
        this.typeSRoute = typeSRoute == null ? null : typeSRoute.trim();
    }

    public String getAuth95012() {
        return auth95012;
    }

    public void setAuth95012(String auth95012) {
        this.auth95012 = auth95012 == null ? null : auth95012.trim();
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc == null ? null : desc.trim();
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser == null ? null : createdUser.trim();
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getUpdatedUser() {
        return updatedUser;
    }

    public void setUpdatedUser(String updatedUser) {
        this.updatedUser = updatedUser == null ? null : updatedUser.trim();
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
        ProductRestrictT other = (ProductRestrictT) that;
        return (this.getModelno() == null ? other.getModelno() == null : this.getModelno().equals(other.getModelno()))
            && (this.getCustomerno() == null ? other.getCustomerno() == null : this.getCustomerno().equals(other.getCustomerno()))
            && (this.getIndustryCode() == null ? other.getIndustryCode() == null : this.getIndustryCode().equals(other.getIndustryCode()))
            && (this.getTypeAsp() == null ? other.getTypeAsp() == null : this.getTypeAsp().equals(other.getTypeAsp()))
            && (this.getTypePnc() == null ? other.getTypePnc() == null : this.getTypePnc().equals(other.getTypePnc()))
            && (this.getTypeSGroup() == null ? other.getTypeSGroup() == null : this.getTypeSGroup().equals(other.getTypeSGroup()))
            && (this.getTypeSPrice() == null ? other.getTypeSPrice() == null : this.getTypeSPrice().equals(other.getTypeSPrice()))
            && (this.getTypeSRoute() == null ? other.getTypeSRoute() == null : this.getTypeSRoute().equals(other.getTypeSRoute()))
            && (this.getAuth95012() == null ? other.getAuth95012() == null : this.getAuth95012().equals(other.getAuth95012()))
            && (this.getDesc() == null ? other.getDesc() == null : this.getDesc().equals(other.getDesc()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
            && (this.getCreatedDate() == null ? other.getCreatedDate() == null : this.getCreatedDate().equals(other.getCreatedDate()))
            && (this.getCreatedUser() == null ? other.getCreatedUser() == null : this.getCreatedUser().equals(other.getCreatedUser()))
            && (this.getUpdatedDate() == null ? other.getUpdatedDate() == null : this.getUpdatedDate().equals(other.getUpdatedDate()))
            && (this.getUpdatedUser() == null ? other.getUpdatedUser() == null : this.getUpdatedUser().equals(other.getUpdatedUser()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getModelno() == null) ? 0 : getModelno().hashCode());
        result = prime * result + ((getCustomerno() == null) ? 0 : getCustomerno().hashCode());
        result = prime * result + ((getIndustryCode() == null) ? 0 : getIndustryCode().hashCode());
        result = prime * result + ((getTypeAsp() == null) ? 0 : getTypeAsp().hashCode());
        result = prime * result + ((getTypePnc() == null) ? 0 : getTypePnc().hashCode());
        result = prime * result + ((getTypeSGroup() == null) ? 0 : getTypeSGroup().hashCode());
        result = prime * result + ((getTypeSPrice() == null) ? 0 : getTypeSPrice().hashCode());
        result = prime * result + ((getTypeSRoute() == null) ? 0 : getTypeSRoute().hashCode());
        result = prime * result + ((getAuth95012() == null) ? 0 : getAuth95012().hashCode());
        result = prime * result + ((getDesc() == null) ? 0 : getDesc().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        result = prime * result + ((getCreatedDate() == null) ? 0 : getCreatedDate().hashCode());
        result = prime * result + ((getCreatedUser() == null) ? 0 : getCreatedUser().hashCode());
        result = prime * result + ((getUpdatedDate() == null) ? 0 : getUpdatedDate().hashCode());
        result = prime * result + ((getUpdatedUser() == null) ? 0 : getUpdatedUser().hashCode());
        return result;
    }
}