package com.smc.smccloud.model.kettle;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Author lyc
 * @Date 2023/4/21 14:58
 * @Descripton TODO
 */
@Data
@TableName("kettle_job_manage")
public class KettleJobManageDO {

     @TableId(value = "id", type = IdType.AUTO)
     private Long id;

     @TableField("job_name")
     private String jobName;

    @TableField("status")
    private String status;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_time")
    private Date updateTime;

    @TableField("opt_finish_time")
    private Date optFinishTime;

    @TableField("mdm_table_name")
    private String mdmTableName;

    @TableField("opt_hand_time")
    private Date optHandTime;

}
