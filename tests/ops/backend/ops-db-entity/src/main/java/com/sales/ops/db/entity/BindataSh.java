package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class BindataSh extends BindataShKey implements Serializable {
    private Integer qtybin;

    private String classcode;

    private String casetype;

    private String prodtype;

    private Date logindate;

    private Integer bincell;

    private Date upddate;

    private String stockcode;

    private String positionno;

    private String meshtype;

    private Integer incaseqty;

    private String adjustable;

    private String remark;

    private Integer qtyordpoint;

    private String prodseri;

    private static final long serialVersionUID = 1L;

    public Integer getQtybin() {
        return qtybin;
    }

    public void setQtybin(Integer qtybin) {
        this.qtybin = qtybin;
    }

    public String getClasscode() {
        return classcode;
    }

    public void setClasscode(String classcode) {
        this.classcode = classcode == null ? null : classcode.trim();
    }

    public String getCasetype() {
        return casetype;
    }

    public void setCasetype(String casetype) {
        this.casetype = casetype == null ? null : casetype.trim();
    }

    public String getProdtype() {
        return prodtype;
    }

    public void setProdtype(String prodtype) {
        this.prodtype = prodtype == null ? null : prodtype.trim();
    }

    public Date getLogindate() {
        return logindate;
    }

    public void setLogindate(Date logindate) {
        this.logindate = logindate;
    }

    public Integer getBincell() {
        return bincell;
    }

    public void setBincell(Integer bincell) {
        this.bincell = bincell;
    }

    public Date getUpddate() {
        return upddate;
    }

    public void setUpddate(Date upddate) {
        this.upddate = upddate;
    }

    public String getStockcode() {
        return stockcode;
    }

    public void setStockcode(String stockcode) {
        this.stockcode = stockcode == null ? null : stockcode.trim();
    }

    public String getPositionno() {
        return positionno;
    }

    public void setPositionno(String positionno) {
        this.positionno = positionno == null ? null : positionno.trim();
    }

    public String getMeshtype() {
        return meshtype;
    }

    public void setMeshtype(String meshtype) {
        this.meshtype = meshtype == null ? null : meshtype.trim();
    }

    public Integer getIncaseqty() {
        return incaseqty;
    }

    public void setIncaseqty(Integer incaseqty) {
        this.incaseqty = incaseqty;
    }

    public String getAdjustable() {
        return adjustable;
    }

    public void setAdjustable(String adjustable) {
        this.adjustable = adjustable == null ? null : adjustable.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getQtyordpoint() {
        return qtyordpoint;
    }

    public void setQtyordpoint(Integer qtyordpoint) {
        this.qtyordpoint = qtyordpoint;
    }

    public String getProdseri() {
        return prodseri;
    }

    public void setProdseri(String prodseri) {
        this.prodseri = prodseri == null ? null : prodseri.trim();
    }
}