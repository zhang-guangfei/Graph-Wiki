package com.sales.ops.db.entity;

import java.io.Serializable;

public class ProductSupplierConfigKey implements Serializable {
    private String modelno;

    private String supplierid;

    private Integer stddlvdate;

    private static final long serialVersionUID = 1L;

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno == null ? null : modelno.trim();
    }

    public String getSupplierid() {
        return supplierid;
    }

    public void setSupplierid(String supplierid) {
        this.supplierid = supplierid == null ? null : supplierid.trim();
    }

    public Integer getStddlvdate() {
        return stddlvdate;
    }

    public void setStddlvdate(Integer stddlvdate) {
        this.stddlvdate = stddlvdate;
    }
}