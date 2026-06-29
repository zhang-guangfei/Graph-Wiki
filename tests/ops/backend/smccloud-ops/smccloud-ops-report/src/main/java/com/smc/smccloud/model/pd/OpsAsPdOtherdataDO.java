package com.smc.smccloud.model.pd;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.xpath.operations.Bool;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author smc
 * @since 2023-06-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ops_as_pd_otherData")
public class OpsAsPdOtherdataDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 盘点批次号
     */
    @TableField("pd_batch_no")
    private String pdBatchNo;

    /**
     * 导入数据类型
     */
    @TableField("imp_data_type")
    private String impDataType;

    /**
     * 作业类型
     */
    @TableField("job_type")
    private String jobType;

    /**
     * 自动执行时间
     */
    @TableField("job_exec_time")
    private Date jobExecTime;

    @TableField("create_time")
    private Date createTime;

    @TableField("create_user")
    private String createUser;

    @TableField("del_flag")
    private Boolean delFlag;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(exist = false)
    private Date createTimeUI;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(exist = false)
    private Date jobExecTimeUI;

    @TableField("update_time")
    private Date updateTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(exist = false)
    private Date updateTimeUI;

    @TableField("update_user")
    private String updateUser;

    @TableField("exec_flag")
    private String execFlag;

    @TableField(exist = false)
    private String execFlagName;

    @TableField("method_code")
    private String methodCode;
}
