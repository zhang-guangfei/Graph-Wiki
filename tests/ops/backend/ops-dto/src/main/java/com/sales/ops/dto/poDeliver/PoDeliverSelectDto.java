package com.sales.ops.dto.poDeliver;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sales.ops.db.entity.OpsPoDeliveryFact;
import com.sales.ops.db.entity.OpsPoDeliveryPlan;

import java.util.Date;
import java.util.List;

/**
 * 查询返回值实体
 * 
 * @author SMC892N
 *
 */
public class PoDeliverSelectDto {

	private Long id;

	private String orderNo;

	private Integer itemNo;

	private Integer splitItemNo;

	private String poFullNo;

	private Boolean mergeflag;

	private String specmark;
	private String requestWarehouseId;
	private String purchaseWarehouseId;
	private String hopeReceiveWarehouse;
	private String receiveWarehouseId;
	private Date hopeDeliveryDate;
	private Date replyOrderDate;
	private String shikomiAnswerNo;

	private String deptNo;
	private String customerNo;
	private String userNo;
	private String ordType;
	private Date dlvModDate; //变更交货期
	private Date dlvModDateTime;//最新斌更交货期时间
	private Date dlvModDate1;
	private Date dlvModDate2;
	private Date dlvModDate3;
	private Date dlvModDate1Time;
	private Date dlvModDate2Time;
	private Date dlvModDate3Time;
	private String reasonRemark;
	private Date invoiceDate;





	// 供应商接单号，手配号
	private String replyOrderNo;

	private String modelNo;

	private String supplierId;

	private String stateCode;

	private Integer quantity;

	private Date purchaseDate;

	private Date hopeExportDate;

	private Integer finishQty;

	private String detailStatusCode;

	private String statusDescription;

	// 出库区分
	private String produceFactory;

	private String transType;

	private Date beginProduceDate;

	// 生产中数量
	private Integer produceQty;
	private String produceQtyInfo;

	// 仓库处理中数量
	private Integer wQty;
	private String wQtyInfo;

	// 运输中数量
	private Integer transQty;
	private String transQtyInfo;

	// 收货中数量
	private Integer receivingQty;
	private String receivingQtyInfo;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
	private Date deliveryPlanH;

	@JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
	private Date deliveryPlanW;

	private String deliveryPlanA;

	private Integer planQty;

	private Date planCreateTime;

	private Date deliveryFactH;

	private Date deliveryFactW;

	private Date deliveryFactA;

	private String invoiceno;

	private Integer invoiceid;

	private Date factUpdatetime;

	private String transtypefact;

	// 到港时间
	private Date portarrivedate;

	// 报关时间
	private Date customsdate;

	// 预计到达日期
	private Date preArriveDate;

	// 预计到达日期2 15666
	private Date imdatetheoryafter;

	// 实际到达日期
	private Date arriveDate;

	private String waitImpInvoiceNo;

	private List<OpsPoDeliveryPlan> planList;

	private List<OpsPoDeliveryFact> factList;

	public String endUser;

	private String prepareorderno;

	public String getPrepareorderno() {
		return prepareorderno;
	}

	public void setPrepareorderno(String prepareorderno) {
		this.prepareorderno = prepareorderno;
	}

	public String getEndUser() {
		return endUser;
	}

	public void setEndUser(String endUser) {
		this.endUser = endUser;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getItemNo() {
		return itemNo;
	}

	public void setItemNo(Integer itemNo) {
		this.itemNo = itemNo;
	}

	public Integer getSplitItemNo() {
		return splitItemNo;
	}

	public void setSplitItemNo(Integer splitItemNo) {
		this.splitItemNo = splitItemNo;
	}

	public String getPoFullNo() {
		return poFullNo;
	}

	public void setPoFullNo(String poFullNo) {
		this.poFullNo = poFullNo;
	}

	public Boolean getMergeflag() {
		return mergeflag;
	}

	public void setMergeflag(Boolean mergeflag) {
		this.mergeflag = mergeflag;
	}

	public String getSpecmark() {
		return specmark;
	}

	public void setSpecmark(String specmark) {
		this.specmark = specmark;
	}

	public String getRequestWarehouseId() {
		return requestWarehouseId;
	}

	public void setRequestWarehouseId(String requestWarehouseId) {
		this.requestWarehouseId = requestWarehouseId;
	}

	public String getPurchaseWarehouseId() {
		return purchaseWarehouseId;
	}

	public void setPurchaseWarehouseId(String purchaseWarehouseId) {
		this.purchaseWarehouseId = purchaseWarehouseId;
	}

	public String getHopeReceiveWarehouse() {
		return hopeReceiveWarehouse;
	}

	public void setHopeReceiveWarehouse(String hopeReceiveWarehouse) {
		this.hopeReceiveWarehouse = hopeReceiveWarehouse;
	}

	public String getReceiveWarehouseId() {
		return receiveWarehouseId;
	}

	public void setReceiveWarehouseId(String receiveWarehouseId) {
		this.receiveWarehouseId = receiveWarehouseId;
	}

	public Date getHopeDeliveryDate() {
		return hopeDeliveryDate;
	}

	public void setHopeDeliveryDate(Date hopeDeliveryDate) {
		this.hopeDeliveryDate = hopeDeliveryDate;
	}

	public Date getReplyOrderDate() {
		return replyOrderDate;
	}

	public void setReplyOrderDate(Date replyOrderDate) {
		this.replyOrderDate = replyOrderDate;
	}

	public String getShikomiAnswerNo() {
		return shikomiAnswerNo;
	}

	public void setShikomiAnswerNo(String shikomiAnswerNo) {
		this.shikomiAnswerNo = shikomiAnswerNo;
	}

	public String getDeptNo() {
		return deptNo;
	}

	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}

