package com.sales.ops.db.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class Groupcompanypricerule implements Serializable {
    private String sellergroupid;

    private String buyergroupid;

    private String productsourcetypeid;

    private BigDecimal erate;

    private String ruledesc;

    private static final long serialVersionUID = 1L;

    public String getSellergroupid() {
        return sellergroupid;
    }

    public void setSellergroupid(String sellergroupid) {
        this.sellergroupid = sellergroupid == null ? null : sellergroupid.trim();
    }

    public String getBuyergroupid() {
        return buyergroupid;
    }

    public void setBuyergroupid(String buyergroupid) {
        this.buyergroupid = buyergroupid == null ? null : buyergroupid.trim();
    }

    public String getProductsourcetypeid() {
        return productsourcetypeid;
    }

    public void setProductsourcetypeid(String productsourcetypeid) {
        this.productsourcetypeid = productsourcetypeid == null ? null : productsourcetypeid.trim();
    }

    public BigDecimal getErate() {
        return erate;
    }

    public void setErate(BigDecimal erate) {
        this.erate = erate;
    }

    public String getRuledesc() {
        return ruledesc;
    }

    public void setRuledesc(String ruledesc) {
        this.ruledesc = ruledesc == null ? null : ruledesc.trim();
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
        Groupcompanypricerule other = (Groupcompanypricerule) that;
        return (this.getSellergroupid() == null ? other.getSellergroupid() == null : this.getSellergroupid().equals(other.getSellergroupid()))
            && (this.getBuyergroupid() == null ? other.getBuyergroupid() == null : this.getBuyergroupid().equals(other.getBuyergroupid()))
            && (this.getProductsourcetypeid() == null ? other.getProductsourcetypeid() == null : this.getProductsourcetypeid().equals(other.getProductsourcetypeid()))
            && (this.getErate() == null ? other.getErate() == null : this.getErate().equals(other.getErate()))
            && (this.getRuledesc() == null ? other.getRuledesc() == null : this.getRuledesc().equals(other.getRuledesc()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getSellergroupid() == null) ? 0 : getSellergroupid().hashCode());
        result = prime * result + ((getBuyergroupid() == null) ? 0 : getBuyergroupid().hashCode());
        result = prime * result + ((getProductsourcetypeid() == null) ? 0 : getProductsourcetypeid().hashCode());
        result = prime * result + ((getErate() == null) ? 0 : getErate().hashCode());
        result = prime * result + ((getRuledesc() == null) ? 0 : getRuledesc().hashCode());
        return result;
    }
}