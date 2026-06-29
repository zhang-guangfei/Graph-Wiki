package com.sales.ops.db.entity;

import java.io.Serializable;

public class ProductBomDetailKey implements Serializable {
    private Long bomid;

    private String modelno;

    private static final long serialVersionUID = 1L;

    public Long getBomid() {
        return bomid;
    }

    public void setBomid(Long bomid) {
        this.bomid = bomid;
    }

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno == null ? null : modelno.trim();
    }
}