package com.smc.smccloud.model.orderstate;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderStateVO {


    /**
     * 订单类型
     */
    //@NotNull
    private Integer orderType;

    /**
     * 状态代码:
     * 11-发送订单;
     * 12-接入订单;
     * 13-接单处理;
     * 20-采购中;
     * 21-供应商处理异常;
     * 22-供应商已接单;
     * 25-生产中;
     */
    private Integer stateCode;

    /**
     * 状态类型
     */
    private Integer stateType;

    /**
     * 客户交货期
     */
    private Date custDlvDate;

    /**
     * 物流预计发货日期(根据cust_dlv_date算出)
     */
    private Date custShipDate;

    /**
     * 客户签收日期
     */
    private Date custSignDate;

    /**
     * 营业所答应客户的交货期
     */
    private Date deptDlvDate;

    /**
     * 指定工厂出荷日
     */
    private Date poDlvDate;

    /**
     * 供应商发出日
     */
    private Date poShipDate;

    /**
     * 到货日期
     */
    private Date actArrivalDate;

    /**
     * 预计到达日期
     */
    private Date esArrivalDate;

    private Integer rcvStatus;


    // 根据po_dlv_date算预计到达日期
    private Date esArriveDateReq;

    /**
     * 实际物流发货日期
     */
    private Date shipDate;

    /**
     * 预计发货日期
     */
    private Date esShipDate;

    /**
     * 快递送达日期
     */
    private Date expressDlvDate;

    /**
     * 实际送达日期-客户交货期
     */
    private Integer delayDay;

    /**
     * 工厂(采购)延误天数
     */
    private Integer poDelayDay;

    /**
     * 状态来源
     */
    private String provider;

    private Date createTime;

    /**
     * 状态说明
     */
    private String stateDes;

    private String deptNo;

    private String modelNo;

    private String supplierNo;

    private Long id;

    private Long orderStateDetailId;

    private String orderNo;

    private Date stateDate;

    private Integer quantity;

    private String customerNo;

    private String customerType;

    private Date poReplyDate; // 工厂纳期

    private String supplierCode;

    private String userNo;

    private Date updateTime;


    /**
     * 下单人
     */
    private String orderPsnNo;

    /**
     * 审批人
     */
    private String orderAppoverNo;

    private String poInvoiceNo;

    /**
     * 1-出供应商在库
     */
    private String poExpType;


    /**
     * 变更次数
     */
    private Integer changeTimes;


    /**
     * 状态的最早时间
     */
    private Date minDate;

    /**
     * 状态的最晚时间
     */
    //private Date maxDate;

    private String remark;

    private String corderNo;

    /**
     * 客户型号
     */
    private String cmodelNo;

    /**
     * 首次时间
     */
    private Date firstDate;

    /**
     * 运输方式
     */
    private String transType;
    /**
     * 日本订单号
     */
    private String supplierOrderNo;

    private String shikomiNo;

    private Date supplierRcvTime;

    private String poHolon;

    /**
     * 接单订单号，不包括项号
     */
    private String rorderNo;

    /**
     * 采购订单号
     */
    private String poNo;

    /**
     * 下单日期
     */
    private Date orderDate;

    /**
     * A K B U
     */
    private String purchaseType;


    private Date wmsDlvDate;

    /**
     * 接单日期
     */
    private Date receiveDate;

    /**
     * 发送采购订单时间
     */
    private Date poDate;

    /**
     * 操作人工号
     */
    private String optUserNo;
    /**
     * 操作人姓名
     */
    private String optUserName;


    /**
     * 是否同时写入日志
     */
    private Boolean isAddLog;

    /**
     * 采购收货日期
     */
    private Date poRcvTime;

    /**
     * 催促号A
     */
    private String inqA;

    /**
     * 催促号B
     */
    private String inqB;

    /**
     * 工厂纳期
     */
    private Date poReplyDateA;

    /**
     * 工厂纳期
     */
    private Date poReplyDateB;

    /**
     * 工厂纳期
     */
    private Date poReplyDateC;

    /**
     * 采购发单时的原运输方式
     */
    private String poTransType;

    /**
     * 采购单价
     */
    private BigDecimal poPrice;

    private BigDecimal price;

    /**
     * 报关日期
     */
    private Date customsDate;

    /**
     * 开始加工日
     */
    private Date beginProduceDate;

    /**
     * 收货仓库
     */
    private String warehouseCode;

    private  Integer itemNo;
    private  Integer splitNo;

    private String orderPsnDept;

    // 0 - 客户订单 1 采购单
    private int purchaseFlag;

    private String endUser;

    private Boolean intercept;

    private String hrUnitId;

    private String hlCode;

    private String name;

    private String logDataProvider;

    /**
     * 采购发票入库时间
     */
    private  Date poImpTime;

    /**
     * 实际出厂日
     */
    private Date poFacExpdate;

    // 1-delivery文件
    private int dateFrom;

    private String mqSendDate;

    // 预计生产完成日
    private Date expectedProductionCompletionDateH;
    // 预计入物流日
    private Date expectedLogisticsArrivalDateW;


    private Date portArriveDate;
}
