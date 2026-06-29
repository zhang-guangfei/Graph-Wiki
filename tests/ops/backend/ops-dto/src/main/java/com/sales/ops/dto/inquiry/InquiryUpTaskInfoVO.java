package com.sales.ops.dto.inquiry;

/**
 * @author B91717
 * 催促模块使用，回调task的更新实体
 */
public class InquiryUpTaskInfoVO {
    private Long id;
    // 批次号 非空
    private String batchNo;
    // 处理状态 非空(OpsSalesTaskReturnStatus枚举里有状态说明)
    private String handleStatus;
    // 操作人 非空
    private String optUserNo;
    // 回调接口参数 (JSON格式)
    private String callBackParameter;
    // 接口返回结果 (JSON格式)
    private String returnResult;

    private String errorMsg;

    private String returnStatus;

    private int tryCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getHandleStatus() {
        return handleStatus;
    }

    public void setHandleStatus(String handleStatus) {
        this.handleStatus = handleStatus;
    }

    public String getOptUserNo() {
        return optUserNo;
    }

    public void setOptUserNo(String optUserNo) {
        this.optUserNo = optUserNo;
    }

    public String getCallBackParameter() {
        return callBackParameter;
    }

    public void setCallBackParameter(String callBackParameter) {
        this.callBackParameter = callBackParameter;
    }

    public String getReturnResult() {
        return returnResult;
    }

    public void setReturnResult(String returnResult) {
        this.returnResult = returnResult;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getReturnStatus() {
        return returnStatus;
    }

    public void setReturnStatus(String returnStatus) {
        this.returnStatus = returnStatus;
    }

    public int getTryCount() {
        return tryCount;
    }

    public void setTryCount(int tryCount) {
        this.tryCount = tryCount;
    }
}
