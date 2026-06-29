package com.sales.ops.dto.order;

import java.util.Date;

public class OpsSalesNoticeTaskDto {

    /**
     * 内部生成批次号,处理时记录在订单修改表
     */
    private String batchNo;

    /**
     * 业务编码
     */
    private String businessCode;

    /**
     * 申请号
     */
    private String applyNo;

    /**
     * 订单号
     */
    private String orderFno;

    /**
     * 接口参数json格式
     */
    private String parameter;

    /**
     * 接口返回结果json格式
     */
    private String returnResult;

    /**
     * 处理开始时间
     */
    private Date handleStartTime;

    /**
     * 处理结束时间
     */
    private Date handleEndTime;

    /**
     * 处理状态 0：未处理，1，已处理，9处理失败 4系统异常
     */
    private String handleStatus;

    /**
     * 返回状态 0：待返回，1：已返回，9失败
     */
    private String returnStatus;

    /**
     * 备注，异常信息等
     */
    private String errorMsg;

    private Date createTime;

    private String updateUser;

    private Long id;

    private Date updateTime;

    private String createUser;

    private String callBackParameter;

    private int tryCount;

    private String callBackReturnResult;

    private String source;

    private Integer errorHandCount;

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public String getApplyNo() {
        return applyNo;
    }

    public void setApplyNo(String applyNo) {
        this.applyNo = applyNo;
    }

    public String getOrderFno() {
        return orderFno;
    }

    public void setOrderFno(String orderFno) {
        this.orderFno = orderFno;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getReturnResult() {
        return returnResult;
    }

    public void setReturnResult(String returnResult) {
        this.returnResult = returnResult;
    }

    public Date getHandleStartTime() {
        return handleStartTime;
    }

    public void setHandleStartTime(Date handleStartTime) {
        this.handleStartTime = handleStartTime;
    }

    public Date getHandleEndTime() {
        return handleEndTime;
    }

    public void setHandleEndTime(Date handleEndTime) {
        this.handleEndTime = handleEndTime;
    }

    public String getHandleStatus() {
        return handleStatus;
    }

    public void setHandleStatus(String handleStatus) {
        this.handleStatus = handleStatus;
    }

    public String getReturnStatus() {
        return returnStatus;
    }

    public void setReturnStatus(String returnStatus) {
        this.returnStatus = returnStatus;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getCallBackParameter() {
        return callBackParameter;
    }

    public void setCallBackParameter(String callBackParameter) {
        this.callBackParameter = callBackParameter;
    }

    public int getTryCount() {
        return tryCount;
    }

    public void setTryCount(int tryCount) {
        this.tryCount = tryCount;
    }

    public String getCallBackReturnResult() {
        return callBackReturnResult;
    }

    public void setCallBackReturnResult(String callBackReturnResult) {
        this.callBackReturnResult = callBackReturnResult;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getErrorHandCount() {
        return errorHandCount;
    }

    public void setErrorHandCount(Integer errorHandCount) {
        this.errorHandCount = errorHandCount;
    }
}
