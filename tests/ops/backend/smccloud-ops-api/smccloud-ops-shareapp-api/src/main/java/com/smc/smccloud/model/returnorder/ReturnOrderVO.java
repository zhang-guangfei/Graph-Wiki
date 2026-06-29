package com.smc.smccloud.model.returnorder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 
 * </p>
 *
 * @author smc
 * @since 2021-11-06
 */
@Data
public class ReturnOrderVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 申请id
     */
    @NotBlank
    private String applyNo;

    @NotNull
    private Integer status;

    private String statusName;

    /**
     * 订单号
     */
    @NotBlank
    private String orderNo;

    /**
     * 型号
     */
    private String modelNo;

    /**
     * 下单数量
     */
    private Integer orderQty;

    /**
     * 申请数量
     */
    @NotNull
    private Integer applyQty;

    /**
     * 申请不良品数量
     */
    private Integer applyBadqty;

    /**
     * 收到数量
     */
    private Integer rcvFineqty;

    /**
     * 收到坏品数量
     */
    private Integer rcvBadqty;

    /**
     * 退货入库数量
     */
    private Integer returnQty;

    /**
     * 销售单价
     */
    private BigDecimal salesPrice;

    /**
     * 发票号
     */
    private String invoiceNo;

    /**
     * 开票日期
     */
    private Date invoiceDate;

    private String invoiceDateStr;

    // @NotNull
    private int toUserStock;

    private String toUserStockName;

    /**
     * 部门代码
     */
    private String deptNo;
    private String deptName;

    /**
     * 客户代码
     */
    private String customerNo;

    /**
     * 用户代码
     */
    private String userNo;

    /**
     * 申请原因
     */
    @NotBlank
    private String reason;

    /**
     * 回复说明
     */
    private String answerText;

    /**
     * 出库代码
     */
    private String expStocktype;

    /**
     * 申请人
     */
    private String applicant;

    /**
     * 审批人
     */
    private String approver;

    /**
     * 收货人
     */
    private String consignee;

    /**
     * 下单日期
     */
    private Date orderDate;

    private String orderDateStr;

    /**
     * 出库日期
     */
    private Date expDate;
    private String expDateStr;

    /**
     * 申请时间
     */
    private Date applyTime;
    private String applyTimeStr;

    /**
     * 通过时间
     */
    private Date passTime;
    private String passTimeStr;

    /**
     * 收货时间
     */
    private Date receiveTime;
    private String receiveTimeStr;

    /**
     * 入库时间
     */
    private Date inTime;
    private String inTimeStr;

    /**
     * 备注
     */
    private String remark;

    /**
     * 申请原因类型
     */
    private String reasonType;

    /**
     * 责任类型
     */
    private String dutyType;

    /**
     * 原因说明
     */
    private String reasonValue;

    /**
     * 责任说明
     */
    private String dutyValue;

    /**
     * 退货手续费
     */
    @NotNull
    private BigDecimal returnFee;

    /**
     * 手续费率
     */
    private BigDecimal feeRate;

    /**
     * 快递单号
     */
    private String expressNo;

    private Date createTime;

    private Date updateTime;

    /**
     * 申请次数
     */
    @NotNull
    private Integer applyTimes;

    /**
     * 是否组装型号
     */
    private Boolean isAssOrder;

    /**
     * 关联新订单
     */
    private String newOrdernos;

    /**
     * 联系人
     */
    private String contactPsn;

    /**
     * 联系电话
     */
    private String contactTelno;

    /**
     * 打印时间
     */
    private Date printTime;

    /**
     * 交易公司
     */
    private String tradeComanyid;

    /**
     * 收货备注
     */
    private String receiveNote;

    private String createUser;

    private String updateUser;

//    private String rcvWarehouseCode;
//
//    // 出库仓库（客户订单出库仓库或委托在库退货仓库）
//    private String fromWarehouseCode;

    // 要求退货的仓库
    private String requestWarehouseCode;

    private String requestWarehouseCodeName;

    // 实际收货仓库
    private String rcvWarehouseCode;

    private String rcvWarehouseCodeName;
    // 库存是否退回专备
    private Boolean backToCustomerStock;

    // 申请人邮箱
    private String applicantEmail;

    /**
     * 订单类型
     * 1-销售订单
     * 21-委托在库补库订单
     * 9-样品订单
     */
    @NotNull
    private Integer orderType;

    // 申请项号(第几项)
    private Integer itemNo;

    private String expStockCode;

    private Integer extStatus;

    private String locationNo;

    private String isSpecialReserve;

    // 申请人电话
    private String applicantTel;

    // 风险级别
    private String proDangerLevel;

    // 退货类别
    private String returnType;

    // 客户担当
    private String employee;

    private String fileName;

    private String fileRealName;

    // 是否质保期内
    private String whetherSellByDate;
}
