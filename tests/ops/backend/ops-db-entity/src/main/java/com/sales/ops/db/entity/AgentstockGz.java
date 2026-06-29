package com.sales.ops.db.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AgentstockGz implements Serializable {
    private String stockcode;

    private String customerno;

    private String stockname;

    private Date updtime;

    private String contractno;

    private String shortaddress;

    private String addressid;

    private BigDecimal discount;

    private String deptno;

    private Date createtime;

    private String deptnos;

    private Integer totalmodelcount;

    private Integer unexpmodelcount;

    private Date begindate;

    private String email;

    private Short status;

    private Integer exporttype;

    private Boolean autoexport;

    private String address;

    private String contactpsn;

    private String contacttel;

    private String stockid;

    private String maxcaseno;

    private String provincecode;

    private String postcode;

    private static final long serialVersionUID = 1L;

    public String getStockcode() {
        return stockcode;
    }

    public void setStockcode(String stockcode) {
        this.stockcode = stockcode == null ? null : stockcode.trim();
    }

    public String getCustomerno() {
        return customerno;
    }

    public void setCustomerno(String customerno) {
        this.customerno = customerno == null ? null : customerno.trim();
    }

    public String getStockname() {
        return stockname;
    }

    public void setStockname(String stockname) {
        this.stockname = stockname == null ? null : stockname.trim();
    }

    public Date getUpdtime() {
        return updtime;
    }

    public void setUpdtime(Date updtime) {
        this.updtime = updtime;
    }

    public String getContractno() {
        return contractno;
    }

    public void setContractno(String contractno) {
        this.contractno = contractno == null ? null : contractno.trim();
    }

    public String getShortaddress() {
        return shortaddress;
    }

    public void setShortaddress(String shortaddress) {
        this.shortaddress = shortaddress == null ? null : shortaddress.trim();
    }

    public String getAddressid() {
        return addressid;
    }

    public void setAddressid(String addressid) {
        this.addressid = addressid == null ? null : addressid.trim();
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public String getDeptno() {
        return deptno;
    }

    public void setDeptno(String deptno) {
        this.deptno = deptno == null ? null : deptno.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getDeptnos() {
        return deptnos;
    }

    public void setDeptnos(String deptnos) {
        this.deptnos = deptnos == null ? null : deptnos.trim();
    }

    public Integer getTotalmodelcount() {
        return totalmodelcount;
    }

    public void setTotalmodelcount(Integer totalmodelcount) {
        this.totalmodelcount = totalmodelcount;
    }

    public Integer getUnexpmodelcount() {
        return unexpmodelcount;
    }

    public void setUnexpmodelcount(Integer unexpmodelcount) {
        this.unexpmodelcount = unexpmodelcount;
    }

    public Date getBegindate() {
        return begindate;
    }

    public void setBegindate(Date begindate) {
        this.begindate = begindate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Integer getExporttype() {
        return exporttype;
    }

    public void setExporttype(Integer exporttype) {
        this.exporttype = exporttype;
    }

    public Boolean getAutoexport() {
        return autoexport;
    }

    public void setAutoexport(Boolean autoexport) {
        this.autoexport = autoexport;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getContactpsn() {
        return contactpsn;
    }

    public void setContactpsn(String contactpsn) {
        this.contactpsn = contactpsn == null ? null : contactpsn.trim();
    }

    public String getContacttel() {
        return contacttel;
    }

    public void setContacttel(String contacttel) {
        this.contacttel = contacttel == null ? null : contacttel.trim();
    }

    public String getStockid() {
        return stockid;
    }

    public void setStockid(String stockid) {
        this.stockid = stockid == null ? null : stockid.trim();
    }

    public String getMaxcaseno() {
        return maxcaseno;
    }

    public void setMaxcaseno(String maxcaseno) {
        this.maxcaseno = maxcaseno == null ? null : maxcaseno.trim();
    }

    public String getProvincecode() {
        return provincecode;
    }

    public void setProvincecode(String provincecode) {
        this.provincecode = provincecode == null ? null : provincecode.trim();
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode == null ? null : postcode.trim();
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
        AgentstockGz other = (AgentstockGz) that;
        return (this.getStockcode() == null ? other.getStockcode() == null : this.getStockcode().equals(other.getStockcode()))
            && (this.getCustomerno() == null ? other.getCustomerno() == null : this.getCustomerno().equals(other.getCustomerno()))
            && (this.getStockname() == null ? other.getStockname() == null : this.getStockname().equals(other.getStockname()))
            && (this.getUpdtime() == null ? other.getUpdtime() == null : this.getUpdtime().equals(other.getUpdtime()))
            && (this.getContractno() == null ? other.getContractno() == null : this.getContractno().equals(other.getContractno()))
            && (this.getShortaddress() == null ? other.getShortaddress() == null : this.getShortaddress().equals(other.getShortaddress()))
            && (this.getAddressid() == null ? other.getAddressid() == null : this.getAddressid().equals(other.getAddressid()))
            && (this.getDiscount() == null ? other.getDiscount() == null : this.getDiscount().equals(other.getDiscount()))
            && (this.getDeptno() == null ? other.getDeptno() == null : this.getDeptno().equals(other.getDeptno()))
            && (this.getCreatetime() == null ? other.getCreatetime() == null : this.getCreatetime().equals(other.getCreatetime()))
            && (this.getDeptnos() == null ? other.getDeptnos() == null : this.getDeptnos().equals(other.getDeptnos()))
            && (this.getTotalmodelcount() == null ? other.getTotalmodelcount() == null : this.getTotalmodelcount().equals(other.getTotalmodelcount()))
            && (this.getUnexpmodelcount() == null ? other.getUnexpmodelcount() == null : this.getUnexpmodelcount().equals(other.getUnexpmodelcount()))
            && (this.getBegindate() == null ? other.getBegindate() == null : this.getBegindate().equals(other.getBegindate()))
            && (this.getEmail() == null ? other.getEmail() == null : this.getEmail().equals(other.getEmail()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getExporttype() == null ? other.getExporttype() == null : this.getExporttype().equals(other.getExporttype()))
            && (this.getAutoexport() == null ? other.getAutoexport() == null : this.getAutoexport().equals(other.getAutoexport()))
            && (this.getAddress() == null ? other.getAddress() == null : this.getAddress().equals(other.getAddress()))
            && (this.getContactpsn() == null ? other.getContactpsn() == null : this.getContactpsn().equals(other.getContactpsn()))
            && (this.getContacttel() == null ? other.getContacttel() == null : this.getContacttel().equals(other.getContacttel()))
            && (this.getStockid() == null ? other.getStockid() == null : this.getStockid().equals(other.getStockid()))
            && (this.getMaxcaseno() == null ? other.getMaxcaseno() == null : this.getMaxcaseno().equals(other.getMaxcaseno()))
            && (this.getProvincecode() == null ? other.getProvincecode() == null : this.getProvincecode().equals(other.getProvincecode()))
            && (this.getPostcode() == null ? other.getPostcode() == null : this.getPostcode().equals(other.getPostcode()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getStockcode() == null) ? 0 : getStockcode().hashCode());
        result = prime * result + ((getCustomerno() == null) ? 0 : getCustomerno().hashCode());
        result = prime * result + ((getStockname() == null) ? 0 : getStockname().hashCode());
        result = prime * result + ((getUpdtime() == null) ? 0 : getUpdtime().hashCode());
        result = prime * result + ((getContractno() == null) ? 0 : getContractno().hashCode());
        result = prime * result + ((getShortaddress() == null) ? 0 : getShortaddress().hashCode());
        result = prime * result + ((getAddressid() == null) ? 0 : getAddressid().hashCode());
        result = prime * result + ((getDiscount() == null) ? 0 : getDiscount().hashCode());
        result = prime * result + ((getDeptno() == null) ? 0 : getDeptno().hashCode());
        result = prime * result + ((getCreatetime() == null) ? 0 : getCreatetime().hashCode());
        result = prime * result + ((getDeptnos() == null) ? 0 : getDeptnos().hashCode());
        result = prime * result + ((getTotalmodelcount() == null) ? 0 : getTotalmodelcount().hashCode());
        result = prime * result + ((getUnexpmodelcount() == null) ? 0 : getUnexpmodelcount().hashCode());
        result = prime * result + ((getBegindate() == null) ? 0 : getBegindate().hashCode());
        result = prime * result + ((getEmail() == null) ? 0 : getEmail().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getExporttype() == null) ? 0 : getExporttype().hashCode());
        result = prime * result + ((getAutoexport() == null) ? 0 : getAutoexport().hashCode());
        result = prime * result + ((getAddress() == null) ? 0 : getAddress().hashCode());
        result = prime * result + ((getContactpsn() == null) ? 0 : getContactpsn().hashCode());
        result = prime * result + ((getContacttel() == null) ? 0 : getContacttel().hashCode());
        result = prime * result + ((getStockid() == null) ? 0 : getStockid().hashCode());
        result = prime * result + ((getMaxcaseno() == null) ? 0 : getMaxcaseno().hashCode());
        result = prime * result + ((getProvincecode() == null) ? 0 : getProvincecode().hashCode());
        result = prime * result + ((getPostcode() == null) ? 0 : getPostcode().hashCode());
        return result;
    }
}