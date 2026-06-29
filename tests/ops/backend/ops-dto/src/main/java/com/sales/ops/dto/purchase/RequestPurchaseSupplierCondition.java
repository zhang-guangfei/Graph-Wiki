package com.sales.ops.dto.purchase;

import java.io.Serializable;
import java.util.Date;

public class RequestPurchaseSupplierCondition implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9166680555964519953L;

	private Date hopedeliverydate;

	private Integer quantity;

	public Date getHopedeliverydate() {
		return hopedeliverydate;
	}

	public void setHopedeliverydate(Date hopedeliverydate) {
		this.hopedeliverydate = hopedeliverydate;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public long getDay() {
		return (this.hopedeliverydate.getTime() - (new Date()).getTime()) / (1000 * 60 * 60 * 24);
	}

}
