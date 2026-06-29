package com.smc.smccloud.model.binorder;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
/**
 * @author wuweidong
 * @create 2023/6/2 09:54
 * @description
 */

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ops_bin_trial_job_manage")
public class BinTrialJobManageDO
{
    private static final long serialVersionUID = 1L;
    /**ID*/
    @TableId(value = "id", type = IdType.AUTO)
    public Long id ;
    @TableField("job_no")
    public String jobNo ;

    @TableField("job_name")
    public String jobName ;

    @TableField("job_description")
    public String jobDescription ;

    @TableField("warehouse_code")
    public String warehouseCode ;

    @TableField("warehouse_master")
    public String warehouseMaster ;

    @TableField("status")
    public int status ;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @TableField("plan_execute_date")
    public Date planExecuteDate ;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("execute_start_time")
    public Date executeStartTime ;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("execute_end_time")
    public Date executeEndTime ;

    @TableField("is_deleted")
    public int isDeleted ;

    @TableField("result_table_name")
    public String resultTableName ;

    @TableField("create_user")
    public String createUser ;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("create_time")
    public Date createTime ;

    @TableField("update_user")
    public String updateUser ;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("update_time")
    public Date updateTime ;

}
