package com.smc.ops.delivery.model.inqb;

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
 * @since 2024-06-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ops_inqb_apply")
public class OpsInqbApplyDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 问询申请号（OPS内部生成）
     */
    @TableField("inqb_apply_no")
    private String inqbApplyNo;

    /**
     * 问询申请号（外部）
     */
    @TableField("sources_apply_no")
    private String sourcesApplyNo;

    /**
     * 批次号，由task生成传入
     */
    @TableField("batch_no")
    private String batchNo;

    /**
     * 数据来源（ops,sales）
     */
    @TableField("data_sources")
    private String dataSources;

    /**
     * 型号
     */
    @TableField("model_no")
    private String modelNo;

    /**
     * 数量
     */
    @TableField("quantity")
    private Integer quantity;

    /**
     * 最终用户
     */
    @TableField("end_user")
    private String endUser;

    /**
     * 用户所属部门
     */
    @TableField("user_dept")
    private String userDept;

    /**
     * 状态（已退回、待处理、问询中、部分回复、已回复，已使用，已过期）存code
     */
    @TableField("inqb_status")
    private Integer inqbStatus;

    /**
     * INQB整单的有效性状态 （1表示有效，0表示失效）
     */
    @TableField("inqb_validity")
    private String inqbValidity;

    /**
     * 有效截止日（详情表中最早的）
     */
    @TableField("expiration_date")
    private Date expirationDate;

    /**
     * 期望货期
     */
    @TableField("expected_delivery_date")
    private Integer expectedDeliveryDate;

    /**
     * 申请时间
     */
    @TableField("inqb_date")
    private Date inqbDate;

    /**
     * 是否强制问询、一般问询、可预占问询
     */
    @TableField("inqb_type")
    private Integer inqbType;

    /**
     * 问询分类码
     */
    @TableField("inqb_classify")
    private String inqbClassify;

    /**
     * 申请部门
     */
    @TableField("inqb_dept")
    private String inqbDept;

    /**
     * 申请人员工编号
     */
    @TableField("inqb_user")
    private String inqbUser;

    /**
     * 申请人员工姓名
     */
    @TableField("inqb_user_name")
    private String inqbUserName;

    /**
     * 申请备注信息
     */
    @TableField("description")
    private String description;

    /**
     * 回复单位，按detail中回复最晚的
     */
    @TableField("reply_dept")
    private String replyDept;

    /**
     * 回复号，按detail中回复最晚的
     */
    @TableField("reply_no")
    private String replyNo;

    /**
     * 回复货期，按detail中回复最晚的
     */
    @TableField("reply_delivery_days")
    private Integer replyDeliveryDays;

    /**
     * 回复人，按detail中回复最晚的
     */
    @TableField("reply_user")
    private String replyUser;

    /**
     * 回复时间，按detail中回复最晚的
     */
    @TableField("reply_time")
    private Date replyTime;

    /**
     * 回复原因id（参照制造视图OPS_V_DeliveryConsultingReason），按detail中回复最晚的
     */
    @TableField("reply_reason_code")
    private String replyReasonCode;

    /**
     * 回复原因中文描述
     */
    @TableField("reply_reason")
    private String replyReason;

    /**
     * "回复备注，按detail中回复最晚的校验不通过时，原因写入"
     */
    @TableField("reply_remark")
    private String replyRemark;

    /**
     * 创建人
     */
    @TableField("create_user")
    private String createUser;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 更新人
     */
    @TableField("update_user")
    private String updateUser;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private Date updateTime;

    /**
     * 制造接收HL
     */
    @TableField("reply_accept_hl")
    private String replyAcceptHl;

    /**
     * 对应供应商的实际回复部门代码
     */
    @TableField("reply_supplier_dept")
    private String replySupplierDept;


}
