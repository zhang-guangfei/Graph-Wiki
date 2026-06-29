package com.sales.ops.db.entity;

import java.io.Serializable;

public class SupplierCompanys implements Serializable {
    private String 供应地代码;

    private String 供应商代码;

    private String 供应地;

    private static final long serialVersionUID = 1L;

    public String get供应地代码() {
        return 供应地代码;
    }

    public void set供应地代码(String 供应地代码) {
        this.供应地代码 = 供应地代码 == null ? null : 供应地代码.trim();
    }

    public String get供应商代码() {
        return 供应商代码;
    }

    public void set供应商代码(String 供应商代码) {
        this.供应商代码 = 供应商代码 == null ? null : 供应商代码.trim();
    }

    public String get供应地() {
        return 供应地;
    }

    public void set供应地(String 供应地) {
        this.供应地 = 供应地 == null ? null : 供应地.trim();
    }
}