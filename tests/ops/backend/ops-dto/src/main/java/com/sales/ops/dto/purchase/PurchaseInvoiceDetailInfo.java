package com.sales.ops.dto.purchase;

import java.util.Arrays;
import java.util.Date;

public class PurchaseInvoiceDetailInfo {

	// 发票号
	private String invoiceNo;
	private Long invoiceId;
	// 供应商代码
	private String supplierId;
	// 发票状态代码
	private StateCode stateCode;
	// 发运日期
	private Date shipDate;
	// 预计到货日
	private Date esArrivalDate;

	// 到场日期
	private Date receiveDate;
	// 实际到港日
	private Date facProtDate;
	// 报关开始日
	private Date customsDate;
	// 预计到货仓库
	private String warehouseCode;

	private String transferLogisticsStatus;

	public enum StateCode {

		DELIVERY(1, "已发运"), CUSTOMS(2, "已到港报关中"), ARRIVE(3, "已到货"), OTHER(0, "其他");

		private Integer code;
		private String desc;

		StateCode(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}

		public Integer getCode() {
			return code;
		}

		public String getDesc() {
			return desc;
		}

		// 将code解析成枚举类
		public static StateCode parseByCode(Integer code) {
			return Arrays.stream(StateCode.values()).filter(item -> item.getCode().equals(code)).findFirst()
					.orElse(null);
		}
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public Long getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public StateCode getStateCode() {
		return stateCode;
	}

	public void setStateCode(StateCode stateCode) {
		this.stateCode = stateCode;
	}

	public Date getEsArrivalDate() {
		return esArrivalDate;
	}

	public void setEsArrivalDate(Date esArrivalDate) {
		this.esArrivalDate = esArrivalDate;
	}

	public Date getFacProtDate() {
		return facProtDate;
	}

	public void setFacProtDate(Date facProtDate) {
		this.facProtDate = facProtDate;
	}

	public Date getCustomsDate() {
		return customsDate;
	}

	public void setCustomsDate(Date customsDate) {
		this.customsDate = customsDate;
	}

	public String getWarehouseCode() {
		return warehouseCode;
	}

	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}

	public Date getShipDate() {
		return shipDate;
	}

	public void setShipDate(Date shipDate) {
		this.shipDate = shipDate;
	}

	public Date getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}

	public String getTransferLogisticsStatus() {
		return transferLogisticsStatus;
	}

	public void setTransferLogisticsStatus(String transferLogisticsStatus) {
		this.transferLogisticsStatus = transferLogisticsStatus;
	}
}