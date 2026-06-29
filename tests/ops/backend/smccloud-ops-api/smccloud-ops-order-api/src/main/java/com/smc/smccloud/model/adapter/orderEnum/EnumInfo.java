package com.smc.smccloud.model.adapter.orderEnum;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "枚举实体")
public class EnumInfo implements Serializable {
	/**
	 * 枚举代码及说明实体
	 */
	private static final long serialVersionUID = -9193031911025644918L;

	@ApiModelProperty(value = "代码")
	public String value;

	@ApiModelProperty(value = "说明")
	public String label;

	@ApiModelProperty(value = "类型")
	public String type;
}
