package com.sales.ops.db.entity;

import java.io.Serializable;

public class TemprequisitionKey implements Serializable {
    private String requestno;

    private String itemno;

    private static final long serialVersionUID = 1L;

    public String getRequestno() {
        return requestno;
    }

    public void setRequestno(String requestno) {
        this.requestno = requestno == null ? null : requestno.trim();
    }

    public String getItemno() {
        return itemno;
    }

    public void setItemno(String itemno) {
        this.itemno = itemno == null ? null : itemno.trim();
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
        TemprequisitionKey other = (TemprequisitionKey) that;
        return (this.getRequestno() == null ? other.getRequestno() == null : this.getRequestno().equals(other.getRequestno()))
            && (this.getItemno() == null ? other.getItemno() == null : this.getItemno().equals(other.getItemno()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getRequestno() == null) ? 0 : getRequestno().hashCode());
        result = prime * result + ((getItemno() == null) ? 0 : getItemno().hashCode());
        return result;
    }
}