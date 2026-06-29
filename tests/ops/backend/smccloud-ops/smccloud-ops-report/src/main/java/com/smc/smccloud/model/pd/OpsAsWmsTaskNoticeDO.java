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
 * @since 2023-06-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ops_as_wms_task_notice")
public class OpsAsWmsTaskNoticeDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 任务名称
     */
    @TableField("job_name")
    private String jobName;

    /**
     * 0未执行 1需要执行 2wms执行完毕 3ops数据抽取中	4 ops抽取完毕
     */
    @TableField("job_status")
    private String jobStatus;

    /**
     * 任务开始执行时间
     */
    @TableField("job_start_time")
    private Date jobStartTime;

    /**
     * 任务执行结束时间
     */
    @TableField("job_end_time")
    private Date jobEndTime;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private Date updateTime;

    @TableField("create_time")
    private Date createTime;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("pd_batch_no")
    private String pdBatchNo;


}
