package com.smc.smccloud.model.adapter.orderEnum;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(description = "问询info枚举实体")
public class InqueryEnumInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3000888855359844215L;

	@ApiModelProperty(value = "问题")
	public List<EnumInfo> questionList;

	@ApiModelProperty(value = "货期提前原因")
	public List<EnumInfo> reasonList;
}
