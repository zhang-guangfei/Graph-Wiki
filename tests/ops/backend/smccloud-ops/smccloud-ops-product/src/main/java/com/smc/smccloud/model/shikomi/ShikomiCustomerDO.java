package com.smc.smccloud.model.shikomi;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

@Data
@TableName("shikomi_customer")
public class ShikomiCustomerDO {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * shikomi号
     */
    @TableField(value = "ShikomiNo")
    private String shikomiNo;

    @TableField(value = "CustomerNo")
    private String customerNo;

    @TableField(value = "CreateTime", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "CreateUser")
    private String createUser;

    @TableField(value = "UpdateTime", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableField(value = "UpdateUser")
    private String updateUser;

}
