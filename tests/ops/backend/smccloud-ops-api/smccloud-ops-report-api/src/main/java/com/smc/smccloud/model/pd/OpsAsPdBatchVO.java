package com.smc.smccloud.model.pd;

import com.alibaba.fastjson.annotation.JSONField;
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
 * @since 2023-06-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OpsAsPdBatchVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 盘点批次号
     */
    private String pdBatchNo;

    /**
     * 0 盘点数据准备，
     * 1 盘点开始（点击生成盘点票一旦被点击则盘点状态为【盘点开始】），
     * 2 实盘数据录入（盘点票录入一旦被点击则盘点状态为【实盘数据录入】），
     * 3 实盘数据确认（盘点差异导出按钮一旦被点击则盘点状态为【实盘数据确认】），
     * 4 盘点数据已确认（盘点调整按钮一旦被点击则盘点状态为【盘点数据已确认】）
     */
    private String pdState;

    /**
     * 盘点开始时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date pdStartTime;

    /**
     * 点击盘点调整按钮时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date pdDataEndTime;

    /**
     * 0已过期，1激活，2作废
     */
    private String isActive;

    private String createUser;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private String updateUser;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private int pdWmsCount;

}
