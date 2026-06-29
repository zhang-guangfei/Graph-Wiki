package com.sales.ops.dto.purchase;

import com.sales.ops.db.entity.OpsRequestpurchase;

import java.util.List;

/**
 * 采购详情页面，删除所用实体
 *
 */
public class PurchaseDeleteDto {

	/**
	 * 需要筛选的供应商清单
	 */
	private String orderno;

	/**
	 * 筛选完成的数据
	 */
	private  Integer itemno;

	/**
	 * 未筛选的完整数据
	 */
	private Integer splititemno;

	private List<OpsRequestpurchase> requestpurchaselist;

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

	public Integer getSplititemno() {
		return splititemno;
	}

	public void setSplititemno(Integer splititemno) {
		this.splititemno = splititemno;
	}

	public List<OpsRequestpurchase> getRequestpurchaselist() {
		return requestpurchaselist;
	}

	public void setRequestpurchaselist(List<OpsRequestpurchase> requestpurchaselist) {
		this.requestpurchaselist = requestpurchaselist;
	}
}
