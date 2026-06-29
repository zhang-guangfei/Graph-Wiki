package com.sales.ops.db.entity;

import java.io.Serializable;

public class ProductDeliKey implements Serializable {
    private String modelno;

    private String supplyid;

    private static final long serialVersionUID = 1L;

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno == null ? null : modelno.trim();
    }

    public String getSupplyid() {
        return supplyid;
    }

    public void setSupplyid(String supplyid) {
        this.supplyid = supplyid == null ? null : supplyid.trim();
    }
}