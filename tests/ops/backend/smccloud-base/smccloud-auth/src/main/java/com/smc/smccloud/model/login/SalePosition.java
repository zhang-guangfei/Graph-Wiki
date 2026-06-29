package com.smc.smccloud.model.login;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("hr_position")
public class SalePosition implements Serializable
{

    private static final long serialVersionUID = 6398441844269237580L;
    /**
     * id
     */
    @TableField(value = "Id")
    private String id;
    /**
     * 父Id
     */
    @TableField(value = "ParentId")
    private String pid;
    /**
     * 职务名称
     */
    @TableField(value = "Name")
    private String name;
}
