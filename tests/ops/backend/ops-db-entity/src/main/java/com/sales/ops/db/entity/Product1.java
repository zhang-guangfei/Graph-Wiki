package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class Product1 implements Serializable {
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
}