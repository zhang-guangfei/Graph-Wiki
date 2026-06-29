package com.smc.smccloud.model.dict;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("sales_sys_dictionary")
public class Dictionary {
    /**
     * 主键id
     */
    @TableId(value = "ID")
    private String id;

    /**
     * 名称
     */
    @TableField(value = "NAME")
    private String name;

    /**
     * 编码
     */
    @TableField(value = "CODE")
    private String code;

    /**
     * 状态
     */
    @TableField(value = "STATUS")
    private String status;

    /**
     * pid
     */
    @TableField(value = "PID")
    private String pid;

    /**
     * 创建时间
     */
    @TableField(value = "CREATETIME")
    private Date createTime;

    /**
     * 备注
     */
    @TableField(value = "DESCRIPTION")
    private String description;

    /**
     * sort
     */
    @TableField(value = "SORT")
    private Integer sort;

    @TableField(value = "EXTCODE")
    private String extcode;
}
