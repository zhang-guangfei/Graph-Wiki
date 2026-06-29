package com.sales.ops.db.entity;

import java.io.Serializable;

public class ProductTestKey implements Serializable {
    private String modelno;

    private Integer minquantity;

    private static final long serialVersionUID = 1L;

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno == null ? null : modelno.trim();
    }

    public Integer getMinquantity() {
        return minquantity;
    }

    public void setMinquantity(Integer minquantity) {
        this.minquantity = minquantity;
    }
}