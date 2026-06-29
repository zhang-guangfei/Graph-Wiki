package com.smc.smccloud.log.model;

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
 * 日志表
 * </p>
 *
 * @author smc
 * @since 2023-05-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ops_sys_log")
public class OpsSysLogDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 日志类型
     */
    @TableField("status")
    private String status;

    /**
     * 日志标题
     */
    @TableField("title")
    private String title;

    /**
     * 服务ID
     */
    @TableField("serviceid")
    private String serviceid;

    /**
     * 创建者
     */
    @TableField("createid")
    private String createid;

    /**
     * 创建时间
     */
    @TableField("createtime")
    private Date createtime;

    /**
     * 操作IP地址
     */
    @TableField("remoteaddr")
    private String remoteaddr;

    /**
     * 用户代理
     */
    @TableField("useragent")
    private String useragent;

    /**
     * 请求URI
     */
    @TableField("requesturi")
    private String requesturi;

    /**
     * 操作方式
     */
    @TableField("method")
    private String method;

    /**
     * 操作提交的数据
     */
    @TableField("params")
    private String params;

    /**
     * 执行时间
     */
    @TableField("time")
    private Long time;

    private transient Object[] obj;


}
