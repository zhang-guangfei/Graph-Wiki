package com.smc.smccloud.model.receiveorder;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ReceiveOrderVO {

    /**
     * rcvmaster
     */

    private String customerNo;

    private String userNo; // 用户代码

    private String endUser;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date rordDate; // 接单日期

    private String dlvEntire; //货齐出货

    private String dlvSite; // 交货地点

    private String transFee; // 运费付担

    private String transChannel; // 国内运输方式

    private String prjCode; // 项目代码

    private String dlvType; // 交货方式

    private String employee; // 外勤代码

    private String EmployeeNo; // 内勤代码

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date optTime; // 处理日期

    private String operator; // 操作担当

    private String oRorderNo; // 原接单号

    private String contractNo; // 合同号

    private String quotationNo; // 报价单号

    private String purchaseNo; // 客户采购单号

    private String deptNo; // 营业所代码

    private String hlCode; // HL编码

    /**
     * 收货地所在营业所代码
     */
    private String deliveryDeptNo;

    private String tradeCompanyId; // 交易主体




    /**
     * rcdetail
     */

    private Long id;
    private Long rmId;
    private Long rid;

    private String rorderNo;

    private Integer rorderItem;  // 项号

    private Integer status; // 接收状态

    private String rorderFno; // 完整订单号

    private String modelNo; // 型号

    private Integer quantity; // 数量

    private BigDecimal price; // 单价

    private BigDecimal priceEndUser; // 最终用户单价

    private BigDecimal ePrice; // e价钱

    private BigDecimal taxRate; // 税率

    private BigDecimal ntaxPice; // 不含税单价

    private BigDecimal ntaxAmount; // 不含税金额

    private BigDecimal taxAmount; // 税额

    private BigDecimal amount; // 含税金额

    private BigDecimal discount; // 区率 折扣率

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date dlvDate; // 交货日期(客户端)

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date cDlvDate; // 客户的希望交货期

    /**
     * 指定物流出库日
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date wmsDlvDate;

    private String specOfferNo; // 特价号

    private Integer deleteStatus; // 删除状态-1已删除,2删除中,3-退货中

    private String sourceType; // 订单来源

    private String cproductNo; // 客户产品代码

    private String stockCode; // 出库代码

    private String stockType; // 出库类型

    /**
     * 出库区分的库存类别：顾客在库，战略在库、通用在库;出库区分
     */
    private String inventoryTypeCode;

    private String specMark; // 阀与汇流板标识

    private String remark;

    private String productName; // 客户产品名称

    private String opponent; // 竞争对手

    private Date allotTime;

    private Date readyTime;

    private Date expTime; // 完成出货时间

    private String shipTime; // 完成发货时间

    private Integer readyQty; // 在库货齐数量

    private Integer expQty; // 已发出货指令数量

    private Integer allocatedQty; // 已分配数量

    private Integer returnedQty;  // 已退货数量

    private String pplNo; // ppl号

    /**
     * 客户群代码
     */
    private String groupCustomerNo;

    private String shiKomiNo; // SHiKoMi号

    private String salesInfoNo; //  营业情报号

    private String corderNo; // 客户订单号

    private String addressNo; // 收货地址编号

    /**
     * 客户自定义出货代码(打印在标签上)
     */
    private String customCode;

    private Integer orderType; // 订单类型

    // private String customerNo; // 客户代码

    private Date processDate; // 最后的接单处理时间

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
     * 特殊标识
     */
    private Integer expDlvType;

    private String expDlvTypeName;

    private String expLinkNo;

    private Integer version;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime; //

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime; //

    private String createUser; //

    private String updateUser; //

    /**
     * 拆分标识(0:不拆分；1:数量拆分;  2:型号拆分)
     */
    private String prodFlag;


}
