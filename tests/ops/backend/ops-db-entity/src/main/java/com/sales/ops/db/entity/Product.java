package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class Product implements Serializable {
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

    private String isEven;

    private String isSecurity;

    private String rohs;

    private Boolean isDeleted;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;

    private Integer minPackUnit;

    private String remark;

    private String outerboxpartno;

    private Integer outerboxquantity;

    private String nonstandardSizedProduct;

    private String manifoldsign;

    private Boolean isEos;

    private Integer minQuantity;

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

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
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

    public String getManifoldsign() {
        return manifoldsign;
    }

    public void setManifoldsign(String manifoldsign) {
        this.manifoldsign = manifoldsign == null ? null : manifoldsign.trim();
    }

    public Boolean getIsEos() {
        return isEos;
    }

    public void setIsEos(Boolean isEos) {
        this.isEos = isEos;
    }

    public Integer getMinQuantity() {
        return minQuantity;
    }

    public void setMinQuantity(Integer minQuantity) {
        this.minQuantity = minQuantity;
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
        Product other = (Product) that;
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
            && (this.getIsEven() == null ? other.getIsEven() == null : this.getIsEven().equals(other.getIsEven()))
            && (this.getIsSecurity() == null ? other.getIsSecurity() == null : this.getIsSecurity().equals(other.getIsSecurity()))
            && (this.getRohs() == null ? other.getRohs() == null : this.getRohs().equals(other.getRohs()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getCreateUser() == null ? other.getCreateUser() == null : this.getCreateUser().equals(other.getCreateUser()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getUpdateUser() == null ? other.getUpdateUser() == null : this.getUpdateUser().equals(other.getUpdateUser()))
            && (this.getMinPackUnit() == null ? other.getMinPackUnit() == null : this.getMinPackUnit().equals(other.getMinPackUnit()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getOuterboxpartno() == null ? other.getOuterboxpartno() == null : this.getOuterboxpartno().equals(other.getOuterboxpartno()))
            && (this.getOuterboxquantity() == null ? other.getOuterboxquantity() == null : this.getOuterboxquantity().equals(other.getOuterboxquantity()))
            && (this.getNonstandardSizedProduct() == null ? other.getNonstandardSizedProduct() == null : this.getNonstandardSizedProduct().equals(other.getNonstandardSizedProduct()))
            && (this.getManifoldsign() == null ? other.getManifoldsign() == null : this.getManifoldsign().equals(other.getManifoldsign()))
            && (this.getIsEos() == null ? other.getIsEos() == null : this.getIsEos().equals(other.getIsEos()))
            && (this.getMinQuantity() == null ? other.getMinQuantity() == null : this.getMinQuantity().equals(other.getMinQuantity()));
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
        result = prime * result + ((getIsEven() == null) ? 0 : getIsEven().hashCode());
        result = prime * result + ((getIsSecurity() == null) ? 0 : getIsSecurity().hashCode());
        result = prime * result + ((getRohs() == null) ? 0 : getRohs().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getCreateUser() == null) ? 0 : getCreateUser().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getUpdateUser() == null) ? 0 : getUpdateUser().hashCode());
        result = prime * result + ((getMinPackUnit() == null) ? 0 : getMinPackUnit().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getOuterboxpartno() == null) ? 0 : getOuterboxpartno().hashCode());
        result = prime * result + ((getOuterboxquantity() == null) ? 0 : getOuterboxquantity().hashCode());
        result = prime * result + ((getNonstandardSizedProduct() == null) ? 0 : getNonstandardSizedProduct().hashCode());
        result = prime * result + ((getManifoldsign() == null) ? 0 : getManifoldsign().hashCode());
        result = prime * result + ((getIsEos() == null) ? 0 : getIsEos().hashCode());
        result = prime * result + ((getMinQuantity() == null) ? 0 : getMinQuantity().hashCode());
        return result;
    }
}