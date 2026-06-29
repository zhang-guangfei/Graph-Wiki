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

@Data
@ApiModel(description = "订单导出类")
public class OrderExport implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = -2677501882341220626L;

	@ApiModelProperty(value = "订单号")
	private String rorderno;

	@ApiModelProperty(value = "客户")
	private String customerno;

	@ApiModelProperty(value = "用户")
	private String userno;

	@ApiModelProperty(value = "客户名称")
	private String customername;

	@ApiModelProperty(value = "用户名称")
	private String username;

	// 最终用户编码
	private String endUser;
	// 最终用户名称
	private String endUserName;

	@ApiModelProperty(value = "订单日期")
	@JsonFormat(pattern = "yyyy-MM-dd", shape = Shape.STRING, timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JSONField(format = "yyyy-MM-dd")
	private Date rorddate;

	@ApiModelProperty(value = "出货方式")
	private String dlventire;

	@ApiModelProperty(value = "发货方式")
	private String dlvsite;

	@ApiModelProperty(value = "费用承担")
	private String transfee;

	@ApiModelProperty(value = "承运商")
	private String transchannel;

	@ApiModelProperty(value = "项目代码")
	private String prjcode;

	@ApiModelProperty(value = "集约方式")
	private String dlvtype;

	@ApiModelProperty(value = "客户担当")
	private String employee;

	@ApiModelProperty(value = "客户担当姓名")
	private String empSaleName;

	@ApiModelProperty(value = "申请人")
	private String employeeno;

	@ApiModelProperty(value = "申请人姓名")
	private String applyPersonName;

	@ApiModelProperty(value = "营业所")
	private String deptno;

	@ApiModelProperty(value = "合同号")
	private String contractno;

	@ApiModelProperty(value = "报价单")
	private String quotationno;

	@ApiModelProperty(value = "合同订单号")
	private String hddno;

	@ApiModelProperty(value = "采购单号")
	private String purchaseno;

	@ApiModelProperty(value = "HOLON")
	private String hlcode;

	@ApiModelProperty(value = "holon名称")
	private String holonName;

	@ApiModelProperty(value = "交易主体")
	private String tradecompanyid;

	@ApiModelProperty(value = "子项号")
	private String rorderitem;

	@ApiModelProperty(value = "型号")
	private String modelno;

	@ApiModelProperty(value = "数量")
	private Integer quantity;

	@ApiModelProperty(value = "单价")
	private BigDecimal price;

	@ApiModelProperty(value = "客户期望交货期")
	@JsonFormat(pattern = "yyyy-MM-dd", shape = Shape.STRING, timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JSONField(format = "yyyy-MM-dd")
	private Date dlvdate;

	@ApiModelProperty(value = "订单状态")
	private String orderStatus;

	@ApiModelProperty(value = "物料号")
	private String cproductno;

	@ApiModelProperty(value = "出库区分")
	private String expinvcode;

	@ApiModelProperty(value = "海外订货方式")
	private String ordtranstype;

	@ApiModelProperty(value = "特价号")
	private String specofferno;

	@ApiModelProperty(value = "操作日期")
	@JsonFormat(pattern = "yyyy-MM-dd", shape = Shape.STRING, timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JSONField(format = "yyyy-MM-dd")
	private Date optdate;

	@ApiModelProperty(value = "拆分标识")
	private String prodflag;

	@ApiModelProperty(value = "阀汇流板标识")
	private String specmark;

	@ApiModelProperty(value = "E率")
	private Double discount;

	@ApiModelProperty(value = "备注")
	private String remark;

	@ApiModelProperty(value = "SHIKOMI")
	private String shikomi;

	@ApiModelProperty(value = "操作记录")
	private String optrecord;

	@ApiModelProperty(value = "用户价格")
	private BigDecimal priceEnduser;

	@ApiModelProperty(value = "发货日期")
	@JsonFormat(pattern = "yyyy-MM-dd", shape = Shape.STRING, timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JSONField(format = "yyyy-MM-dd")
	private Date dlvdateBj;

	@ApiModelProperty(value = "客户产品名称")
	private String cproductname;

	@ApiModelProperty(value = "营业情报号")
	private String presaleorderno;

	@ApiModelProperty(value = "竞争对手")
	private String opponent;

	@ApiModelProperty(value = "E价")
	private BigDecimal eprice;

	@ApiModelProperty(value = "是否已确认")
	private String isConfirm;

	@ApiModelProperty(value = "订单类别")
	private String orderType;

	@ApiModelProperty(value = "物流实际发货日期")
	@JsonFormat(pattern = "yyyy-MM-dd", shape = Shape.STRING, timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JSONField(format = "yyyy-MM-dd")
	private Date dlvdatefact;

	private String stockType;
}
