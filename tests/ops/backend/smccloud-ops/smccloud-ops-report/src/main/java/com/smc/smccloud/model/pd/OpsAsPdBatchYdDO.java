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
@TableName("ops_as_pd_batch_yd")
public class OpsAsPdBatchYdDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "pd_batch_no")
    private String pdBatchNo;

    @TableField("pd_start_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date pdStartTime;

    @TableField("pd_end_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date pdEndTime;

    /**
     * 0未执行1已执行
     */
    @TableField("status")
    private Integer status;
    @TableField(exist = false)
    private String statusName;

    @TableField("create_time")
    private Date createTime;

    @TableField("create_user")
    private String createUser;

    @TableField("update_time")
    private Date updateTime;

    @TableField("update_user")
    private String updateUser;


}
