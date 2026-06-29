package com.smc.smccloud.model.binorder;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("bin_order_calc")
public class BinOrderCalcDO implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("Calc_Time")
    private Date calcTime;

    @TableField("calc_psn")
    private String calcPsn;

    @TableField("confirm_psn")
    private String confirmPsn;

    @TableField("GSS")
    private Boolean gss;

    @TableField("Status")
    private Integer status;

    @TableField("create_User")
    private String createUser;
    @TableField("update_User")
    private  String updateUser;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    @TableField("calc_type")
    private Integer calcType;

    @TableField("warehouse_code")
    private String warehouseCode;

    @TableField("custoemr_no")
    private String custoemrNo;

    @TableField("property_id")
    private Long propertyId;

    @TableField("calc_finish_time")
    private Date calcFinishTime;
}