	public String getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getOrdType() {
		return ordType;
	}

	public void setOrdType(String ordType) {
		this.ordType = ordType;
	}

	public Date getDlvModDate() {
		return dlvModDate;
	}

	public void setDlvModDate(Date dlvModDate) {
		this.dlvModDate = dlvModDate;
	}

	public Date getDlvModDateTime() {
		return dlvModDateTime;
	}

	public void setDlvModDateTime(Date dlvModDateTime) {
		this.dlvModDateTime = dlvModDateTime;
	}

	public Date getDlvModDate1() {
		return dlvModDate1;
	}

	public void setDlvModDate1(Date dlvModDate1) {
		this.dlvModDate1 = dlvModDate1;
	}

	public Date getDlvModDate2() {
		return dlvModDate2;
	}

	public void setDlvModDate2(Date dlvModDate2) {
		this.dlvModDate2 = dlvModDate2;
	}

	public Date getDlvModDate3() {
		return dlvModDate3;
	}

	public void setDlvModDate3(Date dlvModDate3) {
		this.dlvModDate3 = dlvModDate3;
	}

	public Date getDlvModDate1Time() {
		return dlvModDate1Time;
	}

	public void setDlvModDate1Time(Date dlvModDate1Time) {
		this.dlvModDate1Time = dlvModDate1Time;
	}

	public Date getDlvModDate2Time() {
		return dlvModDate2Time;
	}

	public void setDlvModDate2Time(Date dlvModDate2Time) {
		this.dlvModDate2Time = dlvModDate2Time;
	}

	public Date getDlvModDate3Time() {
		return dlvModDate3Time;
	}

	public void setDlvModDate3Time(Date dlvModDate3Time) {
		this.dlvModDate3Time = dlvModDate3Time;
	}

	public String getReasonRemark() {
		return reasonRemark;
	}

