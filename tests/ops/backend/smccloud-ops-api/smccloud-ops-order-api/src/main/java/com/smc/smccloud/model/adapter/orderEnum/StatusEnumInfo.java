package com.smc.smccloud.model.adapter.orderEnum;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(description = "订单状态枚举实体")
public class StatusEnumInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8608645121728494858L;

	@ApiModelProperty(value = "代码")
	public String value;

	@ApiModelProperty(value = "说明")
	public String label;

	@ApiModelProperty(value = "子项")
	public List<StatusEnumInfo> children;

}
