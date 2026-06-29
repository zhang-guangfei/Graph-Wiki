package com.sales.ops.dto.purchase;

import java.util.Arrays;
import java.util.Date;

public class PurchaseOrderDetailInfo {

	// 采购单号
	private String poOrderFNo;

	private String poOrderNo;

	private Integer poItemNo;

	private Integer poSplitItemNo;

	// 请购单状态
	private StateCode stateCode;

	// 供应商代码
	private String supplierId;

	// 指定运输方式
	private String transType;

	// 指定交货期
	private Date exportDate;

	// 拦截原因
	private String interceptionReason;

	// 下单日期
	private Date sendDate;

	// 出库区分
	private String stockType;

	// 返信纳期
	private Date replyExportDate;

	// 实际运输方式
	private String facTransType;

	// 生产Holon
	private String holon;

	// 实际生产日
	private Date FacProdDate;

	// 实际离厂日
	private Date FacExpDate;

	// 供应商接单号
	private String supplierOrderNo;

	// 供应商接单日期
	private Date supplierOrderDate;

	// bug13893交付系统增加供应商返回原返信
	private String srcdeliverytime;

	// 返信描述
	private String srcdeliverytimedesc;

	public enum StateCode {
		REQUEST(1, "请购处理"), INTERCEPT(2, "请购拦截"), SEND(3, "采购业务已发单"), RECEIVE(4, "供应商已接单"), RECEIVE_ERROR(5,
				"供应商接单异常"), PRODUCT(6,
						"供应商生产中"), TRANSIT(7, "供应商运输中"), FINISH(8, "已完成"), DELETE(9, "已删除"), OTHER(0, "其他"),;
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
	public enum TransferLogisticsStatus {
		SIGNIN(1, "签收"),
		OUT(2, "发货"),
		IN(3, "收货"),;
		private Integer code;
		private String desc;

		TransferLogisticsStatus(Integer code, String desc) {
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
		public static TransferLogisticsStatus parseByCode(Integer code) {
			return Arrays.stream(TransferLogisticsStatus.values()).filter(item -> item.getCode().equals(code)).findFirst()
					.orElse(null);
		}

	}

	public String getPoOrderFNo() {
		return poOrderFNo;
	}

	public void setPoOrderFNo(String poOrderFNo) {
		this.poOrderFNo = poOrderFNo;
	}

	public String getPoOrderNo() {
		return poOrderNo;
	}

	public void setPoOrderNo(String poOrderNo) {
		this.poOrderNo = poOrderNo;
	}

	public Integer getPoItemNo() {
		return poItemNo;
	}

	public void setPoItemNo(Integer poItemNo) {
		this.poItemNo = poItemNo;
	}

	public Integer getPoSplitItemNo() {
		return poSplitItemNo;
	}

	public void setPoSplitItemNo(Integer poSplitItemNo) {
		this.poSplitItemNo = poSplitItemNo;
	}

	public StateCode getStateCode() {
		return stateCode;
	}

	public void setStateCode(StateCode stateCode) {
		this.stateCode = stateCode;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public Date getExportDate() {
		return exportDate;
	}

	public void setExportDate(Date exportDate) {
		this.exportDate = exportDate;
	}

	public String getInterceptionReason() {
		return interceptionReason;
	}

	public void setInterceptionReason(String interceptionReason) {
		this.interceptionReason = interceptionReason;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public String getStockType() {
		return stockType;
	}

	public void setStockType(String stockType) {
		this.stockType = stockType;
	}

	public Date getReplyExportDate() {
		return replyExportDate;
	}

	public void setReplyExportDate(Date replyExportDate) {
		this.replyExportDate = replyExportDate;
	}

	public String getFacTransType() {
		return facTransType;
	}

	public void setFacTransType(String facTransType) {
		this.facTransType = facTransType;
	}

	public String getHolon() {
		return holon;
	}

	public void setHolon(String holon) {
		this.holon = holon;
	}

	public Date getFacProdDate() {
		return FacProdDate;
	}

	public void setFacProdDate(Date facProdDate) {
		FacProdDate = facProdDate;
	}

	public Date getFacExpDate() {
		return FacExpDate;
	}

	public void setFacExpDate(Date facExpDate) {
		FacExpDate = facExpDate;
	}

	public String getSupplierOrderNo() {
		return supplierOrderNo;
	}

	public void setSupplierOrderNo(String supplierOrderNo) {
		this.supplierOrderNo = supplierOrderNo;
	}

	public Date getSupplierOrderDate() {
		return supplierOrderDate;
	}

	public void setSupplierOrderDate(Date supplierOrderDate) {
		this.supplierOrderDate = supplierOrderDate;
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