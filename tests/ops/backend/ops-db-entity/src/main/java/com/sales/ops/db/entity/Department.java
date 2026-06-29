package com.sales.ops.db.entity;

import java.io.Serializable;

public class Department implements Serializable {
    private Integer id;

    private String deptno;

    private String olddeptno;

    private String deptname;

    private String address;

    private String teleno;

    private String faxno;

    private String superintendent;

    private String insidepsn;

    private String emailaddr;

    private String emailac;

    private String stockcode;

    private String statecode;

    private String postcode;

    private String provincecode;

    private String provincename;

    private String deptenname;

    private Double gpslng;

    private Double gpslat;

    private String parentdeptno;

    private String warehouseCode;

    private String subWarehouseCode;

    private String reportemail;

    private String tradeCompanyid;

    private String citycode;

    private Integer dlvday;

    private Boolean isvalid;

    private String emailorder;

    private String emailstock;

    private String emailcsstock;

    private String emailsubstock;

    private String emailuserstock;

    private String emaildirector;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeptno() {
        return deptno;
    }

    public void setDeptno(String deptno) {
        this.deptno = deptno == null ? null : deptno.trim();
    }

    public String getOlddeptno() {
        return olddeptno;
    }

    public void setOlddeptno(String olddeptno) {
        this.olddeptno = olddeptno == null ? null : olddeptno.trim();
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname == null ? null : deptname.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getTeleno() {
        return teleno;
    }

    public void setTeleno(String teleno) {
        this.teleno = teleno == null ? null : teleno.trim();
    }

    public String getFaxno() {
        return faxno;
    }

    public void setFaxno(String faxno) {
        this.faxno = faxno == null ? null : faxno.trim();
    }

    public String getSuperintendent() {
        return superintendent;
    }

    public void setSuperintendent(String superintendent) {
        this.superintendent = superintendent == null ? null : superintendent.trim();
    }

    public String getInsidepsn() {
        return insidepsn;
    }

    public void setInsidepsn(String insidepsn) {
        this.insidepsn = insidepsn == null ? null : insidepsn.trim();
    }

    public String getEmailaddr() {
        return emailaddr;
    }

    public void setEmailaddr(String emailaddr) {
        this.emailaddr = emailaddr == null ? null : emailaddr.trim();
    }

    public String getEmailac() {
        return emailac;
    }

    public void setEmailac(String emailac) {
        this.emailac = emailac == null ? null : emailac.trim();
    }

    public String getStockcode() {
        return stockcode;
    }

    public void setStockcode(String stockcode) {
        this.stockcode = stockcode == null ? null : stockcode.trim();
    }

    public String getStatecode() {
        return statecode;
    }

    public void setStatecode(String statecode) {
        this.statecode = statecode == null ? null : statecode.trim();
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode == null ? null : postcode.trim();
    }

    public String getProvincecode() {
        return provincecode;
    }

    public void setProvincecode(String provincecode) {
        this.provincecode = provincecode == null ? null : provincecode.trim();
    }

    public String getProvincename() {
        return provincename;
    }

    public void setProvincename(String provincename) {
        this.provincename = provincename == null ? null : provincename.trim();
    }

    public String getDeptenname() {
        return deptenname;
    }

    public void setDeptenname(String deptenname) {
        this.deptenname = deptenname == null ? null : deptenname.trim();
    }

    public Double getGpslng() {
        return gpslng;
    }

    public void setGpslng(Double gpslng) {
        this.gpslng = gpslng;
    }

    public Double getGpslat() {
        return gpslat;
    }

    public void setGpslat(Double gpslat) {
        this.gpslat = gpslat;
    }

    public String getParentdeptno() {
        return parentdeptno;
    }

    public void setParentdeptno(String parentdeptno) {
        this.parentdeptno = parentdeptno == null ? null : parentdeptno.trim();
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode == null ? null : warehouseCode.trim();
    }

    public String getSubWarehouseCode() {
        return subWarehouseCode;
    }

    public void setSubWarehouseCode(String subWarehouseCode) {
        this.subWarehouseCode = subWarehouseCode == null ? null : subWarehouseCode.trim();
    }

    public String getReportemail() {
        return reportemail;
    }

    public void setReportemail(String reportemail) {
        this.reportemail = reportemail == null ? null : reportemail.trim();
    }

    public String getTradeCompanyid() {
        return tradeCompanyid;
    }

    public void setTradeCompanyid(String tradeCompanyid) {
        this.tradeCompanyid = tradeCompanyid == null ? null : tradeCompanyid.trim();
    }

    public String getCitycode() {
        return citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode == null ? null : citycode.trim();
    }

    public Integer getDlvday() {
        return dlvday;
    }

    public void setDlvday(Integer dlvday) {
        this.dlvday = dlvday;
    }

    public Boolean getIsvalid() {
        return isvalid;
    }

    public void setIsvalid(Boolean isvalid) {
        this.isvalid = isvalid;
    }

    public String getEmailorder() {
        return emailorder;
    }

    public void setEmailorder(String emailorder) {
        this.emailorder = emailorder == null ? null : emailorder.trim();
    }

    public String getEmailstock() {
        return emailstock;
    }

    public void setEmailstock(String emailstock) {
        this.emailstock = emailstock == null ? null : emailstock.trim();
    }

    public String getEmailcsstock() {
        return emailcsstock;
    }

    public void setEmailcsstock(String emailcsstock) {
        this.emailcsstock = emailcsstock == null ? null : emailcsstock.trim();
    }

    public String getEmailsubstock() {
        return emailsubstock;
    }

    public void setEmailsubstock(String emailsubstock) {
        this.emailsubstock = emailsubstock == null ? null : emailsubstock.trim();
    }

    public String getEmailuserstock() {
        return emailuserstock;
    }

    public void setEmailuserstock(String emailuserstock) {
        this.emailuserstock = emailuserstock == null ? null : emailuserstock.trim();
    }

    public String getEmaildirector() {
        return emaildirector;
    }

    public void setEmaildirector(String emaildirector) {
        this.emaildirector = emaildirector == null ? null : emaildirector.trim();
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
        Department other = (Department) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getDeptno() == null ? other.getDeptno() == null : this.getDeptno().equals(other.getDeptno()))
            && (this.getOlddeptno() == null ? other.getOlddeptno() == null : this.getOlddeptno().equals(other.getOlddeptno()))
            && (this.getDeptname() == null ? other.getDeptname() == null : this.getDeptname().equals(other.getDeptname()))
            && (this.getAddress() == null ? other.getAddress() == null : this.getAddress().equals(other.getAddress()))
            && (this.getTeleno() == null ? other.getTeleno() == null : this.getTeleno().equals(other.getTeleno()))
            && (this.getFaxno() == null ? other.getFaxno() == null : this.getFaxno().equals(other.getFaxno()))
            && (this.getSuperintendent() == null ? other.getSuperintendent() == null : this.getSuperintendent().equals(other.getSuperintendent()))
            && (this.getInsidepsn() == null ? other.getInsidepsn() == null : this.getInsidepsn().equals(other.getInsidepsn()))
            && (this.getEmailaddr() == null ? other.getEmailaddr() == null : this.getEmailaddr().equals(other.getEmailaddr()))
            && (this.getEmailac() == null ? other.getEmailac() == null : this.getEmailac().equals(other.getEmailac()))
            && (this.getStockcode() == null ? other.getStockcode() == null : this.getStockcode().equals(other.getStockcode()))
            && (this.getStatecode() == null ? other.getStatecode() == null : this.getStatecode().equals(other.getStatecode()))
            && (this.getPostcode() == null ? other.getPostcode() == null : this.getPostcode().equals(other.getPostcode()))
            && (this.getProvincecode() == null ? other.getProvincecode() == null : this.getProvincecode().equals(other.getProvincecode()))
            && (this.getProvincename() == null ? other.getProvincename() == null : this.getProvincename().equals(other.getProvincename()))
            && (this.getDeptenname() == null ? other.getDeptenname() == null : this.getDeptenname().equals(other.getDeptenname()))
            && (this.getGpslng() == null ? other.getGpslng() == null : this.getGpslng().equals(other.getGpslng()))
            && (this.getGpslat() == null ? other.getGpslat() == null : this.getGpslat().equals(other.getGpslat()))
            && (this.getParentdeptno() == null ? other.getParentdeptno() == null : this.getParentdeptno().equals(other.getParentdeptno()))
            && (this.getWarehouseCode() == null ? other.getWarehouseCode() == null : this.getWarehouseCode().equals(other.getWarehouseCode()))
            && (this.getSubWarehouseCode() == null ? other.getSubWarehouseCode() == null : this.getSubWarehouseCode().equals(other.getSubWarehouseCode()))
            && (this.getReportemail() == null ? other.getReportemail() == null : this.getReportemail().equals(other.getReportemail()))
            && (this.getTradeCompanyid() == null ? other.getTradeCompanyid() == null : this.getTradeCompanyid().equals(other.getTradeCompanyid()))
            && (this.getCitycode() == null ? other.getCitycode() == null : this.getCitycode().equals(other.getCitycode()))
            && (this.getDlvday() == null ? other.getDlvday() == null : this.getDlvday().equals(other.getDlvday()))
            && (this.getIsvalid() == null ? other.getIsvalid() == null : this.getIsvalid().equals(other.getIsvalid()))
            && (this.getEmailorder() == null ? other.getEmailorder() == null : this.getEmailorder().equals(other.getEmailorder()))
            && (this.getEmailstock() == null ? other.getEmailstock() == null : this.getEmailstock().equals(other.getEmailstock()))
            && (this.getEmailcsstock() == null ? other.getEmailcsstock() == null : this.getEmailcsstock().equals(other.getEmailcsstock()))
            && (this.getEmailsubstock() == null ? other.getEmailsubstock() == null : this.getEmailsubstock().equals(other.getEmailsubstock()))
            && (this.getEmailuserstock() == null ? other.getEmailuserstock() == null : this.getEmailuserstock().equals(other.getEmailuserstock()))
            && (this.getEmaildirector() == null ? other.getEmaildirector() == null : this.getEmaildirector().equals(other.getEmaildirector()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getDeptno() == null) ? 0 : getDeptno().hashCode());
        result = prime * result + ((getOlddeptno() == null) ? 0 : getOlddeptno().hashCode());
        result = prime * result + ((getDeptname() == null) ? 0 : getDeptname().hashCode());
        result = prime * result + ((getAddress() == null) ? 0 : getAddress().hashCode());
        result = prime * result + ((getTeleno() == null) ? 0 : getTeleno().hashCode());
        result = prime * result + ((getFaxno() == null) ? 0 : getFaxno().hashCode());
        result = prime * result + ((getSuperintendent() == null) ? 0 : getSuperintendent().hashCode());
        result = prime * result + ((getInsidepsn() == null) ? 0 : getInsidepsn().hashCode());
        result = prime * result + ((getEmailaddr() == null) ? 0 : getEmailaddr().hashCode());
        result = prime * result + ((getEmailac() == null) ? 0 : getEmailac().hashCode());
        result = prime * result + ((getStockcode() == null) ? 0 : getStockcode().hashCode());
        result = prime * result + ((getStatecode() == null) ? 0 : getStatecode().hashCode());
        result = prime * result + ((getPostcode() == null) ? 0 : getPostcode().hashCode());
        result = prime * result + ((getProvincecode() == null) ? 0 : getProvincecode().hashCode());
        result = prime * result + ((getProvincename() == null) ? 0 : getProvincename().hashCode());
        result = prime * result + ((getDeptenname() == null) ? 0 : getDeptenname().hashCode());
        result = prime * result + ((getGpslng() == null) ? 0 : getGpslng().hashCode());
        result = prime * result + ((getGpslat() == null) ? 0 : getGpslat().hashCode());
        result = prime * result + ((getParentdeptno() == null) ? 0 : getParentdeptno().hashCode());
        result = prime * result + ((getWarehouseCode() == null) ? 0 : getWarehouseCode().hashCode());
        result = prime * result + ((getSubWarehouseCode() == null) ? 0 : getSubWarehouseCode().hashCode());
        result = prime * result + ((getReportemail() == null) ? 0 : getReportemail().hashCode());
        result = prime * result + ((getTradeCompanyid() == null) ? 0 : getTradeCompanyid().hashCode());
        result = prime * result + ((getCitycode() == null) ? 0 : getCitycode().hashCode());
        result = prime * result + ((getDlvday() == null) ? 0 : getDlvday().hashCode());
        result = prime * result + ((getIsvalid() == null) ? 0 : getIsvalid().hashCode());
        result = prime * result + ((getEmailorder() == null) ? 0 : getEmailorder().hashCode());
        result = prime * result + ((getEmailstock() == null) ? 0 : getEmailstock().hashCode());
        result = prime * result + ((getEmailcsstock() == null) ? 0 : getEmailcsstock().hashCode());
        result = prime * result + ((getEmailsubstock() == null) ? 0 : getEmailsubstock().hashCode());
        result = prime * result + ((getEmailuserstock() == null) ? 0 : getEmailuserstock().hashCode());
        result = prime * result + ((getEmaildirector() == null) ? 0 : getEmaildirector().hashCode());
        return result;
    }
}