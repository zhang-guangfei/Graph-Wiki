package com.smc.smccloud.model.order;

import com.smc.smccloud.model.fileupload.FileUpload;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author lyc
 * @Date 2023/11/6 15:13
 * @Descripton TODO
 */
@Data
public class SmsSendOpsDetailTaskBean {
    /**
     * 申请号
     */
    private String applyNo;

    /**
     * 变更类型
     */
    private String modifyType;


    /**
     * 完整订单号
     */
    private String orderNo;

    /**
     * 营业所
     */
    private String deptNo;

    /**
     * 客户代码
     */
    private String customerNo;

    /**
     * 用户代码
     */
    private String userNo;

    /**
     * 转订类型
     */
    private String changeType;

    /**
     * 变更后内容
     */
    private String changeMessage;

    /**
     * 特价号
     */
    private String spAnswerno;

    /**
     * 原因说明,申请理由
     */
    private String reason;

    /**
     * 备注
     */
    private String remark;

    /**
     * 申请人
     */
    private String applyPersonNo;

    /**
     * 申请时间
     */
    private Date applyTime;

    /**
     * 申请部门
     */
    private String applyDeptNo;

    /**
     * 返回数量
     */
    private int returnQuantity;

    /**
     * 返回物流中心
     */
    private String returnLogisticsCenter;

    /**
     * 返回日期
     */
    private Date returnDate;

    /**
     * 结转数量
     */
    private int carryOverQuantity;

    /**
     * 开票单价
     */
    private BigDecimal billingUnitPrice;

    /**
     * 金额，=结转数量*开票单价
     */
    private BigDecimal amount;

    /**
     * 用户单价
     */
    private BigDecimal userUnitPrice;

    /**
     * 特价号
     */
    private String specialPriceNo;

    /**
     * 样品申请类型
     */
    private String sampleApplyType;

    /**
     * 样品结转类型
     */
    private String carryOverType;

    // 样品申请单号
    private String sampleAppleNo;
    // 样品结转部门
    private String carryOverDeptNo;
    // 型号
    private String modelNo;
    // 订单数量
    private int orderQuantity;
    // 原单价
    private BigDecimal orderUnitPrice;
    // 是否结转过
    private boolean isAlreadyBal;
    // 明细表附件
    private List<FileUpload> attachmentList;

    // 结转表id
    private Long sampleBalId;


    /**
     * 快递单号
     */
    private String trackingNo;

    /**
     * 邮寄时间
     */
    private Date mailingDate;

    private boolean autoCarryOverFlag = false;

    // 交易主体
    private String tradeSubject;

    /**
     * 客户类型 0：直销
     * 1：代理店
     * 2：经销
     */
    private String customerType;

}
