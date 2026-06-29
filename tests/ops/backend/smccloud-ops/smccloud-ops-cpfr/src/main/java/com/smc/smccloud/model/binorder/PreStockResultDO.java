package com.smc.smccloud.model.binorder;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * @author B90034
 * @since 2021-11-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("prestock_result")
public class PreStockResultDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("apply_id")
    private Long applyId;

    @TableField("model_no")
    private String modelNo;

    /**
     * 1-采购(生成采购订单);
     * 2-BIN采购(生成采购订单);
     * 3-预约在途(预约到在途订单);
     * 4-调库;
     * 5-异仓调拨;
     * 9-不备库;
     */
    @TableField("process_type")
    private String processType;

    @TableField("order_no")
    private String orderNo;

    @TableField("order_qty")
    private Integer orderQty;

    @TableField("dlv_date_jp")
    private Date dlvDateJp;

    /**
     * 订向库房,采购为供应商代码,调拨/调库为仓库代码
     */
    @TableField("order_stock")
    private String orderStock;

    /**
     * prestock_detail.id
     */
    @TableField("from_id")
    private Long fromId;

    /**
     * prostock_detaild对应的申请型号
     */
    @TableField("apply_model_no")
    private String applyModelNo;

    @TableField(value = "create_Time", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "update_Time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableField("create_user")
    private String createUser;

    @TableField("update_user")
    private String updateUser;

    /**
     * 处理状态: 1-待处理; 2-已处理; 4-取消处理;
     */
    @TableField("opt_status")
    private String optStatus;

    @TableField("remark")
    private String remark;

    /**
     * 项号
     */
    @TableField("item_no")
    private Integer itemNo;

    /**
     * 运输方式 采购时默认为 0-海运
     */
    @TableField("trans_type")
    private String transType;

    /**
     * 预占订单
     */
    @TableField("prepare_order")
    private String prepareOrder;
}
