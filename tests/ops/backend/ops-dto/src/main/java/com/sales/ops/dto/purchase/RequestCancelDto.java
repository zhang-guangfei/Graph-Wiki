package com.sales.ops.dto.purchase;

public class RequestCancelDto {
    // 取消类型（0：请购 1：采购）
    private String canceltype;
    // 订单号
    private String orderno;
    // 订单项号
    private Integer itemno;
    // 订单拆分号
    private Integer splititemno;
    // 状态字段
    private String statecode;
    // 是否合并
    private Boolean mergeflag;
    // 请求来源
    // （0：自动删单，1：手动删单，2：订单还原）
    private String sourceType;

    // bug 10483 采购删单增加操作人字段
    private String operator;
    // bug 12344 新增采购删单原因
    private String reason;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getCanceltype() {
        return canceltype;
    }

    public void setCanceltype(String canceltype) {
        this.canceltype = canceltype;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public Integer getItemno() {
        return itemno;
    }

    public void setItemno(Integer itemno) {
        this.itemno = itemno;
    }

    public Integer getSplititemno() {
        return splititemno;
    }

    public void setSplititemno(Integer splititemno) {
        this.splititemno = splititemno;
    }

    public String getStatecode() {
        return statecode;
    }

    public void setStatecode(String statecode) {
        this.statecode = statecode;
    }

    public Boolean getMergeflag() {
        return mergeflag;
    }

    public void setMergeflag(Boolean mergeflag) {
        this.mergeflag = mergeflag;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }
}