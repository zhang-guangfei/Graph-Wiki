package com.sales.ops.db.entity;

import java.io.Serializable;

public class ShikomiModelKey implements Serializable {
    private String shikomino;

    private String modelno;

    private static final long serialVersionUID = 1L;

    public String getShikomino() {
        return shikomino;
    }

    public void setShikomino(String shikomino) {
        this.shikomino = shikomino == null ? null : shikomino.trim();
    }

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno == null ? null : modelno.trim();
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
        ShikomiModelKey other = (ShikomiModelKey) that;
        return (this.getShikomino() == null ? other.getShikomino() == null : this.getShikomino().equals(other.getShikomino()))
            && (this.getModelno() == null ? other.getModelno() == null : this.getModelno().equals(other.getModelno()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getShikomino() == null) ? 0 : getShikomino().hashCode());
        result = prime * result + ((getModelno() == null) ? 0 : getModelno().hashCode());
        return result;
    }
}