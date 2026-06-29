package com.smc.smccloud.model.OrderSales;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderSalesVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 项号
     */
    @NotNull
    private Integer rorderItem;

    /**
     * 客户代码
     */
    private String customerNo;

    /**
     * 用户代码
     */
    private String userNo;

    /**
     * 货齐出货
     */
    private String dlvEntire;

    /**
     * 运费付担
     */
    private String transFee;

    /**
     * 国内运输方式
     */
    private String transChannel;

    /**
     * 交货地点
     */
    private String dlvSite;

    /**
     * 交货方式
     */
    private String dlvType;

    /**
     * 合同号
     */
    private String contractNo;

    /**
     * 报价单号
     */
    private String quotationNo;

    /**
     * 备注
     */
    private String remark;

    /**
     * 处理日期 下单日期
     */
    private Date workday;

    /**
     * 发送标识
     */
    private String sendOut;

    /**
     * 接收状态
     */
    private String status;

    /**
     * 发送日期
     */
    private Date sendDay;

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
     * 交货日期
     */
    private Date dlvDate;

    /**
     * 订货区分
     */
    private String rcvClassify;

    /**
     * 项目代码
     */
    private String prjCode;

    /**
     * 出库代码
     */
    private String stockCode;

    /**
     * 用户产品代码
     */
    private String cproductNo;

    /**
     * 海外订货运输方式
     */
    private String ordTransType;

    /**
     * 特价号
     */
    private String spcPrice;

    /**
     * E率
     */
    private BigDecimal discount;

    /**
     * 无价格标识
     */
    private String noPrice;

    /**
     * 金额
     */
    private BigDecimal account;

    /**
     * 阀与汇流板标识
     */
    private String specMark;

    /**
     * 项号
     */
    private String recNo;

    /**
     * 明细备注
     */
    private String detailMark;

    /**
     * 操作日期
     */
    private Date optDate;

    /**
     * 外勤代码
     */
    private String empSale;

    /**
     * 内勤代码
     */
    private String empOffice;

    /**
     * 营业所代码
     */
    private String deptNo;

    /**
     * 采购单号
     */
    private String purchaseNo;

    /**
     * 是否有合同
     */
    private String cttFlag;

    /**
     * 营业所物流发货日期
     */
    private Date warehouseSendDate;

    /**
     * 客户产品名称
     */
    private String cproductName;

    /**
     * 营业情报号
     */
    private String preSaleOrderNo;

    /**
     * 竞争对手
     */
    private String opponent;

    /**
     * 订单类别
     */
    private Integer typeCode;

    /**
     * 是否价格证
     */
    private Boolean priceChecked;

    /**
     * 交易主体
     */
    private String tradeCompanyId;

    /**
     * 收货地址编号
     */
    private String addressNo;

    /**
     * 客户期望的交货期
     */
    private Date cdlvDate;

    /**
     * 客户订单号
     */
    private String corderNo;

    /**
     * 客户自定义出货代码(打印在标签上)
     */
    private String customCode;

    private Date createTime;

    private String updateUser;

    @NotEmpty
    private String rorderNo;

    private String endUser;

    private BigDecimal ePrice;

    private Date updateTime;

    /**
     * SHiKoMi号
     */
    private String shikomiNo;

    private String salesInfoNo;

    private String pplNo;

    private Long id;

    private String fullOrderNo;

    private String projectNo;

    private BigDecimal taxRate;

    private String createUser;

    // 特殊出货方式
    private Integer expDlvType;

    private String expLinkNo;

    private String groupCustomerNo;

    // 行业代码
    private String industryId;

    // 客户类型
    private String customerType;
}
