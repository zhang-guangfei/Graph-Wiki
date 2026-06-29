package com.smc.smccloud.model.adapter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "库存查询参数")
public class FuzzySearchCondition implements Serializable {

	private static final long serialVersionUID = 6951342894505362297L;

	@ApiModelProperty(value = "模糊型号")
	private String modelNo;

	/**
	 * 排序字段名
	 */
	@ApiModelProperty(value = "查询条件：排序字段名")
	private String property;
	/**
	 * 排序规则（正序/倒序）
	 */
	@ApiModelProperty(value = "查询条件：排序规则")
	private String order;

}