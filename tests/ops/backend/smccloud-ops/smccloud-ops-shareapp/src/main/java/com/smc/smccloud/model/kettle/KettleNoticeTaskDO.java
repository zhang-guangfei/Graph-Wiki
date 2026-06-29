package com.smc.smccloud.model.kettle;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Author lyc
 * @Date 2023/4/21 15:21
 * @Descripton TODO
 */
@Data
@TableName("kettle_notice_task")
public class KettleNoticeTaskDO {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("mdm_table_name")
    private String mdmTableName;

    @TableField("job_name")
    private String jobName;

    @TableField("error_msg")
    private String errorMsg;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_time")
    private Date updateTime;

    @TableField("opt_start_time")
    private Date optStartTime;

    @TableField("opt_end_time")
    private Date optEndTime;

    @TableField("opt_status")
    private String optStatus; // 处理状态  0未处理1已处理

    @TableField("mdm_batch_no")
    private String mdmBatchNo; // 批次号yyyyMMddhhMMss

    @TableField("last_update_time")
    private Date lastUpdateTime;  // mdm最后更新时间

}
