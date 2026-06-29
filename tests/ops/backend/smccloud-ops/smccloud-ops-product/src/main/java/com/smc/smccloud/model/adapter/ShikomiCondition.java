package com.smc.smccloud.model.adapter;

import java.io.Serializable;

/**
 * shikomi查询条件
 * ClassName: ShikomiCondition <br/>  
 * Function: TODO ADD FUNCTION. <br/>  
 * Reason: TODO ADD REASON(可选). <br/>  
 * date: 2021年1月11日 下午5:50:24 <br/>  
 *  
 * @author B74718  
 * @version   
 * @since JDK 1.8
 */
public class ShikomiCondition implements Serializable {

	private static final long serialVersionUID = 585508905951948578L;
	/**
	 * 型号，不能为空
	 */
	private String modelNo;
	
	/**
	 * 客户代码
	 */
	private String customerNo;
	
	/**
	 * 数量
	 */
	private Integer quantity;
	
	

	public ShikomiCondition(String modelNo, String customerNo, Integer quantity) {
		this.modelNo = modelNo;
		this.customerNo = customerNo;
		this.quantity = quantity;
	}

	public ShikomiCondition() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getModelNo() {
		return modelNo;
	}

	public void setModelNo(String modelNo) {
		this.modelNo = modelNo;
	}

	public String getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "ShikomiCondition [modelNo=" + modelNo + ", customerNo=" + customerNo + ", quantity=" + quantity + "]";
	}
	
}
