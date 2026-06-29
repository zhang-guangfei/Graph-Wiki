package com.sales.ops.dto.purchase;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PurchaseUpdateInfo implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -9155762953622838307L;

	// 发送给供应商的采购单号
	private String pono;

	// 发送给供应商的采购单号对应的lineitem号
	private Integer lineitem;

	// 订单号、子项号、拆分单号
	private String orderno;
	private Integer itemno;
	private Integer splitno;

	// 到港时间
	private Date portarrivedate;

	// 报关时间
	private Date customsdate;

	// 开始生产日
	private Date beginproducedate;

	// 采购价格
	private BigDecimal poprice;

	// 供应商文件中运输方式
	private String transtype;

	// 供应商
	private String supplierid;

	// 供应商订单号
	private String replyorderno;

	// 回复货期
	private Date replyexportdate;

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

	private Date replyorderdate;

	private Date preReceiveDate;

	// bug13893交付系统增加供应商返回原返信
	private String srcdeliverytime;
	// 返信描述
	private String srcdeliverytimedesc;

	public Date getPreReceiveDate() {
		return preReceiveDate;
	}

	public void setPreReceiveDate(Date preReceiveDate) {
		this.preReceiveDate = preReceiveDate;
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

}
