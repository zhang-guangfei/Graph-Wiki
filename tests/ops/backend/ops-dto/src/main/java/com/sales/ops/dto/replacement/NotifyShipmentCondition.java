package com.sales.ops.dto.replacement;

import com.sales.ops.db.entity.OpsDo;

import java.io.Serializable;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2024/11/27 9:32
 */
public class NotifyShipmentCondition implements Serializable {
    private static final long serialVersionUID = 884561900019912483L;

    // 传入参数
    private String orderId;
    private String orderItem;
    private String reason; // 拆分原因
    private OpsDo paramDo;



    private Boolean containMoveFlag = false;// 包含在途
    private Boolean pcoFlag = false;//拆分型号标识
    private String doId;
    private String pcoId;
    private Integer pcoItem;


    public NotifyShipmentCondition(){}

    // 外部构造方法 用计划拆分
    public NotifyShipmentCondition(String orderId, String orderItem, String reason){
        this.orderId = orderId;
        this.orderItem = orderItem;
        this.reason = reason;
    }

    // 外部构造方法 用计划拆分
    public NotifyShipmentCondition(OpsDo paramDo, String reason){
        this.orderId = paramDo.getOrderId();
        this.orderItem = paramDo.getOrderItem();
        this.reason = reason;
        this.paramDo = paramDo;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(String orderItem) {
        this.orderItem = orderItem;
    }

    public Boolean getPcoFlag() {
        return pcoFlag;
    }

    public Boolean getContainMoveFlag() {
        return containMoveFlag;
    }

    public void setContainMoveFlag(Boolean containMoveFlag) {
        this.containMoveFlag = containMoveFlag;
    }

    public void setPcoFlag(Boolean pcoFlag) {
        this.pcoFlag = pcoFlag;
    }


    public String getDoId() {
        return doId;
    }

    public void setDoId(String doId) {
        this.doId = doId;
    }

    public String getPcoId() {
        return pcoId;
    }

    public void setPcoId(String pcoId) {
        this.pcoId = pcoId;
    }

    public Integer getPcoItem() {
        return pcoItem;
    }

    public void setPcoItem(Integer pcoItem) {
        this.pcoItem = pcoItem;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public OpsDo getParamDo() {
        return paramDo;
    }

    public void setParamDo(OpsDo paramDo) {
        this.paramDo = paramDo;
    }
}
