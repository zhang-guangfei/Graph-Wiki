package com.smc.smccloud.model.sampleorder;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author smc
 * @since 2021-11-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sampleOrder_apply")
public class SampleOrderApplyDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    /**
     * 申请号
     */
    @TableField(value = "apply_no")
    private String applyNo;

    /**
     * 接单订单号
     */
    @TableField("order_no")
    private String orderNo;

    /**
     * 状态
     */
    @TableField("status")
    private Integer status;

    /**
     * 客户单号
     */
    @TableField("corder_no")
    private String corderNo;

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
     * 申请部门
     */
    @TableField("apply_deptNo")
    private String applyDeptNo;

    /**
     * 申请类型
     */
    @TableField("apply_type")
    private String applyType;

    /**
     * 成本结算类型
     */
    @TableField("cost_type")
    private String costType;

    /**
     *  申请类别名称
     */
    @TableField("apply_type_name")
    private String applyTypeName;

    /**
     * 结算类型名称
     */
    @TableField("cost_type_name")
    private String costTypeName;

    /**
     * 申请目的
     */
    @TableField("purpose")
    private String purpose;

    /**
     * 部门
     */
    @TableField("dept_no")
    private String deptNo;

    /**
     * 出货方式
     */
    @TableField("dlv_entire")
    private String dlvEntire;

    /**
     * 出货类别1
     */
    @TableField("dlv_type1")
    private String dlvType1;

    /**
     * 出货类别2
     */
    @TableField("dlv_type2")
    private String dlvType2;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 收货地址类别
     */
    @TableField("address_type")
    private Integer addressType;

    /**
     * 地址编号
     */
    @TableField("address_no")
    private String addressNo;

    /**
     * 申请时间
     */
    @TableField("apply_time")
    private Date applyTime;

    /**
     * 生成订单时间
     */
    @TableField("order_date")
    private Date orderDate;

    /**
     * 回复说明
     */
    @TableField("answer_text")
    private String answerText;

    /**
     * 处理人
     */
    @TableField("answer_psn")
    private String answerPsn;

    /**
     * 处理时间
     */
    @TableField("answer_time")
    private Date answerTime;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_time")
    private Date updateTime;

    @TableField("create_user")
    private String createUser;

    @TableField("update_user")
    private String updateUser;

    /**
     * 申请人
     */
    @TableField("apply_psn")
    private String applyPsn;

    /**
     * 收货人地址
     */
    @TableField("receiver_address")
    private String receiverAddress;

    /**
     * 收货人电话
     */
    @TableField("receiver_phone")
    private String receiverPhone;

    /**
     * 收货人
     */
    @TableField("receiver_name")
    private String receiverName;

    /**
     * 收货公司
     */
    @TableField("receiver_company")
    private String receiverCompany;

    /**
     * 订单的SMC交易主体
     */
    @TableField("trade_companyId")
    private String tradeCompanyId;

    @TableField("receiver_postCode")
    private String receiverPostCode;

    @TableField("receiver_province")
    private String receiverProvince;

    @TableField("receiver_city")
    private String receiverCity;

    @TableField("receiver_district")
    private String receiverDistrict;

    @TableField("receiver_stateCode")
    private String receiverStateCode;

    @TableField("receiver_carrierId")
    private String receiverCarrierId;

    // 索赔号
    @TableField("claim_no")
    private String claimNo;

    // 索赔金额
    @TableField("claim_amount")
    private String claimAmount;

    // 索赔公司
    @TableField("express_company")
    private String expressCompany;

    /**
     * 收货地所在营业所代码
     */
    @TableField("delivery_dept_no")
    private String deliveryDeptNo;
}
