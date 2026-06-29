package com.sales.ops.db.entity;

import java.io.Serializable;

public class OrdersalesGzKey implements Serializable {
    private String rorderno;

    private String rorderitem;

    private static final long serialVersionUID = 1L;

    public String getRorderno() {
        return rorderno;
    }

    public void setRorderno(String rorderno) {
        this.rorderno = rorderno == null ? null : rorderno.trim();
    }

    public String getRorderitem() {
        return rorderitem;
    }

    public void setRorderitem(String rorderitem) {
        this.rorderitem = rorderitem == null ? null : rorderitem.trim();
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
        OrdersalesGzKey other = (OrdersalesGzKey) that;
        return (this.getRorderno() == null ? other.getRorderno() == null : this.getRorderno().equals(other.getRorderno()))
            && (this.getRorderitem() == null ? other.getRorderitem() == null : this.getRorderitem().equals(other.getRorderitem()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getRorderno() == null) ? 0 : getRorderno().hashCode());
        result = prime * result + ((getRorderitem() == null) ? 0 : getRorderitem().hashCode());
        return result;
    }
}