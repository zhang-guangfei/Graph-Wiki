package com.sales.ops.db.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ProductEcode implements Serializable {
    private String id;

    private String ecode;

    private BigDecimal parameter;

    private BigDecimal eparameter;

    private String classname;

    private String classmodel;

    private String classname6;

    private String classcode;

    private String impcustcname;

    private String jformcname;

    private BigDecimal custtax;

    private String invname;

    private String engname;

    private String crmtype;

    private String crmcode;

    private String taxcode;

    private String desc;

    private Boolean isDeleted;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getEcode() {
        return ecode;
    }

    public void setEcode(String ecode) {
        this.ecode = ecode == null ? null : ecode.trim();
    }

    public BigDecimal getParameter() {
        return parameter;
    }

    public void setParameter(BigDecimal parameter) {
        this.parameter = parameter;
    }

    public BigDecimal getEparameter() {
        return eparameter;
    }

    public void setEparameter(BigDecimal eparameter) {
        this.eparameter = eparameter;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname == null ? null : classname.trim();
    }

    public String getClassmodel() {
        return classmodel;
    }

    public void setClassmodel(String classmodel) {
        this.classmodel = classmodel == null ? null : classmodel.trim();
    }

    public String getClassname6() {
        return classname6;
    }

    public void setClassname6(String classname6) {
        this.classname6 = classname6 == null ? null : classname6.trim();
    }

    public String getClasscode() {
        return classcode;
    }

    public void setClasscode(String classcode) {
        this.classcode = classcode == null ? null : classcode.trim();
    }

    public String getImpcustcname() {
        return impcustcname;
    }

    public void setImpcustcname(String impcustcname) {
        this.impcustcname = impcustcname == null ? null : impcustcname.trim();
    }

    public String getJformcname() {
        return jformcname;
    }

    public void setJformcname(String jformcname) {
        this.jformcname = jformcname == null ? null : jformcname.trim();
    }

    public BigDecimal getCusttax() {
        return custtax;
    }

    public void setCusttax(BigDecimal custtax) {
        this.custtax = custtax;
    }

    public String getInvname() {
        return invname;
    }

    public void setInvname(String invname) {
        this.invname = invname == null ? null : invname.trim();
    }

    public String getEngname() {
        return engname;
    }

    public void setEngname(String engname) {
        this.engname = engname == null ? null : engname.trim();
    }

    public String getCrmtype() {
        return crmtype;
    }

    public void setCrmtype(String crmtype) {
        this.crmtype = crmtype == null ? null : crmtype.trim();
    }

    public String getCrmcode() {
        return crmcode;
    }

    public void setCrmcode(String crmcode) {
        this.crmcode = crmcode == null ? null : crmcode.trim();
    }

    public String getTaxcode() {
        return taxcode;
    }

    public void setTaxcode(String taxcode) {
        this.taxcode = taxcode == null ? null : taxcode.trim();
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
        ProductEcode other = (ProductEcode) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getEcode() == null ? other.getEcode() == null : this.getEcode().equals(other.getEcode()))
            && (this.getParameter() == null ? other.getParameter() == null : this.getParameter().equals(other.getParameter()))
            && (this.getEparameter() == null ? other.getEparameter() == null : this.getEparameter().equals(other.getEparameter()))
            && (this.getClassname() == null ? other.getClassname() == null : this.getClassname().equals(other.getClassname()))
            && (this.getClassmodel() == null ? other.getClassmodel() == null : this.getClassmodel().equals(other.getClassmodel()))
            && (this.getClassname6() == null ? other.getClassname6() == null : this.getClassname6().equals(other.getClassname6()))
            && (this.getClasscode() == null ? other.getClasscode() == null : this.getClasscode().equals(other.getClasscode()))
            && (this.getImpcustcname() == null ? other.getImpcustcname() == null : this.getImpcustcname().equals(other.getImpcustcname()))
            && (this.getJformcname() == null ? other.getJformcname() == null : this.getJformcname().equals(other.getJformcname()))
            && (this.getCusttax() == null ? other.getCusttax() == null : this.getCusttax().equals(other.getCusttax()))
            && (this.getInvname() == null ? other.getInvname() == null : this.getInvname().equals(other.getInvname()))
            && (this.getEngname() == null ? other.getEngname() == null : this.getEngname().equals(other.getEngname()))
            && (this.getCrmtype() == null ? other.getCrmtype() == null : this.getCrmtype().equals(other.getCrmtype()))
            && (this.getCrmcode() == null ? other.getCrmcode() == null : this.getCrmcode().equals(other.getCrmcode()))
            && (this.getTaxcode() == null ? other.getTaxcode() == null : this.getTaxcode().equals(other.getTaxcode()))
            && (this.getDesc() == null ? other.getDesc() == null : this.getDesc().equals(other.getDesc()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getCreateUser() == null ? other.getCreateUser() == null : this.getCreateUser().equals(other.getCreateUser()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getUpdateUser() == null ? other.getUpdateUser() == null : this.getUpdateUser().equals(other.getUpdateUser()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getEcode() == null) ? 0 : getEcode().hashCode());
        result = prime * result + ((getParameter() == null) ? 0 : getParameter().hashCode());
        result = prime * result + ((getEparameter() == null) ? 0 : getEparameter().hashCode());
        result = prime * result + ((getClassname() == null) ? 0 : getClassname().hashCode());
        result = prime * result + ((getClassmodel() == null) ? 0 : getClassmodel().hashCode());
        result = prime * result + ((getClassname6() == null) ? 0 : getClassname6().hashCode());
        result = prime * result + ((getClasscode() == null) ? 0 : getClasscode().hashCode());
        result = prime * result + ((getImpcustcname() == null) ? 0 : getImpcustcname().hashCode());
        result = prime * result + ((getJformcname() == null) ? 0 : getJformcname().hashCode());
        result = prime * result + ((getCusttax() == null) ? 0 : getCusttax().hashCode());
        result = prime * result + ((getInvname() == null) ? 0 : getInvname().hashCode());
        result = prime * result + ((getEngname() == null) ? 0 : getEngname().hashCode());
        result = prime * result + ((getCrmtype() == null) ? 0 : getCrmtype().hashCode());
        result = prime * result + ((getCrmcode() == null) ? 0 : getCrmcode().hashCode());
        result = prime * result + ((getTaxcode() == null) ? 0 : getTaxcode().hashCode());
        result = prime * result + ((getDesc() == null) ? 0 : getDesc().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getCreateUser() == null) ? 0 : getCreateUser().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getUpdateUser() == null) ? 0 : getUpdateUser().hashCode());
        return result;
    }
}