package com.smc.smccloud.model.prestock;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author smc
 * @since 2024-08-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("preorder_account_apply_detail")
public class PreorderAccountApplyDetailDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 订单号
     */
    @TableField("order_no")
    private String orderNo;

    /**
     * 入库日期
     */
    @TableField("ro_date")
    private Date roDate;

    /**
     * 入库数
     */
    @TableField("ro_qty")
    private Integer roQty;

    /**
     * 可用在库
     */
    @TableField("ava_qty")
    private Integer avaQty;

    /**
     * 需求日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format="yyyy-MM-dd")
    @TableField("requirement_date")
    private Date requirementDate;

    /**
     * 决算日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("count_date")
    private Date countDate;

    /**
     * 决算状态
     */
    @TableField("status")
    private int status;

    /**
     * 推送决算数
     */
    @TableField("push_qty")
    private Integer pushQty;

    /**
     * 确认延期日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @TableField("delay_date")
    private Date delayDate;

    /**
     * 确认决算数，门户传值
     */
    @TableField("sure_account_qty")
    private Integer sureAccountQty;

    /**
     * 确认延期数，门户传值
     */
    @TableField("delay_qty")
    private Integer delayQty;

    /**
     * 调库号
     */
    @TableField("trans_no")
    private String transNo;

    /**
     * 调库时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("trans_time")
    private Date transTime;

    /**
     * 清算数，实际处理的调库数量
     */
    @TableField("trans_qty")
    private Integer transQty;

    /**
     * 显示该条订单申请时的LOT E价格
     */
    @TableField("eprice")
    private BigDecimal ePrice;

    /**
     * E价格*决算数
     */
    @TableField("eamount")
    private BigDecimal eAmount;

    /**
     * BIN(ALL存在1个BIN设置，就显示“是”)
     */
    @TableField("is_bin")
    private Boolean isBin;

    /**
     * 担当
     */
    @TableField("charger")
    private String charger;

    /**
     * 库存日志id
     */
    @TableField("inventoty_log_id")
    private String inventotyLogId;

    /**
     * 库存id项号
     */
    @TableField("inventory_id_item")
    private Integer inventoryIdItem;

    /**
     * yyyyMMdd格式批次号
     */
    @TableField("batch_no")
    private String batchNo;

    /**
     * 最大延期日
     */
    @TableField("max_delay_date")
    private Date maxDelayDate;

    /**
     * 申请号
     */
    @TableField("apply_no")
    private String applyNo;

    /**
     * 申请项号
     */
    @TableField("apply_item_no")
    private String applyItemNo;

    /**
     * 决算说明
     */
    @TableField("account_desc")
    private String accountDesc;

    /**
     * 延期说明
     */
    @TableField("delay_desc")
    private String delayDesc;

    /**
     * 实际延期数量
     */
    @TableField("fact_delay_qty")
    private int factDelayQty;

    /**
     * 决算申请号
     */
    @TableField("account_apply_no")
    private String accountApplyNo;

    @TableField("modifier")
    private String modifier;

    @TableField("inventory_id")
    private Long inventoryId;

    @TableField("create_time")
    private Date createTime;

    @TableField("model_no")
    private String modelNo;

    @TableField("modify_time")
    private Date modifyTime;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("creator")
    private String creator;

    @TableField("delflag")
    private int delFlag;

    @TableField("preflag")
    private int preFlag;

    @TableField("dept_no")
    private String deptNo;

    @TableField("approve_qty")
    private int approveQty;


}
