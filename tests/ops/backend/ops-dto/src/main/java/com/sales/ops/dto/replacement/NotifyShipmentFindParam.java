package com.sales.ops.dto.replacement;

import java.io.Serializable;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2024/12/20 10:03
 */
public class NotifyShipmentFindParam implements Serializable {

    private String planNo;
    private String orderFno;

    private Integer status;// 0 待分配 1：分配中 2：分配完成

    private String orderModelNo;

    private String modelNo;

    public String getPlanNo() {
        return planNo;
    }

    public void setPlanNo(String planNo) {
        this.planNo = planNo;
    }

    public String getOrderFno() {
        return orderFno;
    }

    public void setOrderFno(String orderFno) {
        this.orderFno = orderFno;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getOrderModelNo() {
        return orderModelNo;
    }

    public void setOrderModelNo(String orderModelNo) {
        this.orderModelNo = orderModelNo;
    }

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo;
    }
}
