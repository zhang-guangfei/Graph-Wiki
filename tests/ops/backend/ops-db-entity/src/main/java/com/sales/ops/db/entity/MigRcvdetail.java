package com.sales.ops.db.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class MigRcvdetail implements Serializable {
    private String rorderno;

    private String rorderitem;

    private String modelno;

    private Integer quantity;

    private BigDecimal price;

    private Date dlvdate;

    private String dlvtype;

    private String optcode;

    private String optstate;

    private String rcvclassify;

    private String stockcode;

    private String cproductno;

    private String expinvcode;

    private String ordtranstype;

    private String specofferno;

    private Date optdate;

    private String prodflag;

    private String specmark;

    private String ororderno;

    private String invflag;

    private Date ndlvdate;

    private Double discount;

    private Date opttime;

    private String operator;

    private String remark;

    private String shikomi;

    private String optrecord;

    private BigDecimal priceEnduser;

    private String smccode;

    private Date dlvdateBj;

    private String cproductname;

    private String presaleorderno;

    private String opponent;

    private BigDecimal eprice;

    private String stockcustomerno;

    private String fullorderno;

    private String invoicegroupkey;

    private String extOrderno;

    private String extOrderItem;

    private String ordersourcesys;

    private Date inserttime;

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

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno == null ? null : modelno.trim();
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getDlvdate() {
        return dlvdate;
    }

    public void setDlvdate(Date dlvdate) {
        this.dlvdate = dlvdate;
    }

    public String getDlvtype() {
        return dlvtype;
    }

    public void setDlvtype(String dlvtype) {
        this.dlvtype = dlvtype == null ? null : dlvtype.trim();
    }

    public String getOptcode() {
        return optcode;
    }

    public void setOptcode(String optcode) {
        this.optcode = optcode == null ? null : optcode.trim();
    }

    public String getOptstate() {
        return optstate;
    }

    public void setOptstate(String optstate) {
        this.optstate = optstate == null ? null : optstate.trim();
    }

    public String getRcvclassify() {
        return rcvclassify;
    }

    public void setRcvclassify(String rcvclassify) {
        this.rcvclassify = rcvclassify == null ? null : rcvclassify.trim();
    }

    public String getStockcode() {
        return stockcode;
    }

    public void setStockcode(String stockcode) {
        this.stockcode = stockcode == null ? null : stockcode.trim();
    }

    public String getCproductno() {
        return cproductno;
    }

    public void setCproductno(String cproductno) {
        this.cproductno = cproductno == null ? null : cproductno.trim();
    }

    public String getExpinvcode() {
        return expinvcode;
    }

    public void setExpinvcode(String expinvcode) {
        this.expinvcode = expinvcode == null ? null : expinvcode.trim();
    }

    public String getOrdtranstype() {
        return ordtranstype;
    }

    public void setOrdtranstype(String ordtranstype) {
        this.ordtranstype = ordtranstype == null ? null : ordtranstype.trim();
    }

    public String getSpecofferno() {
        return specofferno;
    }

    public void setSpecofferno(String specofferno) {
        this.specofferno = specofferno == null ? null : specofferno.trim();
    }

    public Date getOptdate() {
        return optdate;
    }

    public void setOptdate(Date optdate) {
        this.optdate = optdate;
    }

    public String getProdflag() {
        return prodflag;
    }

    public void setProdflag(String prodflag) {
        this.prodflag = prodflag == null ? null : prodflag.trim();
    }

    public String getSpecmark() {
        return specmark;
    }

    public void setSpecmark(String specmark) {
        this.specmark = specmark == null ? null : specmark.trim();
    }

    public String getOrorderno() {
        return ororderno;
    }

    public void setOrorderno(String ororderno) {
        this.ororderno = ororderno == null ? null : ororderno.trim();
    }

    public String getInvflag() {
        return invflag;
    }

    public void setInvflag(String invflag) {
        this.invflag = invflag == null ? null : invflag.trim();
    }

    public Date getNdlvdate() {
        return ndlvdate;
    }

    public void setNdlvdate(Date ndlvdate) {
        this.ndlvdate = ndlvdate;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Date getOpttime() {
        return opttime;
    }

    public void setOpttime(Date opttime) {
        this.opttime = opttime;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getShikomi() {
        return shikomi;
    }

    public void setShikomi(String shikomi) {
        this.shikomi = shikomi == null ? null : shikomi.trim();
    }

    public String getOptrecord() {
        return optrecord;
    }

    public void setOptrecord(String optrecord) {
        this.optrecord = optrecord == null ? null : optrecord.trim();
    }

    public BigDecimal getPriceEnduser() {
        return priceEnduser;
    }

    public void setPriceEnduser(BigDecimal priceEnduser) {
        this.priceEnduser = priceEnduser;
    }

    public String getSmccode() {
        return smccode;
    }

    public void setSmccode(String smccode) {
        this.smccode = smccode == null ? null : smccode.trim();
    }

    public Date getDlvdateBj() {
        return dlvdateBj;
    }

    public void setDlvdateBj(Date dlvdateBj) {
        this.dlvdateBj = dlvdateBj;
    }

    public String getCproductname() {
        return cproductname;
    }

    public void setCproductname(String cproductname) {
        this.cproductname = cproductname == null ? null : cproductname.trim();
    }

    public String getPresaleorderno() {
        return presaleorderno;
    }

    public void setPresaleorderno(String presaleorderno) {
        this.presaleorderno = presaleorderno == null ? null : presaleorderno.trim();
    }

    public String getOpponent() {
        return opponent;
    }

    public void setOpponent(String opponent) {
        this.opponent = opponent == null ? null : opponent.trim();
    }

    public BigDecimal getEprice() {
        return eprice;
    }

    public void setEprice(BigDecimal eprice) {
        this.eprice = eprice;
    }

    public String getStockcustomerno() {
        return stockcustomerno;
    }

    public void setStockcustomerno(String stockcustomerno) {
        this.stockcustomerno = stockcustomerno == null ? null : stockcustomerno.trim();
    }

    public String getFullorderno() {
        return fullorderno;
    }

    public void setFullorderno(String fullorderno) {
        this.fullorderno = fullorderno == null ? null : fullorderno.trim();
    }

    public String getInvoicegroupkey() {
        return invoicegroupkey;
    }

    public void setInvoicegroupkey(String invoicegroupkey) {
        this.invoicegroupkey = invoicegroupkey == null ? null : invoicegroupkey.trim();
    }

    public String getExtOrderno() {
        return extOrderno;
    }

    public void setExtOrderno(String extOrderno) {
        this.extOrderno = extOrderno == null ? null : extOrderno.trim();
    }

    public String getExtOrderItem() {
        return extOrderItem;
    }

    public void setExtOrderItem(String extOrderItem) {
        this.extOrderItem = extOrderItem == null ? null : extOrderItem.trim();
    }

    public String getOrdersourcesys() {
        return ordersourcesys;
    }

    public void setOrdersourcesys(String ordersourcesys) {
        this.ordersourcesys = ordersourcesys == null ? null : ordersourcesys.trim();
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
        MigRcvdetail other = (MigRcvdetail) that;
        return (this.getRorderno() == null ? other.getRorderno() == null : this.getRorderno().equals(other.getRorderno()))
            && (this.getRorderitem() == null ? other.getRorderitem() == null : this.getRorderitem().equals(other.getRorderitem()))
            && (this.getModelno() == null ? other.getModelno() == null : this.getModelno().equals(other.getModelno()))
            && (this.getQuantity() == null ? other.getQuantity() == null : this.getQuantity().equals(other.getQuantity()))
            && (this.getPrice() == null ? other.getPrice() == null : this.getPrice().equals(other.getPrice()))
            && (this.getDlvdate() == null ? other.getDlvdate() == null : this.getDlvdate().equals(other.getDlvdate()))
            && (this.getDlvtype() == null ? other.getDlvtype() == null : this.getDlvtype().equals(other.getDlvtype()))
            && (this.getOptcode() == null ? other.getOptcode() == null : this.getOptcode().equals(other.getOptcode()))
            && (this.getOptstate() == null ? other.getOptstate() == null : this.getOptstate().equals(other.getOptstate()))
            && (this.getRcvclassify() == null ? other.getRcvclassify() == null : this.getRcvclassify().equals(other.getRcvclassify()))
            && (this.getStockcode() == null ? other.getStockcode() == null : this.getStockcode().equals(other.getStockcode()))
            && (this.getCproductno() == null ? other.getCproductno() == null : this.getCproductno().equals(other.getCproductno()))
            && (this.getExpinvcode() == null ? other.getExpinvcode() == null : this.getExpinvcode().equals(other.getExpinvcode()))
            && (this.getOrdtranstype() == null ? other.getOrdtranstype() == null : this.getOrdtranstype().equals(other.getOrdtranstype()))
            && (this.getSpecofferno() == null ? other.getSpecofferno() == null : this.getSpecofferno().equals(other.getSpecofferno()))
            && (this.getOptdate() == null ? other.getOptdate() == null : this.getOptdate().equals(other.getOptdate()))
            && (this.getProdflag() == null ? other.getProdflag() == null : this.getProdflag().equals(other.getProdflag()))
            && (this.getSpecmark() == null ? other.getSpecmark() == null : this.getSpecmark().equals(other.getSpecmark()))
            && (this.getOrorderno() == null ? other.getOrorderno() == null : this.getOrorderno().equals(other.getOrorderno()))
            && (this.getInvflag() == null ? other.getInvflag() == null : this.getInvflag().equals(other.getInvflag()))
            && (this.getNdlvdate() == null ? other.getNdlvdate() == null : this.getNdlvdate().equals(other.getNdlvdate()))
            && (this.getDiscount() == null ? other.getDiscount() == null : this.getDiscount().equals(other.getDiscount()))
            && (this.getOpttime() == null ? other.getOpttime() == null : this.getOpttime().equals(other.getOpttime()))
            && (this.getOperator() == null ? other.getOperator() == null : this.getOperator().equals(other.getOperator()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getShikomi() == null ? other.getShikomi() == null : this.getShikomi().equals(other.getShikomi()))
            && (this.getOptrecord() == null ? other.getOptrecord() == null : this.getOptrecord().equals(other.getOptrecord()))
            && (this.getPriceEnduser() == null ? other.getPriceEnduser() == null : this.getPriceEnduser().equals(other.getPriceEnduser()))
            && (this.getSmccode() == null ? other.getSmccode() == null : this.getSmccode().equals(other.getSmccode()))
            && (this.getDlvdateBj() == null ? other.getDlvdateBj() == null : this.getDlvdateBj().equals(other.getDlvdateBj()))
            && (this.getCproductname() == null ? other.getCproductname() == null : this.getCproductname().equals(other.getCproductname()))
            && (this.getPresaleorderno() == null ? other.getPresaleorderno() == null : this.getPresaleorderno().equals(other.getPresaleorderno()))
            && (this.getOpponent() == null ? other.getOpponent() == null : this.getOpponent().equals(other.getOpponent()))
            && (this.getEprice() == null ? other.getEprice() == null : this.getEprice().equals(other.getEprice()))
            && (this.getStockcustomerno() == null ? other.getStockcustomerno() == null : this.getStockcustomerno().equals(other.getStockcustomerno()))
            && (this.getFullorderno() == null ? other.getFullorderno() == null : this.getFullorderno().equals(other.getFullorderno()))
            && (this.getInvoicegroupkey() == null ? other.getInvoicegroupkey() == null : this.getInvoicegroupkey().equals(other.getInvoicegroupkey()))
            && (this.getExtOrderno() == null ? other.getExtOrderno() == null : this.getExtOrderno().equals(other.getExtOrderno()))
            && (this.getExtOrderItem() == null ? other.getExtOrderItem() == null : this.getExtOrderItem().equals(other.getExtOrderItem()))
            && (this.getOrdersourcesys() == null ? other.getOrdersourcesys() == null : this.getOrdersourcesys().equals(other.getOrdersourcesys()))
            && (this.getInserttime() == null ? other.getInserttime() == null : this.getInserttime().equals(other.getInserttime()));
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
        result = prime * result + ((getOptcode() == null) ? 0 : getOptcode().hashCode());
        result = prime * result + ((getOptstate() == null) ? 0 : getOptstate().hashCode());
        result = prime * result + ((getRcvclassify() == null) ? 0 : getRcvclassify().hashCode());
        result = prime * result + ((getStockcode() == null) ? 0 : getStockcode().hashCode());
        result = prime * result + ((getCproductno() == null) ? 0 : getCproductno().hashCode());
        result = prime * result + ((getExpinvcode() == null) ? 0 : getExpinvcode().hashCode());
        result = prime * result + ((getOrdtranstype() == null) ? 0 : getOrdtranstype().hashCode());
        result = prime * result + ((getSpecofferno() == null) ? 0 : getSpecofferno().hashCode());
        result = prime * result + ((getOptdate() == null) ? 0 : getOptdate().hashCode());
        result = prime * result + ((getProdflag() == null) ? 0 : getProdflag().hashCode());
        result = prime * result + ((getSpecmark() == null) ? 0 : getSpecmark().hashCode());
        result = prime * result + ((getOrorderno() == null) ? 0 : getOrorderno().hashCode());
        result = prime * result + ((getInvflag() == null) ? 0 : getInvflag().hashCode());
        result = prime * result + ((getNdlvdate() == null) ? 0 : getNdlvdate().hashCode());
        result = prime * result + ((getDiscount() == null) ? 0 : getDiscount().hashCode());
        result = prime * result + ((getOpttime() == null) ? 0 : getOpttime().hashCode());
        result = prime * result + ((getOperator() == null) ? 0 : getOperator().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getShikomi() == null) ? 0 : getShikomi().hashCode());
        result = prime * result + ((getOptrecord() == null) ? 0 : getOptrecord().hashCode());
        result = prime * result + ((getPriceEnduser() == null) ? 0 : getPriceEnduser().hashCode());
        result = prime * result + ((getSmccode() == null) ? 0 : getSmccode().hashCode());
        result = prime * result + ((getDlvdateBj() == null) ? 0 : getDlvdateBj().hashCode());
        result = prime * result + ((getCproductname() == null) ? 0 : getCproductname().hashCode());
        result = prime * result + ((getPresaleorderno() == null) ? 0 : getPresaleorderno().hashCode());
        result = prime * result + ((getOpponent() == null) ? 0 : getOpponent().hashCode());
        result = prime * result + ((getEprice() == null) ? 0 : getEprice().hashCode());
        result = prime * result + ((getStockcustomerno() == null) ? 0 : getStockcustomerno().hashCode());
        result = prime * result + ((getFullorderno() == null) ? 0 : getFullorderno().hashCode());
        result = prime * result + ((getInvoicegroupkey() == null) ? 0 : getInvoicegroupkey().hashCode());
        result = prime * result + ((getExtOrderno() == null) ? 0 : getExtOrderno().hashCode());
        result = prime * result + ((getExtOrderItem() == null) ? 0 : getExtOrderItem().hashCode());
        result = prime * result + ((getOrdersourcesys() == null) ? 0 : getOrdersourcesys().hashCode());
        result = prime * result + ((getInserttime() == null) ? 0 : getInserttime().hashCode());
        return result;
    }
}