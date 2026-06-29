package com.smc.smccloud.model.orderstate;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author smc
 * @since 2021-10-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("order_state")
public class OrderStateDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 订单类型
     */
    @TableField("order_type")
    private Integer orderType;

    /**
     * 状态代码
     */
    @TableField("state_code")
    private Integer stateCode;

    /**
     * 状态类型
     */
    @TableField("state_type")
    private Integer stateType;

    /**
     * 客户交货期
     */
    @TableField("cust_dlv_date")
    private Date custDlvDate;

    /**
     * 预计发货日期(根据cust_dlv_date算出)
     */
    @TableField("cust_ship_date")
    private Date custShipDate;

    /**
     * 客户签收日期
     */
    @TableField("cust_sign_date")
    private Date custSignDate;

    /**
     * 营业所答应客户的交货期
     */
    @TableField("dept_dlv_date")
    private Date deptDlvDate;

    /**
     * 指定工厂出荷日
     */
    @TableField("po_dlv_date")
    private Date poDlvDate;

    /**
     * 供应商发出日
     */
    @TableField("po_ship_date")
    private Date poShipDate;

    /**
     * 到货日期
     */
    @TableField("act_arrival_date")
    private Date actArrivalDate;

    /**
     * 预计到达日期
     */
    @TableField("es_arrival_date")
    private Date esArrivalDate;

    /**
     * 根据po_dlv_date算预计到达日期
     */
    @TableField(value = "es_arrive_date_req")
    private Date esArriveDateReq;

    /**
     * 实际物流发货日期
     */
    @TableField("ship_date")
    private Date shipDate;

    /**
     * 预计发货日期
     */
    @TableField("es_ship_date")
    private Date esShipDate;

    /**
     * 快递送达日期
     */
    @TableField("express_dlv_date")
    private Date expressDlvDate;

    /**
     * 实际送达日期-客户交货期
     */
    @TableField("delay_day")
    private Integer delayDay;

    /**
     * 工厂(采购)延误天数
     */
    @TableField("po_delay_day")
    private Integer poDelayDay;

    // 延误到达天数
    @TableField(exist = false)
    private Integer arrivalDelayDay;

    @TableField("provider")
    private String provider;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField("state_des")
    private String stateDes;

    @TableField("dept_no")
    private String deptNo;

    @TableField("model_no")
    private String modelNo;

    @TableField("supplier_no")
    private String supplierNo;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "order_no")
    private String orderNo;

    @TableField(value = "state_date",fill = FieldFill.INSERT )
    private Date stateDate;

    @TableField("quantity")
    private Integer quantity;

    @TableField("customer_no")
    private String customerNo;

    @TableField("po_reply_date")
    private Date poReplyDate;

    @TableField("supplier_code")
    private String supplierCode;

    @TableField("user_no")
    private String userNo;

    @TableField(value = "update_time")
    private Date updateTime;

    @TableField(value = "corder_no")
    private String corderNo;

    @TableField(value = "cmodel_no")
    private String cmodelNo;

    @TableField(value = "trans_Type")
    private String transType;

    /**
     * 供应商订单号
     */
    @TableField(value = "supplier_orderNo")
    private String supplierOrderNo;

    @TableField(value = "shikomi_no")
    private String shikomiNo;

    /**
     * 供应商接单时间
     */
    @TableField("supplier_rcvtime")
    private Date supplierRcvTime;

    /**
     * 下单人
     */
    @TableField("order_psn_no")
    private String orderPsnNo;

    /**
     * 审批人
     */
    @TableField("order_appover_no")
    private String orderAppoverNo;

    @TableField("po_invoice_no")
    private String poInvoiceNo;

    /**
     * 1-出供应商在库
     */
    @TableField("po_exp_type")
    private String poExpType;


    /**
     * 接单订单号，不包括项号
     */
    @TableField("rorder_no")
    private String rorderNo;

    @TableField("po_holon")
    private String poHolon;

    /**
     * 发给供应商的订单号
     */
    @TableField("po_No")
    private  String poNo;

    /**
     * 下单日期
     */
    @TableField("order_date")
    private Date orderDate;

    /**
     *采购类型 A B K U
     */
    @TableField("purchase_type")
    private String purchaseType;

    @TableField(value = "Receive_Date")
    private Date receiveDate;

    @TableField(value = "po_date")
    private Date poDate;

    @TableField(value = "opt_user_no")
    private String optUserNo;

    @TableField(value = "opt_user_name")
    private String optUserName;

    /**
     * 采购收货日期
     */
    @TableField(value = "po_rcv_time")
    private Date poRcvTime;

    /**
     * 催促号A
     */
    @TableField(value = "inqA")
    private String inqA;

    @TableField(value = "inqB")
    private String inqB;

    /**
     * 工厂纳期
     */
    @TableField(value = "po_reply_dateA")
    private Date poReplyDateA;

    /**
     * 工厂纳期
     */
    @TableField(value = "po_reply_dateB")
    private Date poReplyDateB;

    /**
     * 工厂纳期
     */
    @TableField(value = "po_reply_dateC")
    private Date poReplyDateC;

    /**
     * 采购发单时的原运输方式
     */
    @TableField(value = "po_trans_type")
    private String poTransType;

    @TableField(value = "warehouse_code")
    private String warehouseCode;

    @TableField(value = "port_ArriveDate")
    private Date portArriveDate;

    @TableField(value = "customs_Date")
    private Date customsDate;

    @TableField(value = "begin_produce_Date")
    private  Date beginProduceDate;

    @TableField(value = "po_Price")
    private BigDecimal poPrice;

    @TableField(value = "item_no")
    private Integer itemNo;

    @TableField(value = "split_no")
    private  Integer splitNo;

    @TableField("rcv_status")
    private Integer rcvStatus;

    @TableField("order_psn_dept")
    private String orderPsnDept;

    @TableField("end_user")
    private String endUser;

    @TableField("intercept")
    private Boolean intercept;

    /**
     * 发票导入时间
     */
    @TableField("po_imptime")
    private Date poImpTime;
    /**
     * 实际出厂日
     */
    @TableField("po_facExpdate")
    private Date poFacExpdate;

    // 0 - 客户订单 1 采购单
    @TableField(exist = false)
    private int purchaseFlag;

    @TableField("dlv_updtime")
    private Date dlvUpdtime;

    @TableField(exist = false)
    private String remark;

    // 预计生产完成日
    @TableField("expected_production_completion_dateH")
    private Date expectedProductionCompletionDateH;
    // 预计入物流日
    @TableField("expected_logistics_arrival_dateW")
    private Date expectedLogisticsArrivalDateW;

}
