package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class OpsReqPoMapping extends OpsReqPoMappingKey implements Serializable {
    private String requestorderno;

    private Integer requestitemno;

    private Integer requestsplititemno;

    private Date updatetime;

    private static final long serialVersionUID = 1L;

    public String getRequestorderno() {
        return requestorderno;
    }

    public void setRequestorderno(String requestorderno) {
        this.requestorderno = requestorderno == null ? null : requestorderno.trim();
    }

    public Integer getRequestitemno() {
        return requestitemno;
    }

    public void setRequestitemno(Integer requestitemno) {
        this.requestitemno = requestitemno;
    }

    public Integer getRequestsplititemno() {
        return requestsplititemno;
    }

    public void setRequestsplititemno(Integer requestsplititemno) {
        this.requestsplititemno = requestsplititemno;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
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
        OpsReqPoMapping other = (OpsReqPoMapping) that;
        return (this.getRequestpurchaseid() == null ? other.getRequestpurchaseid() == null : this.getRequestpurchaseid().equals(other.getRequestpurchaseid()))
            && (this.getPurchaseorderno() == null ? other.getPurchaseorderno() == null : this.getPurchaseorderno().equals(other.getPurchaseorderno()))
            && (this.getRequestorderno() == null ? other.getRequestorderno() == null : this.getRequestorderno().equals(other.getRequestorderno()))
            && (this.getRequestitemno() == null ? other.getRequestitemno() == null : this.getRequestitemno().equals(other.getRequestitemno()))
            && (this.getRequestsplititemno() == null ? other.getRequestsplititemno() == null : this.getRequestsplititemno().equals(other.getRequestsplititemno()))
            && (this.getUpdatetime() == null ? other.getUpdatetime() == null : this.getUpdatetime().equals(other.getUpdatetime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getRequestpurchaseid() == null) ? 0 : getRequestpurchaseid().hashCode());
        result = prime * result + ((getPurchaseorderno() == null) ? 0 : getPurchaseorderno().hashCode());
        result = prime * result + ((getRequestorderno() == null) ? 0 : getRequestorderno().hashCode());
        result = prime * result + ((getRequestitemno() == null) ? 0 : getRequestitemno().hashCode());
        result = prime * result + ((getRequestsplititemno() == null) ? 0 : getRequestsplititemno().hashCode());
        result = prime * result + ((getUpdatetime() == null) ? 0 : getUpdatetime().hashCode());
        return result;
    }
}