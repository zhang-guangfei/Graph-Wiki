package com.smc.smccloud.model.adapter.order;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@ApiModel(description = "存储删单信息实体")
public class OrderDelete implements Serializable {

	private static final long serialVersionUID = 3829377158911379331L;

	@ApiModelProperty(value = "请求删单子项")
	private List<OrderDeleteItem> items;

	@ApiModelProperty(value = "申请人代码")
	private String applyPersonNo;

	@ApiModelProperty(value = "申请理由")
	private String applyReason;

	@ApiModelProperty(value = "申请时间")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date applyTime;

}
