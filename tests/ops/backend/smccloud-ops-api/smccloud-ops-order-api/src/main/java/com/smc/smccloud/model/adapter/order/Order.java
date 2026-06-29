package com.smc.smccloud.model.adapter.order;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@ApiModel(description = "订单实体")
public class Order implements Serializable {

	private static final long serialVersionUID = -1242493306307174690L;

	@ApiModelProperty(value = "业务单号")
	private String orderNo;

	@ApiModelProperty(value = "采购单号")
	private String purchaseNo;

	@ApiModelProperty(value = "客户代码")
	private String customerNo;

	@ApiModelProperty(value = "客户名称")
	private String customerName;

	@ApiModelProperty(value = "holon代码 销售部门编码")
	private String holonNo;

	@ApiModelProperty(value = "holon名称 销售部门名称")
	private String holonName;

	@ApiModelProperty(value = "用户代码")
	private String userNo;

	@ApiModelProperty(value = "用户名称")
	private String userName;

	@ApiModelProperty(value = "客户担当")
	private String empSale;

	@ApiModelProperty(value = "客户担当姓名")
	private String empSaleName;

	@ApiModelProperty(value = "订货日期")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JSONField(format = "yyyy-MM-dd")
	private Date orderDate;

	@ApiModelProperty(value = "申请人编号")
	private String applyPersonNo;

	@ApiModelProperty(value = "申请人姓名")
	private String applyPersonName;

	@ApiModelProperty(value = "报价单号")
	private String quotationNo;

	@ApiModelProperty(value = "合同订单号")
	private String hddno;

	@ApiModelProperty(value = "集约方式代码")
	private String intensiveNo;

	@ApiModelProperty(value = "集约方式名称")
	private String intensive;

	@ApiModelProperty(value = "出货方式代码")
	private String deliveryEntireNo;

	@ApiModelProperty(value = "出货方式名称")
	private String deliveryEntire;

	@ApiModelProperty(value = "运费承担方")
	private String fee;

	@ApiModelProperty(value = "订单数量")
	private int orderQuantity;

	@ApiModelProperty(value = "未发货数量")
	private int undeliveredQuantity;

	@ApiModelProperty(value = "客户类型名称")
	private String cstmType;

	@ApiModelProperty(value = "发票类型")
	private String invoiceType;

	@ApiModelProperty(value = "订单金额")
	private BigDecimal orderAmount;

	// 是否可更改出货方式
	@ApiModelProperty(value = "是否可更改发货方式")
	private boolean canChangeDeliveryModel = false;

	@ApiModelProperty(value = "子项")
	private List<OrderItem> itemList;



}
