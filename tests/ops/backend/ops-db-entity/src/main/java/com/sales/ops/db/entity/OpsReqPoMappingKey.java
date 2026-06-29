package com.sales.ops.db.entity;

import java.io.Serializable;

public class OpsReqPoMappingKey implements Serializable {
    private Long requestpurchaseid;

    private String purchaseorderno;

    private static final long serialVersionUID = 1L;

    public Long getRequestpurchaseid() {
        return requestpurchaseid;
    }

    public void setRequestpurchaseid(Long requestpurchaseid) {
        this.requestpurchaseid = requestpurchaseid;
    }

    public String getPurchaseorderno() {
        return purchaseorderno;
    }

    public void setPurchaseorderno(String purchaseorderno) {
        this.purchaseorderno = purchaseorderno == null ? null : purchaseorderno.trim();
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
        OpsReqPoMappingKey other = (OpsReqPoMappingKey) that;
        return (this.getRequestpurchaseid() == null ? other.getRequestpurchaseid() == null : this.getRequestpurchaseid().equals(other.getRequestpurchaseid()))
            && (this.getPurchaseorderno() == null ? other.getPurchaseorderno() == null : this.getPurchaseorderno().equals(other.getPurchaseorderno()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getRequestpurchaseid() == null) ? 0 : getRequestpurchaseid().hashCode());
        result = prime * result + ((getPurchaseorderno() == null) ? 0 : getPurchaseorderno().hashCode());
        return result;
    }
}