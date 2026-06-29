package com.sales.ops.dto.purchase;

import java.io.Serializable;

public class PurchaseInfoForCancel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6693742378192775253L;

	// 请购单号
	private String requestno;
	private Integer requestItemno;
	private Integer requestSplitno;

	// 采购单号
	private String purchaseno;
	private Integer purchaseItemno;
	private Integer purchaseSplitno;

	// 是否合并
	private boolean merge;

	// 型号
	private String modelno;

	// 数量
	private int requestQuantity;
	private int quantity;

	// 供应商
	private String supplierid;

	// 状态
	private String status;

	// 是否bin
	private boolean bin = false;

	// bin数量
	private Integer binquantity;

	// 供应商同意删单
	private boolean deleteok = false;
	// 制定调库计划
	private boolean transfer = false;

	// bug9927 马腾 是否可删:1可删，2需人工处理，3不可删
	// bug14473 变更iscandelete为boolean类型对应数值，0不可删，1可删
	private Integer iscandelete = 0;

	private Integer risk;// 1:低风险 2：高风险

	private String endUser;//最终用户

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getRequestno() {
		return requestno;
	}

	public void setRequestno(String requestno) {
		this.requestno = requestno;
	}

	public Integer getRequestItemno() {
		return requestItemno;
	}

	public void setRequestItemno(Integer requestItemno) {
		this.requestItemno = requestItemno;
	}

	public Integer getRequestSplitno() {
		return requestSplitno;
	}

	public void setRequestSplitno(Integer requestSplitno) {
		this.requestSplitno = requestSplitno;
	}

	public String getPurchaseno() {
		return purchaseno;
	}

	public void setPurchaseno(String purchaseno) {
		this.purchaseno = purchaseno;
	}

	public Integer getPurchaseItemno() {
		return purchaseItemno;
	}

	public void setPurchaseItemno(Integer purchaseItemno) {
		this.purchaseItemno = purchaseItemno;
	}

	public Integer getPurchaseSplitno() {
		return purchaseSplitno;
	}

	public void setPurchaseSplitno(Integer purchaseSplitno) {
		this.purchaseSplitno = purchaseSplitno;
	}

	public boolean isMerge() {
		return merge;
	}

	public void setMerge(boolean merge) {
		this.merge = merge;
	}

	public String getModelno() {
		return modelno;
	}

	public void setModelno(String modelno) {
		this.modelno = modelno;
	}

	public int getRequestQuantity() {
		return requestQuantity;
	}

	public void setRequestQuantity(int requestQuantity) {
		this.requestQuantity = requestQuantity;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getSupplierid() {
		return supplierid;
	}

	public void setSupplierid(String supplierid) {
		this.supplierid = supplierid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isBin() {
		return bin;
	}

	public void setBin(boolean bin) {
		this.bin = bin;
	}

	public Integer getBinquantity() {
		return binquantity;
	}

	public void setBinquantity(Integer binquantity) {
		this.binquantity = binquantity;
	}

	public boolean isDeleteok() {
		return deleteok;
	}

	public void setDeleteok(boolean deleteok) {
		this.deleteok = deleteok;
	}

	public boolean isTransfer() {
		return transfer;
	}

	public void setTransfer(boolean transfer) {
		this.transfer = transfer;
	}

	public Integer getIscandelete() {
		return iscandelete;
	}

	public void setIscandelete(Integer iscandelete) {
		this.iscandelete = iscandelete;
	}

	public Integer getRisk() {
		return risk;
	}

	public void setRisk(Integer risk) {
		this.risk = risk;
	}

	public String getEndUser() {
		return endUser;
	}

	public void setEndUser(String endUser) {
		this.endUser = endUser;
	}
}
