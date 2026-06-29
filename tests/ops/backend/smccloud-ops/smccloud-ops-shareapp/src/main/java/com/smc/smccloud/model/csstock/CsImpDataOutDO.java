package com.smc.smccloud.model.csstock;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author edp04
 * @title: CsImpDataOutDO
 * @date 2022/07/15 16:13
 */
@Data
@TableName("cs_impdata_out")
public class CsImpDataOutDO {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("agent_no")
    private String agentNo;

    @TableField("warehouse_code")
    private String warehouseCode;

    @TableField("order_no")
    private String orderNo;

    @TableField("exp_order_no")
    private String expOrderNo;

    @TableField("model_no")
    private String modelNo;

    @TableField("out_qty")
    private Integer outQty;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_time")
    private Date updateTime;

    @TableField("update_user")
    private String updateUser;

    @TableField("create_user")
    private String createUser;

}
