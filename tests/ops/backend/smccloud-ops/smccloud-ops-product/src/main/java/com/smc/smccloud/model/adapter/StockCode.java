package com.smc.smccloud.model.adapter;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "库房实体")
public class StockCode implements Serializable {

	private static final long serialVersionUID = -7077358988023000266L;

	@ApiModelProperty(value = "库房代码")
	private String stockCode;

	@ApiModelProperty(value = "库房名称")
	private String stockName;

	@ApiModelProperty(value = "库房类型：分库、")
	private String stockType;

	@ApiModelProperty(value = "库房类型名称")
	private String stockTypeName;

}
