package com.sales.ops.db.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OrdersalesBj implements Serializable {
    private String rorderno;

    private String customerno;

    private String userno;

    private String dlventire;

    private String transfee;

    private String transchannel;

    private String dlvsite;

    private String dlvtype;

    private String contractno;

    private String quotationno;

    private String remark;

    private String oplog;

    private Date workday;

    private String sendout;

    private String status;

    private Date sendday;

    private Short rorderitem;

    private String modelno;

    private Integer quantity;

    private BigDecimal price;

    private Date dlvdate;

    private String rcvclassify;

    private String prjcode;

    private String stockcode;

    private String cproductno;

    private String ordtranstype;

    private String spcprice;

    private BigDecimal discount;

    private String noprice;

    private BigDecimal account;

    private String specmark;

    private String recno;

    private String detailmark;

    private Date optdate;

    private String rowguid;

    private String empsale;

    private String empoffice;

    private String deptno;

    private String purchaseno;

    private String shikomi;

    private BigDecimal priceEnduser;

    private String cttflag;

    private Date warehousesenddate;

    private String cproductname;

    private String optrecord;

    private String presaleorderno;

    private String opponent;

    private String typecode;

    private Boolean pricechecked;

    private BigDecimal eprice;

    private String tradecompanyid;

    private String ordersourcesys;

    private static final long serialVersionUID = 1L;

    public String getRorderno() {
        return rorderno;
    }

    public void setRorderno(String rorderno) {
        this.rorderno = rorderno == null ? null : rorderno.trim();
    }

    public String getCustomerno() {
        return customerno;
    }

    public void setCustomerno(String customerno) {
        this.customerno = customerno == null ? null : customerno.trim();
    }

    public String getUserno() {
        return userno;
    }

    public void setUserno(String userno) {
        this.userno = userno == null ? null : userno.trim();
    }

    public String getDlventire() {
        return dlventire;
    }

    public void setDlventire(String dlventire) {
        this.dlventire = dlventire == null ? null : dlventire.trim();
    }

    public String getTransfee() {
        return transfee;
    }

    public void setTransfee(String transfee) {
        this.transfee = transfee == null ? null : transfee.trim();
    }

    public String getTranschannel() {
        return transchannel;
    }

    public void setTranschannel(String transchannel) {
        this.transchannel = transchannel == null ? null : transchannel.trim();
    }

    public String getDlvsite() {
        return dlvsite;
    }

    public void setDlvsite(String dlvsite) {
        this.dlvsite = dlvsite == null ? null : dlvsite.trim();
    }

    public String getDlvtype() {
        return dlvtype;
    }

    public void setDlvtype(String dlvtype) {
        this.dlvtype = dlvtype == null ? null : dlvtype.trim();
    }

    public String getContractno() {
        return contractno;
    }

    public void setContractno(String contractno) {
        this.contractno = contractno == null ? null : contractno.trim();
    }

    public String getQuotationno() {
        return quotationno;
    }

    public void setQuotationno(String quotationno) {
        this.quotationno = quotationno == null ? null : quotationno.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getOplog() {
        return oplog;
    }

    public void setOplog(String oplog) {
        this.oplog = oplog == null ? null : oplog.trim();
    }

    public Date getWorkday() {
        return workday;
    }

    public void setWorkday(Date workday) {
        this.workday = workday;
    }

    public String getSendout() {
        return sendout;
    }

    public void setSendout(String sendout) {
        this.sendout = sendout == null ? null : sendout.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getSendday() {
        return sendday;
    }

    public void setSendday(Date sendday) {
        this.sendday = sendday;
    }

    public Short getRorderitem() {
        return rorderitem;
    }

    public void setRorderitem(Short rorderitem) {
        this.rorderitem = rorderitem;
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

    public String getRcvclassify() {
        return rcvclassify;
    }

    public void setRcvclassify(String rcvclassify) {
        this.rcvclassify = rcvclassify == null ? null : rcvclassify.trim();
    }

    public String getPrjcode() {
        return prjcode;
    }

    public void setPrjcode(String prjcode) {
        this.prjcode = prjcode == null ? null : prjcode.trim();
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

    public String getOrdtranstype() {
        return ordtranstype;
    }

    public void setOrdtranstype(String ordtranstype) {
        this.ordtranstype = ordtranstype == null ? null : ordtranstype.trim();
    }

    public String getSpcprice() {
        return spcprice;
    }

    public void setSpcprice(String spcprice) {
        this.spcprice = spcprice == null ? null : spcprice.trim();
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public String getNoprice() {
        return noprice;
    }

    public void setNoprice(String noprice) {
        this.noprice = noprice == null ? null : noprice.trim();
    }

    public BigDecimal getAccount() {
        return account;
    }

    public void setAccount(BigDecimal account) {
        this.account = account;
    }

    public String getSpecmark() {
        return specmark;
    }

    public void setSpecmark(String specmark) {
        this.specmark = specmark == null ? null : specmark.trim();
    }

    public String getRecno() {
        return recno;
    }

    public void setRecno(String recno) {
        this.recno = recno == null ? null : recno.trim();
    }

    public String getDetailmark() {
        return detailmark;
    }

    public void setDetailmark(String detailmark) {
        this.detailmark = detailmark == null ? null : detailmark.trim();
    }

    public Date getOptdate() {
        return optdate;
    }

    public void setOptdate(Date optdate) {
        this.optdate = optdate;
    }

    public String getRowguid() {
        return rowguid;
    }

    public void setRowguid(String rowguid) {
        this.rowguid = rowguid == null ? null : rowguid.trim();
    }

    public String getEmpsale() {
        return empsale;
    }

    public void setEmpsale(String empsale) {
        this.empsale = empsale == null ? null : empsale.trim();
    }

    public String getEmpoffice() {
        return empoffice;
    }

    public void setEmpoffice(String empoffice) {
        this.empoffice = empoffice == null ? null : empoffice.trim();
    }

    public String getDeptno() {
        return deptno;
    }

    public void setDeptno(String deptno) {
        this.deptno = deptno == null ? null : deptno.trim();
    }

    public String getPurchaseno() {
        return purchaseno;
    }

    public void setPurchaseno(String purchaseno) {
        this.purchaseno = purchaseno == null ? null : purchaseno.trim();
    }

    public String getShikomi() {
        return shikomi;
    }

    public void setShikomi(String shikomi) {
        this.shikomi = shikomi == null ? null : shikomi.trim();
    }

    public BigDecimal getPriceEnduser() {
        return priceEnduser;
    }

    public void setPriceEnduser(BigDecimal priceEnduser) {
        this.priceEnduser = priceEnduser;
    }

    public String getCttflag() {
        return cttflag;
    }

    public void setCttflag(String cttflag) {
        this.cttflag = cttflag == null ? null : cttflag.trim();
    }

    public Date getWarehousesenddate() {
        return warehousesenddate;
    }

    public void setWarehousesenddate(Date warehousesenddate) {
        this.warehousesenddate = warehousesenddate;
    }

    public String getCproductname() {
        return cproductname;
    }

    public void setCproductname(String cproductname) {
        this.cproductname = cproductname == null ? null : cproductname.trim();
    }

    public String getOptrecord() {
        return optrecord;
    }

    public void setOptrecord(String optrecord) {
        this.optrecord = optrecord == null ? null : optrecord.trim();
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

    public String getTypecode() {
        return typecode;
    }

    public void setTypecode(String typecode) {
        this.typecode = typecode == null ? null : typecode.trim();
    }

    public Boolean getPricechecked() {
        return pricechecked;
    }

    public void setPricechecked(Boolean pricechecked) {
        this.pricechecked = pricechecked;
    }

    public BigDecimal getEprice() {
        return eprice;
    }

    public void setEprice(BigDecimal eprice) {
        this.eprice = eprice;
    }

    public String getTradecompanyid() {
        return tradecompanyid;
    }

    public void setTradecompanyid(String tradecompanyid) {
        this.tradecompanyid = tradecompanyid == null ? null : tradecompanyid.trim();
    }

    public String getOrdersourcesys() {
        return ordersourcesys;
    }

    public void setOrdersourcesys(String ordersourcesys) {
        this.ordersourcesys = ordersourcesys == null ? null : ordersourcesys.trim();
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
        OrdersalesBj other = (OrdersalesBj) that;
        return (this.getRorderno() == null ? other.getRorderno() == null : this.getRorderno().equals(other.getRorderno()))
            && (this.getCustomerno() == null ? other.getCustomerno() == null : this.getCustomerno().equals(other.getCustomerno()))
            && (this.getUserno() == null ? other.getUserno() == null : this.getUserno().equals(other.getUserno()))
            && (this.getDlventire() == null ? other.getDlventire() == null : this.getDlventire().equals(other.getDlventire()))
            && (this.getTransfee() == null ? other.getTransfee() == null : this.getTransfee().equals(other.getTransfee()))
            && (this.getTranschannel() == null ? other.getTranschannel() == null : this.getTranschannel().equals(other.getTranschannel()))
            && (this.getDlvsite() == null ? other.getDlvsite() == null : this.getDlvsite().equals(other.getDlvsite()))
            && (this.getDlvtype() == null ? other.getDlvtype() == null : this.getDlvtype().equals(other.getDlvtype()))
            && (this.getContractno() == null ? other.getContractno() == null : this.getContractno().equals(other.getContractno()))
            && (this.getQuotationno() == null ? other.getQuotationno() == null : this.getQuotationno().equals(other.getQuotationno()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getOplog() == null ? other.getOplog() == null : this.getOplog().equals(other.getOplog()))
            && (this.getWorkday() == null ? other.getWorkday() == null : this.getWorkday().equals(other.getWorkday()))
            && (this.getSendout() == null ? other.getSendout() == null : this.getSendout().equals(other.getSendout()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getSendday() == null ? other.getSendday() == null : this.getSendday().equals(other.getSendday()))
            && (this.getRorderitem() == null ? other.getRorderitem() == null : this.getRorderitem().equals(other.getRorderitem()))
            && (this.getModelno() == null ? other.getModelno() == null : this.getModelno().equals(other.getModelno()))
            && (this.getQuantity() == null ? other.getQuantity() == null : this.getQuantity().equals(other.getQuantity()))
            && (this.getPrice() == null ? other.getPrice() == null : this.getPrice().equals(other.getPrice()))
            && (this.getDlvdate() == null ? other.getDlvdate() == null : this.getDlvdate().equals(other.getDlvdate()))
            && (this.getRcvclassify() == null ? other.getRcvclassify() == null : this.getRcvclassify().equals(other.getRcvclassify()))
            && (this.getPrjcode() == null ? other.getPrjcode() == null : this.getPrjcode().equals(other.getPrjcode()))
            && (this.getStockcode() == null ? other.getStockcode() == null : this.getStockcode().equals(other.getStockcode()))
            && (this.getCproductno() == null ? other.getCproductno() == null : this.getCproductno().equals(other.getCproductno()))
            && (this.getOrdtranstype() == null ? other.getOrdtranstype() == null : this.getOrdtranstype().equals(other.getOrdtranstype()))
            && (this.getSpcprice() == null ? other.getSpcprice() == null : this.getSpcprice().equals(other.getSpcprice()))
            && (this.getDiscount() == null ? other.getDiscount() == null : this.getDiscount().equals(other.getDiscount()))
            && (this.getNoprice() == null ? other.getNoprice() == null : this.getNoprice().equals(other.getNoprice()))
            && (this.getAccount() == null ? other.getAccount() == null : this.getAccount().equals(other.getAccount()))
            && (this.getSpecmark() == null ? other.getSpecmark() == null : this.getSpecmark().equals(other.getSpecmark()))
            && (this.getRecno() == null ? other.getRecno() == null : this.getRecno().equals(other.getRecno()))
            && (this.getDetailmark() == null ? other.getDetailmark() == null : this.getDetailmark().equals(other.getDetailmark()))
            && (this.getOptdate() == null ? other.getOptdate() == null : this.getOptdate().equals(other.getOptdate()))
            && (this.getRowguid() == null ? other.getRowguid() == null : this.getRowguid().equals(other.getRowguid()))
            && (this.getEmpsale() == null ? other.getEmpsale() == null : this.getEmpsale().equals(other.getEmpsale()))
            && (this.getEmpoffice() == null ? other.getEmpoffice() == null : this.getEmpoffice().equals(other.getEmpoffice()))
            && (this.getDeptno() == null ? other.getDeptno() == null : this.getDeptno().equals(other.getDeptno()))
            && (this.getPurchaseno() == null ? other.getPurchaseno() == null : this.getPurchaseno().equals(other.getPurchaseno()))
            && (this.getShikomi() == null ? other.getShikomi() == null : this.getShikomi().equals(other.getShikomi()))
            && (this.getPriceEnduser() == null ? other.getPriceEnduser() == null : this.getPriceEnduser().equals(other.getPriceEnduser()))
            && (this.getCttflag() == null ? other.getCttflag() == null : this.getCttflag().equals(other.getCttflag()))
            && (this.getWarehousesenddate() == null ? other.getWarehousesenddate() == null : this.getWarehousesenddate().equals(other.getWarehousesenddate()))
            && (this.getCproductname() == null ? other.getCproductname() == null : this.getCproductname().equals(other.getCproductname()))
            && (this.getOptrecord() == null ? other.getOptrecord() == null : this.getOptrecord().equals(other.getOptrecord()))
            && (this.getPresaleorderno() == null ? other.getPresaleorderno() == null : this.getPresaleorderno().equals(other.getPresaleorderno()))
            && (this.getOpponent() == null ? other.getOpponent() == null : this.getOpponent().equals(other.getOpponent()))
            && (this.getTypecode() == null ? other.getTypecode() == null : this.getTypecode().equals(other.getTypecode()))
            && (this.getPricechecked() == null ? other.getPricechecked() == null : this.getPricechecked().equals(other.getPricechecked()))
            && (this.getEprice() == null ? other.getEprice() == null : this.getEprice().equals(other.getEprice()))
            && (this.getTradecompanyid() == null ? other.getTradecompanyid() == null : this.getTradecompanyid().equals(other.getTradecompanyid()))
            && (this.getOrdersourcesys() == null ? other.getOrdersourcesys() == null : this.getOrdersourcesys().equals(other.getOrdersourcesys()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getRorderno() == null) ? 0 : getRorderno().hashCode());
        result = prime * result + ((getCustomerno() == null) ? 0 : getCustomerno().hashCode());
        result = prime * result + ((getUserno() == null) ? 0 : getUserno().hashCode());
        result = prime * result + ((getDlventire() == null) ? 0 : getDlventire().hashCode());
        result = prime * result + ((getTransfee() == null) ? 0 : getTransfee().hashCode());
        result = prime * result + ((getTranschannel() == null) ? 0 : getTranschannel().hashCode());
        result = prime * result + ((getDlvsite() == null) ? 0 : getDlvsite().hashCode());
        result = prime * result + ((getDlvtype() == null) ? 0 : getDlvtype().hashCode());
        result = prime * result + ((getContractno() == null) ? 0 : getContractno().hashCode());
        result = prime * result + ((getQuotationno() == null) ? 0 : getQuotationno().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getOplog() == null) ? 0 : getOplog().hashCode());
        result = prime * result + ((getWorkday() == null) ? 0 : getWorkday().hashCode());
        result = prime * result + ((getSendout() == null) ? 0 : getSendout().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getSendday() == null) ? 0 : getSendday().hashCode());
        result = prime * result + ((getRorderitem() == null) ? 0 : getRorderitem().hashCode());
        result = prime * result + ((getModelno() == null) ? 0 : getModelno().hashCode());
        result = prime * result + ((getQuantity() == null) ? 0 : getQuantity().hashCode());
        result = prime * result + ((getPrice() == null) ? 0 : getPrice().hashCode());
        result = prime * result + ((getDlvdate() == null) ? 0 : getDlvdate().hashCode());
        result = prime * result + ((getRcvclassify() == null) ? 0 : getRcvclassify().hashCode());
        result = prime * result + ((getPrjcode() == null) ? 0 : getPrjcode().hashCode());
        result = prime * result + ((getStockcode() == null) ? 0 : getStockcode().hashCode());
        result = prime * result + ((getCproductno() == null) ? 0 : getCproductno().hashCode());
        result = prime * result + ((getOrdtranstype() == null) ? 0 : getOrdtranstype().hashCode());
        result = prime * result + ((getSpcprice() == null) ? 0 : getSpcprice().hashCode());
        result = prime * result + ((getDiscount() == null) ? 0 : getDiscount().hashCode());
        result = prime * result + ((getNoprice() == null) ? 0 : getNoprice().hashCode());
        result = prime * result + ((getAccount() == null) ? 0 : getAccount().hashCode());
        result = prime * result + ((getSpecmark() == null) ? 0 : getSpecmark().hashCode());
        result = prime * result + ((getRecno() == null) ? 0 : getRecno().hashCode());
        result = prime * result + ((getDetailmark() == null) ? 0 : getDetailmark().hashCode());
        result = prime * result + ((getOptdate() == null) ? 0 : getOptdate().hashCode());
        result = prime * result + ((getRowguid() == null) ? 0 : getRowguid().hashCode());
        result = prime * result + ((getEmpsale() == null) ? 0 : getEmpsale().hashCode());
        result = prime * result + ((getEmpoffice() == null) ? 0 : getEmpoffice().hashCode());
        result = prime * result + ((getDeptno() == null) ? 0 : getDeptno().hashCode());
        result = prime * result + ((getPurchaseno() == null) ? 0 : getPurchaseno().hashCode());
        result = prime * result + ((getShikomi() == null) ? 0 : getShikomi().hashCode());
        result = prime * result + ((getPriceEnduser() == null) ? 0 : getPriceEnduser().hashCode());
        result = prime * result + ((getCttflag() == null) ? 0 : getCttflag().hashCode());
        result = prime * result + ((getWarehousesenddate() == null) ? 0 : getWarehousesenddate().hashCode());
        result = prime * result + ((getCproductname() == null) ? 0 : getCproductname().hashCode());
        result = prime * result + ((getOptrecord() == null) ? 0 : getOptrecord().hashCode());
        result = prime * result + ((getPresaleorderno() == null) ? 0 : getPresaleorderno().hashCode());
        result = prime * result + ((getOpponent() == null) ? 0 : getOpponent().hashCode());
        result = prime * result + ((getTypecode() == null) ? 0 : getTypecode().hashCode());
        result = prime * result + ((getPricechecked() == null) ? 0 : getPricechecked().hashCode());
        result = prime * result + ((getEprice() == null) ? 0 : getEprice().hashCode());
        result = prime * result + ((getTradecompanyid() == null) ? 0 : getTradecompanyid().hashCode());
        result = prime * result + ((getOrdersourcesys() == null) ? 0 : getOrdersourcesys().hashCode());
        return result;
    }
}