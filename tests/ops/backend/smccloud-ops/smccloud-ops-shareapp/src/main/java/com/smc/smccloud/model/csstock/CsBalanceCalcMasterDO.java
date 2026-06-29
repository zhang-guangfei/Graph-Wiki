package com.smc.smccloud.model.csstock;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * @author edp04
 * @title: CsBalanceCalcMasterDO
 * @date 2022/06/14 09:50
 */
@Data
@TableName("cs_balance_master")
public class CsBalanceCalcMasterDO {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(value = "monthDate")
    private Date monthDate;

    @TableField(value = "fromTime")
    private Date fromTime;

    @TableField(value = "toTime")
    private Date toTime;

    @TableField(value = "createTime", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "updateTime", fill = FieldFill.UPDATE)
    private Date updateTime;

    @TableField(value = "status")
    private Integer status;
}
