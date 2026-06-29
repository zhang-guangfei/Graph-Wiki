package com.smc.smccloud.model.ordermodify;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author edp02 @Date 2021/11/5 15:23
 */
@Data
public class OrderModifyVO implements Serializable {

    private static final long serialVersionUID = 1559320742538937827L;

    /**
     * 任务批次号
     */
    private String batchNo;

    /**
     * 申请号
     */
    private String applyNo;

    /**
     * 变更类型
     */
    private String modifyType;

    private String modifyTypeName;

    /**
     * 处理状态
     */
    private Integer status;

    private String statusName;

    /**
     * 完整订单号
     */
    private String orderNo;

    private String rorderNo;

    private int rorderItem;

    /**
     * 营业所
     */
    private String deptNo;

    /**
     * 客户代码
     */
    private String customerNo;

    /**
     * 转订类型
     */
    private String changeType;

    private String changeTypeName;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date applyTime;

    /**
     * 申请部门
     */
    private String applyDeptNo;

    /**
     * 业务处理人
     */
    private String answerPns;

    /**
     * 业务处理回复内容
     */
    private String answerText;

    /**
     * 业务处理时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date answerTime;

    private String updateUser;

    private Long id;

    private String createUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private String supplierOrderNo;


    // =================  表结构里没有的字段=============
    private String modelNo;

    private Integer quantity;

    // 客户所在部门
    private String customerDeptNo;//HL部门
    private String customerDeptNo2;//部门

    // 出库区分
    private String stockCode;
    // 拆分标识
    private String prodFlag;
    // 最终用户名称
    private String endUser;

    // private String endUserName;

    // 订单状态
    private String orderStatus;

    @JsonFormat(pattern = "yyyy-MM-dd",shape = JsonFormat.Shape.STRING,timezone = "GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JSONField(format="yyyy-MM-dd")
    private Date hopeExportDate;

    private String transType;

    private String supplierId;

    private String special;

    private Boolean isGt500w;

    private String secondProcessName;

    private Boolean secondProcess;

    private String isGt500wName;

    // 责任方
    private String responsibleParty;

    // 删单原因
    private String cancelReason;

    // 手续费率
    private BigDecimal processingFeeRate;

    private String source;

    private String stockType;

    // 是否制定调库计划
    private Boolean transfer = false;
    private String transferName;

    // 调入用户专备
    private String endUserForTransferSpecial;

    //二级部
    private String secondDeptNo;

    //订货日期
    private Date orderDate;
    private String orderDateStr;

    private String associateNo;

    //返信日期
    private String deliveryTime;

    //子项供应商BIN
    private String binflag;
    //采购状态
    private String poStateCode;
    //删单对策
    private String cancelStrategy;

    private String orderType;
}
