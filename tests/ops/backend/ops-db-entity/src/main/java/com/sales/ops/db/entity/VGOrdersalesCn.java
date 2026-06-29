package com.sales.ops.db.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class VGOrdersalesCn implements Serializable {
    private Long id;

    private String rorderno;

    private String rorderitem;

    private String recno;

    private String customerno;

    private String userno;

    private String modelno;

    private Integer quantity;

    private BigDecimal price;

    private BigDecimal account;

    private Date dlvdate;

    private String rcvclassify;

    private String prjcode;

    private String cproductno;

    private String stockcode;

    private String expinvcode;

    private String spcprice;

    private String ordtranstype;

    private String specmark;

    private Double discount;

    private String dlventire;

    private String transfee;

    private String transchannel;

    private String dlvsite;

    private String dlvtype1;

    private String dlvtype2;

    private String dlvtype3;

    private String dlvtype4;

    private String contractno;

    private String quotationno;

    private String cqueryno;

    private String corderno;

    private String remark;

    private String oplog;

    private Date workday;

    private String sendout;

    private String status;

    private Date sendday;

    private String noprice;

    private String detailmark;

    private Date optdate;

    private String locationno;

    private String name;

    private String mobile;

    private String address;

    private String zipcode;

    private String ordertype;

    private String ordersourceentity;

    private String gzorderno;

    private Date updatetime;

    private String addressid;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getRecno() {
        return recno;
    }

    public void setRecno(String recno) {
        this.recno = recno == null ? null : recno.trim();
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

    public BigDecimal getAccount() {
        return account;
    }

    public void setAccount(BigDecimal account) {
        this.account = account;
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

    public String getCproductno() {
        return cproductno;
    }

    public void setCproductno(String cproductno) {
        this.cproductno = cproductno == null ? null : cproductno.trim();
    }

    public String getStockcode() {
        return stockcode;
    }

    public void setStockcode(String stockcode) {
        this.stockcode = stockcode == null ? null : stockcode.trim();
    }

    public String getExpinvcode() {
        return expinvcode;
    }

    public void setExpinvcode(String expinvcode) {
        this.expinvcode = expinvcode == null ? null : expinvcode.trim();
    }

    public String getSpcprice() {
        return spcprice;
    }

    public void setSpcprice(String spcprice) {
        this.spcprice = spcprice == null ? null : spcprice.trim();
    }

    public String getOrdtranstype() {
        return ordtranstype;
    }

    public void setOrdtranstype(String ordtranstype) {
        this.ordtranstype = ordtranstype == null ? null : ordtranstype.trim();
    }

    public String getSpecmark() {
        return specmark;
    }

    public void setSpecmark(String specmark) {
        this.specmark = specmark == null ? null : specmark.trim();
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
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

    public String getDlvtype1() {
        return dlvtype1;
    }

    public void setDlvtype1(String dlvtype1) {
        this.dlvtype1 = dlvtype1 == null ? null : dlvtype1.trim();
    }

    public String getDlvtype2() {
        return dlvtype2;
    }

    public void setDlvtype2(String dlvtype2) {
        this.dlvtype2 = dlvtype2 == null ? null : dlvtype2.trim();
    }

    public String getDlvtype3() {
        return dlvtype3;
    }

    public void setDlvtype3(String dlvtype3) {
        this.dlvtype3 = dlvtype3 == null ? null : dlvtype3.trim();
    }

    public String getDlvtype4() {
        return dlvtype4;
    }

    public void setDlvtype4(String dlvtype4) {
        this.dlvtype4 = dlvtype4 == null ? null : dlvtype4.trim();
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

    public String getCqueryno() {
        return cqueryno;
    }

    public void setCqueryno(String cqueryno) {
        this.cqueryno = cqueryno == null ? null : cqueryno.trim();
    }

    public String getCorderno() {
        return corderno;
    }

    public void setCorderno(String corderno) {
        this.corderno = corderno == null ? null : corderno.trim();
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

    public String getNoprice() {
        return noprice;
    }

    public void setNoprice(String noprice) {
        this.noprice = noprice == null ? null : noprice.trim();
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

    public String getLocationno() {
        return locationno;
    }

    public void setLocationno(String locationno) {
        this.locationno = locationno == null ? null : locationno.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode == null ? null : zipcode.trim();
    }

    public String getOrdertype() {
        return ordertype;
    }

    public void setOrdertype(String ordertype) {
        this.ordertype = ordertype == null ? null : ordertype.trim();
    }

    public String getOrdersourceentity() {
        return ordersourceentity;
    }

    public void setOrdersourceentity(String ordersourceentity) {
        this.ordersourceentity = ordersourceentity == null ? null : ordersourceentity.trim();
    }

    public String getGzorderno() {
        return gzorderno;
    }

    public void setGzorderno(String gzorderno) {
        this.gzorderno = gzorderno == null ? null : gzorderno.trim();
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getAddressid() {
        return addressid;
    }

    public void setAddressid(String addressid) {
        this.addressid = addressid == null ? null : addressid.trim();
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
        VGOrdersalesCn other = (VGOrdersalesCn) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getRorderno() == null ? other.getRorderno() == null : this.getRorderno().equals(other.getRorderno()))
            && (this.getRorderitem() == null ? other.getRorderitem() == null : this.getRorderitem().equals(other.getRorderitem()))
            && (this.getRecno() == null ? other.getRecno() == null : this.getRecno().equals(other.getRecno()))
            && (this.getCustomerno() == null ? other.getCustomerno() == null : this.getCustomerno().equals(other.getCustomerno()))
            && (this.getUserno() == null ? other.getUserno() == null : this.getUserno().equals(other.getUserno()))
            && (this.getModelno() == null ? other.getModelno() == null : this.getModelno().equals(other.getModelno()))
            && (this.getQuantity() == null ? other.getQuantity() == null : this.getQuantity().equals(other.getQuantity()))
            && (this.getPrice() == null ? other.getPrice() == null : this.getPrice().equals(other.getPrice()))
            && (this.getAccount() == null ? other.getAccount() == null : this.getAccount().equals(other.getAccount()))
            && (this.getDlvdate() == null ? other.getDlvdate() == null : this.getDlvdate().equals(other.getDlvdate()))
            && (this.getRcvclassify() == null ? other.getRcvclassify() == null : this.getRcvclassify().equals(other.getRcvclassify()))
            && (this.getPrjcode() == null ? other.getPrjcode() == null : this.getPrjcode().equals(other.getPrjcode()))
            && (this.getCproductno() == null ? other.getCproductno() == null : this.getCproductno().equals(other.getCproductno()))
            && (this.getStockcode() == null ? other.getStockcode() == null : this.getStockcode().equals(other.getStockcode()))
            && (this.getExpinvcode() == null ? other.getExpinvcode() == null : this.getExpinvcode().equals(other.getExpinvcode()))
            && (this.getSpcprice() == null ? other.getSpcprice() == null : this.getSpcprice().equals(other.getSpcprice()))
            && (this.getOrdtranstype() == null ? other.getOrdtranstype() == null : this.getOrdtranstype().equals(other.getOrdtranstype()))
            && (this.getSpecmark() == null ? other.getSpecmark() == null : this.getSpecmark().equals(other.getSpecmark()))
            && (this.getDiscount() == null ? other.getDiscount() == null : this.getDiscount().equals(other.getDiscount()))
            && (this.getDlventire() == null ? other.getDlventire() == null : this.getDlventire().equals(other.getDlventire()))
            && (this.getTransfee() == null ? other.getTransfee() == null : this.getTransfee().equals(other.getTransfee()))
            && (this.getTranschannel() == null ? other.getTranschannel() == null : this.getTranschannel().equals(other.getTranschannel()))
            && (this.getDlvsite() == null ? other.getDlvsite() == null : this.getDlvsite().equals(other.getDlvsite()))
            && (this.getDlvtype1() == null ? other.getDlvtype1() == null : this.getDlvtype1().equals(other.getDlvtype1()))
            && (this.getDlvtype2() == null ? other.getDlvtype2() == null : this.getDlvtype2().equals(other.getDlvtype2()))
            && (this.getDlvtype3() == null ? other.getDlvtype3() == null : this.getDlvtype3().equals(other.getDlvtype3()))
            && (this.getDlvtype4() == null ? other.getDlvtype4() == null : this.getDlvtype4().equals(other.getDlvtype4()))
            && (this.getContractno() == null ? other.getContractno() == null : this.getContractno().equals(other.getContractno()))
            && (this.getQuotationno() == null ? other.getQuotationno() == null : this.getQuotationno().equals(other.getQuotationno()))
            && (this.getCqueryno() == null ? other.getCqueryno() == null : this.getCqueryno().equals(other.getCqueryno()))
            && (this.getCorderno() == null ? other.getCorderno() == null : this.getCorderno().equals(other.getCorderno()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getOplog() == null ? other.getOplog() == null : this.getOplog().equals(other.getOplog()))
            && (this.getWorkday() == null ? other.getWorkday() == null : this.getWorkday().equals(other.getWorkday()))
            && (this.getSendout() == null ? other.getSendout() == null : this.getSendout().equals(other.getSendout()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getSendday() == null ? other.getSendday() == null : this.getSendday().equals(other.getSendday()))
            && (this.getNoprice() == null ? other.getNoprice() == null : this.getNoprice().equals(other.getNoprice()))
            && (this.getDetailmark() == null ? other.getDetailmark() == null : this.getDetailmark().equals(other.getDetailmark()))
            && (this.getOptdate() == null ? other.getOptdate() == null : this.getOptdate().equals(other.getOptdate()))
            && (this.getLocationno() == null ? other.getLocationno() == null : this.getLocationno().equals(other.getLocationno()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getMobile() == null ? other.getMobile() == null : this.getMobile().equals(other.getMobile()))
            && (this.getAddress() == null ? other.getAddress() == null : this.getAddress().equals(other.getAddress()))
            && (this.getZipcode() == null ? other.getZipcode() == null : this.getZipcode().equals(other.getZipcode()))
            && (this.getOrdertype() == null ? other.getOrdertype() == null : this.getOrdertype().equals(other.getOrdertype()))
            && (this.getOrdersourceentity() == null ? other.getOrdersourceentity() == null : this.getOrdersourceentity().equals(other.getOrdersourceentity()))
            && (this.getGzorderno() == null ? other.getGzorderno() == null : this.getGzorderno().equals(other.getGzorderno()))
            && (this.getUpdatetime() == null ? other.getUpdatetime() == null : this.getUpdatetime().equals(other.getUpdatetime()))
            && (this.getAddressid() == null ? other.getAddressid() == null : this.getAddressid().equals(other.getAddressid()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getRorderno() == null) ? 0 : getRorderno().hashCode());
        result = prime * result + ((getRorderitem() == null) ? 0 : getRorderitem().hashCode());
        result = prime * result + ((getRecno() == null) ? 0 : getRecno().hashCode());
        result = prime * result + ((getCustomerno() == null) ? 0 : getCustomerno().hashCode());
        result = prime * result + ((getUserno() == null) ? 0 : getUserno().hashCode());
        result = prime * result + ((getModelno() == null) ? 0 : getModelno().hashCode());
        result = prime * result + ((getQuantity() == null) ? 0 : getQuantity().hashCode());
        result = prime * result + ((getPrice() == null) ? 0 : getPrice().hashCode());
        result = prime * result + ((getAccount() == null) ? 0 : getAccount().hashCode());
        result = prime * result + ((getDlvdate() == null) ? 0 : getDlvdate().hashCode());
        result = prime * result + ((getRcvclassify() == null) ? 0 : getRcvclassify().hashCode());
        result = prime * result + ((getPrjcode() == null) ? 0 : getPrjcode().hashCode());
        result = prime * result + ((getCproductno() == null) ? 0 : getCproductno().hashCode());
        result = prime * result + ((getStockcode() == null) ? 0 : getStockcode().hashCode());
        result = prime * result + ((getExpinvcode() == null) ? 0 : getExpinvcode().hashCode());
        result = prime * result + ((getSpcprice() == null) ? 0 : getSpcprice().hashCode());
        result = prime * result + ((getOrdtranstype() == null) ? 0 : getOrdtranstype().hashCode());
        result = prime * result + ((getSpecmark() == null) ? 0 : getSpecmark().hashCode());
        result = prime * result + ((getDiscount() == null) ? 0 : getDiscount().hashCode());
        result = prime * result + ((getDlventire() == null) ? 0 : getDlventire().hashCode());
        result = prime * result + ((getTransfee() == null) ? 0 : getTransfee().hashCode());
        result = prime * result + ((getTranschannel() == null) ? 0 : getTranschannel().hashCode());
        result = prime * result + ((getDlvsite() == null) ? 0 : getDlvsite().hashCode());
        result = prime * result + ((getDlvtype1() == null) ? 0 : getDlvtype1().hashCode());
        result = prime * result + ((getDlvtype2() == null) ? 0 : getDlvtype2().hashCode());
        result = prime * result + ((getDlvtype3() == null) ? 0 : getDlvtype3().hashCode());
        result = prime * result + ((getDlvtype4() == null) ? 0 : getDlvtype4().hashCode());
        result = prime * result + ((getContractno() == null) ? 0 : getContractno().hashCode());
        result = prime * result + ((getQuotationno() == null) ? 0 : getQuotationno().hashCode());
        result = prime * result + ((getCqueryno() == null) ? 0 : getCqueryno().hashCode());
        result = prime * result + ((getCorderno() == null) ? 0 : getCorderno().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getOplog() == null) ? 0 : getOplog().hashCode());
        result = prime * result + ((getWorkday() == null) ? 0 : getWorkday().hashCode());
        result = prime * result + ((getSendout() == null) ? 0 : getSendout().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getSendday() == null) ? 0 : getSendday().hashCode());
        result = prime * result + ((getNoprice() == null) ? 0 : getNoprice().hashCode());
        result = prime * result + ((getDetailmark() == null) ? 0 : getDetailmark().hashCode());
        result = prime * result + ((getOptdate() == null) ? 0 : getOptdate().hashCode());
        result = prime * result + ((getLocationno() == null) ? 0 : getLocationno().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getMobile() == null) ? 0 : getMobile().hashCode());
        result = prime * result + ((getAddress() == null) ? 0 : getAddress().hashCode());
        result = prime * result + ((getZipcode() == null) ? 0 : getZipcode().hashCode());
        result = prime * result + ((getOrdertype() == null) ? 0 : getOrdertype().hashCode());
        result = prime * result + ((getOrdersourceentity() == null) ? 0 : getOrdersourceentity().hashCode());
        result = prime * result + ((getGzorderno() == null) ? 0 : getGzorderno().hashCode());
        result = prime * result + ((getUpdatetime() == null) ? 0 : getUpdatetime().hashCode());
        result = prime * result + ((getAddressid() == null) ? 0 : getAddressid().hashCode());
        return result;
    }
}