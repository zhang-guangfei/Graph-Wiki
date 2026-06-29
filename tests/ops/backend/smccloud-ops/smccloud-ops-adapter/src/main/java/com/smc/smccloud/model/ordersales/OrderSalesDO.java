package com.smc.smccloud.model.ordersales;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Author: B90034
 * Date: 2022-02-15 10:23
 * Description:
 */
@Data
@TableName("OrderSales")
public class OrderSalesDO {

    /**
     * 项号
     */
    @TableField("ROrderItem")
    private Integer rorderItem;

    /**
     * 客户代码
     */
    @TableField("CustomerNo")
    private String customerNo;

    /**
     * 用户代码
     */
    @TableField("UserNo")
    private String userNo;

    /**
     * 货齐出货
     */
    @TableField("DlvEntire")
    private String dlvEntire;

    /**
     * 运费付担
     */
    @TableField("TransFee")
    private String transFee;

    /**
     * 国内运输方式
     */
    @TableField("TransChannel")
    private String transChannel;

    /**
     * 交货地点
     */
    @TableField("DlvSite")
    private String dlvSite;

    /**
     * 交货方式
     */
    @TableField("DlvType")
    private String dlvType;

    /**
     * 合同号
     */
    @TableField("ContractNo")
    private String contractNo;

    /**
     * 报价单号
     */
    @TableField("QuotationNo")
    private String quotationNo;

    /**
     * 备注
     */
    @TableField("Remark")
    private String remark;

    /**
     * 处理日期 下单日期
     */
    @TableField("WorkDay")
    private Date workday;

    /**
     * 发送标识
     */
    @TableField("Sendout")
    private String sendOut;

    /**
     * 接收状态
     */
    @TableField("Status")
    private String status;

    /**
     * 发送日期
     */
    @TableField("SendDay")
    private Date sendDay;

    /**
     * 型号
     */
    @TableField("ModelNo")
    private String modelNo;

    /**
     * 数量
     */
    @TableField("Quantity")
    private Integer quantity;

    /**
     * 单价
     */
    @TableField("Price")
    private BigDecimal price;

    /**
     * 最终用户单价
     */
    @TableField("Price_EndUser")
    private BigDecimal priceEndUser;

    /**
     * 交货日期
     */
    @TableField("DlvDate")
    private Date dlvDate;

    /**
     * 订货区分
     */
    @TableField("RcvClassify")
    private String rcvClassify;

    /**
     * 项目代码
     */
    @TableField("PrjCode")
    private String prjCode;

    /**
     * 出库代码
     */
    @TableField("StockCode")
    private String stockCode;

    /**
     * 用户产品代码
     */
    @TableField("CProductNo")
    private String cproductNo;

    /**
     * 海外订货运输方式
     */
    @TableField("OrdTransType")
    private String ordTransType;

    /**
     * 特价号
     */
    @TableField("SpcPrice")
    private String spcPrice;

    /**
     * E率
     */
    @TableField("Discount")
    private BigDecimal discount;

    /**
     * 无价格标识
     */
    @TableField("NoPrice")
    private String noPrice;

    /**
     * 金额
     */
    @TableField("Account")
    private BigDecimal account;

    /**
     * 阀与汇流板标识
     */
    @TableField("SpecMark")
    private String specMark;

    /**
     * 项号
     */
    @TableField("RecNo")
    private String recNo;

    /**
     * 明细备注
     */
    @TableField("DetailMark")
    private String detailMark;

    /**
     * 操作日期
     */
    @TableField("OptDate")
    private Date optDate;

    /**
     * 外勤代码
     */
    @TableField("EmpSale")
    private String empSale;

    /**
     * 内勤代码
     */
    @TableField("EmpOffice")
    private String empOffice;

    /**
     * 营业所代码
     */
    @TableField("DeptNo")
    private String deptNo;


    /**
     * Holon代码
     */
    @TableField("HLCode")
    private String HLCode;

    /**
     * 采购单号
     */
    @TableField("PurchaseNo")
    private String purchaseNo;

    /**
     * 是否有合同
     */
    @TableField("cttFlag")
    private String cttFlag;

    /**
     * 营业所物流发货日期
     */
    @TableField("WarehouseSendDate")
    private Date warehouseSendDate;

    /**
     * 客户产品名称
     */
    @TableField("CProductName")
    private String cproductName;

    /**
     * 营业情报号
     */
    @TableField(exist = false)
    private String preSaleOrderNo;

    /**
     * 竞争对手
     */
    @TableField("Opponent")
    private String opponent;

    /**
     * 订单类别
     */
    @TableField("TypeCode")
    private Integer typeCode;

    /**
     * 是否价格证
     */
    @TableField("PriceChecked")
    private Boolean priceChecked;

    /**
     * 交易主体
     */
    @TableField("TradeCompanyId")
    private String tradeCompanyId;

    /**
     * 收货地址编号
     */
    @TableField("address_no")
    private String addressNo;

    /**
     * 客户期望的交货期
     */
    @TableField("CDlvDate")
    private Date cdlvDate;

    /**
     * 客户订单号
     */
    @TableField("COrderNo")
    private String corderNo;

    /**
     * 客户自定义出货代码(打印在标签上)
     */

    @TableField("custom_code")
    private String customCode;

    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Date createTime;

    @TableField("update_user")
    private String updateUser;

    @TableField("rorderno")
    private String rorderNo;

    @TableField("EndUser")
    private String endUser;

    @TableField("EPrice")
    private BigDecimal ePrice;

    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * SHiKoMi号
     */
    @TableField("shikomi_no")
    private String shikomiNo;

    @TableField("sales_info_no")
    private String salesInfoNo;

    @TableField("ppl_no")
    private String pplNo;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("fullOrderNo")
    private String fullOrderNo;

    @TableField("project_no")
    private String projectNo;

    @TableField("tax_rate")
    private BigDecimal taxRate;

    @TableField("create_user")
    private String createUser;

    /**
     * 特殊出货方式
     */
    @TableField("exp_dlv_type")
    private Integer expDlvType;

    @TableField(exist = false)
    private String expDlvTypeString;

    @TableField("exp_link_no")
    private String expLinkNo;

    /**
     * 合同订单号
     */
    @TableField("OrOrderNo")
    private String orOrderNo;

    /**
     * 门户发注号
     */
    @TableField("PreSalesOrderNo")
    private String PreSalesOrderNo;

    @TableField("delivery_dept_no")
    private String deliveryDeptNo;

    /**
     * 用户价格
     */
    @TableField("user_price")
    private BigDecimal userPrice;

    /**
     * 用户E率
     */
    @TableField("user_ediscount")
    private Double userEdiscount;

    /**
     * 客户群代码（以","分隔）
     */
    @TableField("group_customer_no")
    private String groupCustomerNo;

    // ops原始订单号
    @TableField(exist = false)
    private String opsOrderNo;

    // 行业代码
    @TableField("industryId")
    private String industryId;

    // 0 直销 1 代销 2 经销
    @TableField("customerType")
    private String customerType;

    @TableField("inqb_apply_no")
    private String inqbApplyNo;

    @TableField("credit_sale_client_flag")
    private String creditSaleClientFlag;
}
