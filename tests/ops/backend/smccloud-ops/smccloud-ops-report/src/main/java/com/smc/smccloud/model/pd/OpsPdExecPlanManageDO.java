package com.smc.smccloud.model.pd;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author smc
 * @since 2024-11-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ops_pd_exec_plan_manage")
public class OpsPdExecPlanManageDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 执行时间
     */
    @TableField("exec_date")
    private String execDate;

    @TableField("create_user")
    private String createUser;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_user")
    private String updateUser;

    @TableField("update_time")
    private Date updateTime;

    /**
     * 1已执行0未执行
     */
    @TableField("exec_flag")
    private Integer execFlag;

    @TableField(exist = false)
    private String execFlagName;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;


}
