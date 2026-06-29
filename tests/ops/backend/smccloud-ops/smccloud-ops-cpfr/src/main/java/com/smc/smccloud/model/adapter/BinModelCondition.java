package com.smc.smccloud.model.adapter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(description = "客户在库补库根据型号查询库存及销售等信息")
public class BinModelCondition implements Serializable {

	private static final long serialVersionUID = -8540400044400280845L;

	@ApiModelProperty(value = "客户代码")
	private String customerNo;

	@ApiModelProperty(value = "库房代码")
	private String stockCode;

	@ApiModelProperty(value = "型号")
	private List<String> modelNoList;

}