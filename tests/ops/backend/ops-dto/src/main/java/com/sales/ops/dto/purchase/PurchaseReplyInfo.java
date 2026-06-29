package com.sales.ops.dto.purchase;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PurchaseReplyInfo implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 2206334982948396234L;

	private Date receiveDate;


	// bug10280增加以下三个参数
	private String orderno;

	private Integer itemno;

	private Integer splitno;

	// 发送给供应商的采购单号
	private String pono;

	// 发送给供应商的采购单号对应的lineitem号
	private Integer lineitem;

	// 制造CNno
	private String cnno;

	// 型号
	private String modelno;

	// 供应商文件中运输方式
	private String transtype;

	// 供应商
	private String supplierid;

	// 唯一标识发票号
	private Long invoiceid;

	// 发票号
	private String invoiceno;

	// 供应商订单号
	private String replyorderno;

	// 接单日期
	private Date replyorderdate;

	// 回复货期
	private Date replyexportdate;

	// 到货日期：在途时为预计到货日期，到货后为实际到货日期
	private Date impdate;

	// 在途数量
	private Integer qtyTrans;

	// 到货数量
	private Integer qtyreceive;

	// 交货期变更原因
	private String reasonremark;

	// 催促号
	private String dlvanswerno;

	// 订单区分，生产工厂
	private String producefactory;

	// 生产HOLON
	private String produceholon;

	// 错误描述
	private String errdescription;

	// 条形码
	private String barCode;

	// 箱号
	private String caseNo;

	// 到货仓
	private String warehouseid;

	// 到港时间
	private Date portarrivedate;

	// 报关时间
	private Date customsdate;

	// 开始生产日
	private Date beginproducedate;

	// 采购价格
	private BigDecimal poprice;

	// bug9415 马腾 增加供应商实际出库时间
	private Date facexpdate;

	// 交付系统增加供应商返回原返信
	private String srcdeliverytime;

	private String srcdeliverytimedesc;

	// 返信表id，对应采购返信表sourceid字段
	private Long planid;

	// 返信数量
	private Integer planqty;

	// 实际返信id，对应采购返信fact表sourceid字段
	private Long factid;

	// 实际到货数量
	private Integer factqty;

	// bug14223 交付系统关务发票导入时，报关单号字段的赋值
	private String declarationNo;

	// 工厂实际完工日
	private Date deliverytimeHFact;

	// 供应商出库时间
	private Date deliverytimeAFact;

	// 工厂实际入库日
	private Date deliverytimeWFact;

	// 工厂预计完工日
	private Date deliverytimeHPlan;

	// 工厂预计入库日
	private Date deliverytimeWPlan;

	private String transtypeFact;

	// bug14957 增加调用updateinvoice方法的来源
	private String source;

    // 发单文件位置信息
    private String sendFilePath;

    public String getSendFilePath() {
        return sendFilePath;
    }

    public void setSendFilePath(String sendFilePath) {
        this.sendFilePath = sendFilePath;
    }

    public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getTranstypeFact() {
		return transtypeFact;
	}

	public void setTranstypeFact(String transtypeFact) {
		this.transtypeFact = transtypeFact;
	}

	public Date getDeliverytimeHFact() {
		return deliverytimeHFact;
	}

	public void setDeliverytimeHFact(Date deliverytimeHFact) {
		this.deliverytimeHFact = deliverytimeHFact;
	}

	public Date getDeliverytimeAFact() {
		return deliverytimeAFact;
	}

	public void setDeliverytimeAFact(Date deliverytimeAFact) {
		this.deliverytimeAFact = deliverytimeAFact;
	}

	public Date getDeliverytimeWFact() {
		return deliverytimeWFact;
	}

	public void setDeliverytimeWFact(Date deliverytimeWFact) {
		this.deliverytimeWFact = deliverytimeWFact;
	}

	public Date getDeliverytimeHPlan() {
		return deliverytimeHPlan;
	}

	public void setDeliverytimeHPlan(Date deliverytimeHPlan) {
		this.deliverytimeHPlan = deliverytimeHPlan;
	}

	public Date getDeliverytimeWPlan() {
		return deliverytimeWPlan;
	}

	public void setDeliverytimeWPlan(Date deliverytimeWPlan) {
		this.deliverytimeWPlan = deliverytimeWPlan;
	}

	public String getDeclarationNo() {
		return declarationNo;
	}

	public void setDeclarationNo(String declarationNo) {
		this.declarationNo = declarationNo;
	}

	public String getOrderno() {
		return orderno;
	}

	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}

	public Integer getItemno() {
		return itemno;
	}

	public void setItemno(Integer itemno) {
		this.itemno = itemno;
	}

	public Integer getSplitno() {
		return splitno;
	}

	public void setSplitno(Integer splitno) {
		this.splitno = splitno;
	}

	public String getPono() {
		return pono;
	}

	public void setPono(String pono) {
		this.pono = pono;
	}

	public Integer getLineitem() {
		return lineitem;
	}

	public void setLineitem(Integer lineitem) {
		this.lineitem = lineitem;
	}

	public String getCnno() {
		return cnno;
	}

	public void setCnno(String cnno) {
		this.cnno = cnno;
	}

	public String getTranstype() {
		return transtype;
	}

	public void setTranstype(String transtype) {
		this.transtype = transtype;
	}

	public String getSupplierid() {
		return supplierid;
	}

	public void setSupplierid(String supplierid) {
		this.supplierid = supplierid;
	}

	public String getInvoiceno() {
		return invoiceno;
	}

	public void setInvoiceno(String invoiceno) {
		this.invoiceno = invoiceno;
	}

	public String getReplyorderno() {
		return replyorderno;
	}

	public void setReplyorderno(String replyorderno) {
		this.replyorderno = replyorderno;
	}

	public Date getReplyexportdate() {
		return replyexportdate;
	}

	public void setReplyexportdate(Date replyexportdate) {
		this.replyexportdate = replyexportdate;
	}

	public Integer getQtyreceive() {
		return qtyreceive;
	}

	public void setQtyreceive(Integer qtyreceive) {
		this.qtyreceive = qtyreceive;
	}

	public String getReasonremark() {
		return reasonremark;
	}

	public void setReasonremark(String reasonremark) {
		this.reasonremark = reasonremark;
	}

	public String getDlvanswerno() {
		return dlvanswerno;
	}

	public void setDlvanswerno(String dlvanswerno) {
		this.dlvanswerno = dlvanswerno;
	}

	public String getProducefactory() {
		return producefactory;
	}

	public void setProducefactory(String producefactory) {
		this.producefactory = producefactory;
	}

	public String getProduceholon() {
		return produceholon;
	}

	public void setProduceholon(String produceholon) {
		this.produceholon = produceholon;
	}

	public String getErrdescription() {
		return errdescription;
	}

	public void setErrdescription(String errdescription) {
		this.errdescription = errdescription;
	}

	public Date getReplyorderdate() {
		return replyorderdate;
	}

	public void setReplyorderdate(Date replyorderdate) {
		this.replyorderdate = replyorderdate;
	}

	public Long getInvoiceid() {
		return invoiceid;
	}

	public void setInvoiceid(Long invoiceid) {
		this.invoiceid = invoiceid;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public String getCaseNo() {
		return caseNo;
	}

	public void setCaseNo(String caseNo) {
		this.caseNo = caseNo;
	}

	public Integer getQtyTrans() {
		return qtyTrans;
	}

	public void setQtyTrans(Integer qtyTrans) {
		this.qtyTrans = qtyTrans;
	}

	public Date getImpdate() {
		return impdate;
	}

	public void setImpdate(Date impdate) {
		this.impdate = impdate;
	}

	public String getModelno() {
		return modelno;
	}

	public void setModelno(String modelno) {
		this.modelno = modelno;
	}

	public String getWarehouseid() {
		return warehouseid;
	}

	public void setWarehouseid(String warehouseid) {
		this.warehouseid = warehouseid;
	}

	public Date getPortarrivedate() {
		return portarrivedate;
	}

	public void setPortarrivedate(Date portarrivedate) {
		this.portarrivedate = portarrivedate;
	}

	public Date getCustomsdate() {
		return customsdate;
	}

	public void setCustomsdate(Date customsdate) {
		this.customsdate = customsdate;
	}

	public Date getBeginproducedate() {
		return beginproducedate;
	}

	public void setBeginproducedate(Date beginproducedate) {
		this.beginproducedate = beginproducedate;
	}

	public BigDecimal getPoprice() {
		return poprice;
	}

	public void setPoprice(BigDecimal poprice) {
		this.poprice = poprice;
	}

	public Date getFacexpdate() {
		return facexpdate;
	}

	public void setFacexpdate(Date facexpdate) {
		this.facexpdate = facexpdate;
	}

	public String getSrcdeliverytime() {
		return srcdeliverytime;
	}

	public void setSrcdeliverytime(String srcdeliverytime) {
		this.srcdeliverytime = srcdeliverytime;
	}

	public String getSrcdeliverytimedesc() {
		return srcdeliverytimedesc;
	}

	public void setSrcdeliverytimedesc(String srcdeliverytimedesc) {
		this.srcdeliverytimedesc = srcdeliverytimedesc;
	}

	public Long getPlanid() {
		return planid;
	}

	public void setPlanid(Long planid) {
		this.planid = planid;
	}

	public Integer getPlanqty() {
		return planqty;
	}

	public void setPlanqty(Integer planqty) {
		this.planqty = planqty;
	}

	public Long getFactid() {
		return factid;
	}

	public void setFactid(Long factid) {
		this.factid = factid;
	}

	public Integer getFactqty() {
		return factqty;
	}

	public void setFactqty(Integer factqty) {
		this.factqty = factqty;
	}

	public Date getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}
}
