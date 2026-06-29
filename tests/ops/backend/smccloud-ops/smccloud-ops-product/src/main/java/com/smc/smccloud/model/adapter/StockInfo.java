package com.smc.smccloud.model.adapter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "库存信息")
public class StockInfo implements Serializable {

	private static final long serialVersionUID = -2219180878239540472L;

	@ApiModelProperty(value = "库房代码")
	private String stockCode;

	@ApiModelProperty(value = "库房名称")
	private String stockName;
}