package com.smc.smccloud.model.adapter.order;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel(description = "发票实体类")
public class SalesInvoice implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4193190169577734474L;

	@ApiModelProperty(value = "id")
	private String id;

	@ApiModelProperty(value = "交易主体")
	private String tradecompanyid;

	@ApiModelProperty(value = "接单号")
	private String rorderno;

	@ApiModelProperty(value = "型号")
	private String modelno;

	@ApiModelProperty(value = "数量")
	private Integer quantity;

	@ApiModelProperty(value = "单价")
	private BigDecimal price;

	@ApiModelProperty(value = "客户代码")
	private String customerno;

	@ApiModelProperty(value = "用户代码")
	private String userno;

	@ApiModelProperty(value = "是否货齐出货")
	private String dlventire;

	@ApiModelProperty(value = "总金额")
	private BigDecimal amount;

	@ApiModelProperty(value = "税额")
	private BigDecimal taxamount;

	@ApiModelProperty(value = "不含税金额")
	private BigDecimal ntaxamount;

	@ApiModelProperty(value = "开票标识：0待开票；1已确认；2已开票；3样品结转；D拆分")
	private String invflag;

	@ApiModelProperty(value = "处理日期")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	@JSONField(format="yyyy-MM-dd")
	private Date optdate;

	@ApiModelProperty(value = "处理状态：0正常；R退货；")
	private String optcode;

	@ApiModelProperty(value = "合开标识：0正常；1合开")
	private String classflag;

	@ApiModelProperty(value = "发票号")
	private String invoiceno;

	@ApiModelProperty(value = "开票日期")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	@JSONField(format="yyyy-MM-dd")
	private Date invdate;

	@ApiModelProperty(value = "正负票标识：1正票；0负票")
	private String optstate;

	@ApiModelProperty(value = "出库库房")
	private String stockno;

	@ApiModelProperty(value = "作废标识：1作废；0正常票")
	private String invflag1;

	@ApiModelProperty(value = "工厂拆分标识：0整型号；1拆分")
	private String prodflag;

	@ApiModelProperty(value = "票据号")
	private String billno;

	@ApiModelProperty(value = "操作担当")
	private String username;

	@ApiModelProperty(value = "操作时间")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date opttime;

	@ApiModelProperty(value = "收货担当")
	private String receiveEmp;

	@ApiModelProperty(value = "收货客户")
	private String receiveCust;

	@ApiModelProperty(value = "收货日期")
	private Date receiveDate;

	@ApiModelProperty(value = "收货备注")
	private String receiveRemark;

	@ApiModelProperty(value = "收货状态")
	private String receiveOptstate;

	@ApiModelProperty(value = "客户采购单号")
	private String purchaseno;

	@ApiModelProperty(value = "代理客户是否运算过返利单价")
	private String agentpriceFlag;

	@ApiModelProperty(value = "部门代码")
	private String deptno;

	@ApiModelProperty(value = "折扣金额")
	private BigDecimal discountamt;

	@ApiModelProperty(value = "订单类别：0营业所订单；1专备；等")
	private String ordtype;

	@ApiModelProperty(value = "发票代码")
	private String invoicecode;

	@ApiModelProperty(value = "发票类别：0增票；2普票")
	private String invtype;

	@ApiModelProperty(value = "日本发票号")
	private String invoicenoJp;

	@ApiModelProperty(value = "税率")
	private BigDecimal taxrate;

	@ApiModelProperty(value = "不含税折扣金额")
	private BigDecimal ntaxdiscountamt;

	@ApiModelProperty(value = "折扣金额税额")
	private BigDecimal taxdiscountamt;

	@ApiModelProperty(value = "写入时间")
	private Date inserttime;

	@ApiModelProperty(value = "作废日期")
	private Date canceldate;

	@ApiModelProperty(value = "是否抽取到总分发票表")
	private String extracted;

	@ApiModelProperty(value = "抽取时间")
	private Date extracttime;

	@ApiModelProperty(value = "发票分组")
	private String invoicegroupkey;
}
