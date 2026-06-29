package com.smc.smccloud.model.delivery;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
//获取物流发货货期信息请求实体
public class DeliveryInfo implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1144740492072999739L;

	// 部门代码
	private String deptNo;

	// 客户代码
	private String customerNo;

	// PPL代码
	private String ppl;

	// PJ代码
	private String pj;

	// 集团客户代码
	private String groupCustomer;

	// 物流发货期明细  型号信息
	private List<DeliveryModelInfo> modelList;

}
