package com.smc.smccloud.model.adapter.order;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(description = "订单流程图连线")
public class OrderNode implements Serializable {

	private static final long serialVersionUID = -1242493306302174690L;

	// 订单号
	@ApiModelProperty(value = "订单号")
	private String orderNo;

	// 名称
	@ApiModelProperty(value = "节点名称")
	private String name;

	// 标题
	@ApiModelProperty(value = "标题")
	private String title = "";

	// 详情
	@ApiModelProperty(value = "详情")
	private String detail = "";

	// 激活 : true ,非激活:false
	@ApiModelProperty(value = "激活")
	private boolean active = false;

	// 代表南北方标识，0：北方，1：南方，5：广州
	@ApiModelProperty(value = "区域")
	private String area;

	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date updateTime;

	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date insertTime;

}
