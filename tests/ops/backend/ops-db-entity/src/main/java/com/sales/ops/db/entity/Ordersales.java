package com.sales.ops.db.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Ordersales extends OrdersalesKey implements Serializable {
    private Long id;

    private String customerno;

    private String userno;

    private String enduser;

    private String dlventire;

    private String transfee;

    private String transchannel;

    private String dlvsite;

    private String dlvtype;

    private String contractno;

    private String quotationno;

    private String remark;

    private Date workday;

    private String sendout;

    private String status;

    private Date sendday;

    private String modelno;

    private Integer quantity;

    private BigDecimal price;

    private BigDecimal priceEnduser;

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

    private String empsale;

    private String empoffice;

    private String deptno;

    private String purchaseno;

    private String shikomi;

    private String cttflag;

    private Date warehousesenddate;

    private String cproductname;

    private String presaleorderno;

    private String opponent;

    private Short typecode;

    private Boolean pricechecked;

    private BigDecimal eprice;

    private String tradecompanyid;

    private Date createTime;

    private Date updateTime;

    private String createUser;

    private String updateUser;

    private String addressNo;

    private BigDecimal taxRate;

    private Date cdlvdate;

    private String corderno;

    private String fullorderno;

    private String salesInfoNo;

    private String pplNo;

    private String projectNo;

    private String shikomiNo;

    private String customCode;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getEnduser() {
        return enduser;
    }

    public void setEnduser(String enduser) {
        this.enduser = enduser == null ? null : enduser.trim();
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

    public BigDecimal getPriceEnduser() {
        return priceEnduser;
    }

    public void setPriceEnduser(BigDecimal priceEnduser) {
        this.priceEnduser = priceEnduser;
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

    public Short getTypecode() {
        return typecode;
    }

    public void setTypecode(Short typecode) {
        this.typecode = typecode;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    public String getAddressNo() {
        return addressNo;
    }

    public void setAddressNo(String addressNo) {
        this.addressNo = addressNo == null ? null : addressNo.trim();
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public Date getCdlvdate() {
        return cdlvdate;
    }

    public void setCdlvdate(Date cdlvdate) {
        this.cdlvdate = cdlvdate;
    }

    public String getCorderno() {
        return corderno;
    }

    public void setCorderno(String corderno) {
        this.corderno = corderno == null ? null : corderno.trim();
    }

    public String getFullorderno() {
        return fullorderno;
    }

    public void setFullorderno(String fullorderno) {
        this.fullorderno = fullorderno == null ? null : fullorderno.trim();
    }

    public String getSalesInfoNo() {
        return salesInfoNo;
    }

    public void setSalesInfoNo(String salesInfoNo) {
        this.salesInfoNo = salesInfoNo == null ? null : salesInfoNo.trim();
    }

    public String getPplNo() {
        return pplNo;
    }

    public void setPplNo(String pplNo) {
        this.pplNo = pplNo == null ? null : pplNo.trim();
    }

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo == null ? null : projectNo.trim();
    }

    public String getShikomiNo() {
        return shikomiNo;
    }

    public void setShikomiNo(String shikomiNo) {
        this.shikomiNo = shikomiNo == null ? null : shikomiNo.trim();
    }

    public String getCustomCode() {
        return customCode;
    }

    public void setCustomCode(String customCode) {
        this.customCode = customCode == null ? null : customCode.trim();
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
        Ordersales other = (Ordersales) that;
        return (this.getRorderno() == null ? other.getRorderno() == null : this.getRorderno().equals(other.getRorderno()))
            && (this.getRorderitem() == null ? other.getRorderitem() == null : this.getRorderitem().equals(other.getRorderitem()))
            && (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getCustomerno() == null ? other.getCustomerno() == null : this.getCustomerno().equals(other.getCustomerno()))
            && (this.getUserno() == null ? other.getUserno() == null : this.getUserno().equals(other.getUserno()))
            && (this.getEnduser() == null ? other.getEnduser() == null : this.getEnduser().equals(other.getEnduser()))
            && (this.getDlventire() == null ? other.getDlventire() == null : this.getDlventire().equals(other.getDlventire()))
            && (this.getTransfee() == null ? other.getTransfee() == null : this.getTransfee().equals(other.getTransfee()))
            && (this.getTranschannel() == null ? other.getTranschannel() == null : this.getTranschannel().equals(other.getTranschannel()))
            && (this.getDlvsite() == null ? other.getDlvsite() == null : this.getDlvsite().equals(other.getDlvsite()))
            && (this.getDlvtype() == null ? other.getDlvtype() == null : this.getDlvtype().equals(other.getDlvtype()))
            && (this.getContractno() == null ? other.getContractno() == null : this.getContractno().equals(other.getContractno()))
            && (this.getQuotationno() == null ? other.getQuotationno() == null : this.getQuotationno().equals(other.getQuotationno()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getWorkday() == null ? other.getWorkday() == null : this.getWorkday().equals(other.getWorkday()))
            && (this.getSendout() == null ? other.getSendout() == null : this.getSendout().equals(other.getSendout()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getSendday() == null ? other.getSendday() == null : this.getSendday().equals(other.getSendday()))
            && (this.getModelno() == null ? other.getModelno() == null : this.getModelno().equals(other.getModelno()))
            && (this.getQuantity() == null ? other.getQuantity() == null : this.getQuantity().equals(other.getQuantity()))
            && (this.getPrice() == null ? other.getPrice() == null : this.getPrice().equals(other.getPrice()))
            && (this.getPriceEnduser() == null ? other.getPriceEnduser() == null : this.getPriceEnduser().equals(other.getPriceEnduser()))
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
            && (this.getEmpsale() == null ? other.getEmpsale() == null : this.getEmpsale().equals(other.getEmpsale()))
            && (this.getEmpoffice() == null ? other.getEmpoffice() == null : this.getEmpoffice().equals(other.getEmpoffice()))
            && (this.getDeptno() == null ? other.getDeptno() == null : this.getDeptno().equals(other.getDeptno()))
            && (this.getPurchaseno() == null ? other.getPurchaseno() == null : this.getPurchaseno().equals(other.getPurchaseno()))
            && (this.getShikomi() == null ? other.getShikomi() == null : this.getShikomi().equals(other.getShikomi()))
            && (this.getCttflag() == null ? other.getCttflag() == null : this.getCttflag().equals(other.getCttflag()))
            && (this.getWarehousesenddate() == null ? other.getWarehousesenddate() == null : this.getWarehousesenddate().equals(other.getWarehousesenddate()))
            && (this.getCproductname() == null ? other.getCproductname() == null : this.getCproductname().equals(other.getCproductname()))
            && (this.getPresaleorderno() == null ? other.getPresaleorderno() == null : this.getPresaleorderno().equals(other.getPresaleorderno()))
            && (this.getOpponent() == null ? other.getOpponent() == null : this.getOpponent().equals(other.getOpponent()))
            && (this.getTypecode() == null ? other.getTypecode() == null : this.getTypecode().equals(other.getTypecode()))
            && (this.getPricechecked() == null ? other.getPricechecked() == null : this.getPricechecked().equals(other.getPricechecked()))
            && (this.getEprice() == null ? other.getEprice() == null : this.getEprice().equals(other.getEprice()))
            && (this.getTradecompanyid() == null ? other.getTradecompanyid() == null : this.getTradecompanyid().equals(other.getTradecompanyid()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getCreateUser() == null ? other.getCreateUser() == null : this.getCreateUser().equals(other.getCreateUser()))
            && (this.getUpdateUser() == null ? other.getUpdateUser() == null : this.getUpdateUser().equals(other.getUpdateUser()))
            && (this.getAddressNo() == null ? other.getAddressNo() == null : this.getAddressNo().equals(other.getAddressNo()))
            && (this.getTaxRate() == null ? other.getTaxRate() == null : this.getTaxRate().equals(other.getTaxRate()))
            && (this.getCdlvdate() == null ? other.getCdlvdate() == null : this.getCdlvdate().equals(other.getCdlvdate()))
            && (this.getCorderno() == null ? other.getCorderno() == null : this.getCorderno().equals(other.getCorderno()))
            && (this.getFullorderno() == null ? other.getFullorderno() == null : this.getFullorderno().equals(other.getFullorderno()))
            && (this.getSalesInfoNo() == null ? other.getSalesInfoNo() == null : this.getSalesInfoNo().equals(other.getSalesInfoNo()))
            && (this.getPplNo() == null ? other.getPplNo() == null : this.getPplNo().equals(other.getPplNo()))
            && (this.getProjectNo() == null ? other.getProjectNo() == null : this.getProjectNo().equals(other.getProjectNo()))
            && (this.getShikomiNo() == null ? other.getShikomiNo() == null : this.getShikomiNo().equals(other.getShikomiNo()))
            && (this.getCustomCode() == null ? other.getCustomCode() == null : this.getCustomCode().equals(other.getCustomCode()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getRorderno() == null) ? 0 : getRorderno().hashCode());
        result = prime * result + ((getRorderitem() == null) ? 0 : getRorderitem().hashCode());
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCustomerno() == null) ? 0 : getCustomerno().hashCode());
        result = prime * result + ((getUserno() == null) ? 0 : getUserno().hashCode());
        result = prime * result + ((getEnduser() == null) ? 0 : getEnduser().hashCode());
        result = prime * result + ((getDlventire() == null) ? 0 : getDlventire().hashCode());
        result = prime * result + ((getTransfee() == null) ? 0 : getTransfee().hashCode());
        result = prime * result + ((getTranschannel() == null) ? 0 : getTranschannel().hashCode());
        result = prime * result + ((getDlvsite() == null) ? 0 : getDlvsite().hashCode());
        result = prime * result + ((getDlvtype() == null) ? 0 : getDlvtype().hashCode());
        result = prime * result + ((getContractno() == null) ? 0 : getContractno().hashCode());
        result = prime * result + ((getQuotationno() == null) ? 0 : getQuotationno().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getWorkday() == null) ? 0 : getWorkday().hashCode());
        result = prime * result + ((getSendout() == null) ? 0 : getSendout().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getSendday() == null) ? 0 : getSendday().hashCode());
        result = prime * result + ((getModelno() == null) ? 0 : getModelno().hashCode());
        result = prime * result + ((getQuantity() == null) ? 0 : getQuantity().hashCode());
        result = prime * result + ((getPrice() == null) ? 0 : getPrice().hashCode());
        result = prime * result + ((getPriceEnduser() == null) ? 0 : getPriceEnduser().hashCode());
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
        result = prime * result + ((getEmpsale() == null) ? 0 : getEmpsale().hashCode());
        result = prime * result + ((getEmpoffice() == null) ? 0 : getEmpoffice().hashCode());
        result = prime * result + ((getDeptno() == null) ? 0 : getDeptno().hashCode());
        result = prime * result + ((getPurchaseno() == null) ? 0 : getPurchaseno().hashCode());
        result = prime * result + ((getShikomi() == null) ? 0 : getShikomi().hashCode());
        result = prime * result + ((getCttflag() == null) ? 0 : getCttflag().hashCode());
        result = prime * result + ((getWarehousesenddate() == null) ? 0 : getWarehousesenddate().hashCode());
        result = prime * result + ((getCproductname() == null) ? 0 : getCproductname().hashCode());
        result = prime * result + ((getPresaleorderno() == null) ? 0 : getPresaleorderno().hashCode());
        result = prime * result + ((getOpponent() == null) ? 0 : getOpponent().hashCode());
        result = prime * result + ((getTypecode() == null) ? 0 : getTypecode().hashCode());
        result = prime * result + ((getPricechecked() == null) ? 0 : getPricechecked().hashCode());
        result = prime * result + ((getEprice() == null) ? 0 : getEprice().hashCode());
        result = prime * result + ((getTradecompanyid() == null) ? 0 : getTradecompanyid().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getCreateUser() == null) ? 0 : getCreateUser().hashCode());
        result = prime * result + ((getUpdateUser() == null) ? 0 : getUpdateUser().hashCode());
        result = prime * result + ((getAddressNo() == null) ? 0 : getAddressNo().hashCode());
        result = prime * result + ((getTaxRate() == null) ? 0 : getTaxRate().hashCode());
        result = prime * result + ((getCdlvdate() == null) ? 0 : getCdlvdate().hashCode());
        result = prime * result + ((getCorderno() == null) ? 0 : getCorderno().hashCode());
        result = prime * result + ((getFullorderno() == null) ? 0 : getFullorderno().hashCode());
        result = prime * result + ((getSalesInfoNo() == null) ? 0 : getSalesInfoNo().hashCode());
        result = prime * result + ((getPplNo() == null) ? 0 : getPplNo().hashCode());
        result = prime * result + ((getProjectNo() == null) ? 0 : getProjectNo().hashCode());
        result = prime * result + ((getShikomiNo() == null) ? 0 : getShikomiNo().hashCode());
        result = prime * result + ((getCustomCode() == null) ? 0 : getCustomCode().hashCode());
        return result;
    }
}