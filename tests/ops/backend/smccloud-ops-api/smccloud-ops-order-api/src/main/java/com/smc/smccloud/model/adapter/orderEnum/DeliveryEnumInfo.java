package com.smc.smccloud.model.adapter.orderEnum;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(description = "发货info枚举实体")
public class DeliveryEnumInfo implements Serializable {
	/**
	 * 出货方式：1货齐出货；0随到随发 交货方式：1直发客户；2直发营业所；3自提
	 */
	private static final long serialVersionUID = 7846270958382810634L;

	@ApiModelProperty(value = "出货方式")
	public List<EnumInfo> entireList;

	@ApiModelProperty(value = "交货（发货）方式")
	public List<EnumInfo> siteList;

	@ApiModelProperty(value = "运费承担者")
	public List<EnumInfo> feeList;
}
