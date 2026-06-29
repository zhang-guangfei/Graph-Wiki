package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class ProductT implements Serializable {
    private String modelno;

    private String ecode;

    private String chinesename;

    private String englishname;

    private String typeid;

    private String classifycode1;

    private String classifycode2;

    private String classifycode3;

    private String competitivenessid;

    private String designtypeid;

    private String category;

    private String isRestrict;

    private String isEos;

    private String isError;

    private String longLeadTime;

    private String isOverweight;

    private String isOverlength;

    private String shiptype;

    private String bucklingsign;

    private String manifoldsign;

    private String isAtex;

    private String isEven;

    private String isSecurity;

    private String rohs;

    private String largeSize;

    private Boolean isDeleted;

    private Date createdDate;

    private String createdUser;

    private Date updatetime;

    private String updatedUser;

    private Integer minPackUnit;

    private String remark;

    private String outerboxpartno;

    private Integer outerboxquantity;

    private String nonstandardSizedProduct;

    private static final long serialVersionUID = 1L;

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno == null ? null : modelno.trim();
    }

    public String getEcode() {
        return ecode;
    }

    public void setEcode(String ecode) {
        this.ecode = ecode == null ? null : ecode.trim();
    }

    public String getChinesename() {
        return chinesename;
    }

    public void setChinesename(String chinesename) {
        this.chinesename = chinesename == null ? null : chinesename.trim();
    }

    public String getEnglishname() {
        return englishname;
    }

    public void setEnglishname(String englishname) {
        this.englishname = englishname == null ? null : englishname.trim();
    }

    public String getTypeid() {
        return typeid;
    }

    public void setTypeid(String typeid) {
        this.typeid = typeid == null ? null : typeid.trim();
    }

    public String getClassifycode1() {
        return classifycode1;
    }

    public void setClassifycode1(String classifycode1) {
        this.classifycode1 = classifycode1 == null ? null : classifycode1.trim();
    }

    public String getClassifycode2() {
        return classifycode2;
    }

    public void setClassifycode2(String classifycode2) {
        this.classifycode2 = classifycode2 == null ? null : classifycode2.trim();
    }

    public String getClassifycode3() {
        return classifycode3;
    }

    public void setClassifycode3(String classifycode3) {
        this.classifycode3 = classifycode3 == null ? null : classifycode3.trim();
    }

    public String getCompetitivenessid() {
        return competitivenessid;
    }

    public void setCompetitivenessid(String competitivenessid) {
        this.competitivenessid = competitivenessid == null ? null : competitivenessid.trim();
    }

    public String getDesigntypeid() {
        return designtypeid;
    }

    public void setDesigntypeid(String designtypeid) {
        this.designtypeid = designtypeid == null ? null : designtypeid.trim();
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category == null ? null : category.trim();
    }

    public String getIsRestrict() {
        return isRestrict;
    }

    public void setIsRestrict(String isRestrict) {
        this.isRestrict = isRestrict == null ? null : isRestrict.trim();
    }

    public String getIsEos() {
        return isEos;
    }

    public void setIsEos(String isEos) {
        this.isEos = isEos == null ? null : isEos.trim();
    }

    public String getIsError() {
        return isError;
    }

    public void setIsError(String isError) {
        this.isError = isError == null ? null : isError.trim();
    }

    public String getLongLeadTime() {
        return longLeadTime;
    }

    public void setLongLeadTime(String longLeadTime) {
        this.longLeadTime = longLeadTime == null ? null : longLeadTime.trim();
    }

    public String getIsOverweight() {
        return isOverweight;
    }

    public void setIsOverweight(String isOverweight) {
        this.isOverweight = isOverweight == null ? null : isOverweight.trim();
    }

    public String getIsOverlength() {
        return isOverlength;
    }

    public void setIsOverlength(String isOverlength) {
        this.isOverlength = isOverlength == null ? null : isOverlength.trim();
    }

    public String getShiptype() {
        return shiptype;
    }

    public void setShiptype(String shiptype) {
        this.shiptype = shiptype == null ? null : shiptype.trim();
    }

    public String getBucklingsign() {
        return bucklingsign;
    }

    public void setBucklingsign(String bucklingsign) {
        this.bucklingsign = bucklingsign == null ? null : bucklingsign.trim();
    }

    public String getManifoldsign() {
        return manifoldsign;
    }

    public void setManifoldsign(String manifoldsign) {
        this.manifoldsign = manifoldsign == null ? null : manifoldsign.trim();
    }

    public String getIsAtex() {
        return isAtex;
    }

    public void setIsAtex(String isAtex) {
        this.isAtex = isAtex == null ? null : isAtex.trim();
    }

    public String getIsEven() {
        return isEven;
    }

    public void setIsEven(String isEven) {
        this.isEven = isEven == null ? null : isEven.trim();
    }

    public String getIsSecurity() {
        return isSecurity;
    }

    public void setIsSecurity(String isSecurity) {
        this.isSecurity = isSecurity == null ? null : isSecurity.trim();
    }

    public String getRohs() {
        return rohs;
    }

    public void setRohs(String rohs) {
        this.rohs = rohs == null ? null : rohs.trim();
    }

    public String getLargeSize() {
        return largeSize;
    }

    public void setLargeSize(String largeSize) {
        this.largeSize = largeSize == null ? null : largeSize.trim();
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

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getUpdatedUser() {
        return updatedUser;
    }

    public void setUpdatedUser(String updatedUser) {
        this.updatedUser = updatedUser == null ? null : updatedUser.trim();
    }

    public Integer getMinPackUnit() {
        return minPackUnit;
    }

    public void setMinPackUnit(Integer minPackUnit) {
        this.minPackUnit = minPackUnit;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getOuterboxpartno() {
        return outerboxpartno;
    }

    public void setOuterboxpartno(String outerboxpartno) {
        this.outerboxpartno = outerboxpartno == null ? null : outerboxpartno.trim();
    }

    public Integer getOuterboxquantity() {
        return outerboxquantity;
    }

    public void setOuterboxquantity(Integer outerboxquantity) {
        this.outerboxquantity = outerboxquantity;
    }

    public String getNonstandardSizedProduct() {
        return nonstandardSizedProduct;
    }

    public void setNonstandardSizedProduct(String nonstandardSizedProduct) {
        this.nonstandardSizedProduct = nonstandardSizedProduct == null ? null : nonstandardSizedProduct.trim();
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
        ProductT other = (ProductT) that;
        return (this.getModelno() == null ? other.getModelno() == null : this.getModelno().equals(other.getModelno()))
            && (this.getEcode() == null ? other.getEcode() == null : this.getEcode().equals(other.getEcode()))
            && (this.getChinesename() == null ? other.getChinesename() == null : this.getChinesename().equals(other.getChinesename()))
            && (this.getEnglishname() == null ? other.getEnglishname() == null : this.getEnglishname().equals(other.getEnglishname()))
            && (this.getTypeid() == null ? other.getTypeid() == null : this.getTypeid().equals(other.getTypeid()))
            && (this.getClassifycode1() == null ? other.getClassifycode1() == null : this.getClassifycode1().equals(other.getClassifycode1()))
            && (this.getClassifycode2() == null ? other.getClassifycode2() == null : this.getClassifycode2().equals(other.getClassifycode2()))
            && (this.getClassifycode3() == null ? other.getClassifycode3() == null : this.getClassifycode3().equals(other.getClassifycode3()))
            && (this.getCompetitivenessid() == null ? other.getCompetitivenessid() == null : this.getCompetitivenessid().equals(other.getCompetitivenessid()))
            && (this.getDesigntypeid() == null ? other.getDesigntypeid() == null : this.getDesigntypeid().equals(other.getDesigntypeid()))
            && (this.getCategory() == null ? other.getCategory() == null : this.getCategory().equals(other.getCategory()))
            && (this.getIsRestrict() == null ? other.getIsRestrict() == null : this.getIsRestrict().equals(other.getIsRestrict()))
            && (this.getIsEos() == null ? other.getIsEos() == null : this.getIsEos().equals(other.getIsEos()))
            && (this.getIsError() == null ? other.getIsError() == null : this.getIsError().equals(other.getIsError()))
            && (this.getLongLeadTime() == null ? other.getLongLeadTime() == null : this.getLongLeadTime().equals(other.getLongLeadTime()))
            && (this.getIsOverweight() == null ? other.getIsOverweight() == null : this.getIsOverweight().equals(other.getIsOverweight()))
            && (this.getIsOverlength() == null ? other.getIsOverlength() == null : this.getIsOverlength().equals(other.getIsOverlength()))
            && (this.getShiptype() == null ? other.getShiptype() == null : this.getShiptype().equals(other.getShiptype()))
            && (this.getBucklingsign() == null ? other.getBucklingsign() == null : this.getBucklingsign().equals(other.getBucklingsign()))
            && (this.getManifoldsign() == null ? other.getManifoldsign() == null : this.getManifoldsign().equals(other.getManifoldsign()))
            && (this.getIsAtex() == null ? other.getIsAtex() == null : this.getIsAtex().equals(other.getIsAtex()))
            && (this.getIsEven() == null ? other.getIsEven() == null : this.getIsEven().equals(other.getIsEven()))
            && (this.getIsSecurity() == null ? other.getIsSecurity() == null : this.getIsSecurity().equals(other.getIsSecurity()))
            && (this.getRohs() == null ? other.getRohs() == null : this.getRohs().equals(other.getRohs()))
            && (this.getLargeSize() == null ? other.getLargeSize() == null : this.getLargeSize().equals(other.getLargeSize()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
            && (this.getCreatedDate() == null ? other.getCreatedDate() == null : this.getCreatedDate().equals(other.getCreatedDate()))
            && (this.getCreatedUser() == null ? other.getCreatedUser() == null : this.getCreatedUser().equals(other.getCreatedUser()))
            && (this.getUpdatetime() == null ? other.getUpdatetime() == null : this.getUpdatetime().equals(other.getUpdatetime()))
            && (this.getUpdatedUser() == null ? other.getUpdatedUser() == null : this.getUpdatedUser().equals(other.getUpdatedUser()))
            && (this.getMinPackUnit() == null ? other.getMinPackUnit() == null : this.getMinPackUnit().equals(other.getMinPackUnit()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getOuterboxpartno() == null ? other.getOuterboxpartno() == null : this.getOuterboxpartno().equals(other.getOuterboxpartno()))
            && (this.getOuterboxquantity() == null ? other.getOuterboxquantity() == null : this.getOuterboxquantity().equals(other.getOuterboxquantity()))
            && (this.getNonstandardSizedProduct() == null ? other.getNonstandardSizedProduct() == null : this.getNonstandardSizedProduct().equals(other.getNonstandardSizedProduct()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getModelno() == null) ? 0 : getModelno().hashCode());
        result = prime * result + ((getEcode() == null) ? 0 : getEcode().hashCode());
        result = prime * result + ((getChinesename() == null) ? 0 : getChinesename().hashCode());
        result = prime * result + ((getEnglishname() == null) ? 0 : getEnglishname().hashCode());
        result = prime * result + ((getTypeid() == null) ? 0 : getTypeid().hashCode());
        result = prime * result + ((getClassifycode1() == null) ? 0 : getClassifycode1().hashCode());
        result = prime * result + ((getClassifycode2() == null) ? 0 : getClassifycode2().hashCode());
        result = prime * result + ((getClassifycode3() == null) ? 0 : getClassifycode3().hashCode());
        result = prime * result + ((getCompetitivenessid() == null) ? 0 : getCompetitivenessid().hashCode());
        result = prime * result + ((getDesigntypeid() == null) ? 0 : getDesigntypeid().hashCode());
        result = prime * result + ((getCategory() == null) ? 0 : getCategory().hashCode());
        result = prime * result + ((getIsRestrict() == null) ? 0 : getIsRestrict().hashCode());
        result = prime * result + ((getIsEos() == null) ? 0 : getIsEos().hashCode());
        result = prime * result + ((getIsError() == null) ? 0 : getIsError().hashCode());
        result = prime * result + ((getLongLeadTime() == null) ? 0 : getLongLeadTime().hashCode());
        result = prime * result + ((getIsOverweight() == null) ? 0 : getIsOverweight().hashCode());
        result = prime * result + ((getIsOverlength() == null) ? 0 : getIsOverlength().hashCode());
        result = prime * result + ((getShiptype() == null) ? 0 : getShiptype().hashCode());
        result = prime * result + ((getBucklingsign() == null) ? 0 : getBucklingsign().hashCode());
        result = prime * result + ((getManifoldsign() == null) ? 0 : getManifoldsign().hashCode());
        result = prime * result + ((getIsAtex() == null) ? 0 : getIsAtex().hashCode());
        result = prime * result + ((getIsEven() == null) ? 0 : getIsEven().hashCode());
        result = prime * result + ((getIsSecurity() == null) ? 0 : getIsSecurity().hashCode());
        result = prime * result + ((getRohs() == null) ? 0 : getRohs().hashCode());
        result = prime * result + ((getLargeSize() == null) ? 0 : getLargeSize().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        result = prime * result + ((getCreatedDate() == null) ? 0 : getCreatedDate().hashCode());
        result = prime * result + ((getCreatedUser() == null) ? 0 : getCreatedUser().hashCode());
        result = prime * result + ((getUpdatetime() == null) ? 0 : getUpdatetime().hashCode());
        result = prime * result + ((getUpdatedUser() == null) ? 0 : getUpdatedUser().hashCode());
        result = prime * result + ((getMinPackUnit() == null) ? 0 : getMinPackUnit().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getOuterboxpartno() == null) ? 0 : getOuterboxpartno().hashCode());
        result = prime * result + ((getOuterboxquantity() == null) ? 0 : getOuterboxquantity().hashCode());
        result = prime * result + ((getNonstandardSizedProduct() == null) ? 0 : getNonstandardSizedProduct().hashCode());
        return result;
    }
}