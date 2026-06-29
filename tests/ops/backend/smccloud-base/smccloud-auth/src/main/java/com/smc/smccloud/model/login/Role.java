package com.smc.smccloud.model.login;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.smc.smccloud.core.model.constants.Constants;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName(value = "sales_sys_role")
public class Role implements Serializable
{
    @TableField(value = "ID")
    private String id;
    /**
     * 名称
     */
    @TableField(value = "NAME")
    private String name;

    /**
     * 描述
     */
    @TableField(value = "DESCRIPTION")
    private String  description;

    /**
     * status:状态
     */
    @TableField(value = "STATUS")
    private String status = Constants.VALID;

    @TableField(value = "CREATETIME")
    private Date createTime;

}
