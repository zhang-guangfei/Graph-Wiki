package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class Tablestructure extends TablestructureKey implements Serializable {
    private Short itemno;

    private String bepk;

    private String beidentity;

    private String datatype;

    private Short datalength;

    private String benull;

    private String defaultvalue;

    private Object remark;

    private Date updtime;

    private Integer decimallength;

    private String coldes;

    private static final long serialVersionUID = 1L;

    public Short getItemno() {
        return itemno;
    }

    public void setItemno(Short itemno) {
        this.itemno = itemno;
    }

    public String getBepk() {
        return bepk;
    }

    public void setBepk(String bepk) {
        this.bepk = bepk == null ? null : bepk.trim();
    }

    public String getBeidentity() {
        return beidentity;
    }

    public void setBeidentity(String beidentity) {
        this.beidentity = beidentity == null ? null : beidentity.trim();
    }

    public String getDatatype() {
        return datatype;
    }

    public void setDatatype(String datatype) {
        this.datatype = datatype == null ? null : datatype.trim();
    }

    public Short getDatalength() {
        return datalength;
    }

    public void setDatalength(Short datalength) {
        this.datalength = datalength;
    }

    public String getBenull() {
        return benull;
    }

    public void setBenull(String benull) {
        this.benull = benull == null ? null : benull.trim();
    }

    public String getDefaultvalue() {
        return defaultvalue;
    }

    public void setDefaultvalue(String defaultvalue) {
        this.defaultvalue = defaultvalue == null ? null : defaultvalue.trim();
    }

    public Object getRemark() {
        return remark;
    }

    public void setRemark(Object remark) {
        this.remark = remark;
    }

    public Date getUpdtime() {
        return updtime;
    }

    public void setUpdtime(Date updtime) {
        this.updtime = updtime;
    }

    public Integer getDecimallength() {
        return decimallength;
    }

    public void setDecimallength(Integer decimallength) {
        this.decimallength = decimallength;
    }

    public String getColdes() {
        return coldes;
    }

    public void setColdes(String coldes) {
        this.coldes = coldes == null ? null : coldes.trim();
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
        Tablestructure other = (Tablestructure) that;
        return (this.getDbname() == null ? other.getDbname() == null : this.getDbname().equals(other.getDbname()))
            && (this.getTblname() == null ? other.getTblname() == null : this.getTblname().equals(other.getTblname()))
            && (this.getColname() == null ? other.getColname() == null : this.getColname().equals(other.getColname()))
            && (this.getItemno() == null ? other.getItemno() == null : this.getItemno().equals(other.getItemno()))
            && (this.getBepk() == null ? other.getBepk() == null : this.getBepk().equals(other.getBepk()))
            && (this.getBeidentity() == null ? other.getBeidentity() == null : this.getBeidentity().equals(other.getBeidentity()))
            && (this.getDatatype() == null ? other.getDatatype() == null : this.getDatatype().equals(other.getDatatype()))
            && (this.getDatalength() == null ? other.getDatalength() == null : this.getDatalength().equals(other.getDatalength()))
            && (this.getBenull() == null ? other.getBenull() == null : this.getBenull().equals(other.getBenull()))
            && (this.getDefaultvalue() == null ? other.getDefaultvalue() == null : this.getDefaultvalue().equals(other.getDefaultvalue()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getUpdtime() == null ? other.getUpdtime() == null : this.getUpdtime().equals(other.getUpdtime()))
            && (this.getDecimallength() == null ? other.getDecimallength() == null : this.getDecimallength().equals(other.getDecimallength()))
            && (this.getColdes() == null ? other.getColdes() == null : this.getColdes().equals(other.getColdes()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getDbname() == null) ? 0 : getDbname().hashCode());
        result = prime * result + ((getTblname() == null) ? 0 : getTblname().hashCode());
        result = prime * result + ((getColname() == null) ? 0 : getColname().hashCode());
        result = prime * result + ((getItemno() == null) ? 0 : getItemno().hashCode());
        result = prime * result + ((getBepk() == null) ? 0 : getBepk().hashCode());
        result = prime * result + ((getBeidentity() == null) ? 0 : getBeidentity().hashCode());
        result = prime * result + ((getDatatype() == null) ? 0 : getDatatype().hashCode());
        result = prime * result + ((getDatalength() == null) ? 0 : getDatalength().hashCode());
        result = prime * result + ((getBenull() == null) ? 0 : getBenull().hashCode());
        result = prime * result + ((getDefaultvalue() == null) ? 0 : getDefaultvalue().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getUpdtime() == null) ? 0 : getUpdtime().hashCode());
        result = prime * result + ((getDecimallength() == null) ? 0 : getDecimallength().hashCode());
        result = prime * result + ((getColdes() == null) ? 0 : getColdes().hashCode());
        return result;
    }
}