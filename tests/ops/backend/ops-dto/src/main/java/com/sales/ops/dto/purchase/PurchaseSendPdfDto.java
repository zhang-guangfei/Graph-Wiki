package com.sales.ops.dto.purchase;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

/**
 * bug13409 采购发单增加pdf文件
 * 
 * @author SMC892N
 *
 */
public class PurchaseSendPdfDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8307566385176481724L;

	public String orderno = StringUtils.EMPTY;

	public String modelno = StringUtils.EMPTY;

	public String qty = StringUtils.EMPTY;

	public String delivery = StringUtils.EMPTY;

	public String shipment = StringUtils.EMPTY;

	public String price = StringUtils.EMPTY;

	// 快递单号
	public String exno = StringUtils.EMPTY;

	public String shikomi = StringUtils.EMPTY;

	public String remark = StringUtils.EMPTY;

	public String apno = StringUtils.EMPTY;

	public String getOrderno() {
		return orderno;
	}

	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}

	public String getModelno() {
		return modelno;
	}

	public void setModelno(String modelno) {
		this.modelno = modelno;
	}

	public String getQty() {
		return qty;
	}

	public void setQty(String qty) {
		this.qty = qty;
	}

	public String getDelivery() {
		return delivery;
	}

	public void setDelivery(String delivery) {
		this.delivery = delivery;
	}

	public String getShipment() {
		return shipment;
	}

	public void setShipment(String shipment) {
		this.shipment = shipment;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getExno() {
		return exno;
	}

	public void setExno(String exno) {
		this.exno = exno;
	}

	public String getShikomi() {
		return shikomi;
	}

	public void setShikomi(String shikomi) {
		this.shikomi = shikomi;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getApno() {
		return apno;
	}

	public void setApno(String apno) {
		this.apno = apno;
	}

}
