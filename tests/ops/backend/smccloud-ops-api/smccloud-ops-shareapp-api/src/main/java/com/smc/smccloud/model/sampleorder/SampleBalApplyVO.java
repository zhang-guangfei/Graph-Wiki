package com.smc.smccloud.model.sampleorder;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author lyc
 * @Date 2023/10/26 15:09
 * @Descripton TODO
 */
@Data
public class SampleBalApplyVO {
    private static final long serialVersionUID = 1L;

    /**
     * 样品结转申请号
     */
    private String sampleBalApplyNo;

    /**
     * 样品申请号
     */
    private String applyNo;

    /**
     * 样品订单号
     */
    private String orderNo;

    /**
     * 客户代码
     */
    private String customerNo;

    /**
     * 型号
     */
    private String modelNo;

    /**
     * 订单数量
     */
    private Integer quantity;

    /**
     * 申请结转数量
     */
    private Integer applyBalQty;

    /**
     * 原单价
     */
    private BigDecimal price;

    /**
     * 申请结转单价
     */
    private BigDecimal applyBalPrice;

    /**
     * 用户单价
     */
    private BigDecimal userPrice;

    /**
     * 特价号
     */
    private String specOfferNo;

    /**
     * 申请部门
     */
    private String applyDeptNo;

    /**
     * 申请人
     */
    private String applyPsnNo;

    /**
     * 申请时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date applyTime;

    /**
     * 样品申请类型
     */
    private String applyType;

    /**
     * 样品申请结转类型
     */
    private String applyBalType;

    /**
     * 结转部门
     */
    private String balDeptNo;

    /**
     * 申请返回物流中心
     */
    private String backWarehource;

    /**
     * 返回日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date backTime;

    /**
     * 寄送承运商
     */
    private String carrierId;

    /**
     * 寄送运单号
     */
    private String expressNo;

    /**
     * 申请理由
     */
    private String applyReason;

    /**
     * 0未受理 1已受理 2已结转 3退回申请
     */
    private Integer handStatus;

    private String handStatusName;

    /**
     * 受理说明(当申请发送后校验不符合要求退回时填写)
     */
    private String handRemark;

    /**
     * 受理时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date handTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private String updateUser;

    private String createUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private Long id;

    private Boolean toUserStock;

    private int signQty;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date signTime;

    // 结转表id
    private Long sampleBalId;

    private String batchNo;

    private Boolean isAlreadyBal;

    // json格式存储特殊字段
    private String special;

    private String userNo;

    private String forceBalFlag;

    private String tradeCompanyId;

    private String customerType;

}