	public void setReasonRemark(String reasonRemark) {
		this.reasonRemark = reasonRemark;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getReplyOrderNo() {
		return replyOrderNo;
	}

	public void setReplyOrderNo(String replyOrderNo) {
		this.replyOrderNo = replyOrderNo;
	}

	public String getModelNo() {
		return modelNo;
	}

	public void setModelNo(String modelNo) {
		this.modelNo = modelNo;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public String getDetailStatusCode() {
		return detailStatusCode;
	}

	public void setDetailStatusCode(String detailStatusCode) {
		this.detailStatusCode = detailStatusCode;
	}

	public String getStatusDescription() {
		return statusDescription;
	}

	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public Date getHopeExportDate() {
		return hopeExportDate;
	}

	public void setHopeExportDate(Date hopeExportDate) {
		this.hopeExportDate = hopeExportDate;
	}

	public Integer getFinishQty() {
		return finishQty;
	}

	public void setFinishQty(Integer finishQty) {
		this.finishQty = finishQty;
	}

	public String getProduceFactory() {
		return produceFactory;
	}

	public void setProduceFactory(String produceFactory) {
		this.produceFactory = produceFactory;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public Date getBeginProduceDate() {
		return beginProduceDate;
	}

	public void setBeginProduceDate(Date beginProduceDate) {
		this.beginProduceDate = beginProduceDate;
	}

	public Integer getProduceQty() {
		return produceQty;
	}

	public void setProduceQty(Integer produceQty) {
		this.produceQty = produceQty;
	}

	public String getProduceQtyInfo() {
		return produceQtyInfo;
	}

	public void setProduceQtyInfo(String produceQtyInfo) {
		this.produceQtyInfo = produceQtyInfo;
	}

	public Integer getwQty() {
		return wQty;
	}

	public void setwQty(Integer wQty) {
		this.wQty = wQty;
	}

	public String getwQtyInfo() {
		return wQtyInfo;
	}

	public void setwQtyInfo(String wQtyInfo) {
		this.wQtyInfo = wQtyInfo;
	}

	public Integer getTransQty() {
		return transQty;
	}

	public void setTransQty(Integer transQty) {
		this.transQty = transQty;
	}

	public String getTransQtyInfo() {
		return transQtyInfo;
	}

	public void setTransQtyInfo(String transQtyInfo) {
		this.transQtyInfo = transQtyInfo;
	}

	public Integer getReceivingQty() {
		return receivingQty;
	}

	public void setReceivingQty(Integer receivingQty) {
		this.receivingQty = receivingQty;
	}

	public String getReceivingQtyInfo() {
		return receivingQtyInfo;
	}

	public void setReceivingQtyInfo(String receivingQtyInfo) {
		this.receivingQtyInfo = receivingQtyInfo;
	}

	public Date getDeliveryPlanH() {
		return deliveryPlanH;
	}

	public void setDeliveryPlanH(Date deliveryPlanH) {
		this.deliveryPlanH = deliveryPlanH;
	}

	public Date getDeliveryPlanW() {
		return deliveryPlanW;
	}

	public void setDeliveryPlanW(Date deliveryPlanW) {
		this.deliveryPlanW = deliveryPlanW;
	}

	public String getDeliveryPlanA() {
		return deliveryPlanA;
	}

	public void setDeliveryPlanA(String deliveryPlanA) {
		this.deliveryPlanA = deliveryPlanA;
	}

	public Integer getPlanQty() {
		return planQty;
	}

	public void setPlanQty(Integer planQty) {
		this.planQty = planQty;
	}

	public Date getPlanCreateTime() {
		return planCreateTime;
	}

	public void setPlanCreateTime(Date planCreateTime) {
		this.planCreateTime = planCreateTime;
	}

	public Date getDeliveryFactH() {
		return deliveryFactH;
	}

	public void setDeliveryFactH(Date deliveryFactH) {
		this.deliveryFactH = deliveryFactH;
	}

	public Date getDeliveryFactW() {
		return deliveryFactW;
	}

	public void setDeliveryFactW(Date deliveryFactW) {
		this.deliveryFactW = deliveryFactW;
	}

	public Date getDeliveryFactA() {
		return deliveryFactA;
	}

	public void setDeliveryFactA(Date deliveryFactA) {
		this.deliveryFactA = deliveryFactA;
	}

	public String getInvoiceno() {
		return invoiceno;
	}

	public void setInvoiceno(String invoiceno) {
		this.invoiceno = invoiceno;
	}

	public Integer getInvoiceid() {
		return invoiceid;
	}

	public void setInvoiceid(Integer invoiceid) {
		this.invoiceid = invoiceid;
	}

	public Date getFactUpdatetime() {
		return factUpdatetime;
	}

	public void setFactUpdatetime(Date factUpdatetime) {
		this.factUpdatetime = factUpdatetime;
	}

	public String getTranstypefact() {
		return transtypefact;
	}

	public void setTranstypefact(String transtypefact) {
		this.transtypefact = transtypefact;
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

	public Date getPreArriveDate() {
		return preArriveDate;
	}

	public void setPreArriveDate(Date preArriveDate) {
		this.preArriveDate = preArriveDate;
	}

	public Date getImdatetheoryafter() {
		return imdatetheoryafter;
	}

	public void setImdatetheoryafter(Date imdatetheoryafter) {
		this.imdatetheoryafter = imdatetheoryafter;
	}

	public Date getArriveDate() {
		return arriveDate;
	}

	public void setArriveDate(Date arriveDate) {
		this.arriveDate = arriveDate;
	}

	public String getWaitImpInvoiceNo() {
		return waitImpInvoiceNo;
	}

	public void setWaitImpInvoiceNo(String waitImpInvoiceNo) {
		this.waitImpInvoiceNo = waitImpInvoiceNo;
	}

	public List<OpsPoDeliveryPlan> getPlanList() {
		return planList;
	}

	public void setPlanList(List<OpsPoDeliveryPlan> planList) {
		this.planList = planList;
	}

	public List<OpsPoDeliveryFact> getFactList() {
		return factList;
	}

	public void setFactList(List<OpsPoDeliveryFact> factList) {
		this.factList = factList;
	}
}
