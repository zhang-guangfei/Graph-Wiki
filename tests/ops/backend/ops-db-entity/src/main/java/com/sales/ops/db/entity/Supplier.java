package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class Supplier implements Serializable {
    private String id;

    private String name;

    private String companyid;

    private Integer stddeliveryday;

    private Boolean enablestddeliveryday;

    private Boolean enableinventory;

    private String fcostCode;

    private Integer fstdeliveryday;

    private String fsttranstypeid;

    private Integer shipdeliveryday;

    private Integer stdproductmanuday;

    private Integer nstdproductmanuday;

    private String fullname;

    private String transcurrency;

    private Integer paymentday;

    private Short sort;

    private Date updatetime;

    private String updator;

    private Boolean isautosend;

    private String email;

    private String supplierClass;

    private Boolean enableShikomiCal;

    private Boolean specmarkFlag;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getCompanyid() {
        return companyid;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid == null ? null : companyid.trim();
    }

    public Integer getStddeliveryday() {
        return stddeliveryday;
    }

    public void setStddeliveryday(Integer stddeliveryday) {
        this.stddeliveryday = stddeliveryday;
    }

    public Boolean getEnablestddeliveryday() {
        return enablestddeliveryday;
    }

    public void setEnablestddeliveryday(Boolean enablestddeliveryday) {
        this.enablestddeliveryday = enablestddeliveryday;
    }

    public Boolean getEnableinventory() {
        return enableinventory;
    }

    public void setEnableinventory(Boolean enableinventory) {
        this.enableinventory = enableinventory;
    }

    public String getFcostCode() {
        return fcostCode;
    }

    public void setFcostCode(String fcostCode) {
        this.fcostCode = fcostCode == null ? null : fcostCode.trim();
    }

    public Integer getFstdeliveryday() {
        return fstdeliveryday;
    }

    public void setFstdeliveryday(Integer fstdeliveryday) {
        this.fstdeliveryday = fstdeliveryday;
    }

    public String getFsttranstypeid() {
        return fsttranstypeid;
    }

    public void setFsttranstypeid(String fsttranstypeid) {
        this.fsttranstypeid = fsttranstypeid == null ? null : fsttranstypeid.trim();
    }

    public Integer getShipdeliveryday() {
        return shipdeliveryday;
    }

    public void setShipdeliveryday(Integer shipdeliveryday) {
        this.shipdeliveryday = shipdeliveryday;
    }

    public Integer getStdproductmanuday() {
        return stdproductmanuday;
    }

    public void setStdproductmanuday(Integer stdproductmanuday) {
        this.stdproductmanuday = stdproductmanuday;
    }

    public Integer getNstdproductmanuday() {
        return nstdproductmanuday;
    }

    public void setNstdproductmanuday(Integer nstdproductmanuday) {
        this.nstdproductmanuday = nstdproductmanuday;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname == null ? null : fullname.trim();
    }

    public String getTranscurrency() {
        return transcurrency;
    }

    public void setTranscurrency(String transcurrency) {
        this.transcurrency = transcurrency == null ? null : transcurrency.trim();
    }

    public Integer getPaymentday() {
        return paymentday;
    }

    public void setPaymentday(Integer paymentday) {
        this.paymentday = paymentday;
    }

    public Short getSort() {
        return sort;
    }

    public void setSort(Short sort) {
        this.sort = sort;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getUpdator() {
        return updator;
    }

    public void setUpdator(String updator) {
        this.updator = updator == null ? null : updator.trim();
    }

    public Boolean getIsautosend() {
        return isautosend;
    }

    public void setIsautosend(Boolean isautosend) {
        this.isautosend = isautosend;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getSupplierClass() {
        return supplierClass;
    }

    public void setSupplierClass(String supplierClass) {
        this.supplierClass = supplierClass == null ? null : supplierClass.trim();
    }

    public Boolean getEnableShikomiCal() {
        return enableShikomiCal;
    }

    public void setEnableShikomiCal(Boolean enableShikomiCal) {
        this.enableShikomiCal = enableShikomiCal;
    }

    public Boolean getSpecmarkFlag() {
        return specmarkFlag;
    }

    public void setSpecmarkFlag(Boolean specmarkFlag) {
        this.specmarkFlag = specmarkFlag;
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
        Supplier other = (Supplier) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getCompanyid() == null ? other.getCompanyid() == null : this.getCompanyid().equals(other.getCompanyid()))
            && (this.getStddeliveryday() == null ? other.getStddeliveryday() == null : this.getStddeliveryday().equals(other.getStddeliveryday()))
            && (this.getEnablestddeliveryday() == null ? other.getEnablestddeliveryday() == null : this.getEnablestddeliveryday().equals(other.getEnablestddeliveryday()))
            && (this.getEnableinventory() == null ? other.getEnableinventory() == null : this.getEnableinventory().equals(other.getEnableinventory()))
            && (this.getFcostCode() == null ? other.getFcostCode() == null : this.getFcostCode().equals(other.getFcostCode()))
            && (this.getFstdeliveryday() == null ? other.getFstdeliveryday() == null : this.getFstdeliveryday().equals(other.getFstdeliveryday()))
            && (this.getFsttranstypeid() == null ? other.getFsttranstypeid() == null : this.getFsttranstypeid().equals(other.getFsttranstypeid()))
            && (this.getShipdeliveryday() == null ? other.getShipdeliveryday() == null : this.getShipdeliveryday().equals(other.getShipdeliveryday()))
            && (this.getStdproductmanuday() == null ? other.getStdproductmanuday() == null : this.getStdproductmanuday().equals(other.getStdproductmanuday()))
            && (this.getNstdproductmanuday() == null ? other.getNstdproductmanuday() == null : this.getNstdproductmanuday().equals(other.getNstdproductmanuday()))
            && (this.getFullname() == null ? other.getFullname() == null : this.getFullname().equals(other.getFullname()))
            && (this.getTranscurrency() == null ? other.getTranscurrency() == null : this.getTranscurrency().equals(other.getTranscurrency()))
            && (this.getPaymentday() == null ? other.getPaymentday() == null : this.getPaymentday().equals(other.getPaymentday()))
            && (this.getSort() == null ? other.getSort() == null : this.getSort().equals(other.getSort()))
            && (this.getUpdatetime() == null ? other.getUpdatetime() == null : this.getUpdatetime().equals(other.getUpdatetime()))
            && (this.getUpdator() == null ? other.getUpdator() == null : this.getUpdator().equals(other.getUpdator()))
            && (this.getIsautosend() == null ? other.getIsautosend() == null : this.getIsautosend().equals(other.getIsautosend()))
            && (this.getEmail() == null ? other.getEmail() == null : this.getEmail().equals(other.getEmail()))
            && (this.getSupplierClass() == null ? other.getSupplierClass() == null : this.getSupplierClass().equals(other.getSupplierClass()))
            && (this.getEnableShikomiCal() == null ? other.getEnableShikomiCal() == null : this.getEnableShikomiCal().equals(other.getEnableShikomiCal()))
            && (this.getSpecmarkFlag() == null ? other.getSpecmarkFlag() == null : this.getSpecmarkFlag().equals(other.getSpecmarkFlag()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getCompanyid() == null) ? 0 : getCompanyid().hashCode());
        result = prime * result + ((getStddeliveryday() == null) ? 0 : getStddeliveryday().hashCode());
        result = prime * result + ((getEnablestddeliveryday() == null) ? 0 : getEnablestddeliveryday().hashCode());
        result = prime * result + ((getEnableinventory() == null) ? 0 : getEnableinventory().hashCode());
        result = prime * result + ((getFcostCode() == null) ? 0 : getFcostCode().hashCode());
        result = prime * result + ((getFstdeliveryday() == null) ? 0 : getFstdeliveryday().hashCode());
        result = prime * result + ((getFsttranstypeid() == null) ? 0 : getFsttranstypeid().hashCode());
        result = prime * result + ((getShipdeliveryday() == null) ? 0 : getShipdeliveryday().hashCode());
        result = prime * result + ((getStdproductmanuday() == null) ? 0 : getStdproductmanuday().hashCode());
        result = prime * result + ((getNstdproductmanuday() == null) ? 0 : getNstdproductmanuday().hashCode());
        result = prime * result + ((getFullname() == null) ? 0 : getFullname().hashCode());
        result = prime * result + ((getTranscurrency() == null) ? 0 : getTranscurrency().hashCode());
        result = prime * result + ((getPaymentday() == null) ? 0 : getPaymentday().hashCode());
        result = prime * result + ((getSort() == null) ? 0 : getSort().hashCode());
        result = prime * result + ((getUpdatetime() == null) ? 0 : getUpdatetime().hashCode());
        result = prime * result + ((getUpdator() == null) ? 0 : getUpdator().hashCode());
        result = prime * result + ((getIsautosend() == null) ? 0 : getIsautosend().hashCode());
        result = prime * result + ((getEmail() == null) ? 0 : getEmail().hashCode());
        result = prime * result + ((getSupplierClass() == null) ? 0 : getSupplierClass().hashCode());
        result = prime * result + ((getEnableShikomiCal() == null) ? 0 : getEnableShikomiCal().hashCode());
        result = prime * result + ((getSpecmarkFlag() == null) ? 0 : getSpecmarkFlag().hashCode());
        return result;
    }
}