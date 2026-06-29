package com.smc.smccloud.model.adapter.order;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@ApiModel(description = "订单子项实体")
public class OrderItem implements Serializable {

	private static final long serialVersionUID = -1242493306302174690L;

	@ApiModelProperty(value = "业务单号子单号")
	private String orderNo;

	@ApiModelProperty(value = "订单状态名称")
	private String orderStatus;

	// 客户变更货期
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JSONField(format = "yyyy-MM-dd")
	private Date customerChangeDate;

	@ApiModelProperty(value = "客户希望交货日期")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JSONField(format = "yyyy-MM-dd")
	private Date dlvDate;

	@ApiModelProperty(value = "物流发货日期")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JSONField(format = "yyyy-MM-dd")
	private Date warehouseSendDate;

	@ApiModelProperty(value = "型号")
	private String modelNo;

	@ApiModelProperty(value = "客户物料号")
	private String customerProductNo;

	@ApiModelProperty(value = "数量")
	private int quantity;

	@ApiModelProperty(value = "单价")
	private BigDecimal price;

	@ApiModelProperty(value = "E率")
	private BigDecimal eRate;

	// 用户价格
	private BigDecimal userPrice;

	// 用户E率
	private BigDecimal userERate;

	@ApiModelProperty(value = "最终用户价格")
	private BigDecimal endUserPrice;

	@ApiModelProperty(value = "特价号")
	private String specOfferNo;

	@ApiModelProperty(value = "预占库存号")
	private String preSaleOrderNo;

	@ApiModelProperty(value = "shikomi")
	private String shikomi;

	// 出库区分代码
	@ApiModelProperty(value = "出库区分")
	private String expInvCode;

	// 出库区分类型
	private String expStockType;

	@ApiModelProperty(value = "备注")
	private String remark;

	// 实际发货日
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JSONField(format = "yyyy-MM-dd")
	private Date shipDate;

	// 运单号
	private String trackOrderNo;

	// 承运商代码
	private String cerrierNo;

	@ApiModelProperty(value = "发票号")
	private String invoiceNo;

	// 开票日期
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JSONField(format = "yyyy-MM-dd")
	private Date billingDate;

	// 特发/普通
	private String specialNormal;
	// 承运商(实际发货的具体承运商（申通，圆通，顺丰等）)
	private String carrier;
	// 承运商编码
	private String carrierNo;
	// 承运商方式(下单选择的)
	private String optCarrier;
	// 是否出组装波次
	private Boolean chubociFlag;
	// 收货地址实体类
	private DeliveryAddressInfo deliveryAddressInfo;
	// 是否装箱
	private Boolean packingFlag;
	// 子项特发/组装指令
	private String assemble;
	// 是否发货
	private Boolean deliveryFlag;
	// 拆分标识
	private String prodFlag;

	private String Rohs10;

	@ApiModelProperty(value = "客户代码")
	private String customerNo;

	@ApiModelProperty(value = "客户名称")
	private String customerName;

	@ApiModelProperty(value = "订单号")
	private String mainOrderNo;
	@ApiModelProperty(value = "订单状态代码")
	private String orderStatusCode;

	@ApiModelProperty(value = "是否可更改出货方式")
	private boolean canChangeDeliveryModel = false;

	@ApiModelProperty(value = "是否可更改货期")
	private boolean canChangeDeliveryDate = false;

	private String optState;

	// 异常信息
	private String expMsg;

	private String orderTypeName;

	private Boolean canDelete;

	private Boolean intercept;

	private Date interceptTime;

	// 是否可以变更特发
	private Boolean canUpSpec;
}
