package com.smc.smccloud.model.order;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author lyc
 * @Date 2022/7/4 12:13
 * @Descripton TODO
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("rcvorder_view")
public class RcvOrderViewDO {

    private static final long serialVersionUID = 1L;

    /**
     * 接单号
     */
    @TableField(value = "rorder_no")
    private String rorderNo;

    /**
     * 项号
     */
    @TableField("rorder_item")
    private Integer rorderItem;

    /**
     * 完整订单号
     */
    @TableField("rorder_fno")
    private String rorderFno;

    /**
     * 型号
     */
    @TableField("model_no")
    private String modelNo;

    /**
     * 数量
     */
    @TableField("quantity")
    private Integer quantity;

    /**
     * 单价
     */
    @TableField("price")
    private BigDecimal price;

    /**
     * 最终用户单价
     */
    @TableField("price_enduser")
    private BigDecimal priceEndUser;

    /**
     * e价
     */
    @TableField("eprice")
    private BigDecimal ePrice;

    /**
     * 税率
     */
    @TableField("tax_rate")
    private BigDecimal taxRate;

    /**
     * 不含税单价
     */
    @TableField("ntax_pice")
    private BigDecimal ntaxPice;

    /**
     * 不含税金额
     */
    @TableField("ntax_amount")
    private BigDecimal ntaxAmount;

    /**
     * 税额
     */
    @TableField("tax_amount")
    private BigDecimal taxAmount;


    /**
     * 含税金额
     */
    @TableField("amount")
    private BigDecimal amount;


    /**
     * 交货方式
     */
//    @TableField("dlv_type")
//    private String dlvType;


    /**
     * 折扣率
     */
    @TableField("discount")
    private Double discount;

    /**
     * 交货日期(客户端)
     */
    @TableField("dlv_date")
    private Date dlvDate;

    /**
     * 客户的希望交货期
     */
    @TableField("cdlv_date")
    private Date cdlvDate;


    /**
     * 处理状态1
     */
    @TableField("status")
    private Integer status;


    /**
     * 特价号
     */
    @TableField("spec_offer_no")
    private String specOfferNo;

    /**
     * 删除状态-1已删除,2删除中,3-退货中
     */
    @TableField("delete_status")
    private Integer deleteStatus;


    /**
     * 订单来源
     */
    @TableField("source_type")
    private String sourceType;

    /**
     * 客户产品代码
     */
    @TableField("cproduct_no")
    private String cproductNo;

    /**
     * 出库代码
     */
    @TableField("stock_code")
    private String stockCode;

    /**
     * 出库类型
     */
    @TableField("stock_type")
    private String stockType;

    /**
     * 阀与汇流板标识
     */
    @TableField("spec_mark")
    private String specMark;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 客户产品名称
     */
    @TableField("product_name")
    private String productName;

    /**
     * 竞争对手
     */
    @TableField("opponent")
    private String opponent;

    @TableField("allot_time")
    private Date allotTime;

    @TableField("ready_time")
    private Date readyTime;

    /**
     * 完成出货时间
     */
    @TableField("exp_time")
    private Date expTime;


    /**
     * 完成发货日期
     */
    @TableField("ship_time")
    private Date shipTime;

    /**
     * 在库货齐数量
     */
    @TableField("ready_qty")
    private Integer readyQty;


    /**
     * 已发出货指令数量
     */
    @TableField("exp_qty")
    private Integer expQty;


    /**
     * 已退货数量
     */
    @TableField("returned_qty")
    private Integer returnedQty;

    /**
     * 已分配数量
     */
//    @TableField("allocated_qty")
//    private Integer allocatedQty;

    /**
     * ppl号
     */
    @TableField("ppl_no")
    private String pplNo;

    /**
     * 项目号
     */
    @TableField("project_no")
    private String projectNo;

    /**
     * 发货地址
     */
    @TableField("address_no")
    private String addressNo;

    /**
     * 客户自定义出货代码(打印在标签上)
     */
    @TableField("custom_code")
    private String customCode;

