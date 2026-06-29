package com.sales.ops.dto.poDeliver;

import java.util.Date;

public class ChangeFromSupplier {

	private Long id;

	private String maincode;

	private String pono;

	private Integer lineitem;

	private String suppliercode;

	private String modelno;

	// 接单号
	private String supplierordermainno;

	private String supplierorderlineno;

	// 接单日期
	private Date receiveorderdate;

	// 接单时返回运输方式
	private String replytranstype;

	// 返信
	private Date latestdeliverytime;

	private String srcdeliverytime;

	// 工厂预计入库日
	private Date deliverytimeWPlan;

	// 工厂预计完工日
	private Date deliverytimeHPlan;

	// 计划运输方式
	private String plantranstype;

	// 计划交付返信备注信息
	private String planreamrk;

	// 计划交付数量
	private Integer planqty;

	// 出库区分
	private String goodssourcecode;

	private String goodsourcecoderemark;

	// 生产HL
	private String produceunit;

	// 开始生产时间
	private Date producestarttime;

    // 发单文件位置信息
    private String sendFilePath;

	// 供应商出库时间（工厂实际入库日）
	private Date deliverytimeWFact;

	// 工厂实际完工日
	private Date deliverytimeHFact;

	// 交付运输方式
	private String facttranstype;

	// 返信表id，对应采购返信plan表sourceid字段
	private Long planid;

	// 实际返信id，对应采购返信fact表sourceid字段
	private Long factid;

	// 实际到货数量
	private Integer factqty;

	private String invoiceNo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMaincode() {
		return maincode;
	}

	public void setMaincode(String maincode) {
		this.maincode = maincode;
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

	public String getSuppliercode() {
		return suppliercode;
	}

	public void setSuppliercode(String suppliercode) {
		this.suppliercode = suppliercode;
	}

	public String getModelno() {
		return modelno;
	}

	public void setModelno(String modelno) {
		this.modelno = modelno;
	}

	public String getSupplierordermainno() {
		return supplierordermainno;
	}

	public void setSupplierordermainno(String supplierordermainno) {
		this.supplierordermainno = supplierordermainno;
	}

	public String getSupplierorderlineno() {
		return supplierorderlineno;
	}

	public void setSupplierorderlineno(String supplierorderlineno) {
		this.supplierorderlineno = supplierorderlineno;
	}

	public Date getReceiveorderdate() {
		return receiveorderdate;
	}

	public void setReceiveorderdate(Date receiveorderdate) {
		this.receiveorderdate = receiveorderdate;
	}

	public String getReplytranstype() {
		return replytranstype;
	}

	public void setReplytranstype(String replytranstype) {
		this.replytranstype = replytranstype;
	}

	public Date getLatestdeliverytime() {
		return latestdeliverytime;
	}

	public void setLatestdeliverytime(Date latestdeliverytime) {
		this.latestdeliverytime = latestdeliverytime;
	}

	public String getSrcdeliverytime() {
		return srcdeliverytime;
	}

	public void setSrcdeliverytime(String srcdeliverytime) {
		this.srcdeliverytime = srcdeliverytime;
	}

	public String getPlantranstype() {
		return plantranstype;
	}

	public void setPlantranstype(String plantranstype) {
		this.plantranstype = plantranstype;
	}

	public String getPlanreamrk() {
		return planreamrk;
	}

	public void setPlanreamrk(String planreamrk) {
		this.planreamrk = planreamrk;
	}

	public String getGoodssourcecode() {
		return goodssourcecode;
	}

	public void setGoodssourcecode(String goodssourcecode) {
		this.goodssourcecode = goodssourcecode;
	}

	public String getGoodsourcecoderemark() {
		return goodsourcecoderemark;
	}

	public void setGoodsourcecoderemark(String goodsourcecoderemark) {
		this.goodsourcecoderemark = goodsourcecoderemark;
	}

	public String getProduceunit() {
		return produceunit;
	}

	public void setProduceunit(String produceunit) {
		this.produceunit = produceunit;
	}

	public Date getProducestarttime() {
		return producestarttime;
	}

	public void setProducestarttime(Date producestarttime) {
		this.producestarttime = producestarttime;
	}

	public String getFacttranstype() {
		return facttranstype;
	}

	public void setFacttranstype(String facttranstype) {
		this.facttranstype = facttranstype;
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

	public Date getDeliverytimeWFact() {
		return deliverytimeWFact;
	}

	public void setDeliverytimeWFact(Date deliverytimeWFact) {
		this.deliverytimeWFact = deliverytimeWFact;
	}

	public Date getDeliverytimeHFact() {
		return deliverytimeHFact;
	}

	public void setDeliverytimeHFact(Date deliverytimeHFact) {
		this.deliverytimeHFact = deliverytimeHFact;
	}

	public Date getDeliverytimeWPlan() {
		return deliverytimeWPlan;
	}

	public void setDeliverytimeWPlan(Date deliverytimeWPlan) {
		this.deliverytimeWPlan = deliverytimeWPlan;
	}

	public Date getDeliverytimeHPlan() {
		return deliverytimeHPlan;
	}

	public void setDeliverytimeHPlan(Date deliverytimeHPlan) {
		this.deliverytimeHPlan = deliverytimeHPlan;
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

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

    public String getSendFilePath() {
        return sendFilePath;
    }

    public void setSendFilePath(String sendFilePath) {
        this.sendFilePath = sendFilePath;
    }
}
