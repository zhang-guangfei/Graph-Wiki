package com.sales.ops.serviceimpl.model;

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
public class OpsSysLogDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 日志类型
     */
    private String status;

    /**
     * 日志标题
     */
    private String title;

    /**
     * 服务ID
     */
    private String serviceid;

    /**
     * 创建者
     */
    private String createid;

    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * 操作IP地址
     */
    private String remoteaddr;

    /**
     * 用户代理
     */
    private String useragent;

    /**
     * 请求URI
     */
    private String requesturi;

    /**
     * 操作方式
     */
    private String method;

    /**
     * 操作提交的数据
     */
    private String params;

    /**
     * 执行时间
     */
    private Long time;

    private transient Object[] obj;


}
