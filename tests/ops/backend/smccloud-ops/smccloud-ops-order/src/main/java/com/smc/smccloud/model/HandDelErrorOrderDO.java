package com.smc.smccloud.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author lyc
 * @Date 2022/9/8 14:00
 * @Descripton TODO
 */

@TableName("hand_del_error_order")
@Data
public class HandDelErrorOrderDO {

    @TableField("order_no")
    private String orderNo;

    @TableField("hand_status")
    private String handStatus;

}
