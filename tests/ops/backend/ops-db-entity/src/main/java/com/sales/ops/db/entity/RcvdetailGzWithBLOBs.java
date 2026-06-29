package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Arrays;

public class RcvdetailGzWithBLOBs extends RcvdetailGz implements Serializable {
    private String optrecord;

    private byte[] rv;

    private static final long serialVersionUID = 1L;

    public String getOptrecord() {
        return optrecord;
    }

    public void setOptrecord(String optrecord) {
        this.optrecord = optrecord == null ? null : optrecord.trim();
    }

    public byte[] getRv() {
        return rv;
    }

    public void setRv(byte[] rv) {
        this.rv = rv;
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
        RcvdetailGzWithBLOBs other = (RcvdetailGzWithBLOBs) that;
        return (this.getRorderno() == null ? other.getRorderno() == null : this.getRorderno().equals(other.getRorderno()))
            && (this.getRorderitem() == null ? other.getRorderitem() == null : this.getRorderitem().equals(other.getRorderitem()))
            && (this.getModelno() == null ? other.getModelno() == null : this.getModelno().equals(other.getModelno()))
            && (this.getQuantity() == null ? other.getQuantity() == null : this.getQuantity().equals(other.getQuantity()))
            && (this.getPrice() == null ? other.getPrice() == null : this.getPrice().equals(other.getPrice()))
            && (this.getDlvdate() == null ? other.getDlvdate() == null : this.getDlvdate().equals(other.getDlvdate()))
            && (this.getDlvtype() == null ? other.getDlvtype() == null : this.getDlvtype().equals(other.getDlvtype()))
            && (this.getRcvclassify() == null ? other.getRcvclassify() == null : this.getRcvclassify().equals(other.getRcvclassify()))
            && (this.getStockcode() == null ? other.getStockcode() == null : this.getStockcode().equals(other.getStockcode()))
            && (this.getOrdtranstype() == null ? other.getOrdtranstype() == null : this.getOrdtranstype().equals(other.getOrdtranstype()))
            && (this.getSpecofferno() == null ? other.getSpecofferno() == null : this.getSpecofferno().equals(other.getSpecofferno()))
            && (this.getCproductno() == null ? other.getCproductno() == null : this.getCproductno().equals(other.getCproductno()))
            && (this.getDiscount() == null ? other.getDiscount() == null : this.getDiscount().equals(other.getDiscount()))
            && (this.getSpecmark() == null ? other.getSpecmark() == null : this.getSpecmark().equals(other.getSpecmark()))
            && (this.getProdflag() == null ? other.getProdflag() == null : this.getProdflag().equals(other.getProdflag()))
            && (this.getExpinvcode() == null ? other.getExpinvcode() == null : this.getExpinvcode().equals(other.getExpinvcode()))
            && (this.getDeptDlvdate() == null ? other.getDeptDlvdate() == null : this.getDeptDlvdate().equals(other.getDeptDlvdate()))
            && (this.getCustDlvdate() == null ? other.getCustDlvdate() == null : this.getCustDlvdate().equals(other.getCustDlvdate()))
            && (this.getOptcode() == null ? other.getOptcode() == null : this.getOptcode().equals(other.getOptcode()))
            && (this.getOptstate() == null ? other.getOptstate() == null : this.getOptstate().equals(other.getOptstate()))
            && (this.getOptdate() == null ? other.getOptdate() == null : this.getOptdate().equals(other.getOptdate()))
            && (this.getOperator() == null ? other.getOperator() == null : this.getOperator().equals(other.getOperator()))
            && (this.getOpttime() == null ? other.getOpttime() == null : this.getOpttime().equals(other.getOpttime()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getDlvaddress() == null ? other.getDlvaddress() == null : this.getDlvaddress().equals(other.getDlvaddress()))
            && (this.getAgentsalesprice() == null ? other.getAgentsalesprice() == null : this.getAgentsalesprice().equals(other.getAgentsalesprice()))
            && (this.getStockcustomerno() == null ? other.getStockcustomerno() == null : this.getStockcustomerno().equals(other.getStockcustomerno()))
            && (this.getShipdate() == null ? other.getShipdate() == null : this.getShipdate().equals(other.getShipdate()))
            && (this.getExpdlvtype() == null ? other.getExpdlvtype() == null : this.getExpdlvtype().equals(other.getExpdlvtype()))
            && (this.getDlvaddressid() == null ? other.getDlvaddressid() == null : this.getDlvaddressid().equals(other.getDlvaddressid()))
            && (this.getEprice() == null ? other.getEprice() == null : this.getEprice().equals(other.getEprice()))
            && (this.getTaxrate() == null ? other.getTaxrate() == null : this.getTaxrate().equals(other.getTaxrate()))
            && (this.getNtaxamount() == null ? other.getNtaxamount() == null : this.getNtaxamount().equals(other.getNtaxamount()))
            && (this.getExptime() == null ? other.getExptime() == null : this.getExptime().equals(other.getExptime()))
            && (this.getReadytime() == null ? other.getReadytime() == null : this.getReadytime().equals(other.getReadytime()))
            && (this.getOwnercode() == null ? other.getOwnercode() == null : this.getOwnercode().equals(other.getOwnercode()))
            && (this.getDetailmark() == null ? other.getDetailmark() == null : this.getDetailmark().equals(other.getDetailmark()))
            && (this.getSigntime() == null ? other.getSigntime() == null : this.getSigntime().equals(other.getSigntime()))
            && (this.getOptrecord() == null ? other.getOptrecord() == null : this.getOptrecord().equals(other.getOptrecord()))
            && (Arrays.equals(this.getRv(), other.getRv()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getRorderno() == null) ? 0 : getRorderno().hashCode());
        result = prime * result + ((getRorderitem() == null) ? 0 : getRorderitem().hashCode());
        result = prime * result + ((getModelno() == null) ? 0 : getModelno().hashCode());
        result = prime * result + ((getQuantity() == null) ? 0 : getQuantity().hashCode());
        result = prime * result + ((getPrice() == null) ? 0 : getPrice().hashCode());
        result = prime * result + ((getDlvdate() == null) ? 0 : getDlvdate().hashCode());
        result = prime * result + ((getDlvtype() == null) ? 0 : getDlvtype().hashCode());
        result = prime * result + ((getRcvclassify() == null) ? 0 : getRcvclassify().hashCode());
        result = prime * result + ((getStockcode() == null) ? 0 : getStockcode().hashCode());
        result = prime * result + ((getOrdtranstype() == null) ? 0 : getOrdtranstype().hashCode());
        result = prime * result + ((getSpecofferno() == null) ? 0 : getSpecofferno().hashCode());
        result = prime * result + ((getCproductno() == null) ? 0 : getCproductno().hashCode());
        result = prime * result + ((getDiscount() == null) ? 0 : getDiscount().hashCode());
        result = prime * result + ((getSpecmark() == null) ? 0 : getSpecmark().hashCode());
        result = prime * result + ((getProdflag() == null) ? 0 : getProdflag().hashCode());
        result = prime * result + ((getExpinvcode() == null) ? 0 : getExpinvcode().hashCode());
        result = prime * result + ((getDeptDlvdate() == null) ? 0 : getDeptDlvdate().hashCode());
        result = prime * result + ((getCustDlvdate() == null) ? 0 : getCustDlvdate().hashCode());
        result = prime * result + ((getOptcode() == null) ? 0 : getOptcode().hashCode());
        result = prime * result + ((getOptstate() == null) ? 0 : getOptstate().hashCode());
        result = prime * result + ((getOptdate() == null) ? 0 : getOptdate().hashCode());
        result = prime * result + ((getOperator() == null) ? 0 : getOperator().hashCode());
        result = prime * result + ((getOpttime() == null) ? 0 : getOpttime().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getDlvaddress() == null) ? 0 : getDlvaddress().hashCode());
        result = prime * result + ((getAgentsalesprice() == null) ? 0 : getAgentsalesprice().hashCode());
        result = prime * result + ((getStockcustomerno() == null) ? 0 : getStockcustomerno().hashCode());
        result = prime * result + ((getShipdate() == null) ? 0 : getShipdate().hashCode());
        result = prime * result + ((getExpdlvtype() == null) ? 0 : getExpdlvtype().hashCode());
        result = prime * result + ((getDlvaddressid() == null) ? 0 : getDlvaddressid().hashCode());
        result = prime * result + ((getEprice() == null) ? 0 : getEprice().hashCode());
        result = prime * result + ((getTaxrate() == null) ? 0 : getTaxrate().hashCode());
        result = prime * result + ((getNtaxamount() == null) ? 0 : getNtaxamount().hashCode());
        result = prime * result + ((getExptime() == null) ? 0 : getExptime().hashCode());
        result = prime * result + ((getReadytime() == null) ? 0 : getReadytime().hashCode());
        result = prime * result + ((getOwnercode() == null) ? 0 : getOwnercode().hashCode());
        result = prime * result + ((getDetailmark() == null) ? 0 : getDetailmark().hashCode());
        result = prime * result + ((getSigntime() == null) ? 0 : getSigntime().hashCode());
        result = prime * result + ((getOptrecord() == null) ? 0 : getOptrecord().hashCode());
        result = prime * result + (Arrays.hashCode(getRv()));
        return result;
    }
}