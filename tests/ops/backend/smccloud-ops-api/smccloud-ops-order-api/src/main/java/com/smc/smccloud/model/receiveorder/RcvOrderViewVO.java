package com.smc.smccloud.model.receiveorder;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author lyc
 * @Date 2022/7/4 12:09
 * @Descripton TODO
 */

@Data
public class RcvOrderViewVO {

    private static final long serialVersionUID = 1L;

    /**
     * 接单号
     */
    private String rorderNo;

    /**
     * 项号
     */
    private Integer rorderItem;

    /**
     * 完整订单号
     */
    private String rorderFno;

    /**
     * 型号
     */
    private String modelNo;

    /**
     * 数量
     */
    private Integer quantity;

    /**
     * 单价
     */
    private BigDecimal price;

    /**
     * 最终用户单价
     */
    private BigDecimal priceEndUser;

    /**
     * e价
     */
    private BigDecimal ePrice;

    /**
     * 税率
     */
    private BigDecimal taxRate;

    /**
     * 不含税单价
     */
    private BigDecimal ntaxPice;

    /**
     * 不含税金额
     */
    private BigDecimal ntaxAmount;

    /**
     * 税额
     */
    private BigDecimal taxAmount;


    /**
     * 含税金额
     */
    private BigDecimal amount;


    /**
     * 交货方式
     */
    private String dlvType;


    /**
     * 折扣率
     */
    private Double discount;

    /**
     * 交货日期(客户端)
     */
    private Date dlvDate;

    /**
     * 客户的希望交货期
     */
    private Date cdlvDate;


    /**
     * 处理状态1
     */
    private Integer status;


    /**
     * 特价号
     */
    private String specOfferNo;

    /**
     * 删除状态-1已删除,2删除中,3-退货中
     */
    private Integer deleteStatus;

    /**
     * 订单来源
     */
    private String sourceType;

    /**
     * 客户产品代码
     */
    private String cproductNo;

    /**
     * 出库代码
     */
    private String stockCode;

    /**
     * 出库类型
     */
    private String stockType;

    /**
     * 阀与汇流板标识
     */
    private String specMark;

    /**
     * 备注
     */
    private String remark;

    /**
     * 客户产品名称
     */
    private String productName;

    /**
     * 竞争对手
     */
    private String opponent;

    private Date allotTime;

    private Date readyTime;

    /**
     * 完成出货时间
     */
    private Date expTime;

    /**
     * 完成发货日期
     */
    private Date shipTime;

    /**
     * 在库货齐数量
     */
    private Integer readyQty;


    /**
     * 已发出货指令数量
     */
    private Integer expQty;


    /**
     * 已退货数量
     */
    private Integer returnedQty;

    /**
     * 已分配数量
     */
    private Integer allocatedQty;

    /**
     * ppl号
     */
    private String pplNo;

    /**
     * 项目号
     */
    private String projectNo;


    /**
     * 发货地址
     */
    private String addressNo;

    /**
     * 客户自定义出货代码(打印在标签上)
     */
    private String customCode;

    /**
     * 更新版本号
     */
    private Integer version;

    private Date createTime;

    private String createUser;

    /**
     * 处理日期
     */
    private Date updateTime;

    private String updateUser;

    /**
     * 客户代码
     */
    private String customerNo;

    /**
     * 用户代码
     */
    private String userNo;

    /**
     * 最终用户
     */
    private String endUser;

    /**
     * 指定物流出库日
     */
    private Date wmsDlvDate;

    /**
     * 出库区分的库存类别：顾客在库，战略在库、通用在库;出库区分
     */
    private String inventoryTypeCode;

    /**
     * 拆分标识(0:不拆分；1:数量拆分;  2:型号拆分)
     */
    private String prodFlag;


    /**
     * 客户群代码
     */
    private String groupCustomerNo;

    /**
     * shikomi号
     */
    private String shikomiNo;


    /**
     * 最后的接单处理时间
     */
    private Date processDate;

    /**
     * 营业情报号
     */
    private String salesInfoNo;

    private Integer orderType;

    private Long id;


    /**
     * 借库号
     */
    private String borrowNo;

    /**
     * 采购数量
     */
    private Integer poQty;

    /**
     * 异常信息
     */
    private String expMsg;

    /**
     * 特殊出货方式
     */
    private Integer expDlvType;

    private String expLinkNo;

    private String invoiceGroupKey;

    private Integer invoiceQty;

    private Date invoiceTime;

    private String corderNo;

    private String carrierId;

    private String expressNo;

    private Date handoverTime;

    /**
     * 用户价格
     */
    private BigDecimal priceUser;

    // 前端与OPS交互单号，如果是门户上的订单则是发注单号
    private String preSalesOrderNo;


    /**
     * 接单日期
     */
    private Date rordDate;

    /**
     * 货齐出货
     */
    private String dlvEntire;

    /**
     * 交货地点
     */
    private String dlvSite;

    /**
     * 运费负担
     */
    private String transFee;

    /**
     * 国内运输方式
     */
    private String transChannel;

    /**
     * 项目代码
     */
    private String prjCode;

    /**
     * 原接单号 （合同订单号）
     */
    private String orOrderNo;


    /**
     * 外勤
     */
    private String employee;

    /**
     * 内勤
     */
    private String employeeNo;

    /**
     * 处理时间
     */
    private Date optTime;

    /**
     * 操作担当
     */
    private String operator;

    /**
     * 营业所代码
     */
    private String deptNo;

    /**
     * 合同号
     */
    private String contractNo;

    /**
     * 报价单号
     */
    private String quotationNo;

    /**
     * 客户采购单号
     */
    private String purchaseNo;

    /**
     * HL编码
     */
    private String hlCode;

    /**
     * 订单的SMC交易主体
     */
    private String tradeCompanyId;

    private String invoiceNo;

    private Date invoiceDate;
}
