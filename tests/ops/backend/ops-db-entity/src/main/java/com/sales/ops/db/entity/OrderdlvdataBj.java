package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class OrderdlvdataBj implements Serializable {
    private String rorderno;

    private String customerno;

    private String cstmname;

    private String dlvaddress;

    private String contactpsn;

    private String teleno;

    private String postcode;

    private String dlvtype;

    private String deptno;

    private String idcard;

    private Date arriveddate;

    private String preflag;

    private String editemployeeid;

    private Date upddate;

    private String dlvflag;

    private static final long serialVersionUID = 1L;

    public String getRorderno() {
        return rorderno;
    }

    public void setRorderno(String rorderno) {
        this.rorderno = rorderno == null ? null : rorderno.trim();
    }

    public String getCustomerno() {
        return customerno;
    }

    public void setCustomerno(String customerno) {
        this.customerno = customerno == null ? null : customerno.trim();
    }

    public String getCstmname() {
        return cstmname;
    }

    public void setCstmname(String cstmname) {
        this.cstmname = cstmname == null ? null : cstmname.trim();
    }

    public String getDlvaddress() {
        return dlvaddress;
    }

    public void setDlvaddress(String dlvaddress) {
        this.dlvaddress = dlvaddress == null ? null : dlvaddress.trim();
    }

    public String getContactpsn() {
        return contactpsn;
    }

    public void setContactpsn(String contactpsn) {
        this.contactpsn = contactpsn == null ? null : contactpsn.trim();
    }

    public String getTeleno() {
        return teleno;
    }

    public void setTeleno(String teleno) {
        this.teleno = teleno == null ? null : teleno.trim();
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode == null ? null : postcode.trim();
    }

    public String getDlvtype() {
        return dlvtype;
    }

    public void setDlvtype(String dlvtype) {
        this.dlvtype = dlvtype == null ? null : dlvtype.trim();
    }

    public String getDeptno() {
        return deptno;
    }

    public void setDeptno(String deptno) {
        this.deptno = deptno == null ? null : deptno.trim();
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard == null ? null : idcard.trim();
    }

    public Date getArriveddate() {
        return arriveddate;
    }

    public void setArriveddate(Date arriveddate) {
        this.arriveddate = arriveddate;
    }

    public String getPreflag() {
        return preflag;
    }

    public void setPreflag(String preflag) {
        this.preflag = preflag == null ? null : preflag.trim();
    }

    public String getEditemployeeid() {
        return editemployeeid;
    }

    public void setEditemployeeid(String editemployeeid) {
        this.editemployeeid = editemployeeid == null ? null : editemployeeid.trim();
    }

    public Date getUpddate() {
        return upddate;
    }

    public void setUpddate(Date upddate) {
        this.upddate = upddate;
    }

    public String getDlvflag() {
        return dlvflag;
    }

    public void setDlvflag(String dlvflag) {
        this.dlvflag = dlvflag == null ? null : dlvflag.trim();
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
        OrderdlvdataBj other = (OrderdlvdataBj) that;
        return (this.getRorderno() == null ? other.getRorderno() == null : this.getRorderno().equals(other.getRorderno()))
            && (this.getCustomerno() == null ? other.getCustomerno() == null : this.getCustomerno().equals(other.getCustomerno()))
            && (this.getCstmname() == null ? other.getCstmname() == null : this.getCstmname().equals(other.getCstmname()))
            && (this.getDlvaddress() == null ? other.getDlvaddress() == null : this.getDlvaddress().equals(other.getDlvaddress()))
            && (this.getContactpsn() == null ? other.getContactpsn() == null : this.getContactpsn().equals(other.getContactpsn()))
            && (this.getTeleno() == null ? other.getTeleno() == null : this.getTeleno().equals(other.getTeleno()))
            && (this.getPostcode() == null ? other.getPostcode() == null : this.getPostcode().equals(other.getPostcode()))
            && (this.getDlvtype() == null ? other.getDlvtype() == null : this.getDlvtype().equals(other.getDlvtype()))
            && (this.getDeptno() == null ? other.getDeptno() == null : this.getDeptno().equals(other.getDeptno()))
            && (this.getIdcard() == null ? other.getIdcard() == null : this.getIdcard().equals(other.getIdcard()))
            && (this.getArriveddate() == null ? other.getArriveddate() == null : this.getArriveddate().equals(other.getArriveddate()))
            && (this.getPreflag() == null ? other.getPreflag() == null : this.getPreflag().equals(other.getPreflag()))
            && (this.getEditemployeeid() == null ? other.getEditemployeeid() == null : this.getEditemployeeid().equals(other.getEditemployeeid()))
            && (this.getUpddate() == null ? other.getUpddate() == null : this.getUpddate().equals(other.getUpddate()))
            && (this.getDlvflag() == null ? other.getDlvflag() == null : this.getDlvflag().equals(other.getDlvflag()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getRorderno() == null) ? 0 : getRorderno().hashCode());
        result = prime * result + ((getCustomerno() == null) ? 0 : getCustomerno().hashCode());
        result = prime * result + ((getCstmname() == null) ? 0 : getCstmname().hashCode());
        result = prime * result + ((getDlvaddress() == null) ? 0 : getDlvaddress().hashCode());
        result = prime * result + ((getContactpsn() == null) ? 0 : getContactpsn().hashCode());
        result = prime * result + ((getTeleno() == null) ? 0 : getTeleno().hashCode());
        result = prime * result + ((getPostcode() == null) ? 0 : getPostcode().hashCode());
        result = prime * result + ((getDlvtype() == null) ? 0 : getDlvtype().hashCode());
        result = prime * result + ((getDeptno() == null) ? 0 : getDeptno().hashCode());
        result = prime * result + ((getIdcard() == null) ? 0 : getIdcard().hashCode());
        result = prime * result + ((getArriveddate() == null) ? 0 : getArriveddate().hashCode());
        result = prime * result + ((getPreflag() == null) ? 0 : getPreflag().hashCode());
        result = prime * result + ((getEditemployeeid() == null) ? 0 : getEditemployeeid().hashCode());
        result = prime * result + ((getUpddate() == null) ? 0 : getUpddate().hashCode());
        result = prime * result + ((getDlvflag() == null) ? 0 : getDlvflag().hashCode());
        return result;
    }
}