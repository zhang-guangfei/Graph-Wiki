package com.smc.smccloud.model.adapter.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel(description = "出库单实体")
public class Ordersales implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3378527277180389824L;

	@ApiModelProperty(value = "订单号")
	private String rorderno;

	@ApiModelProperty(value = "客户代码")
	private String customerno;

	@ApiModelProperty(value = "用户代码")
	private String userno;

	@ApiModelProperty(value = "是否货齐出货")
	private String dlventire;

	@ApiModelProperty(value = "运费负担")
	private String transfee;

	@ApiModelProperty(value = "国内运输方式")
	private String transchannel;

	@ApiModelProperty(value = "交货地点")
	private String dlvsite;

	@ApiModelProperty(value = "交货方式")
	private String dlvtype;

	@ApiModelProperty(value = "合同代码--自动订货时写入样品等申请号")
	private String contractno;

	@ApiModelProperty(value = "报价单代码")
	private String quotationno;

	@ApiModelProperty(value = "合同订单号")
	private String hddno;

	@ApiModelProperty(value = "备注")
	private String remark;

	private String oplog;

	@ApiModelProperty(value = "处理日期")
	private Date workday;

	@ApiModelProperty(value = "发送标识")
	private String sendout;

	@ApiModelProperty(value = "接收状态")
	private String status;

	@ApiModelProperty(value = "发送日期")
	private Date sendday;

	@ApiModelProperty(value = "项号")
	private Short rorderitem;

	@ApiModelProperty(value = "型号")
	private String modelno;

	@ApiModelProperty(value = "数量")
	private Integer quantity;

	@ApiModelProperty(value = "单价")
	private BigDecimal price;

	@ApiModelProperty(value = "交货日期")
	private Date dlvdate;

	@ApiModelProperty(value = "订货区分")
	private String rcvclassify;

	@ApiModelProperty(value = "项目代码")
	private String prjcode;

	@ApiModelProperty(value = "出库代码")
	private String stockcode;

	// 物料号
	@ApiModelProperty(value = "用户产品代码，物料号")
	private String cproductno;

	@ApiModelProperty(value = "海外订货运输方式")
	private String ordtranstype;

	@ApiModelProperty(value = "特价号")
	private String spcprice;

	@ApiModelProperty(value = "E率")
	private BigDecimal discount;

	@ApiModelProperty(value = "无价格标识")
	private String noprice;

	@ApiModelProperty(value = "金额")
	private BigDecimal account;

	@ApiModelProperty(value = "阀与汇流板标识")
	private String specmark;

	@ApiModelProperty(value = "项号")
	private String recno;

	@ApiModelProperty(value = "明细备注")
	private String detailmark;

	@ApiModelProperty(value = "操作日期")
	private Date optdate;

	@ApiModelProperty(value = "外勤担当")
	private String empsale;

	@ApiModelProperty(value = "内勤代码")
	private String empoffice;

	@ApiModelProperty(value = "部门代码")
	private String deptno;

	@ApiModelProperty(value = "采购单号")
	private String purchaseno;

	@ApiModelProperty(value = "SHIKOMI")
	private String shikomi;

	@ApiModelProperty(value = "最终用户价格")
	private BigDecimal priceEnduser;

	@ApiModelProperty(value = "是否有合同")
	private String cttflag;

	@ApiModelProperty(value = "物流发货日")
	private Date warehousesenddate;

	@ApiModelProperty(value = "客户产品名称")
	private String cproductname;

	@ApiModelProperty(value = "操作记录")
	private String optrecord;

	@ApiModelProperty(value = "营业情报号")
	private String presaleorderno;

	@ApiModelProperty(value = "竞争对手")
	private String opponent;

	@ApiModelProperty(value = "订单类别,直销寄售标识")
	private String typecode;

	@ApiModelProperty(value = "是否价格校验")
	private Boolean pricechecked;

	@ApiModelProperty(value = "E价格")
	private BigDecimal eprice;

	@ApiModelProperty(value = "发货地址id")
	private String addressId;

	@ApiModelProperty(value = "发货地址信息")
	private DeliveryAddressInfo addressInfo;

	@ApiModelProperty(value = "交易主体")
	private String tradecompanyid;

	@ApiModelProperty(value = "数据来源：001营业所；002代理店；101直销")
	private String OrderSourceSys;

}