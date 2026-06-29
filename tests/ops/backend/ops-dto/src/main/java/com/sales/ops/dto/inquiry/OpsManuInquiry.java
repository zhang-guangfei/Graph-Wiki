package com.sales.ops.dto.inquiry;

import java.util.Date;

/**
 * @description: 催促对接中国制造的实体
 * @author: B91717
 * @time: 2023/12/25 12:02
 */
public class OpsManuInquiry {
    // 催促申请号，即task号
    private String queryNo;
    // 催促类型（inqA,inqB）
    private String queryType;
    // 完整订单号
    private String orderNumber;
    // 型号
    private String model;
    // 数量
    private int  quantity;
    // 期望出库日
    private Date queryDlvyDate;
    // 催促人员工代码
    private String queryPsnid;
    // 催促申请时间
    private Date queryDateTime;
    // 催促分类码
    private int queryQuestionId;
    // 催促描述
    private String queryDetail;
    // 催促备注
    private String questionRemark;
    // 客户代码
    private String customerNo;
    // 申请部门代码
    private String queryDeptNo;
    //货期提前原因
    private String reasonCode;
    // 初始状态
    private String stateCode;

    // 回复货期
    private Date answerDlvyDate;
    // 回复人
    private String answerOperator;
    // 回复日期
    private Date answerDate;
    // 回复备注
    private String answerRemark;
    // 回复部门
    private String answerDept;

    private String delayReason;

    private String requestNo;

    public String getRequestNo() {
        return requestNo;
    }

    public void setRequestNo(String requestNo) {
        this.requestNo = requestNo;
    }

    public String getAnswerOperator() {
        return answerOperator;
    }

    public void setAnswerOperator(String answerOperator) {
        this.answerOperator = answerOperator;
    }

    public Date getAnswerDate() {
        return answerDate;
    }

    public void setAnswerDate(Date answerDate) {
        this.answerDate = answerDate;
    }

    public String getAnswerRemark() {
        return answerRemark;
    }

    public void setAnswerRemark(String answerRemark) {
        this.answerRemark = answerRemark;
    }

    public String getAnswerDept() {
        return answerDept;
    }

    public void setAnswerDept(String answerDept) {
        this.answerDept = answerDept;
    }

    public String getDelayReason() {
        return delayReason;
    }

    public void setDelayReason(String delayReason) {
        this.delayReason = delayReason;
    }

    public String getQueryNo() {
        return queryNo;
    }

    public void setQueryNo(String queryNo) {
        this.queryNo = queryNo;
    }

    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getQueryDlvyDate() {
        return queryDlvyDate;
    }

    public void setQueryDlvyDate(Date queryDlvyDate) {
        this.queryDlvyDate = queryDlvyDate;
    }

    public String getQueryPsnid() {
        return queryPsnid;
    }

    public void setQueryPsnid(String queryPsnid) {
        this.queryPsnid = queryPsnid;
    }

    public Date getQueryDateTime() {
        return queryDateTime;
    }

    public void setQueryDateTime(Date queryDateTime) {
        this.queryDateTime = queryDateTime;
    }

    public int getQueryQuestionId() {
        return queryQuestionId;
    }

    public void setQueryQuestionId(int queryQuestionId) {
        this.queryQuestionId = queryQuestionId;
    }

    public String getQueryDetail() {
        return queryDetail;
    }

    public void setQueryDetail(String queryDetail) {
        this.queryDetail = queryDetail;
    }

    public String getQuestionRemark() {
        return questionRemark;
    }

    public void setQuestionRemark(String questionRemark) {
        this.questionRemark = questionRemark;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getQueryDeptNo() {
        return queryDeptNo;
    }

    public void setQueryDeptNo(String queryDeptNo) {
        this.queryDeptNo = queryDeptNo;
    }

    public String getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public Date getAnswerDlvyDate() {
        return answerDlvyDate;
    }

    public void setAnswerDlvyDate(Date answerDlvyDate) {
        this.answerDlvyDate = answerDlvyDate;
    }
}
