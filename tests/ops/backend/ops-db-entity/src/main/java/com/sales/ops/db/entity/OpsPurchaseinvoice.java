package com.sales.ops.db.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OpsPurchaseinvoice implements Serializable {
    private Long id;

    private String pono;

    private Integer lineitem;

    private String orderno;

    private Integer itemno;

    private Integer splititemno;

    private String modelno;

    private Integer quantity;

    private BigDecimal stdprice;

    private String transtype;

    private Date purchasedate;

    private Date hopedeliverydate;

    private String supplierid;

    private String statecode;

    private String specmark;

    private String receivewarehouseid;

    private String remark;

    private Date hopeexportdate;

    private String inqno;

    private String shikomianswerno;

    private String deptno;

    private String deliveryflag;

    private String smccode;

    private String hscode;

    private String greencode;

    private String customerno;

    private String userno;

    private String locationno;

    private String vipcode;

    private String salesinfono;

    private String purchasetype;

    private String supplierpartno;

    private BigDecimal importfobpriceoriginal;

    private String importcurrencyid;

    private Integer jpsplitvt;

    private String operatorid;

    private String cnno;

    private Long invoiceid;

    private String invoiceno;

    private String replyorderno;

    private Date replyorderdate;

    private Date replyexportdate;

    private Integer qtytrans;

    private Integer qtyreceive;

    private Integer qtyimport;

    private Date impdate;

    private Date dlvmoddate1;

    private Date dlvmoddate2;

    private Date dlvmoddate3;

    private Date dlvmoddate1time;

    private Date dlvmoddate2time;

    private Date dlvmoddate3time;

    private String reasonremark;

    private String transtypemod;

    private String dlvanswerno1;

    private String dlvanswerno2;

    private String producefactory;

    private String produceholon;

    private String errdescription;

    private Date imdatetheory;

    private Date imdateinfact;

    private Date beginproducedate;

    private String barcode;

    private String caseno;

    private Date sendtime;

    private Date updatetime;

    private String serverremark;

    private Date portArrivedate;

    private Date customsDate;

    private BigDecimal poPrice;

    private String inspectiontype;

    private String add3c;

    private Date facexpdate;

    private String ordtype;

    private String hopereceivewarehouse;

    private String filepath;

    private String sendversion;

    private String infojson;

    private String srcDeliveryTime;

    private String transtypeFact;

    private Date imdatetheoryafter;

    private String subCode;

    private String supplierAssignType;

    private String endUser;

    private String prepareorderno;

    private Integer detailstatuscode;

    private String statusdescription;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPono() {
        return pono;
    }

    public void setPono(String pono) {
        this.pono = pono == null ? null : pono.trim();
    }

    public Integer getLineitem() {
        return lineitem;
    }

    public void setLineitem(Integer lineitem) {
        this.lineitem = lineitem;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno == null ? null : orderno.trim();
    }

    public Integer getItemno() {
        return itemno;
    }

    public void setItemno(Integer itemno) {
        this.itemno = itemno;
    }

    public Integer getSplititemno() {
        return splititemno;
    }

    public void setSplititemno(Integer splititemno) {
        this.splititemno = splititemno;
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

    public Date getPurchasedate() {
        return purchasedate;
    }

    public void setPurchasedate(Date purchasedate) {
        this.purchasedate = purchasedate;
    }

    public Date getHopedeliverydate() {
        return hopedeliverydate;
    }

    public void setHopedeliverydate(Date hopedeliverydate) {
        this.hopedeliverydate = hopedeliverydate;
    }

    public String getSupplierid() {
        return supplierid;
    }

    public void setSupplierid(String supplierid) {
        this.supplierid = supplierid == null ? null : supplierid.trim();
    }

    public String getStatecode() {
        return statecode;
    }

    public void setStatecode(String statecode) {
        this.statecode = statecode == null ? null : statecode.trim();
    }

    public String getSpecmark() {
        return specmark;
    }

    public void setSpecmark(String specmark) {
        this.specmark = specmark == null ? null : specmark.trim();
    }

    public String getReceivewarehouseid() {
        return receivewarehouseid;
    }

    public void setReceivewarehouseid(String receivewarehouseid) {
        this.receivewarehouseid = receivewarehouseid == null ? null : receivewarehouseid.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getHopeexportdate() {
        return hopeexportdate;
    }

    public void setHopeexportdate(Date hopeexportdate) {
        this.hopeexportdate = hopeexportdate;
    }

    public String getInqno() {
        return inqno;
    }

    public void setInqno(String inqno) {
        this.inqno = inqno == null ? null : inqno.trim();
    }

    public String getShikomianswerno() {
        return shikomianswerno;
    }

    public void setShikomianswerno(String shikomianswerno) {
        this.shikomianswerno = shikomianswerno == null ? null : shikomianswerno.trim();
    }

    public String getDeptno() {
        return deptno;
    }

    public void setDeptno(String deptno) {
        this.deptno = deptno == null ? null : deptno.trim();
    }

    public String getDeliveryflag() {
        return deliveryflag;
    }

    public void setDeliveryflag(String deliveryflag) {
        this.deliveryflag = deliveryflag == null ? null : deliveryflag.trim();
    }

    public String getSmccode() {
        return smccode;
    }

    public void setSmccode(String smccode) {
        this.smccode = smccode == null ? null : smccode.trim();
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

    public String getLocationno() {
        return locationno;
    }

    public void setLocationno(String locationno) {
        this.locationno = locationno == null ? null : locationno.trim();
    }

    public String getVipcode() {
        return vipcode;
    }

    public void setVipcode(String vipcode) {
        this.vipcode = vipcode == null ? null : vipcode.trim();
    }

    public String getSalesinfono() {
        return salesinfono;
    }

    public void setSalesinfono(String salesinfono) {
        this.salesinfono = salesinfono == null ? null : salesinfono.trim();
    }

    public String getPurchasetype() {
        return purchasetype;
    }

    public void setPurchasetype(String purchasetype) {
        this.purchasetype = purchasetype == null ? null : purchasetype.trim();
    }

    public String getSupplierpartno() {
        return supplierpartno;
    }

    public void setSupplierpartno(String supplierpartno) {
        this.supplierpartno = supplierpartno == null ? null : supplierpartno.trim();
    }

    public BigDecimal getImportfobpriceoriginal() {
        return importfobpriceoriginal;
    }

    public void setImportfobpriceoriginal(BigDecimal importfobpriceoriginal) {
        this.importfobpriceoriginal = importfobpriceoriginal;
    }

    public String getImportcurrencyid() {
        return importcurrencyid;
    }

    public void setImportcurrencyid(String importcurrencyid) {
        this.importcurrencyid = importcurrencyid == null ? null : importcurrencyid.trim();
    }

    public Integer getJpsplitvt() {
        return jpsplitvt;
    }

    public void setJpsplitvt(Integer jpsplitvt) {
        this.jpsplitvt = jpsplitvt;
    }

    public String getOperatorid() {
        return operatorid;
    }

    public void setOperatorid(String operatorid) {
        this.operatorid = operatorid == null ? null : operatorid.trim();
    }

    public String getCnno() {
        return cnno;
    }

    public void setCnno(String cnno) {
        this.cnno = cnno == null ? null : cnno.trim();
    }

    public Long getInvoiceid() {
        return invoiceid;
    }

    public void setInvoiceid(Long invoiceid) {
        this.invoiceid = invoiceid;
    }

    public String getInvoiceno() {
        return invoiceno;
    }

    public void setInvoiceno(String invoiceno) {
        this.invoiceno = invoiceno == null ? null : invoiceno.trim();
    }

    public String getReplyorderno() {
        return replyorderno;
    }

    public void setReplyorderno(String replyorderno) {
        this.replyorderno = replyorderno == null ? null : replyorderno.trim();
    }

    public Date getReplyorderdate() {
        return replyorderdate;
    }

    public void setReplyorderdate(Date replyorderdate) {
        this.replyorderdate = replyorderdate;
    }

    public Date getReplyexportdate() {
        return replyexportdate;
    }

    public void setReplyexportdate(Date replyexportdate) {
        this.replyexportdate = replyexportdate;
    }

    public Integer getQtytrans() {
        return qtytrans;
    }

    public void setQtytrans(Integer qtytrans) {
        this.qtytrans = qtytrans;
    }

    public Integer getQtyreceive() {
        return qtyreceive;
    }

    public void setQtyreceive(Integer qtyreceive) {
        this.qtyreceive = qtyreceive;
    }

    public Integer getQtyimport() {
        return qtyimport;
    }

    public void setQtyimport(Integer qtyimport) {
        this.qtyimport = qtyimport;
    }

    public Date getImpdate() {
        return impdate;
    }

    public void setImpdate(Date impdate) {
        this.impdate = impdate;
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

    public Date getDlvmoddate3() {
        return dlvmoddate3;
    }

    public void setDlvmoddate3(Date dlvmoddate3) {
        this.dlvmoddate3 = dlvmoddate3;
    }

    public Date getDlvmoddate1time() {
        return dlvmoddate1time;
    }

    public void setDlvmoddate1time(Date dlvmoddate1time) {
        this.dlvmoddate1time = dlvmoddate1time;
    }

    public Date getDlvmoddate2time() {
        return dlvmoddate2time;
    }

    public void setDlvmoddate2time(Date dlvmoddate2time) {
        this.dlvmoddate2time = dlvmoddate2time;
    }

    public Date getDlvmoddate3time() {
        return dlvmoddate3time;
    }

    public void setDlvmoddate3time(Date dlvmoddate3time) {
        this.dlvmoddate3time = dlvmoddate3time;
    }

    public String getReasonremark() {
        return reasonremark;
    }

    public void setReasonremark(String reasonremark) {
        this.reasonremark = reasonremark == null ? null : reasonremark.trim();
    }

    public String getTranstypemod() {
        return transtypemod;
    }

    public void setTranstypemod(String transtypemod) {
        this.transtypemod = transtypemod == null ? null : transtypemod.trim();
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

    public String getProducefactory() {
        return producefactory;
    }

    public void setProducefactory(String producefactory) {
        this.producefactory = producefactory == null ? null : producefactory.trim();
    }

    public String getProduceholon() {
        return produceholon;
    }

    public void setProduceholon(String produceholon) {
        this.produceholon = produceholon == null ? null : produceholon.trim();
    }

    public String getErrdescription() {
        return errdescription;
    }

    public void setErrdescription(String errdescription) {
        this.errdescription = errdescription == null ? null : errdescription.trim();
    }

    public Date getImdatetheory() {
        return imdatetheory;
    }

    public void setImdatetheory(Date imdatetheory) {
        this.imdatetheory = imdatetheory;
    }

    public Date getImdateinfact() {
        return imdateinfact;
    }

    public void setImdateinfact(Date imdateinfact) {
        this.imdateinfact = imdateinfact;
    }

    public Date getBeginproducedate() {
        return beginproducedate;
    }

    public void setBeginproducedate(Date beginproducedate) {
        this.beginproducedate = beginproducedate;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode == null ? null : barcode.trim();
    }

    public String getCaseno() {
        return caseno;
    }

    public void setCaseno(String caseno) {
        this.caseno = caseno == null ? null : caseno.trim();
    }

    public Date getSendtime() {
        return sendtime;
    }

    public void setSendtime(Date sendtime) {
        this.sendtime = sendtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getServerremark() {
        return serverremark;
    }

    public void setServerremark(String serverremark) {
        this.serverremark = serverremark == null ? null : serverremark.trim();
    }

    public Date getPortArrivedate() {
        return portArrivedate;
    }

    public void setPortArrivedate(Date portArrivedate) {
        this.portArrivedate = portArrivedate;
    }

    public Date getCustomsDate() {
        return customsDate;
    }

    public void setCustomsDate(Date customsDate) {
        this.customsDate = customsDate;
    }

    public BigDecimal getPoPrice() {
        return poPrice;
    }

    public void setPoPrice(BigDecimal poPrice) {
        this.poPrice = poPrice;
    }

    public String getInspectiontype() {
        return inspectiontype;
    }

    public void setInspectiontype(String inspectiontype) {
        this.inspectiontype = inspectiontype == null ? null : inspectiontype.trim();
    }

    public String getAdd3c() {
        return add3c;
    }

    public void setAdd3c(String add3c) {
        this.add3c = add3c == null ? null : add3c.trim();
    }

    public Date getFacexpdate() {
        return facexpdate;
    }

    public void setFacexpdate(Date facexpdate) {
        this.facexpdate = facexpdate;
    }

    public String getOrdtype() {
        return ordtype;
    }

    public void setOrdtype(String ordtype) {
        this.ordtype = ordtype == null ? null : ordtype.trim();
    }

    public String getHopereceivewarehouse() {
        return hopereceivewarehouse;
    }

    public void setHopereceivewarehouse(String hopereceivewarehouse) {
        this.hopereceivewarehouse = hopereceivewarehouse == null ? null : hopereceivewarehouse.trim();
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath == null ? null : filepath.trim();
    }

    public String getSendversion() {
        return sendversion;
    }

    public void setSendversion(String sendversion) {
        this.sendversion = sendversion == null ? null : sendversion.trim();
    }

    public String getInfojson() {
        return infojson;
    }

    public void setInfojson(String infojson) {
        this.infojson = infojson == null ? null : infojson.trim();
    }

    public String getSrcDeliveryTime() {
        return srcDeliveryTime;
    }

    public void setSrcDeliveryTime(String srcDeliveryTime) {
        this.srcDeliveryTime = srcDeliveryTime == null ? null : srcDeliveryTime.trim();
    }

    public String getTranstypeFact() {
        return transtypeFact;
    }

    public void setTranstypeFact(String transtypeFact) {
        this.transtypeFact = transtypeFact == null ? null : transtypeFact.trim();
    }

    public Date getImdatetheoryafter() {
        return imdatetheoryafter;
    }

    public void setImdatetheoryafter(Date imdatetheoryafter) {
        this.imdatetheoryafter = imdatetheoryafter;
    }

    public String getSubCode() {
        return subCode;
    }

    public void setSubCode(String subCode) {
        this.subCode = subCode == null ? null : subCode.trim();
    }

    public String getSupplierAssignType() {
        return supplierAssignType;
    }

    public void setSupplierAssignType(String supplierAssignType) {
        this.supplierAssignType = supplierAssignType == null ? null : supplierAssignType.trim();
    }

    public String getEndUser() {
        return endUser;
    }

    public void setEndUser(String endUser) {
        this.endUser = endUser == null ? null : endUser.trim();
    }

    public String getPrepareorderno() {
        return prepareorderno;
    }

    public void setPrepareorderno(String prepareorderno) {
        this.prepareorderno = prepareorderno == null ? null : prepareorderno.trim();
    }

    public Integer getDetailstatuscode() {
        return detailstatuscode;
    }

    public void setDetailstatuscode(Integer detailstatuscode) {
        this.detailstatuscode = detailstatuscode;
    }

    public String getStatusdescription() {
        return statusdescription;
    }

    public void setStatusdescription(String statusdescription) {
        this.statusdescription = statusdescription == null ? null : statusdescription.trim();
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
        OpsPurchaseinvoice other = (OpsPurchaseinvoice) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getPono() == null ? other.getPono() == null : this.getPono().equals(other.getPono()))
            && (this.getLineitem() == null ? other.getLineitem() == null : this.getLineitem().equals(other.getLineitem()))
            && (this.getOrderno() == null ? other.getOrderno() == null : this.getOrderno().equals(other.getOrderno()))
            && (this.getItemno() == null ? other.getItemno() == null : this.getItemno().equals(other.getItemno()))
            && (this.getSplititemno() == null ? other.getSplititemno() == null : this.getSplititemno().equals(other.getSplititemno()))
            && (this.getModelno() == null ? other.getModelno() == null : this.getModelno().equals(other.getModelno()))
            && (this.getQuantity() == null ? other.getQuantity() == null : this.getQuantity().equals(other.getQuantity()))
            && (this.getStdprice() == null ? other.getStdprice() == null : this.getStdprice().equals(other.getStdprice()))
            && (this.getTranstype() == null ? other.getTranstype() == null : this.getTranstype().equals(other.getTranstype()))
            && (this.getPurchasedate() == null ? other.getPurchasedate() == null : this.getPurchasedate().equals(other.getPurchasedate()))
            && (this.getHopedeliverydate() == null ? other.getHopedeliverydate() == null : this.getHopedeliverydate().equals(other.getHopedeliverydate()))
            && (this.getSupplierid() == null ? other.getSupplierid() == null : this.getSupplierid().equals(other.getSupplierid()))
            && (this.getStatecode() == null ? other.getStatecode() == null : this.getStatecode().equals(other.getStatecode()))
            && (this.getSpecmark() == null ? other.getSpecmark() == null : this.getSpecmark().equals(other.getSpecmark()))
            && (this.getReceivewarehouseid() == null ? other.getReceivewarehouseid() == null : this.getReceivewarehouseid().equals(other.getReceivewarehouseid()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getHopeexportdate() == null ? other.getHopeexportdate() == null : this.getHopeexportdate().equals(other.getHopeexportdate()))
            && (this.getInqno() == null ? other.getInqno() == null : this.getInqno().equals(other.getInqno()))
            && (this.getShikomianswerno() == null ? other.getShikomianswerno() == null : this.getShikomianswerno().equals(other.getShikomianswerno()))
            && (this.getDeptno() == null ? other.getDeptno() == null : this.getDeptno().equals(other.getDeptno()))
            && (this.getDeliveryflag() == null ? other.getDeliveryflag() == null : this.getDeliveryflag().equals(other.getDeliveryflag()))
            && (this.getSmccode() == null ? other.getSmccode() == null : this.getSmccode().equals(other.getSmccode()))
            && (this.getHscode() == null ? other.getHscode() == null : this.getHscode().equals(other.getHscode()))
            && (this.getGreencode() == null ? other.getGreencode() == null : this.getGreencode().equals(other.getGreencode()))
            && (this.getCustomerno() == null ? other.getCustomerno() == null : this.getCustomerno().equals(other.getCustomerno()))
            && (this.getUserno() == null ? other.getUserno() == null : this.getUserno().equals(other.getUserno()))
            && (this.getLocationno() == null ? other.getLocationno() == null : this.getLocationno().equals(other.getLocationno()))
            && (this.getVipcode() == null ? other.getVipcode() == null : this.getVipcode().equals(other.getVipcode()))
            && (this.getSalesinfono() == null ? other.getSalesinfono() == null : this.getSalesinfono().equals(other.getSalesinfono()))
            && (this.getPurchasetype() == null ? other.getPurchasetype() == null : this.getPurchasetype().equals(other.getPurchasetype()))
            && (this.getSupplierpartno() == null ? other.getSupplierpartno() == null : this.getSupplierpartno().equals(other.getSupplierpartno()))
            && (this.getImportfobpriceoriginal() == null ? other.getImportfobpriceoriginal() == null : this.getImportfobpriceoriginal().equals(other.getImportfobpriceoriginal()))
            && (this.getImportcurrencyid() == null ? other.getImportcurrencyid() == null : this.getImportcurrencyid().equals(other.getImportcurrencyid()))
            && (this.getJpsplitvt() == null ? other.getJpsplitvt() == null : this.getJpsplitvt().equals(other.getJpsplitvt()))
            && (this.getOperatorid() == null ? other.getOperatorid() == null : this.getOperatorid().equals(other.getOperatorid()))
            && (this.getCnno() == null ? other.getCnno() == null : this.getCnno().equals(other.getCnno()))
            && (this.getInvoiceid() == null ? other.getInvoiceid() == null : this.getInvoiceid().equals(other.getInvoiceid()))
            && (this.getInvoiceno() == null ? other.getInvoiceno() == null : this.getInvoiceno().equals(other.getInvoiceno()))
            && (this.getReplyorderno() == null ? other.getReplyorderno() == null : this.getReplyorderno().equals(other.getReplyorderno()))
            && (this.getReplyorderdate() == null ? other.getReplyorderdate() == null : this.getReplyorderdate().equals(other.getReplyorderdate()))
            && (this.getReplyexportdate() == null ? other.getReplyexportdate() == null : this.getReplyexportdate().equals(other.getReplyexportdate()))
            && (this.getQtytrans() == null ? other.getQtytrans() == null : this.getQtytrans().equals(other.getQtytrans()))
            && (this.getQtyreceive() == null ? other.getQtyreceive() == null : this.getQtyreceive().equals(other.getQtyreceive()))
            && (this.getQtyimport() == null ? other.getQtyimport() == null : this.getQtyimport().equals(other.getQtyimport()))
            && (this.getImpdate() == null ? other.getImpdate() == null : this.getImpdate().equals(other.getImpdate()))
            && (this.getDlvmoddate1() == null ? other.getDlvmoddate1() == null : this.getDlvmoddate1().equals(other.getDlvmoddate1()))
            && (this.getDlvmoddate2() == null ? other.getDlvmoddate2() == null : this.getDlvmoddate2().equals(other.getDlvmoddate2()))
            && (this.getDlvmoddate3() == null ? other.getDlvmoddate3() == null : this.getDlvmoddate3().equals(other.getDlvmoddate3()))
            && (this.getDlvmoddate1time() == null ? other.getDlvmoddate1time() == null : this.getDlvmoddate1time().equals(other.getDlvmoddate1time()))
            && (this.getDlvmoddate2time() == null ? other.getDlvmoddate2time() == null : this.getDlvmoddate2time().equals(other.getDlvmoddate2time()))
            && (this.getDlvmoddate3time() == null ? other.getDlvmoddate3time() == null : this.getDlvmoddate3time().equals(other.getDlvmoddate3time()))
            && (this.getReasonremark() == null ? other.getReasonremark() == null : this.getReasonremark().equals(other.getReasonremark()))
            && (this.getTranstypemod() == null ? other.getTranstypemod() == null : this.getTranstypemod().equals(other.getTranstypemod()))
            && (this.getDlvanswerno1() == null ? other.getDlvanswerno1() == null : this.getDlvanswerno1().equals(other.getDlvanswerno1()))
            && (this.getDlvanswerno2() == null ? other.getDlvanswerno2() == null : this.getDlvanswerno2().equals(other.getDlvanswerno2()))
            && (this.getProducefactory() == null ? other.getProducefactory() == null : this.getProducefactory().equals(other.getProducefactory()))
            && (this.getProduceholon() == null ? other.getProduceholon() == null : this.getProduceholon().equals(other.getProduceholon()))
            && (this.getErrdescription() == null ? other.getErrdescription() == null : this.getErrdescription().equals(other.getErrdescription()))
            && (this.getImdatetheory() == null ? other.getImdatetheory() == null : this.getImdatetheory().equals(other.getImdatetheory()))
            && (this.getImdateinfact() == null ? other.getImdateinfact() == null : this.getImdateinfact().equals(other.getImdateinfact()))
            && (this.getBeginproducedate() == null ? other.getBeginproducedate() == null : this.getBeginproducedate().equals(other.getBeginproducedate()))
            && (this.getBarcode() == null ? other.getBarcode() == null : this.getBarcode().equals(other.getBarcode()))
            && (this.getCaseno() == null ? other.getCaseno() == null : this.getCaseno().equals(other.getCaseno()))
            && (this.getSendtime() == null ? other.getSendtime() == null : this.getSendtime().equals(other.getSendtime()))
            && (this.getUpdatetime() == null ? other.getUpdatetime() == null : this.getUpdatetime().equals(other.getUpdatetime()))
            && (this.getServerremark() == null ? other.getServerremark() == null : this.getServerremark().equals(other.getServerremark()))
            && (this.getPortArrivedate() == null ? other.getPortArrivedate() == null : this.getPortArrivedate().equals(other.getPortArrivedate()))
            && (this.getCustomsDate() == null ? other.getCustomsDate() == null : this.getCustomsDate().equals(other.getCustomsDate()))
            && (this.getPoPrice() == null ? other.getPoPrice() == null : this.getPoPrice().equals(other.getPoPrice()))
            && (this.getInspectiontype() == null ? other.getInspectiontype() == null : this.getInspectiontype().equals(other.getInspectiontype()))
            && (this.getAdd3c() == null ? other.getAdd3c() == null : this.getAdd3c().equals(other.getAdd3c()))
            && (this.getFacexpdate() == null ? other.getFacexpdate() == null : this.getFacexpdate().equals(other.getFacexpdate()))
            && (this.getOrdtype() == null ? other.getOrdtype() == null : this.getOrdtype().equals(other.getOrdtype()))
            && (this.getHopereceivewarehouse() == null ? other.getHopereceivewarehouse() == null : this.getHopereceivewarehouse().equals(other.getHopereceivewarehouse()))
            && (this.getFilepath() == null ? other.getFilepath() == null : this.getFilepath().equals(other.getFilepath()))
            && (this.getSendversion() == null ? other.getSendversion() == null : this.getSendversion().equals(other.getSendversion()))
            && (this.getInfojson() == null ? other.getInfojson() == null : this.getInfojson().equals(other.getInfojson()))
            && (this.getSrcDeliveryTime() == null ? other.getSrcDeliveryTime() == null : this.getSrcDeliveryTime().equals(other.getSrcDeliveryTime()))
            && (this.getTranstypeFact() == null ? other.getTranstypeFact() == null : this.getTranstypeFact().equals(other.getTranstypeFact()))
            && (this.getImdatetheoryafter() == null ? other.getImdatetheoryafter() == null : this.getImdatetheoryafter().equals(other.getImdatetheoryafter()))
            && (this.getSubCode() == null ? other.getSubCode() == null : this.getSubCode().equals(other.getSubCode()))
            && (this.getSupplierAssignType() == null ? other.getSupplierAssignType() == null : this.getSupplierAssignType().equals(other.getSupplierAssignType()))
            && (this.getEndUser() == null ? other.getEndUser() == null : this.getEndUser().equals(other.getEndUser()))
            && (this.getPrepareorderno() == null ? other.getPrepareorderno() == null : this.getPrepareorderno().equals(other.getPrepareorderno()))
            && (this.getDetailstatuscode() == null ? other.getDetailstatuscode() == null : this.getDetailstatuscode().equals(other.getDetailstatuscode()))
            && (this.getStatusdescription() == null ? other.getStatusdescription() == null : this.getStatusdescription().equals(other.getStatusdescription()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getPono() == null) ? 0 : getPono().hashCode());
        result = prime * result + ((getLineitem() == null) ? 0 : getLineitem().hashCode());
        result = prime * result + ((getOrderno() == null) ? 0 : getOrderno().hashCode());
        result = prime * result + ((getItemno() == null) ? 0 : getItemno().hashCode());
        result = prime * result + ((getSplititemno() == null) ? 0 : getSplititemno().hashCode());
        result = prime * result + ((getModelno() == null) ? 0 : getModelno().hashCode());
        result = prime * result + ((getQuantity() == null) ? 0 : getQuantity().hashCode());
        result = prime * result + ((getStdprice() == null) ? 0 : getStdprice().hashCode());
        result = prime * result + ((getTranstype() == null) ? 0 : getTranstype().hashCode());
        result = prime * result + ((getPurchasedate() == null) ? 0 : getPurchasedate().hashCode());
        result = prime * result + ((getHopedeliverydate() == null) ? 0 : getHopedeliverydate().hashCode());
        result = prime * result + ((getSupplierid() == null) ? 0 : getSupplierid().hashCode());
        result = prime * result + ((getStatecode() == null) ? 0 : getStatecode().hashCode());
        result = prime * result + ((getSpecmark() == null) ? 0 : getSpecmark().hashCode());
        result = prime * result + ((getReceivewarehouseid() == null) ? 0 : getReceivewarehouseid().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getHopeexportdate() == null) ? 0 : getHopeexportdate().hashCode());
        result = prime * result + ((getInqno() == null) ? 0 : getInqno().hashCode());
        result = prime * result + ((getShikomianswerno() == null) ? 0 : getShikomianswerno().hashCode());
        result = prime * result + ((getDeptno() == null) ? 0 : getDeptno().hashCode());
        result = prime * result + ((getDeliveryflag() == null) ? 0 : getDeliveryflag().hashCode());
        result = prime * result + ((getSmccode() == null) ? 0 : getSmccode().hashCode());
        result = prime * result + ((getHscode() == null) ? 0 : getHscode().hashCode());
        result = prime * result + ((getGreencode() == null) ? 0 : getGreencode().hashCode());
        result = prime * result + ((getCustomerno() == null) ? 0 : getCustomerno().hashCode());
        result = prime * result + ((getUserno() == null) ? 0 : getUserno().hashCode());
        result = prime * result + ((getLocationno() == null) ? 0 : getLocationno().hashCode());
        result = prime * result + ((getVipcode() == null) ? 0 : getVipcode().hashCode());
        result = prime * result + ((getSalesinfono() == null) ? 0 : getSalesinfono().hashCode());
        result = prime * result + ((getPurchasetype() == null) ? 0 : getPurchasetype().hashCode());
        result = prime * result + ((getSupplierpartno() == null) ? 0 : getSupplierpartno().hashCode());
        result = prime * result + ((getImportfobpriceoriginal() == null) ? 0 : getImportfobpriceoriginal().hashCode());
        result = prime * result + ((getImportcurrencyid() == null) ? 0 : getImportcurrencyid().hashCode());
        result = prime * result + ((getJpsplitvt() == null) ? 0 : getJpsplitvt().hashCode());
        result = prime * result + ((getOperatorid() == null) ? 0 : getOperatorid().hashCode());
        result = prime * result + ((getCnno() == null) ? 0 : getCnno().hashCode());
        result = prime * result + ((getInvoiceid() == null) ? 0 : getInvoiceid().hashCode());
        result = prime * result + ((getInvoiceno() == null) ? 0 : getInvoiceno().hashCode());
        result = prime * result + ((getReplyorderno() == null) ? 0 : getReplyorderno().hashCode());
        result = prime * result + ((getReplyorderdate() == null) ? 0 : getReplyorderdate().hashCode());
        result = prime * result + ((getReplyexportdate() == null) ? 0 : getReplyexportdate().hashCode());
        result = prime * result + ((getQtytrans() == null) ? 0 : getQtytrans().hashCode());
        result = prime * result + ((getQtyreceive() == null) ? 0 : getQtyreceive().hashCode());
        result = prime * result + ((getQtyimport() == null) ? 0 : getQtyimport().hashCode());
        result = prime * result + ((getImpdate() == null) ? 0 : getImpdate().hashCode());
        result = prime * result + ((getDlvmoddate1() == null) ? 0 : getDlvmoddate1().hashCode());
        result = prime * result + ((getDlvmoddate2() == null) ? 0 : getDlvmoddate2().hashCode());
        result = prime * result + ((getDlvmoddate3() == null) ? 0 : getDlvmoddate3().hashCode());
        result = prime * result + ((getDlvmoddate1time() == null) ? 0 : getDlvmoddate1time().hashCode());
        result = prime * result + ((getDlvmoddate2time() == null) ? 0 : getDlvmoddate2time().hashCode());
        result = prime * result + ((getDlvmoddate3time() == null) ? 0 : getDlvmoddate3time().hashCode());
        result = prime * result + ((getReasonremark() == null) ? 0 : getReasonremark().hashCode());
        result = prime * result + ((getTranstypemod() == null) ? 0 : getTranstypemod().hashCode());
        result = prime * result + ((getDlvanswerno1() == null) ? 0 : getDlvanswerno1().hashCode());
        result = prime * result + ((getDlvanswerno2() == null) ? 0 : getDlvanswerno2().hashCode());
        result = prime * result + ((getProducefactory() == null) ? 0 : getProducefactory().hashCode());
        result = prime * result + ((getProduceholon() == null) ? 0 : getProduceholon().hashCode());
        result = prime * result + ((getErrdescription() == null) ? 0 : getErrdescription().hashCode());
        result = prime * result + ((getImdatetheory() == null) ? 0 : getImdatetheory().hashCode());
        result = prime * result + ((getImdateinfact() == null) ? 0 : getImdateinfact().hashCode());
        result = prime * result + ((getBeginproducedate() == null) ? 0 : getBeginproducedate().hashCode());
        result = prime * result + ((getBarcode() == null) ? 0 : getBarcode().hashCode());
        result = prime * result + ((getCaseno() == null) ? 0 : getCaseno().hashCode());
        result = prime * result + ((getSendtime() == null) ? 0 : getSendtime().hashCode());
        result = prime * result + ((getUpdatetime() == null) ? 0 : getUpdatetime().hashCode());
        result = prime * result + ((getServerremark() == null) ? 0 : getServerremark().hashCode());
        result = prime * result + ((getPortArrivedate() == null) ? 0 : getPortArrivedate().hashCode());
        result = prime * result + ((getCustomsDate() == null) ? 0 : getCustomsDate().hashCode());
        result = prime * result + ((getPoPrice() == null) ? 0 : getPoPrice().hashCode());
        result = prime * result + ((getInspectiontype() == null) ? 0 : getInspectiontype().hashCode());
        result = prime * result + ((getAdd3c() == null) ? 0 : getAdd3c().hashCode());
        result = prime * result + ((getFacexpdate() == null) ? 0 : getFacexpdate().hashCode());
        result = prime * result + ((getOrdtype() == null) ? 0 : getOrdtype().hashCode());
        result = prime * result + ((getHopereceivewarehouse() == null) ? 0 : getHopereceivewarehouse().hashCode());
        result = prime * result + ((getFilepath() == null) ? 0 : getFilepath().hashCode());
        result = prime * result + ((getSendversion() == null) ? 0 : getSendversion().hashCode());
        result = prime * result + ((getInfojson() == null) ? 0 : getInfojson().hashCode());
        result = prime * result + ((getSrcDeliveryTime() == null) ? 0 : getSrcDeliveryTime().hashCode());
        result = prime * result + ((getTranstypeFact() == null) ? 0 : getTranstypeFact().hashCode());
        result = prime * result + ((getImdatetheoryafter() == null) ? 0 : getImdatetheoryafter().hashCode());
        result = prime * result + ((getSubCode() == null) ? 0 : getSubCode().hashCode());
        result = prime * result + ((getSupplierAssignType() == null) ? 0 : getSupplierAssignType().hashCode());
        result = prime * result + ((getEndUser() == null) ? 0 : getEndUser().hashCode());
        result = prime * result + ((getPrepareorderno() == null) ? 0 : getPrepareorderno().hashCode());
        result = prime * result + ((getDetailstatuscode() == null) ? 0 : getDetailstatuscode().hashCode());
        result = prime * result + ((getStatusdescription() == null) ? 0 : getStatusdescription().hashCode());
        return result;
    }
}