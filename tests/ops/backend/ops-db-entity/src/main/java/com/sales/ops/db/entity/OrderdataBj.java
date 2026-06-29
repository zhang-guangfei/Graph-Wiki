package com.sales.ops.db.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OrderdataBj implements Serializable {
    private String ordrno;

    private String modelno;

    private Integer quantity;

    private BigDecimal presprice;

    private BigDecimal stdprice;

    private String transtype;

    private Date orddate;

    private Date dlvdate;

    private String customerno;

    private String userno;

    private String vndcode;

    private String optstate;

    private String specmark;

    private String locationno;

    private String deptno;

    private String deliveryno;

    private String stateflag;

    private Integer qtyreceive;

    private Date finishdate;

    private Long discount;

    private String errcode;

    private String spflag;

    private String remark;

    private String vipcode;

    private String orddateflag;

    private Integer preqtyord;

    private String answerno;

    private String smccode;

    private String ordflag;

    private BigDecimal sweight;

    private Date dlvdateJp;

    private Date expdateGc;

    private Date dlvmoddate;

    private String remarkGc;

    private BigDecimal redprice;

    private String multpriceFlag;

    private String empordprocess;

    private Integer qtyimput;

    private Date dlvmoddate1;

    private Date dlvmoddate2;

    private String reasonRemark;

    private String produceDeptno;

    private String invoiceno;

    private Date impdate;

    private String transtypeMod;

    private String dlvanswerno1;

    private String dlvanswerno2;

    private Integer qtyProduced;

    private String cnno;

    private String shikomino;

    private Date dlvmoddate3;

    private String ordtype;

    private String ordadmitFlag;

    private String deliveryflag;

    private Integer qtyonhand;

    private String produceFactory;

    private String produceHolon;

    private String errdescription;

    private String salesinfono;

    private String hscode;

    private String greencode;

    private String ordernoGss;

    private String ordernoItemGss;

    private Date indateTheory;

    private Date rcvordDate;

    private Date indateInfact;

    private Date expDate;

    private String exportFlag;

    private String holonName;

    private Date beginproduceDate;

    private Date dlvmodtime1;

    private Date dlvmodtime2;

    private Date dlvmodtime3;

    private String tradecompanyid;

    private static final long serialVersionUID = 1L;

    public String getOrdrno() {
        return ordrno;
    }

    public void setOrdrno(String ordrno) {
        this.ordrno = ordrno == null ? null : ordrno.trim();
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

    public BigDecimal getPresprice() {
        return presprice;
    }

    public void setPresprice(BigDecimal presprice) {
        this.presprice = presprice;
    }

    public BigDecimal getStdprice() {
        return stdprice;
    }

    public void setStdprice(BigDecimal stdprice) {
        this.stdprice = stdprice;
    }

    public String getTranstype() {
        return transtype;
    }

    public void setTranstype(String transtype) {
        this.transtype = transtype == null ? null : transtype.trim();
    }

    public Date getOrddate() {
        return orddate;
    }

    public void setOrddate(Date orddate) {
        this.orddate = orddate;
    }

    public Date getDlvdate() {
        return dlvdate;
    }

    public void setDlvdate(Date dlvdate) {
        this.dlvdate = dlvdate;
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

    public String getVndcode() {
        return vndcode;
    }

    public void setVndcode(String vndcode) {
        this.vndcode = vndcode == null ? null : vndcode.trim();
    }

    public String getOptstate() {
        return optstate;
    }

    public void setOptstate(String optstate) {
        this.optstate = optstate == null ? null : optstate.trim();
    }

    public String getSpecmark() {
        return specmark;
    }

    public void setSpecmark(String specmark) {
        this.specmark = specmark == null ? null : specmark.trim();
    }

    public String getLocationno() {
        return locationno;
    }

    public void setLocationno(String locationno) {
        this.locationno = locationno == null ? null : locationno.trim();
    }

    public String getDeptno() {
        return deptno;
    }

    public void setDeptno(String deptno) {
        this.deptno = deptno == null ? null : deptno.trim();
    }

    public String getDeliveryno() {
        return deliveryno;
    }

    public void setDeliveryno(String deliveryno) {
        this.deliveryno = deliveryno == null ? null : deliveryno.trim();
    }

    public String getStateflag() {
        return stateflag;
    }

    public void setStateflag(String stateflag) {
        this.stateflag = stateflag == null ? null : stateflag.trim();
    }

    public Integer getQtyreceive() {
        return qtyreceive;
    }

    public void setQtyreceive(Integer qtyreceive) {
        this.qtyreceive = qtyreceive;
    }

    public Date getFinishdate() {
        return finishdate;
    }

    public void setFinishdate(Date finishdate) {
        this.finishdate = finishdate;
    }

    public Long getDiscount() {
        return discount;
    }

    public void setDiscount(Long discount) {
        this.discount = discount;
    }

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode == null ? null : errcode.trim();
    }

    public String getSpflag() {
        return spflag;
    }

    public void setSpflag(String spflag) {
        this.spflag = spflag == null ? null : spflag.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getVipcode() {
        return vipcode;
    }

    public void setVipcode(String vipcode) {
        this.vipcode = vipcode == null ? null : vipcode.trim();
    }

    public String getOrddateflag() {
        return orddateflag;
    }

    public void setOrddateflag(String orddateflag) {
        this.orddateflag = orddateflag == null ? null : orddateflag.trim();
    }

    public Integer getPreqtyord() {
        return preqtyord;
    }

    public void setPreqtyord(Integer preqtyord) {
        this.preqtyord = preqtyord;
    }

    public String getAnswerno() {
        return answerno;
    }

    public void setAnswerno(String answerno) {
        this.answerno = answerno == null ? null : answerno.trim();
    }

    public String getSmccode() {
        return smccode;
    }

    public void setSmccode(String smccode) {
        this.smccode = smccode == null ? null : smccode.trim();
    }

    public String getOrdflag() {
        return ordflag;
    }

    public void setOrdflag(String ordflag) {
        this.ordflag = ordflag == null ? null : ordflag.trim();
    }

    public BigDecimal getSweight() {
        return sweight;
    }

    public void setSweight(BigDecimal sweight) {
        this.sweight = sweight;
    }

    public Date getDlvdateJp() {
        return dlvdateJp;
    }

    public void setDlvdateJp(Date dlvdateJp) {
        this.dlvdateJp = dlvdateJp;
    }

    public Date getExpdateGc() {
        return expdateGc;
    }

    public void setExpdateGc(Date expdateGc) {
        this.expdateGc = expdateGc;
    }

    public Date getDlvmoddate() {
        return dlvmoddate;
    }

    public void setDlvmoddate(Date dlvmoddate) {
        this.dlvmoddate = dlvmoddate;
    }

    public String getRemarkGc() {
        return remarkGc;
    }

    public void setRemarkGc(String remarkGc) {
        this.remarkGc = remarkGc == null ? null : remarkGc.trim();
    }

    public BigDecimal getRedprice() {
        return redprice;
    }

    public void setRedprice(BigDecimal redprice) {
        this.redprice = redprice;
    }

    public String getMultpriceFlag() {
        return multpriceFlag;
    }

    public void setMultpriceFlag(String multpriceFlag) {
        this.multpriceFlag = multpriceFlag == null ? null : multpriceFlag.trim();
    }

    public String getEmpordprocess() {
        return empordprocess;
    }

    public void setEmpordprocess(String empordprocess) {
        this.empordprocess = empordprocess == null ? null : empordprocess.trim();
    }

    public Integer getQtyimput() {
        return qtyimput;
    }

    public void setQtyimput(Integer qtyimput) {
        this.qtyimput = qtyimput;
    }

    public Date getDlvmoddate1() {
        return dlvmoddate1;
    }

    public void setDlvmoddate1(Date dlvmoddate1) {
        this.dlvmoddate1 = dlvmoddate1;
    }

    public Date getDlvmoddate2() {
        return dlvmoddate2;
    }

    public void setDlvmoddate2(Date dlvmoddate2) {
        this.dlvmoddate2 = dlvmoddate2;
    }

    public String getReasonRemark() {
        return reasonRemark;
    }

    public void setReasonRemark(String reasonRemark) {
        this.reasonRemark = reasonRemark == null ? null : reasonRemark.trim();
    }

    public String getProduceDeptno() {
        return produceDeptno;
    }

    public void setProduceDeptno(String produceDeptno) {
        this.produceDeptno = produceDeptno == null ? null : produceDeptno.trim();
    }

    public String getInvoiceno() {
        return invoiceno;
    }

    public void setInvoiceno(String invoiceno) {
        this.invoiceno = invoiceno == null ? null : invoiceno.trim();
    }

    public Date getImpdate() {
        return impdate;
    }

    public void setImpdate(Date impdate) {
        this.impdate = impdate;
    }

    public String getTranstypeMod() {
        return transtypeMod;
    }

    public void setTranstypeMod(String transtypeMod) {
        this.transtypeMod = transtypeMod == null ? null : transtypeMod.trim();
    }

    public String getDlvanswerno1() {
        return dlvanswerno1;
    }

    public void setDlvanswerno1(String dlvanswerno1) {
        this.dlvanswerno1 = dlvanswerno1 == null ? null : dlvanswerno1.trim();
    }

    public String getDlvanswerno2() {
        return dlvanswerno2;
    }

    public void setDlvanswerno2(String dlvanswerno2) {
        this.dlvanswerno2 = dlvanswerno2 == null ? null : dlvanswerno2.trim();
    }

    public Integer getQtyProduced() {
        return qtyProduced;
    }

    public void setQtyProduced(Integer qtyProduced) {
        this.qtyProduced = qtyProduced;
    }

    public String getCnno() {
        return cnno;
    }

    public void setCnno(String cnno) {
        this.cnno = cnno == null ? null : cnno.trim();
    }

    public String getShikomino() {
        return shikomino;
    }

    public void setShikomino(String shikomino) {
        this.shikomino = shikomino == null ? null : shikomino.trim();
    }

    public Date getDlvmoddate3() {
        return dlvmoddate3;
    }

    public void setDlvmoddate3(Date dlvmoddate3) {
        this.dlvmoddate3 = dlvmoddate3;
    }

    public String getOrdtype() {
        return ordtype;
    }

    public void setOrdtype(String ordtype) {
        this.ordtype = ordtype == null ? null : ordtype.trim();
    }

    public String getOrdadmitFlag() {
        return ordadmitFlag;
    }

    public void setOrdadmitFlag(String ordadmitFlag) {
        this.ordadmitFlag = ordadmitFlag == null ? null : ordadmitFlag.trim();
    }

    public String getDeliveryflag() {
        return deliveryflag;
    }

    public void setDeliveryflag(String deliveryflag) {
        this.deliveryflag = deliveryflag == null ? null : deliveryflag.trim();
    }

    public Integer getQtyonhand() {
        return qtyonhand;
    }

    public void setQtyonhand(Integer qtyonhand) {
        this.qtyonhand = qtyonhand;
    }

    public String getProduceFactory() {
        return produceFactory;
    }

    public void setProduceFactory(String produceFactory) {
        this.produceFactory = produceFactory == null ? null : produceFactory.trim();
    }

    public String getProduceHolon() {
        return produceHolon;
    }

    public void setProduceHolon(String produceHolon) {
        this.produceHolon = produceHolon == null ? null : produceHolon.trim();
    }

    public String getErrdescription() {
        return errdescription;
    }

    public void setErrdescription(String errdescription) {
        this.errdescription = errdescription == null ? null : errdescription.trim();
    }

    public String getSalesinfono() {
        return salesinfono;
    }

    public void setSalesinfono(String salesinfono) {
        this.salesinfono = salesinfono == null ? null : salesinfono.trim();
    }

    public String getHscode() {
        return hscode;
    }

    public void setHscode(String hscode) {
        this.hscode = hscode == null ? null : hscode.trim();
    }

    public String getGreencode() {
        return greencode;
    }

    public void setGreencode(String greencode) {
        this.greencode = greencode == null ? null : greencode.trim();
    }

    public String getOrdernoGss() {
        return ordernoGss;
    }

    public void setOrdernoGss(String ordernoGss) {
        this.ordernoGss = ordernoGss == null ? null : ordernoGss.trim();
    }

    public String getOrdernoItemGss() {
        return ordernoItemGss;
    }

    public void setOrdernoItemGss(String ordernoItemGss) {
        this.ordernoItemGss = ordernoItemGss == null ? null : ordernoItemGss.trim();
    }

    public Date getIndateTheory() {
        return indateTheory;
    }

    public void setIndateTheory(Date indateTheory) {
        this.indateTheory = indateTheory;
    }

    public Date getRcvordDate() {
        return rcvordDate;
    }

    public void setRcvordDate(Date rcvordDate) {
        this.rcvordDate = rcvordDate;
    }

    public Date getIndateInfact() {
        return indateInfact;
    }

    public void setIndateInfact(Date indateInfact) {
        this.indateInfact = indateInfact;
    }

    public Date getExpDate() {
        return expDate;
    }

    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }

    public String getExportFlag() {
        return exportFlag;
    }

    public void setExportFlag(String exportFlag) {
        this.exportFlag = exportFlag == null ? null : exportFlag.trim();
    }

    public String getHolonName() {
        return holonName;
    }

    public void setHolonName(String holonName) {
        this.holonName = holonName == null ? null : holonName.trim();
    }

    public Date getBeginproduceDate() {
        return beginproduceDate;
    }

    public void setBeginproduceDate(Date beginproduceDate) {
        this.beginproduceDate = beginproduceDate;
    }

    public Date getDlvmodtime1() {
        return dlvmodtime1;
    }

    public void setDlvmodtime1(Date dlvmodtime1) {
        this.dlvmodtime1 = dlvmodtime1;
    }

    public Date getDlvmodtime2() {
        return dlvmodtime2;
    }

    public void setDlvmodtime2(Date dlvmodtime2) {
        this.dlvmodtime2 = dlvmodtime2;
    }

    public Date getDlvmodtime3() {
        return dlvmodtime3;
    }

    public void setDlvmodtime3(Date dlvmodtime3) {
        this.dlvmodtime3 = dlvmodtime3;
    }

    public String getTradecompanyid() {
        return tradecompanyid;
    }

    public void setTradecompanyid(String tradecompanyid) {
        this.tradecompanyid = tradecompanyid == null ? null : tradecompanyid.trim();
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
        OrderdataBj other = (OrderdataBj) that;
        return (this.getOrdrno() == null ? other.getOrdrno() == null : this.getOrdrno().equals(other.getOrdrno()))
            && (this.getModelno() == null ? other.getModelno() == null : this.getModelno().equals(other.getModelno()))
            && (this.getQuantity() == null ? other.getQuantity() == null : this.getQuantity().equals(other.getQuantity()))
            && (this.getPresprice() == null ? other.getPresprice() == null : this.getPresprice().equals(other.getPresprice()))
            && (this.getStdprice() == null ? other.getStdprice() == null : this.getStdprice().equals(other.getStdprice()))
            && (this.getTranstype() == null ? other.getTranstype() == null : this.getTranstype().equals(other.getTranstype()))
            && (this.getOrddate() == null ? other.getOrddate() == null : this.getOrddate().equals(other.getOrddate()))
            && (this.getDlvdate() == null ? other.getDlvdate() == null : this.getDlvdate().equals(other.getDlvdate()))
            && (this.getCustomerno() == null ? other.getCustomerno() == null : this.getCustomerno().equals(other.getCustomerno()))
            && (this.getUserno() == null ? other.getUserno() == null : this.getUserno().equals(other.getUserno()))
            && (this.getVndcode() == null ? other.getVndcode() == null : this.getVndcode().equals(other.getVndcode()))
            && (this.getOptstate() == null ? other.getOptstate() == null : this.getOptstate().equals(other.getOptstate()))
            && (this.getSpecmark() == null ? other.getSpecmark() == null : this.getSpecmark().equals(other.getSpecmark()))
            && (this.getLocationno() == null ? other.getLocationno() == null : this.getLocationno().equals(other.getLocationno()))
            && (this.getDeptno() == null ? other.getDeptno() == null : this.getDeptno().equals(other.getDeptno()))
            && (this.getDeliveryno() == null ? other.getDeliveryno() == null : this.getDeliveryno().equals(other.getDeliveryno()))
            && (this.getStateflag() == null ? other.getStateflag() == null : this.getStateflag().equals(other.getStateflag()))
            && (this.getQtyreceive() == null ? other.getQtyreceive() == null : this.getQtyreceive().equals(other.getQtyreceive()))
            && (this.getFinishdate() == null ? other.getFinishdate() == null : this.getFinishdate().equals(other.getFinishdate()))
            && (this.getDiscount() == null ? other.getDiscount() == null : this.getDiscount().equals(other.getDiscount()))
            && (this.getErrcode() == null ? other.getErrcode() == null : this.getErrcode().equals(other.getErrcode()))
            && (this.getSpflag() == null ? other.getSpflag() == null : this.getSpflag().equals(other.getSpflag()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getVipcode() == null ? other.getVipcode() == null : this.getVipcode().equals(other.getVipcode()))
            && (this.getOrddateflag() == null ? other.getOrddateflag() == null : this.getOrddateflag().equals(other.getOrddateflag()))
            && (this.getPreqtyord() == null ? other.getPreqtyord() == null : this.getPreqtyord().equals(other.getPreqtyord()))
            && (this.getAnswerno() == null ? other.getAnswerno() == null : this.getAnswerno().equals(other.getAnswerno()))
            && (this.getSmccode() == null ? other.getSmccode() == null : this.getSmccode().equals(other.getSmccode()))
            && (this.getOrdflag() == null ? other.getOrdflag() == null : this.getOrdflag().equals(other.getOrdflag()))
            && (this.getSweight() == null ? other.getSweight() == null : this.getSweight().equals(other.getSweight()))
            && (this.getDlvdateJp() == null ? other.getDlvdateJp() == null : this.getDlvdateJp().equals(other.getDlvdateJp()))
            && (this.getExpdateGc() == null ? other.getExpdateGc() == null : this.getExpdateGc().equals(other.getExpdateGc()))
            && (this.getDlvmoddate() == null ? other.getDlvmoddate() == null : this.getDlvmoddate().equals(other.getDlvmoddate()))
            && (this.getRemarkGc() == null ? other.getRemarkGc() == null : this.getRemarkGc().equals(other.getRemarkGc()))
            && (this.getRedprice() == null ? other.getRedprice() == null : this.getRedprice().equals(other.getRedprice()))
            && (this.getMultpriceFlag() == null ? other.getMultpriceFlag() == null : this.getMultpriceFlag().equals(other.getMultpriceFlag()))
            && (this.getEmpordprocess() == null ? other.getEmpordprocess() == null : this.getEmpordprocess().equals(other.getEmpordprocess()))
            && (this.getQtyimput() == null ? other.getQtyimput() == null : this.getQtyimput().equals(other.getQtyimput()))
            && (this.getDlvmoddate1() == null ? other.getDlvmoddate1() == null : this.getDlvmoddate1().equals(other.getDlvmoddate1()))
            && (this.getDlvmoddate2() == null ? other.getDlvmoddate2() == null : this.getDlvmoddate2().equals(other.getDlvmoddate2()))
            && (this.getReasonRemark() == null ? other.getReasonRemark() == null : this.getReasonRemark().equals(other.getReasonRemark()))
            && (this.getProduceDeptno() == null ? other.getProduceDeptno() == null : this.getProduceDeptno().equals(other.getProduceDeptno()))
            && (this.getInvoiceno() == null ? other.getInvoiceno() == null : this.getInvoiceno().equals(other.getInvoiceno()))
            && (this.getImpdate() == null ? other.getImpdate() == null : this.getImpdate().equals(other.getImpdate()))
            && (this.getTranstypeMod() == null ? other.getTranstypeMod() == null : this.getTranstypeMod().equals(other.getTranstypeMod()))
            && (this.getDlvanswerno1() == null ? other.getDlvanswerno1() == null : this.getDlvanswerno1().equals(other.getDlvanswerno1()))
            && (this.getDlvanswerno2() == null ? other.getDlvanswerno2() == null : this.getDlvanswerno2().equals(other.getDlvanswerno2()))
            && (this.getQtyProduced() == null ? other.getQtyProduced() == null : this.getQtyProduced().equals(other.getQtyProduced()))
            && (this.getCnno() == null ? other.getCnno() == null : this.getCnno().equals(other.getCnno()))
            && (this.getShikomino() == null ? other.getShikomino() == null : this.getShikomino().equals(other.getShikomino()))
            && (this.getDlvmoddate3() == null ? other.getDlvmoddate3() == null : this.getDlvmoddate3().equals(other.getDlvmoddate3()))
            && (this.getOrdtype() == null ? other.getOrdtype() == null : this.getOrdtype().equals(other.getOrdtype()))
            && (this.getOrdadmitFlag() == null ? other.getOrdadmitFlag() == null : this.getOrdadmitFlag().equals(other.getOrdadmitFlag()))
            && (this.getDeliveryflag() == null ? other.getDeliveryflag() == null : this.getDeliveryflag().equals(other.getDeliveryflag()))
            && (this.getQtyonhand() == null ? other.getQtyonhand() == null : this.getQtyonhand().equals(other.getQtyonhand()))
            && (this.getProduceFactory() == null ? other.getProduceFactory() == null : this.getProduceFactory().equals(other.getProduceFactory()))
            && (this.getProduceHolon() == null ? other.getProduceHolon() == null : this.getProduceHolon().equals(other.getProduceHolon()))
            && (this.getErrdescription() == null ? other.getErrdescription() == null : this.getErrdescription().equals(other.getErrdescription()))
            && (this.getSalesinfono() == null ? other.getSalesinfono() == null : this.getSalesinfono().equals(other.getSalesinfono()))
            && (this.getHscode() == null ? other.getHscode() == null : this.getHscode().equals(other.getHscode()))
            && (this.getGreencode() == null ? other.getGreencode() == null : this.getGreencode().equals(other.getGreencode()))
            && (this.getOrdernoGss() == null ? other.getOrdernoGss() == null : this.getOrdernoGss().equals(other.getOrdernoGss()))
            && (this.getOrdernoItemGss() == null ? other.getOrdernoItemGss() == null : this.getOrdernoItemGss().equals(other.getOrdernoItemGss()))
            && (this.getIndateTheory() == null ? other.getIndateTheory() == null : this.getIndateTheory().equals(other.getIndateTheory()))
            && (this.getRcvordDate() == null ? other.getRcvordDate() == null : this.getRcvordDate().equals(other.getRcvordDate()))
            && (this.getIndateInfact() == null ? other.getIndateInfact() == null : this.getIndateInfact().equals(other.getIndateInfact()))
            && (this.getExpDate() == null ? other.getExpDate() == null : this.getExpDate().equals(other.getExpDate()))
            && (this.getExportFlag() == null ? other.getExportFlag() == null : this.getExportFlag().equals(other.getExportFlag()))
            && (this.getHolonName() == null ? other.getHolonName() == null : this.getHolonName().equals(other.getHolonName()))
            && (this.getBeginproduceDate() == null ? other.getBeginproduceDate() == null : this.getBeginproduceDate().equals(other.getBeginproduceDate()))
            && (this.getDlvmodtime1() == null ? other.getDlvmodtime1() == null : this.getDlvmodtime1().equals(other.getDlvmodtime1()))
            && (this.getDlvmodtime2() == null ? other.getDlvmodtime2() == null : this.getDlvmodtime2().equals(other.getDlvmodtime2()))
            && (this.getDlvmodtime3() == null ? other.getDlvmodtime3() == null : this.getDlvmodtime3().equals(other.getDlvmodtime3()))
            && (this.getTradecompanyid() == null ? other.getTradecompanyid() == null : this.getTradecompanyid().equals(other.getTradecompanyid()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getOrdrno() == null) ? 0 : getOrdrno().hashCode());
        result = prime * result + ((getModelno() == null) ? 0 : getModelno().hashCode());
        result = prime * result + ((getQuantity() == null) ? 0 : getQuantity().hashCode());
        result = prime * result + ((getPresprice() == null) ? 0 : getPresprice().hashCode());
        result = prime * result + ((getStdprice() == null) ? 0 : getStdprice().hashCode());
        result = prime * result + ((getTranstype() == null) ? 0 : getTranstype().hashCode());
        result = prime * result + ((getOrddate() == null) ? 0 : getOrddate().hashCode());
        result = prime * result + ((getDlvdate() == null) ? 0 : getDlvdate().hashCode());
        result = prime * result + ((getCustomerno() == null) ? 0 : getCustomerno().hashCode());
        result = prime * result + ((getUserno() == null) ? 0 : getUserno().hashCode());
        result = prime * result + ((getVndcode() == null) ? 0 : getVndcode().hashCode());
        result = prime * result + ((getOptstate() == null) ? 0 : getOptstate().hashCode());
        result = prime * result + ((getSpecmark() == null) ? 0 : getSpecmark().hashCode());
        result = prime * result + ((getLocationno() == null) ? 0 : getLocationno().hashCode());
        result = prime * result + ((getDeptno() == null) ? 0 : getDeptno().hashCode());
        result = prime * result + ((getDeliveryno() == null) ? 0 : getDeliveryno().hashCode());
        result = prime * result + ((getStateflag() == null) ? 0 : getStateflag().hashCode());
        result = prime * result + ((getQtyreceive() == null) ? 0 : getQtyreceive().hashCode());
        result = prime * result + ((getFinishdate() == null) ? 0 : getFinishdate().hashCode());
        result = prime * result + ((getDiscount() == null) ? 0 : getDiscount().hashCode());
        result = prime * result + ((getErrcode() == null) ? 0 : getErrcode().hashCode());
        result = prime * result + ((getSpflag() == null) ? 0 : getSpflag().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getVipcode() == null) ? 0 : getVipcode().hashCode());
        result = prime * result + ((getOrddateflag() == null) ? 0 : getOrddateflag().hashCode());
        result = prime * result + ((getPreqtyord() == null) ? 0 : getPreqtyord().hashCode());
        result = prime * result + ((getAnswerno() == null) ? 0 : getAnswerno().hashCode());
        result = prime * result + ((getSmccode() == null) ? 0 : getSmccode().hashCode());
        result = prime * result + ((getOrdflag() == null) ? 0 : getOrdflag().hashCode());
        result = prime * result + ((getSweight() == null) ? 0 : getSweight().hashCode());
        result = prime * result + ((getDlvdateJp() == null) ? 0 : getDlvdateJp().hashCode());
        result = prime * result + ((getExpdateGc() == null) ? 0 : getExpdateGc().hashCode());
        result = prime * result + ((getDlvmoddate() == null) ? 0 : getDlvmoddate().hashCode());
        result = prime * result + ((getRemarkGc() == null) ? 0 : getRemarkGc().hashCode());
        result = prime * result + ((getRedprice() == null) ? 0 : getRedprice().hashCode());
        result = prime * result + ((getMultpriceFlag() == null) ? 0 : getMultpriceFlag().hashCode());
        result = prime * result + ((getEmpordprocess() == null) ? 0 : getEmpordprocess().hashCode());
        result = prime * result + ((getQtyimput() == null) ? 0 : getQtyimput().hashCode());
        result = prime * result + ((getDlvmoddate1() == null) ? 0 : getDlvmoddate1().hashCode());
        result = prime * result + ((getDlvmoddate2() == null) ? 0 : getDlvmoddate2().hashCode());
        result = prime * result + ((getReasonRemark() == null) ? 0 : getReasonRemark().hashCode());
        result = prime * result + ((getProduceDeptno() == null) ? 0 : getProduceDeptno().hashCode());
        result = prime * result + ((getInvoiceno() == null) ? 0 : getInvoiceno().hashCode());
        result = prime * result + ((getImpdate() == null) ? 0 : getImpdate().hashCode());
        result = prime * result + ((getTranstypeMod() == null) ? 0 : getTranstypeMod().hashCode());
        result = prime * result + ((getDlvanswerno1() == null) ? 0 : getDlvanswerno1().hashCode());
        result = prime * result + ((getDlvanswerno2() == null) ? 0 : getDlvanswerno2().hashCode());
        result = prime * result + ((getQtyProduced() == null) ? 0 : getQtyProduced().hashCode());
        result = prime * result + ((getCnno() == null) ? 0 : getCnno().hashCode());
        result = prime * result + ((getShikomino() == null) ? 0 : getShikomino().hashCode());
        result = prime * result + ((getDlvmoddate3() == null) ? 0 : getDlvmoddate3().hashCode());
        result = prime * result + ((getOrdtype() == null) ? 0 : getOrdtype().hashCode());
        result = prime * result + ((getOrdadmitFlag() == null) ? 0 : getOrdadmitFlag().hashCode());
        result = prime * result + ((getDeliveryflag() == null) ? 0 : getDeliveryflag().hashCode());
        result = prime * result + ((getQtyonhand() == null) ? 0 : getQtyonhand().hashCode());
        result = prime * result + ((getProduceFactory() == null) ? 0 : getProduceFactory().hashCode());
        result = prime * result + ((getProduceHolon() == null) ? 0 : getProduceHolon().hashCode());
        result = prime * result + ((getErrdescription() == null) ? 0 : getErrdescription().hashCode());
        result = prime * result + ((getSalesinfono() == null) ? 0 : getSalesinfono().hashCode());
        result = prime * result + ((getHscode() == null) ? 0 : getHscode().hashCode());
        result = prime * result + ((getGreencode() == null) ? 0 : getGreencode().hashCode());
        result = prime * result + ((getOrdernoGss() == null) ? 0 : getOrdernoGss().hashCode());
        result = prime * result + ((getOrdernoItemGss() == null) ? 0 : getOrdernoItemGss().hashCode());
        result = prime * result + ((getIndateTheory() == null) ? 0 : getIndateTheory().hashCode());
        result = prime * result + ((getRcvordDate() == null) ? 0 : getRcvordDate().hashCode());
        result = prime * result + ((getIndateInfact() == null) ? 0 : getIndateInfact().hashCode());
        result = prime * result + ((getExpDate() == null) ? 0 : getExpDate().hashCode());
        result = prime * result + ((getExportFlag() == null) ? 0 : getExportFlag().hashCode());
        result = prime * result + ((getHolonName() == null) ? 0 : getHolonName().hashCode());
        result = prime * result + ((getBeginproduceDate() == null) ? 0 : getBeginproduceDate().hashCode());
        result = prime * result + ((getDlvmodtime1() == null) ? 0 : getDlvmodtime1().hashCode());
        result = prime * result + ((getDlvmodtime2() == null) ? 0 : getDlvmodtime2().hashCode());
        result = prime * result + ((getDlvmodtime3() == null) ? 0 : getDlvmodtime3().hashCode());
        result = prime * result + ((getTradecompanyid() == null) ? 0 : getTradecompanyid().hashCode());
        return result;
    }
}