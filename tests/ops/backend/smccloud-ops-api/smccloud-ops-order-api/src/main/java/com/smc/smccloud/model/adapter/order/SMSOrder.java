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

@Data
@ApiModel(description = "订单实体")
public class SMSOrder implements Serializable {

	private static final long serialVersionUID = -1242493306307174690L;

	@ApiModelProperty(value = "订单号 完整订单号")
	private String orderNo;

	@ApiModelProperty(value = "型号")
	private String modelNo;

	@ApiModelProperty(value = "数量")
	private Integer quantity;

	@ApiModelProperty(value = "单价")
	private BigDecimal price;

	@ApiModelProperty(value = "用户单价")
	private BigDecimal priceUser;

	@ApiModelProperty(value = "最终用户单价")
	private BigDecimal priceEndUser;

	// 不含税单价
	private BigDecimal ntaxPice;

	// 不含税金额
	private BigDecimal ntaxAmount;

	// 税额
	private BigDecimal taxAmount;
	// 含税金额
	private BigDecimal amount;

	// 折扣率 --- e率
	private BigDecimal discount;

	// 交货日期(客户端)  --- 客户变更交货期
	private Date dlvDate;

	// 原交货期 --- 客户交货期
	private Date cdlvDate;

	// 指定物流出库日
    private Date wmsDlvDate;

    // 特价号
    private String specOfferNo;

    // 客户产品代码 -- 物料号
    private String cproductNo;

    // 阀与汇流板标识（0:正常订货;1:底板;2:组装原件）
    private String specMark;

    // 备注
    private String remark;

    // 客户产品名称
    private String productName;

    // 竞争对手
    private String opponent;

    // ppl号
    private String pplNo;

    // 项目号
	private String projectNo;

	// shikomi_no号
	private String shikomiNo;

	// 营业情报号
	private String salesInfoNo;

	// 前端与OPS交互单号，如果是门户上的订单则是发注单号
	private String preSalesOrderNo;
	// 客户订单号
	private String corderNo;
	// 客户自定义出货代码 -- 客户出货代码
	private String customCode;

	// 处理状态
	private Integer status;
	// 处理状态名称
	private String statusName;

	// 删除状态
	private Integer deleteStatus;
	// 删除状态名称
	private String deleteStatusName;

	// 出库区分类别（N：在库 ，T：在途 ，CG： 采购）
	private String stockType;
	// 出库区分类别名称
	private String stockTypeName;

	// 出库区分的库存类别：顾客在库，战略在库、通用在库;出库区分
	private String inventoryTypeCode;
	// 出库区分的库存类别名称
	private String inventoryTypeCodeName;

	// 拆分标识(0:不拆分；1:数量拆分;  2:型号拆分)
	private String prodFlag;
	// 拆分标识名称
	private String prodFlagName;

	// 货齐时间
	private Date readyTime;

	// 完成出货时间
	private Date expTime;

	// 在库货齐数量
	private Integer readyQty;

	// 已发出货指令数量
	private Integer expQty;
	// 已退货数量
	private Integer returnedQty;

	// 发货地址 项地址变更时存OrderDlvData的id
	private String addressNo;

	// 异常信息
	private String expMsg;

	// 开票数量
	private Integer invoiceQty;

	// 开票日期
	private Date invoiceTime;

	// 承运商id
	private String carrierId;
	// 承运商名称
	private String carrierName;

	// 运单号
	private String expressNo;

	// 承运商交接时间(出库时间)
	private Date handoverTime;

	// 处理日期
	private Date updateTime;

	// 报价单
	private String quotationNo;

	// 合同单号
	private String hddno;

	// 销售部门
	private String saleDeptNo;
	// 部门名称
	private String saleDeptNoName;
	// 用户代码
	private String userNo;
	// 用户名称
	private String userName;
	// 客户代码
	private String customerNo;
	// 客户名称
	private String customerName;

	// 最终用户
	private String endUser;
	// 最终用户名称
	private String endUserName;

	private String hlCode;

	private String empSale;
	private String empSaleName;

	private Date rordDate;

	private BigDecimal ePrice;

	private BigDecimal taxRate;

	private Boolean intercept;

	private Date interceptTime;

	// 制单人工号
	private String createId;

	// 制单人姓名
	private String createUserName;

	// 预计出库日
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date expectedDeliveryTime;

    // 最低售价
    private Boolean hasLowMinPrice;

}
