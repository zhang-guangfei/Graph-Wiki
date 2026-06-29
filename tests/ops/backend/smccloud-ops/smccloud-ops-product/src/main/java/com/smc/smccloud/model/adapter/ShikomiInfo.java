package com.smc.smccloud.model.adapter;

import java.util.ArrayList;
import java.util.List;


public class ShikomiInfo {
	
	// 申请号
	private String applyId;
	
	// 批复号
	private String shikomiId;
	
	// 国别
	private String countryName;
	
	// 型号
	private List<String> modelNo = new ArrayList<String>();
	
	// 批复数量
	private Integer quantity;
	
	// 残数
	private Integer onHandQuantity;
	
	// 锁定数量
	private Integer lockQuantity;
	
	// 安全警告数量
	private Integer warnQuantity;
	
	// SHIKOMI可用范围
	private ShikomiShareType sharedType;
	
	// 客户
	private List<String> customerNo = new ArrayList<String>();
	
	// 部门代码
	private String DeptNo;

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public String getShikomiId() {
		return shikomiId;
	}

	public void setShikomiId(String shikomiId) {
		this.shikomiId = shikomiId;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public List<String> getModelNo() {
		return modelNo;
	}

	public void setModelNo(List<String> modelNo) {
		this.modelNo = modelNo;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getOnHandQuantity() {
		return onHandQuantity;
	}

	public void setOnHandQuantity(Integer onHandQuantity) {
		this.onHandQuantity = onHandQuantity;
	}

	public Integer getLockQuantity() {
		return lockQuantity;
	}

	public void setLockQuantity(Integer lockQuantity) {
		this.lockQuantity = lockQuantity;
	}

	public Integer getWarnQuantity() {
		return warnQuantity;
	}

	public void setWarnQuantity(Integer warnQuantity) {
		this.warnQuantity = warnQuantity;
	}

	public ShikomiShareType getSharedType() {
		return sharedType;
	}

	public void setSharedType(ShikomiShareType sharedType) {
		this.sharedType = sharedType;
	}

	public List<String> getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(List<String> customerNo) {
		this.customerNo = customerNo;
	}

	public String getDeptNo() {
		return DeptNo;
	}

	public void setDeptNo(String deptNo) {
		DeptNo = deptNo;
	}

}