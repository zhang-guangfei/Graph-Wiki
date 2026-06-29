package com.sales.ops.db.entity;

import java.io.Serializable;

public class ShikomiCustomerKey implements Serializable {
    private String shikomino;

    private String customerno;

    private static final long serialVersionUID = 1L;

    public String getShikomino() {
        return shikomino;
    }

    public void setShikomino(String shikomino) {
        this.shikomino = shikomino == null ? null : shikomino.trim();
    }

    public String getCustomerno() {
        return customerno;
    }

    public void setCustomerno(String customerno) {
        this.customerno = customerno == null ? null : customerno.trim();
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
        ShikomiCustomerKey other = (ShikomiCustomerKey) that;
        return (this.getShikomino() == null ? other.getShikomino() == null : this.getShikomino().equals(other.getShikomino()))
            && (this.getCustomerno() == null ? other.getCustomerno() == null : this.getCustomerno().equals(other.getCustomerno()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getShikomino() == null) ? 0 : getShikomino().hashCode());
        result = prime * result + ((getCustomerno() == null) ? 0 : getCustomerno().hashCode());
        return result;
    }
}