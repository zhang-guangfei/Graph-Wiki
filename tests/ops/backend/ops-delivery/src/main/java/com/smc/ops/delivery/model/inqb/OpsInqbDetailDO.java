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
@TableName("ops_inqb_detail")
public class OpsInqbDetailDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;


    @TableField("bom_id")
    private String bomId;

    @TableField("expected_delivery_date")
    private Integer expectedDeliveryDate;

    /**
     * 问询申请号（OPS），主表关联
     */
    @TableField("inqb_apply_no")
    private String inqbApplyNo;

    /**
     * 子项号,顺序生成（在库采购排序）
     */
    @TableField("item_no")
    private Integer itemNo;

    /**
     * 拆分类型，0不拆分，1数量拆分，2型号拆分
     */
    @TableField("split_type")
    private String splitType;

    /**
     * 任务号（gps返回），不问询：null
     */
    @TableField("task_no")
    private String taskNo;

    /**
     * 供应商代码
     */
    @TableField("supplier_code")
    private String supplierCode;

    /**
     * 出库区分：在库/采购
     */
    @TableField("stock_code")
    private String stockCode;

    /**
     * 是否生成问询单，0：否，1：是
     */
    @TableField("handle_result")
    private String handleResult;

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
     * 使用数量,订单上单时使用，使用完毕后更新
     */
    @TableField("use_qty")
    private Integer useQty;

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
     * 有效状态，记录使用的状态
     */
    @TableField("status")
    private String status;

    /**
     * 有效截止日
     */
    @TableField("expiration_date")
    private Date expirationDate;

    /**
     * 回复单位(对应催促的供应商)
     */
    @TableField("reply_dept")
    private String replyDept;

    /**
     * 对应供应商的实际回复部门代码
     */
    @TableField("reply_supplier_dept")
    private String replySupplierDept;

    /**
     * 回复号
     */
    @TableField("reply_no")
    private String replyNo;

    /**
     * 回复货期天数
     */
    @TableField("reply_delivery_days")
    private Integer replyDeliveryDays;

    /**
     * 制造接收HL
     */
    @TableField("reply_accept_hl")
    private String replyAcceptHl;

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
     * 回复备注，按detail中回复最晚的
校验不通过时，写入失败原因
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

    @TableField("remark")
    private String remark;


}
