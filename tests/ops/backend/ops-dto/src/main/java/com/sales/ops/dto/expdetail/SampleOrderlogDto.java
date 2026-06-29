package com.sales.ops.dto.expdetail;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class SampleOrderlogDto {

    private Long id;

    /**
     * 订单号
     */
    @NotNull
    private String orderNo;

    /**
     * 操作类型
     */
    @NotNull
    private Integer optType;

    /**
     * 操作说明
     */
    private String description;

    /**
     * 操作时间
     */
    private Date optTime;

    /**
     * 操作人姓名
     */
    private String optUserName;

    /**
     * 操作人id
     */
    private String optUserId;

    /**
     * 操作者IP
     */
    private String ip;

    /**
     * 写入时间
     */
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getOptType() {
        return optType;
    }

    public void setOptType(Integer optType) {
        this.optType = optType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getOptTime() {
        return optTime;
    }

    public void setOptTime(Date optTime) {
        this.optTime = optTime;
    }

    public String getOptUserName() {
        return optUserName;
    }

    public void setOptUserName(String optUserName) {
        this.optUserName = optUserName;
    }

    public String getOptUserId() {
        return optUserId;
    }

    public void setOptUserId(String optUserId) {
        this.optUserId = optUserId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
