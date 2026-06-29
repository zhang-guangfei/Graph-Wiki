package com.sales.ops.dto.replacement;

import java.io.Serializable;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2024/11/28 15:55
 */
public class NotifyShipmentVerifyItemDto implements Serializable {
    private static final long serialVersionUID = -1366651994560061996L;

    private String modelNo;// 子型号

    private Integer orderAssChildQty;// 子型号数量

    private Integer planQty;// 已计划数量  如果已计划数量 == 子型号数量，不可计划

    private Integer readyQty = 0;//就绪数量

    public NotifyShipmentVerifyItemDto(){}

    public NotifyShipmentVerifyItemDto(String modelNo, Integer orderAssChildQty){
        this.modelNo = modelNo;
        this.orderAssChildQty = orderAssChildQty;
    }

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo;
    }

    public Integer getOrderAssChildQty() {
        return orderAssChildQty;
    }

    public void setOrderAssChildQty(Integer orderAssChildQty) {
        this.orderAssChildQty = orderAssChildQty;
    }

    public Integer getPlanQty() {
        return planQty;
    }

    public void setPlanQty(Integer planQty) {
        this.planQty = planQty;
    }

    public Integer getReadyQty() {
        return readyQty;
    }

    public void setReadyQty(Integer readyQty) {
        this.readyQty = readyQty;
    }
}
