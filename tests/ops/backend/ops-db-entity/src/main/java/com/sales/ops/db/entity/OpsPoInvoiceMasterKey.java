package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class OpsPoInvoiceMasterKey implements Serializable {
    private String invoiceno;

    private Date impdate;

    private static final long serialVersionUID = 1L;

    public String getInvoiceno() {
        return invoiceno;
    }

    public void setInvoiceno(String invoiceno) {
        this.invoiceno = invoiceno == null ? null : invoiceno.trim();
    }

    public Date getImpdate() {
        return impdate;
    }

    public void setImpdate(Date impdate) {
        this.impdate = impdate;
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
        OpsPoInvoiceMasterKey other = (OpsPoInvoiceMasterKey) that;
        return (this.getInvoiceno() == null ? other.getInvoiceno() == null : this.getInvoiceno().equals(other.getInvoiceno()))
            && (this.getImpdate() == null ? other.getImpdate() == null : this.getImpdate().equals(other.getImpdate()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getInvoiceno() == null) ? 0 : getInvoiceno().hashCode());
        result = prime * result + ((getImpdate() == null) ? 0 : getImpdate().hashCode());
        return result;
    }
}