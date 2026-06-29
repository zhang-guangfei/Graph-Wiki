package com.smc.smccloud.model.pd;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

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
@TableName("ops_pd_step_manage")
public class OpsPdStepManageDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 步骤编码
     */
    @TableField("step_code")
    private String stepCode;

    /**
     * 步骤编码名称
     */
    @TableField("step_name")
    private String stepName;

    /**
     * 执行顺序
     */
    @TableField("exec_seq")
    private Integer execSeq;

    /**
     * 执行时间
     */
    @TableField("exec_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date execTime;

    /**
     * 状态
     */
    @TableField("status")
    private Integer status;

    @TableField(exist = false)
    private String statusName;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @TableField("create_user")
    private String createUser;

    @TableField("update_user")
    private String updateUser;

    @TableField("remark")
    private String remark;

    @TableField("del_flag")
    private Integer delFlag;

}
