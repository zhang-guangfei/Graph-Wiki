package com.smc.smccloud.model.notice;

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
 * @since 2023-07-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ops_sales_notice_task")
public class OpsSalesNoticeTaskDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 内部生成批次号,处理时记录在订单修改表
     */
    @TableField("batch_no")
    private String batchNo;

    /**
     * 业务编码
     */
    @TableField("business_code")
    private String businessCode;

    /**
     * 申请号
     */
    @TableField("apply_no")
    private String applyNo;

    /**
     * 订单号
     */
    @TableField("order_fno")
    private String orderFno;

    /**
     * 接口参数json格式
     */
    @TableField("parameter")
    private String parameter;

    /**
     * 接口返回结果json格式
     */
    @TableField("return_result")
    private String returnResult;

    /**
     * 处理开始时间
     */
    @TableField("handle_start_time")
    private Date handleStartTime;

    /**
     * 处理结束时间
     */
    @TableField("handle_end_time")
    private Date handleEndTime;

    /**
     * 处理状态 0：未处理，1，已处理，9处理失败 4系统异常
     */
    @TableField("handle_status")
    private String handleStatus;

    /**
     * 返回状态 0：待返回，1：已返回，9失败
     */
    @TableField("return_status")
    private String returnStatus;

    /**
     * 备注，异常信息等
     */
    @TableField("error_msg")
    private String errorMsg;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_user")
    private String updateUser;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("update_time")
    private Date updateTime;

    @TableField("create_user")
    private String createUser;

    @TableField("call_back_parameter")
    private String callBackParameter;

    @TableField("try_count")
    private int tryCount;

    @TableField("call_back_return_result")
    private String callBackReturnResult;

    @TableField("source")
    private String source;

    @TableField("error_hand_count")
    private Integer errorHandCount;

}
