package com.smc.smccloud.model.sampleorder;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
 * @since 2023-10-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sample_bal_apply")
public class SampleBalApplyDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 样品结转申请号
     */
    @TableField("sample_bal_apply_no")
    private String sampleBalApplyNo;

    /**
     * 样品申请号
     */
    @TableField("apply_no")
    private String applyNo;

    /**
     * 样品订单号
     */
    @TableField("order_no")
    private String orderNo;

    /**
     * 客户代码
     */
    @TableField("customer_no")
    private String customerNo;

    /**
     * 型号
     */
    @TableField("model_no")
    private String modelNo;

    /**
     * 订单数量
     */
    @TableField("quantity")
    private Integer quantity;

    /**
     * 申请结转数量
     */
    @TableField("apply_bal_qty")
    private Integer applyBalQty;

    /**
     * 原单价
     */
    @TableField("price")
    private BigDecimal price;

    /**
     * 申请结转单价
     */
    @TableField("apply_bal_price")
    private BigDecimal applyBalPrice;

    /**
     * 用户单价
     */
    @TableField("user_price")
    private BigDecimal userPrice;

    /**
     * 特价号
     */
    @TableField("spec_offer_no")
    private String specOfferNo;

    /**
     * 申请部门
     */
    @TableField("apply_dept_no")
    private String applyDeptNo;

    /**
     * 申请人
     */
    @TableField("apply_psn_no")
    private String applyPsnNo;

    /**
     * 申请时间
     */
    @TableField("apply_time")
    private Date applyTime;

    /**
     * 样品申请类型
     */
    @TableField("apply_type")
    private String applyType;

    /**
     * 样品申请结转类型
     */
    @TableField("apply_bal_type")
    private String applyBalType;

    /**
     * 结转部门
     */
    @TableField("bal_dept_no")
    private String balDeptNo;

    /**
     * 申请返回物流中心
     */
    @TableField("back_warehource")
    private String backWarehource;

    /**
     * 返回日期
     */
    @TableField("back_time")
    private Date backTime;

    /**
     * 寄送承运商
     */
    @TableField("carrier_id")
    private String carrierId;

    /**
     * 寄送运单号
     */
    @TableField("express_no")
    private String expressNo;

    /**
     * 申请理由
     */
    @TableField("apply_reason")
    private String applyReason;

    /**
     * 0未受理 1已受理 2已结转 3退回申请
     */
    @TableField("hand_status")
    private Integer handStatus;

    /**
     * 受理说明(当申请发送后校验不符合要求退回时填写)
     */
    @TableField("hand_remark")
    private String handRemark;

    /**
     * 受理时间
     */
    @TableField("hand_time")
    private Date handTime;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_user")
    private String updateUser;

    @TableField("create_user")
    private String createUser;

    @TableField("update_time")
    private Date updateTime;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("to_user_stock")
    private Boolean toUserStock;

    @TableField("sign_qty")
    private int signQty;

    @TableField("sign_time")
    private Date signTime;

    // 结转表id
    @TableField("sample_bal_id")
    private Long sampleBalId;

    @TableField("batch_no")
    private String batchNo;

    // 是否已结转
    @TableField("is_already_bal")
    private Boolean isAlreadyBal;

    @TableField("special")
    private String special;

    @TableField("user_no")
    private String userNo;

    // trade_companyId
    @TableField("trade_company_id")
    private String tradeCompanyId;

    @TableField("customer_type")
    private String customerType;
}
