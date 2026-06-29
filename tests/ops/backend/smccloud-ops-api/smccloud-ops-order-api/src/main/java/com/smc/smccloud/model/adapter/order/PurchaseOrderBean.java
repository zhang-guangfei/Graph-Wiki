package com.smc.smccloud.model.adapter.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author lyc
 * @Date 2022/3/17 11:15
 * @Descripton TODO
 */

@Data
public class PurchaseOrderBean {

    // 完整订单号  --> 门户: 订单号
    private String orderNo;

    // 客户订单号 --> 客户po
    private String corderNo;

    // 客户型号 --> 物料号
    private String cmodelNo;

    // 客户代码 --> 客户
    private String customerNo;
    private String customerName;

    // 用户代码 --> 用户
    private String userNo;
    private String userName;


    // 订单类型
    private String orderType;
    private String orderTypeName;

    // 型号
    private String modelNo;

    // 数量
    private Integer quantity;

    // 状态代码名称
    private String stateCode;
    private String stateCodeName;

    private String stateDes;

    // 下单日期 --> 订货日期
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date orderDate;

    // 供应商代码(2位) --> 供应商
    private String supplierCode;
    private String supplierCodeName;

    private String supplierNo;

    // 客户交货期
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date custDlvDate;

    // 指定工厂出荷日
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date poDlvDate;

    // 工厂纳期 --> 工厂返信纳期
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date poReplyDate;

    // 实际工厂出荷日
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date poShipDate;

    // 工厂纳期变更1
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date poReplyDateA;
    // 工厂纳期变更2
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date poReplyDateB;
    // 工厂纳期变更3
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date poReplyDateC;

    // 开始生成日期
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date beginProduceDate;

    // 到港时间 --> 到港日期
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date portArrivedate;

    // 报关时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date customDate;

    // 采购holon --> 采购HL
    private String poHolon;

    // 预计到达日期
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date esArrivalDate;

    // 供应商订单号(手配号)
    private String supplierOrderNo;

    private String inqA;

    private String inqB;

    private String shikomiNo;

    // 供应商发票号 --> 发票号
    private String poInvoiceNo;

    private String poTransType;
    private String poTransTypeName;

    private String transType;

    // 采购类型(AKBU) --> 日本订单分类
    private  String purchaseType;
    private String purchaseTypeName;

    // 工厂(采购)延误天数po_ship_date-po_dlv_date --> 延误天数
    private int poDelayDay;

    // 到货仓库
    private String warehouseCode;
    private String warehouseCodeName;

    // 客户所属部门
    private String deptNo;
    private String deptName;

    private String hlCode;
    private String hrUnitId;
    // private String hlCodeName;

    private String endUser;
    private String endUserName;

    private Boolean intercept;

    private String poExpType;

    // 订单所属部门
    private String orderDeptNo;

    private String orderDeptName;

    /**
     * 实际工厂出荷日
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date poFacExpdate;

    // 特殊纳期翻译
    private String poReplyDateStr;

    private String poReplyDateAStr;

    private String poReplyDateBStr;

    private String poReplyDateCStr;

    // 预计生产完成日
    private Date expectedProductionCompletionDateH;
    // 预计入物流日
    private Date expectedLogisticsArrivalDateW;
 }
