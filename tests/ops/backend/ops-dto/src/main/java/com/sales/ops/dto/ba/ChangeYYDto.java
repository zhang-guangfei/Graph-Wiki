package com.sales.ops.dto.ba;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.models.auth.In;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：下预约
 * @date ：Created in 2023/5/23 14:12
 */
public class ChangeYYDto  implements Serializable {

    private static final long serialVersionUID = 3601918678767619514L;
    private String deliveryOrderCode; //指令，DOID、PCOID	TRUE	varchar(50)
    private String modelNo;	         //SKU	TRUE	varchar(150)
    private String warehouseCode;	//仓库	TRUE	varchar (10)	　
    private String orderNo;	        //订单号（13位）	FALSE	varchar (100)	　
    private Integer qty;	            //数量	TRUE	int	指令取消数量
    private String greenCode;	     //环保标识	FALSE	char(1)	OPS响应带回请求值参数值
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date cancelTime;	     //订单取消时间	TRUE	datetime
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date modifyDate;	     //处理时间，getdate()	TRUE	datetime
    private boolean changeYYFlag = false;
    private String changeYYMsg;


    public String getDeliveryOrderCode() {
        return deliveryOrderCode;
    }

    public void setDeliveryOrderCode(String deliveryOrderCode) {
        this.deliveryOrderCode = deliveryOrderCode;
    }

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getGreenCode() {
        return greenCode;
    }

    public void setGreenCode(String greenCode) {
        this.greenCode = greenCode;
    }

    public Date getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(Date cancelTime) {
        this.cancelTime = cancelTime;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public boolean isChangeYYFlag() {
        return changeYYFlag;
    }

    public void setChangeYYFlag(boolean changeYYFlag) {
        this.changeYYFlag = changeYYFlag;
    }

    public String getChangeYYMsg() {
        return changeYYMsg;
    }

    public void setChangeYYMsg(String changeYYMsg) {
        this.changeYYMsg = changeYYMsg;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChangeYYDto that = (ChangeYYDto) o;
        return Objects.equals(deliveryOrderCode, that.deliveryOrderCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deliveryOrderCode);
    }

    @Override
    public String toString() {
        return  deliveryOrderCode+":"+ changeYYFlag;
    }
}
