package com.sales.ops.db.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class RcvdetailGz implements Serializable {
    private String rorderno;

    private String rorderitem;

    private String modelno;

    private Integer quantity;

    private BigDecimal price;

    private Date dlvdate;

    private String dlvtype;

    private String rcvclassify;

    private String stockcode;

    private String ordtranstype;

    private String specofferno;

    private String cproductno;

    private Double discount;

    private String specmark;

    private String prodflag;

    private String expinvcode;

    private Date deptDlvdate;

    private Date custDlvdate;

    private String optcode;

    private String optstate;

    private Date optdate;

    private String operator;

    private Date opttime;

    private String remark;

    private String dlvaddress;

    private BigDecimal agentsalesprice;

    private String stockcustomerno;

    private Date shipdate;

    private Integer expdlvtype;

    private String dlvaddressid;

    private BigDecimal eprice;

    private BigDecimal taxrate;

    private BigDecimal ntaxamount;

    private Date exptime;

    private Date readytime;

    private String ownercode;

    private String detailmark;

    private Date signtime;

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

    public String getCproductno() {
        return cproductno;
    }

    public void setCproductno(String cproductno) {
        this.cproductno = cproductno == null ? null : cproductno.trim();
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public String getSpecmark() {
        return specmark;
    }

    public void setSpecmark(String specmark) {
        this.specmark = specmark == null ? null : specmark.trim();
    }

    public String getProdflag() {
        return prodflag;
    }

    public void setProdflag(String prodflag) {
        this.prodflag = prodflag == null ? null : prodflag.trim();
    }

    public String getExpinvcode() {
        return expinvcode;
    }

    public void setExpinvcode(String expinvcode) {
        this.expinvcode = expinvcode == null ? null : expinvcode.trim();
    }

    public Date getDeptDlvdate() {
        return deptDlvdate;
    }

    public void setDeptDlvdate(Date deptDlvdate) {
        this.deptDlvdate = deptDlvdate;
    }

    public Date getCustDlvdate() {
        return custDlvdate;
    }

    public void setCustDlvdate(Date custDlvdate) {
        this.custDlvdate = custDlvdate;
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

    public Date getOptdate() {
        return optdate;
    }

    public void setOptdate(Date optdate) {
        this.optdate = optdate;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public Date getOpttime() {
        return opttime;
    }

    public void setOpttime(Date opttime) {
        this.opttime = opttime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getDlvaddress() {
        return dlvaddress;
    }

    public void setDlvaddress(String dlvaddress) {
        this.dlvaddress = dlvaddress == null ? null : dlvaddress.trim();
    }

    public BigDecimal getAgentsalesprice() {
        return agentsalesprice;
    }

    public void setAgentsalesprice(BigDecimal agentsalesprice) {
        this.agentsalesprice = agentsalesprice;
    }

    public String getStockcustomerno() {
        return stockcustomerno;
    }

    public void setStockcustomerno(String stockcustomerno) {
        this.stockcustomerno = stockcustomerno == null ? null : stockcustomerno.trim();
    }

    public Date getShipdate() {
        return shipdate;
    }

    public void setShipdate(Date shipdate) {
        this.shipdate = shipdate;
    }

    public Integer getExpdlvtype() {
        return expdlvtype;
    }

    public void setExpdlvtype(Integer expdlvtype) {
        this.expdlvtype = expdlvtype;
    }

    public String getDlvaddressid() {
        return dlvaddressid;
    }

    public void setDlvaddressid(String dlvaddressid) {
        this.dlvaddressid = dlvaddressid == null ? null : dlvaddressid.trim();
    }

    public BigDecimal getEprice() {
        return eprice;
    }

    public void setEprice(BigDecimal eprice) {
        this.eprice = eprice;
    }

    public BigDecimal getTaxrate() {
        return taxrate;
    }

    public void setTaxrate(BigDecimal taxrate) {
        this.taxrate = taxrate;
    }

    public BigDecimal getNtaxamount() {
        return ntaxamount;
    }

    public void setNtaxamount(BigDecimal ntaxamount) {
        this.ntaxamount = ntaxamount;
    }

    public Date getExptime() {
        return exptime;
    }

    public void setExptime(Date exptime) {
        this.exptime = exptime;
    }

    public Date getReadytime() {
        return readytime;
    }

    public void setReadytime(Date readytime) {
        this.readytime = readytime;
    }

    public String getOwnercode() {
        return ownercode;
    }

    public void setOwnercode(String ownercode) {
        this.ownercode = ownercode == null ? null : ownercode.trim();
    }

    public String getDetailmark() {
        return detailmark;
    }

    public void setDetailmark(String detailmark) {
        this.detailmark = detailmark == null ? null : detailmark.trim();
    }

    public Date getSigntime() {
        return signtime;
    }

    public void setSigntime(Date signtime) {
        this.signtime = signtime;
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
        RcvdetailGz other = (RcvdetailGz) that;
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
            && (this.getSigntime() == null ? other.getSigntime() == null : this.getSigntime().equals(other.getSigntime()));
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
        return result;
    }
}