package com.sales.ops.dto.purchase;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author B91717
 * @date 2022/5/11
 * @apiNote
 */
public class OrderSalesGPDto  implements Serializable {

    private String rorderno;

    private String rorderitem;

    private String recno;

    private String workday;

    private String status;

    private String customerno;

    private String userno;

    private String modelno;

    private Integer quantity;

    private BigDecimal price;

    private BigDecimal account;

    private String dlvdate;

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

    /**
     * 交易主体
     */
    private String ordersourceentity;

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

    private String sendout;

    private Date sendday;

    private String noprice;

    private String detailmark;

    private String optdate;

    private String locationno;

    private String name;

    private String mobile;

    private String address;

    private String zipcode;

    private String ordertype;

    private String gzorderno;

    private Date updatetime;

    private String addressid;

    // bug12485 新增vipcode字段

    private String vipcode;

    private Integer vippriority;

    public Integer getVippriority() {
        return vippriority;
    }

    public void setVippriority(Integer vippriority) {
        this.vippriority = vippriority;
    }

    public String getVipcode() {
        return vipcode;
    }

    public void setVipcode(String vipcode) {
        this.vipcode = vipcode;
    }

    public String getRorderno() {
        return rorderno;
    }

    public void setRorderno(String rorderno) {
        this.rorderno = rorderno;
    }

    public String getRorderitem() {
        return rorderitem;
    }

    public void setRorderitem(String rorderitem) {
        this.rorderitem = rorderitem;
    }

    public String getRecno() {
        return recno;
    }

    public void setRecno(String recno) {
        this.recno = recno;
    }

    public String getWorkday() {
        return workday;
    }

    public void setWorkday(String workday) {
        this.workday = workday;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCustomerno() {
        return customerno;
    }

    public void setCustomerno(String customerno) {
        this.customerno = customerno;
    }

    public String getUserno() {
        return userno;
    }

    public void setUserno(String userno) {
        this.userno = userno;
    }

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno;
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

    public String getDlvdate() {
        return dlvdate;
    }

    public void setDlvdate(String dlvdate) {
        this.dlvdate = dlvdate;
    }

    public String getRcvclassify() {
        return rcvclassify;
    }

    public void setRcvclassify(String rcvclassify) {
        this.rcvclassify = rcvclassify;
    }

    public String getPrjcode() {
        return prjcode;
    }

    public void setPrjcode(String prjcode) {
        this.prjcode = prjcode;
    }

    public String getCproductno() {
        return cproductno;
    }

    public void setCproductno(String cproductno) {
        this.cproductno = cproductno;
    }

    public String getStockcode() {
        return stockcode;
    }

    public void setStockcode(String stockcode) {
        this.stockcode = stockcode;
    }

    public String getExpinvcode() {
        return expinvcode;
    }

    public void setExpinvcode(String expinvcode) {
        this.expinvcode = expinvcode;
    }

    public String getSpcprice() {
        return spcprice;
    }

    public void setSpcprice(String spcprice) {
        this.spcprice = spcprice;
    }

    public String getOrdtranstype() {
        return ordtranstype;
    }

    public void setOrdtranstype(String ordtranstype) {
        this.ordtranstype = ordtranstype;
    }

    public String getSpecmark() {
        return specmark;
    }

    public void setSpecmark(String specmark) {
        this.specmark = specmark;
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
        this.dlventire = dlventire;
    }

    public String getTransfee() {
        return transfee;
    }

    public void setTransfee(String transfee) {
        this.transfee = transfee;
    }

    public String getTranschannel() {
        return transchannel;
    }

    public void setTranschannel(String transchannel) {
        this.transchannel = transchannel;
    }

    public String getOrdersourceentity() {
        return ordersourceentity;
    }

    public void setOrdersourceentity(String ordersourceentity) {
        this.ordersourceentity = ordersourceentity;
    }

    public String getDlvsite() {
        return dlvsite;
    }

    public void setDlvsite(String dlvsite) {
        this.dlvsite = dlvsite;
    }

    public String getDlvtype1() {
        return dlvtype1;
    }

    public void setDlvtype1(String dlvtype1) {
        this.dlvtype1 = dlvtype1;
    }

    public String getDlvtype2() {
        return dlvtype2;
    }

    public void setDlvtype2(String dlvtype2) {
        this.dlvtype2 = dlvtype2;
    }

    public String getDlvtype3() {
        return dlvtype3;
    }

    public void setDlvtype3(String dlvtype3) {
        this.dlvtype3 = dlvtype3;
    }

    public String getDlvtype4() {
        return dlvtype4;
    }

    public void setDlvtype4(String dlvtype4) {
        this.dlvtype4 = dlvtype4;
    }

    public String getContractno() {
        return contractno;
    }

    public void setContractno(String contractno) {
        this.contractno = contractno;
    }

    public String getQuotationno() {
        return quotationno;
    }

    public void setQuotationno(String quotationno) {
        this.quotationno = quotationno;
    }

    public String getCqueryno() {
        return cqueryno;
    }

    public void setCqueryno(String cqueryno) {
        this.cqueryno = cqueryno;
    }

    public String getCorderno() {
        return corderno;
    }

    public void setCorderno(String corderno) {
        this.corderno = corderno;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOplog() {
        return oplog;
    }

    public void setOplog(String oplog) {
        this.oplog = oplog;
    }

    public String getSendout() {
        return sendout;
    }

    public void setSendout(String sendout) {
        this.sendout = sendout;
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
        this.noprice = noprice;
    }

    public String getDetailmark() {
        return detailmark;
    }

    public void setDetailmark(String detailmark) {
        this.detailmark = detailmark;
    }

    public String getOptdate() {
        return optdate;
    }

    public void setOptdate(String optdate) {
        this.optdate = optdate;
    }

    public String getLocationno() {
        return locationno;
    }

    public void setLocationno(String locationno) {
        this.locationno = locationno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getOrdertype() {
        return ordertype;
    }

    public void setOrdertype(String ordertype) {
        this.ordertype = ordertype;
    }

    public String getGzorderno() {
        return gzorderno;
    }

    public void setGzorderno(String gzorderno) {
        this.gzorderno = gzorderno;
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
        this.addressid = addressid;
    }
}