    /**
     * 更新版本号
     */
    @TableField("version")
    private Integer version;

    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Date createTime;

    @TableField("create_user")
    private String createUser;

    /**
     * 处理日期
     */
    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableField("update_user")
    private String updateUser;

    /**
     * 客户代码
     */
    @TableField("customer_no")
    private String customerNo;

    /**
     * 用户代码
     */
    @TableField("user_no")
    private String userNo;

    /**
     * 最终用户
     */
    @TableField("end_user")
    private String endUser;

    /**
     * 指定物流出库日
    */
    @TableField("wms_dlv_date")
    private Date wmsDlvDate;

    /**
     * 出库区分的库存类别：顾客在库，战略在库、通用在库;出库区分
     */
    @TableField("inventory_type_code")
    private String inventoryTypeCode;

    /**
     * 拆分标识(0:不拆分；1:数量拆分;  2:型号拆分)
     */
    @TableField("prod_flag")
    private String prodFlag;


    /**
     * 客户群代码
     */
    @TableField("group_customer_no")
    private String groupCustomerNo;

    /**
     * shikomi号
     */
    @TableField("shikomi_no")
    private String shikomiNo;


    /**
     * 最后的接单处理时间
     */
    @TableField("process_date")
    private Date processDate;

    /**
     * 营业情报号
     */
    @TableField("sales_info_no")
    private String salesInfoNo;


    @TableField("order_type")
    private Integer orderType;


    @TableId(value = "id",type = IdType.AUTO)
    private Long id;


    /**
     * 借库号
     */
    @TableField("borrow_no")
    private String borrowNo;

    /**
     * 采购数量
     */
    @TableField("po_qty")
    private Integer poQty;

    /**
     * 异常信息
     */
    @TableField("exp_msg")
    private String expMsg;

    /**
     * 特殊出货方式
     */
    @TableField("exp_dlv_type")
    private Integer expDlvType;

    @TableField("exp_link_no")
    private String expLinkNo;

    @TableField("invoiceGroupKey")
    private String invoiceGroupKey;

    @TableField("invoice_qty")
    private Integer invoiceQty;

    @TableField("invoice_time")
    private Date invoiceTime;

    @TableField("corder_no")
    private String corderNo;

    @TableField("carrierId")
    private String carrierId;

    @TableField("expressNo")
    private String expressNo;

    @TableField("handover_time")
    private Date handoverTime;

    /**
     * 用户价格
     */
    @TableField("price_user")
    private BigDecimal priceUser;

    // 前端与OPS交互单号，如果是门户上的订单则是发注单号
    @TableField("pre_sales_order_no")
    private String preSalesOrderNo;


    /**
     * 接单日期
     */
    @TableField("ROrdDate")
    private Date rordDate;

    /**
     * 货齐出货
     */
    @TableField("dlv_entire")
    private String dlvEntire;

    /**
     * 交货地点
     */
    @TableField("DlvSite")
    private String dlvSite;

    /**
     * 运费负担
     */
    @TableField("TransFee")
    private String transFee;

    /**
     * 国内运输方式
     */
//    @TableField("TransChannel")
//    private String transChannel;

    /**
     * 项目代码
     */
    @TableField("PrjCode")
    private String prjCode;

    /**
     * 原接单号 （合同订单号）
     */
    @TableField("ORorderNo")
    private String orOrderNo;


    /**
     * 外勤
     */
    @TableField("Employee")
    private String employee;

    /**
     * 内勤
     */
    @TableField("EmployeeNo")
    private String employeeNo;

    /**
     * 处理时间
     */
    @TableField("OptTime")
    private Date optTime;

    /**
     * 操作担当
     */
    @TableField("Operator")
    private String operator;

    /**
     * 营业所代码
     */
    @TableField("dept_No")
    private String deptNo;

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
     * 客户采购单号
     */
    @TableField("PurchaseNO")
    private String purchaseNo;

    /**
     * HL编码
     */
    @TableField("HLCode")
    private String hlCode;

    /**
     * 订单的SMC交易主体
     */
    @TableField("trade_companyId")
    private String tradeCompanyId;


}
