package com.smc.smccloud.model.csstock;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * TODO
 *
 * @author wsf
 * @version 1.0
 * @date 2022/1/10 11:03
 */
@Data
@TableName("cs_impdata")
public class CsImpdataDO {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    @TableField("agent_no")
    private String agentNo;

    @TableField("warehouse_code")
    private String warehouseCode;

    @TableField("order_no")
    private String orderNo;

    @TableField("status")
    private Integer status;

    @TableField("imp_date")
    private Date impDate;

    @TableField("ship_date")
    private Date shipDate;

    @TableField("model_no")
    private String modelNo;

    /**
     * 入库数量
     */
    @TableField("quantity")
    private Integer quantity;

    /**
     * 退货数量
     */
    @TableField("return_qty")
    private Integer returnQty;

    /**
     * 可出库数量
     */
    @TableField("exp_qty")
    private Integer expQty;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_time")
    private Date updateTime;

    @TableField("update_user")
    private String updateUser;

    @TableField("create_user")
    private String createUser;

    @TableField("barcode")
    private String barcode;

    @TableField("case_no")
    private String caseNo;

    @TableField("delivery_no")
    private String deliveryNo;

    @TableField("ppl_no")
    private String pplNo;

    @TableField("project_no")
    private String projectNo;

    @TableField("receive_psn")
    private String receivePsn;

    @TableField("receive_time")
    private Date receiveTime;

    @TableField("imp_type")
    private Integer impType;

    @TableField("user_no")
    private String userNo;

    @TableField("location_No")
    private String locationNo;

    @TableField("invoice_no")
    private String invoiceNo;

    @TableField("applyType")
    private Integer applyType;

    @TableField("balance_date")
    private Date balanceDate;

    @TableField("balance_costDate")
    private Date balanceCostDate;

    /**
     * 剩余数量
     */
    @TableField("left_qty")
    private Integer leftQty;

    @TableField("ro_id")
    private String roId;

    @TableField("sign_time")
    private Date signTime;

    @TableField("sign_psn")
    private String signPsn;

    @TableField("cost_state")
    private Integer costState;
}
