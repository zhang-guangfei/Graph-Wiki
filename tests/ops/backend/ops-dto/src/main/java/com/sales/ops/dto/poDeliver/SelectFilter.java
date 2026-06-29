package com.sales.ops.dto.poDeliver;

import com.sales.ops.dto.util.OrderNoInfo;

import java.util.Date;
import java.util.List;

/**
 * 查询筛选条件实体
 * 
 * @author SMC892N
 *
 */
public class SelectFilter {
	private List<String> ponoList;
	private String pono;
	private String orderno;
	private Integer itemno;
	private Integer splitno;
	private String modelNo;
	private List<String> replyOrderNoList;
	private String replyOrderNo;
	private String supplierId;
	private String produceFactory;
	private String stateCode;
	private String detailStatusCode;
	private String purchasetype;
	private String ordtype;
	private String customerno;
	private Date purchaseDateStart;
	private Date purchaseDateEnd;
	private List<OrderNoInfo> orderNoInfoList;

	private List<String> stateCodeList;
	private List<String> detailStatusCodeList;
	private List<String> purchasetypeList;
	private List<String> ordtypeList;
	private List<String> supplierIdList;



	public List<String> getReplyOrderNoList() {
		return replyOrderNoList;
	}

	public void setReplyOrderNoList(List<String> replyOrderNoList) {
		this.replyOrderNoList = replyOrderNoList;
	}

	public List<String> getPonoList() {
		return ponoList;
	}

	public void setPonoList(List<String> ponoList) {
		this.ponoList = ponoList;
	}

	public String getPono() {
		return pono;
	}

	public void setPono(String pono) {
		this.pono = pono;
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

	public String getModelNo() {
		return modelNo;
	}

	public void setModelNo(String modelNo) {
		this.modelNo = modelNo;
	}

	public String getReplyOrderNo() {
		return replyOrderNo;
	}

	public void setReplyOrderNo(String replyOrderNo) {
		this.replyOrderNo = replyOrderNo;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getProduceFactory() {
		return produceFactory;
	}

	public void setProduceFactory(String produceFactory) {
		this.produceFactory = produceFactory;
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

	public String getPurchasetype() {
		return purchasetype;
	}

	public void setPurchasetype(String purchasetype) {
		this.purchasetype = purchasetype;
	}

	public String getOrdtype() {
		return ordtype;
	}

	public void setOrdtype(String ordtype) {
		this.ordtype = ordtype;
	}

	public String getCustomerno() {
		return customerno;
	}

	public void setCustomerno(String customerno) {
		this.customerno = customerno;
	}

	public Date getPurchaseDateStart() {
		return purchaseDateStart;
	}

	public void setPurchaseDateStart(Date purchaseDateStart) {
		this.purchaseDateStart = purchaseDateStart;
	}

	public Date getPurchaseDateEnd() {
		return purchaseDateEnd;
	}

	public void setPurchaseDateEnd(Date purchaseDateEnd) {
		this.purchaseDateEnd = purchaseDateEnd;
	}

	public List<OrderNoInfo> getOrderNoInfoList() {
		return orderNoInfoList;
	}

	public void setOrderNoInfoList(List<OrderNoInfo> orderNoInfoList) {
		this.orderNoInfoList = orderNoInfoList;
	}


	public List<String> getStateCodeList() {
		return stateCodeList;
	}

	public void setStateCodeList(List<String> stateCodeList) {
		this.stateCodeList = stateCodeList;
	}

	public List<String> getDetailStatusCodeList() {
		return detailStatusCodeList;
	}

	public void setDetailStatusCodeList(List<String> detailStatusCodeList) {
		this.detailStatusCodeList = detailStatusCodeList;
	}

	public List<String> getPurchasetypeList() {
		return purchasetypeList;
	}

	public void setPurchasetypeList(List<String> purchasetypeList) {
		this.purchasetypeList = purchasetypeList;
	}

	public List<String> getOrdtypeList() {
		return ordtypeList;
	}

	public void setOrdtypeList(List<String> ordtypeList) {
		this.ordtypeList = ordtypeList;
	}

	public List<String> getSupplierIdList() {
		return supplierIdList;
	}

	public void setSupplierIdList(List<String> supplierIdList) {
		this.supplierIdList = supplierIdList;
	}
}
