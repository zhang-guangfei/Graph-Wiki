package com.smc.smccloud.model.orderstate;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author lyc
 * @Date 2022/2/15 12:02
 * @Descripton TODO
 */
@Data
public class OrderStateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String orderNo;

    private Long id;

    private Integer itemNo;

    private Integer  splitNo;

    private String rorderNo;

    /**
     * 订单类型
     */
    private Integer orderType;

    private String modelNo;

    private Integer quantity;

    /**
     * 状态代码
     */
    private Integer stateCode;

    private String stateDes;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date stateDate;

    /**
     * 状态类型
     */
    private Integer stateType;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 客户交货期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date custDlvDate;

    /**
     * 预计发货日期(根据cust_dlv_date算出)
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date custShipDate;

    /**
     * 客户签收日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date custSignDate;


    /**
     * 营业所答应客户的交货期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date deptDlvDate;

    /**
     * 指定工厂出荷日
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date poDlvDate;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date poReplyDate;

    /**
     * 供应商发出日
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date poShipDate;

    private String customerNo;

    private String userNo;

    private String deptNo;

    private String supplierCode;

    private String supplierNo;

    /**
     * 到货日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date actArrivalDate;

    /**
     * 预计到达日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date esArrivalDate;

    /**
     * 根据po_dlv_date算预计到达日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date esArriveDateReq;

    private String provider;

    /**
     * 实际物流发货日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date shipDate;

    /**
     * 预计发货日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date esShipDate;

    /**
     * 快递送达日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date expressDlvDate;

    /**
     * 实际送达日期-客户交货期
     */
    private Integer delayDay;

    /**
     * 工厂(采购)延误天数
     */
    private Integer poDelayDay;

    // 延误到达天数
    private Integer arrivalDelayDay;

    private String corderNo;

    /**
     * 客户型号
     */
    private String cmodelNo;


    private String transType;

    /**
     * 供应商订单号
     */
    private String supplierOrderNo;


    private String shikomiNo;

    /**
     * 供应商接单时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date supplierRcvTime;


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

    private String poHolon;

    private  String poNo;

    /**
     * 下单日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date orderDate;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date receiveDate;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date poDate;

    private String purchaseType;

    /**
     * 操作人工号
     */
    private  String optUserNo;
    /**
     * 操作人姓名
     */
    private  String optUserName;

    /**
     * 采购收货日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date poRcvTime;

    /**
     * 催促号A
     */
    private String inqA;

    /**
     * 催促号B
     */
    private String inqB;

    private Integer rcvStatus;

    /**
     * 工厂纳期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date poReplyDateA;

    /**
     * 工厂纳期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date poReplyDateB;

    /**
     * 工厂纳期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date poReplyDateC;

    /**
     * 采购发单时的原运输方式
     */
    private String poTransType;

    // 收货仓库
    private String warehouseCode;

    // 到港时间
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date portArriveDate;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date customsDate;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private  Date beginProduceDate;

    private BigDecimal poPrice;

    // >>>>>>>>>>

    private String purchaseNo; // 客户PO号

    // 单据状态
    private String billStatus;
    // 订单类型名称
    private String orderTypeName;
    // 用户名称
    private String userName;
    private String customerName;

    private String orderPsnDept;

    private String endUser;

    private Boolean intercept;

    /**
     * 实际出厂日
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date poFacExpdate;

    // 特殊纳期
    private String poReplyDateStr;

    private String poReplyDateAStr;

    private String poReplyDateBStr;

    private String poReplyDateCStr;

    // 预计生产完成日
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date expectedProductionCompletionDateH;
    // 预计入物流日
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date expectedLogisticsArrivalDateW;

}
