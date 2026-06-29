package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class OpsPurchaseinvoicetranslog implements Serializable {
    private Long id;

    private String pono;

    private Integer lineitem;

    private Long invoiceid;

    private String invoiceno;

    private Integer quantity;

    private String srcType;

    private Date inserttime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPono() {
        return pono;
    }

    public void setPono(String pono) {
        this.pono = pono == null ? null : pono.trim();
    }

    public Integer getLineitem() {
        return lineitem;
    }

    public void setLineitem(Integer lineitem) {
        this.lineitem = lineitem;
    }

    public Long getInvoiceid() {
        return invoiceid;
    }

    public void setInvoiceid(Long invoiceid) {
        this.invoiceid = invoiceid;
    }

    public String getInvoiceno() {
        return invoiceno;
    }

    public void setInvoiceno(String invoiceno) {
        this.invoiceno = invoiceno == null ? null : invoiceno.trim();
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getSrcType() {
        return srcType;
    }

    public void setSrcType(String srcType) {
        this.srcType = srcType == null ? null : srcType.trim();
    }

    public Date getInserttime() {
        return inserttime;
    }

    public void setInserttime(Date inserttime) {
        this.inserttime = inserttime;
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
        OpsPurchaseinvoicetranslog other = (OpsPurchaseinvoicetranslog) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getPono() == null ? other.getPono() == null : this.getPono().equals(other.getPono()))
            && (this.getLineitem() == null ? other.getLineitem() == null : this.getLineitem().equals(other.getLineitem()))
            && (this.getInvoiceid() == null ? other.getInvoiceid() == null : this.getInvoiceid().equals(other.getInvoiceid()))
            && (this.getInvoiceno() == null ? other.getInvoiceno() == null : this.getInvoiceno().equals(other.getInvoiceno()))
            && (this.getQuantity() == null ? other.getQuantity() == null : this.getQuantity().equals(other.getQuantity()))
            && (this.getSrcType() == null ? other.getSrcType() == null : this.getSrcType().equals(other.getSrcType()))
            && (this.getInserttime() == null ? other.getInserttime() == null : this.getInserttime().equals(other.getInserttime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getPono() == null) ? 0 : getPono().hashCode());
        result = prime * result + ((getLineitem() == null) ? 0 : getLineitem().hashCode());
        result = prime * result + ((getInvoiceid() == null) ? 0 : getInvoiceid().hashCode());
        result = prime * result + ((getInvoiceno() == null) ? 0 : getInvoiceno().hashCode());
        result = prime * result + ((getQuantity() == null) ? 0 : getQuantity().hashCode());
        result = prime * result + ((getSrcType() == null) ? 0 : getSrcType().hashCode());
        result = prime * result + ((getInserttime() == null) ? 0 : getInserttime().hashCode());
        return result;
    }
}