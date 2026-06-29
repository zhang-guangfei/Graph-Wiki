package com.sales.ops.dto.prepareOrder;

import java.util.Date;

public class OpsPrepareOrderLiquidationDto {

    private Long id;

    private String orderFno;

    private String modelNo;

    private Integer quantity;

    private Boolean delFlag;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;

    private String handleWay;

    private String handleWayName;

    public String getHandleWayName() {
        return handleWayName;
    }

    public void setHandleWayName(String handleWayName) {
        this.handleWayName = handleWayName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderFno() {
        return orderFno;
    }

    public void setOrderFno(String orderFno) {
        this.orderFno = orderFno;
    }

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Boolean getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Boolean delFlag) {
        this.delFlag = delFlag;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getHandleWay() {
        return handleWay;
    }

    public void setHandleWay(String handleWay) {
        this.handleWay = handleWay;
    }
}
