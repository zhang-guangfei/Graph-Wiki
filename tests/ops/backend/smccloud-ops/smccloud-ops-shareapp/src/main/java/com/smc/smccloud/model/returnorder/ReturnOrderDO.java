package com.smc.smccloud.model.returnorder;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author smc
 * @since 2021-11-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("return_order")
public class ReturnOrderDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "Id", type = IdType.AUTO)
    private Long id;

    /**
     * 申请id
     */
    @TableField("apply_no")
    private String applyNo;

    @TableField("status")
    private Integer status;

    @TableField(exist = false)
    private String statusName;

    /**
     * 订单号
     */
    @TableField("order_no")
    private String orderNo;

    /**
     * 型号
     */
    @TableField("model_no")
    private String modelNo;

    /**
     * 下单数量
     */
    @TableField("order_qty")
    private Integer orderQty;

    /**
     * 申请数量
     */
    @TableField("apply_qty")
    private Integer applyQty;

    /**
     * 申请不良品数量
     */
    @TableField("apply_badQty")
    private Integer applyBadqty;

    /**
     * 收到数量
     */
    @TableField("rcv_fineQty")
    private Integer rcvFineqty;

    /**
     * 收到坏品数量
     */
    @TableField("rcv_badQty")
    private Integer rcvBadqty;

    /**
     * 退货入库数量
     */
    @TableField("return_qty")
    private Integer returnQty;

    /**
     * 销售单价
     */
    @TableField("sales_price")
    private BigDecimal salesPrice;

    /**
     * 发票号
     */
    @TableField("invoice_no")
    private String invoiceNo;

    /**
     * 开票日期
     */
    @TableField("invoice_date")
    private Date invoiceDate;

    /**
     * 部门代码
     */
    @TableField("dept_no")
    private String deptNo;

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
     * 申请原因
     */
    @TableField("reason")
    private String reason;

    /**
     * 回复说明
     */
    @TableField("answer_text")
    private String answerText;

    /**
     * 出库代码
     */
    @TableField("exp_stockType")
    private String expStocktype;

    /**
     * 申请人
     */
    @TableField("applicant")
    private String applicant;

    /**
     * 审批人
     */
    @TableField("approver")
    private String approver;

    /**
     * 收货人
     */
    @TableField("consignee")
    private String consignee;

    /**
     * 下单日期
     */
    @TableField("order_date")
    private Date orderDate;

    /**
     * 出库日期
     */
    @TableField("exp_date")
    private Date expDate;

    /**
     * 申请时间
     */
    @TableField("apply_time")
    private Date applyTime;

    /**
     * 通过时间
     */
    @TableField("pass_time")
    private Date passTime;

    /**
     * 收货时间
     */
    @TableField("receive_time")
    private Date receiveTime;

    /**
     * 入库时间
     */
    @TableField("in_time")
    private Date inTime;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 申请原因类型
     */
    @TableField("reason_type")
    private String reasonType;

    /**
     * 责任类型
     */
    @TableField("duty_type")
    private String dutyType;

    /**
     * 原因说明
     */
    @TableField("reason_value")
    private String reasonValue;

    /**
     * 责任说明
     */
    @TableField("duty_value")
    private String dutyValue;

    /**
     * 退货手续费
     */
    @TableField("return_fee")
    private BigDecimal returnFee;

    /**
     * 手续费率
     */
    @TableField("fee_rate")
    private BigDecimal feeRate;

    /**
     * 快递单号
     */
    @TableField("express_no")
    private String expressNo;

    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 申请次数
     */
    @TableField("apply_times")
    private Integer applyTimes;

    /**
     * 是否组装型号
     */
    @TableField("is_ass_order")
    private Boolean isAssOrder;

    @TableField("to_user_stock")
    private int toUserStock;

    @TableField(exist = false)
    private String toUserStockName;

    /**
     * 关联新订单
     */
    @TableField("new_ordernos")
    private String newOrdernos;

    /**
     * 联系人
     */
    @TableField("contact_psn")
    private String contactPsn;

    /**
     * 联系电话
     */
    @TableField("contact_telno")
    private String contactTelno;

    /**
     * 打印时间
     */
    @TableField("print_time")
    private Date printTime;

    /**
     * 交易公司
     */
    @TableField("trade_comanyId")
    private String tradeComanyid;

    /**
     * 收货备注
     */
    @TableField("receive_note")
    private String receiveNote;

    @TableField("create_user")
    private String createUser;

    @TableField("update_user")
    private String updateUser;

    // 实际收货仓库
    @TableField("rcv_warehouse_code")
    private String rcvWarehouseCode;

    @TableField(exist = false)
    private String rcvWarehouseCodeName;

    /**
     * 要求退货的仓库
     */
    @TableField("request_warehouse_code")
    private String requestWarehouseCode;

    @TableField(exist = false)
    private String requestWarehouseCodeName;

    // 库存是否退回专备
    @TableField("backToCustomerStock")
    private Boolean backToCustomerStock;

    // 申请人邮箱
    @TableField("applicantEmail")
    private String applicantEmail;

    @TableField("order_type")
    private Integer orderType;

    @TableField("item_no")
    private Integer itemNo;

    @TableField("exp_stock_code")
    private String expStockCode;

    @TableField("ext_status")
    private Integer extStatus;

    @TableField("location_no")
    private String locationNo;

    @TableField(exist = false)
    private String deptName;

    // add by LiYingChao from  bugId 8421 in 20221103
    // 申请人电话
    @TableField("applicant_tel")
    private String applicantTel;

    // 风险级别
    @TableField("pro_danger_level")
    private String proDangerLevel;

    // 退货类别
    @TableField("return_type")
    private String returnType;

    // 客户担当
    @TableField("employee")
    private String employee;

    @TableField("fileName")
    private String fileName;

    @TableField("fileRealName")
    private String fileRealName;

    // 是否在质保期内
    @TableField("isWarrantyPeriod")
    private int isWarrantyPeriod;

}
