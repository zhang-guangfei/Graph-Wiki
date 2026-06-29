package com.sales.ops.db.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Agentstock implements Serializable {
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
}