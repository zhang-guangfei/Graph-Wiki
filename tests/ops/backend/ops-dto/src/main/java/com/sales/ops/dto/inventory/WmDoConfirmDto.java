package com.sales.ops.dto.inventory;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sales.ops.dto.util.UserDto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：WMS 出库发货确认参数
 * @date ：Created in 2021/10/19 16:36
 */
public class WmDoConfirmDto implements Serializable {

    private static final long serialVersionUID = -3506450358190538087L;

    private String deliveryOrderCode; //物流指令号

    private String expressCodeChild;//子单号

    private String warehouseCode; //仓库ID

    private String wmsOrderCode; //WMS单号

    private String volume; //长宽高

    private String weight; //重量

    private String sender; //发货担当
    private String boxType;//箱型

    private String msgId; //消息ID，幂等性校验用

    private UserDto userDto;

    private WmDoItemConfirmDto items;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date shipTime;//物流发货日期

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    public String getDeliveryOrderCode() {
        return deliveryOrderCode;
    }

    public void setDeliveryOrderCode(String deliveryOrderCode) {
        this.deliveryOrderCode = deliveryOrderCode;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getWmsOrderCode() {
        return wmsOrderCode;
    }

    public void setWmsOrderCode(String wmsOrderCode) {
        this.wmsOrderCode = wmsOrderCode;
    }

    public WmDoItemConfirmDto getItems() {
        return items;
    }

    public void setItems(WmDoItemConfirmDto items) {
        this.items = items;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getBoxType() {
        return boxType;
    }

    public void setBoxType(String boxType) {
        this.boxType = boxType;
    }

    public Date getShipTime() {
        return shipTime;
    }

    public void setShipTime(Date shipTime) {
        this.shipTime = shipTime;
    }

    public String getExpressCodeChild() {
        return expressCodeChild;
    }

    public void setExpressCodeChild(String expressCodeChild) {
        this.expressCodeChild = expressCodeChild;
    }

    @Override
    public String toString() {
        if(Objects.nonNull(items)){
            return " WmDoConfirmDto{" +
                    "deliveryOrderCode='" + deliveryOrderCode + '\'' +
                    "expressCodeChild='" + expressCodeChild + '\'' +
                    ", warehouseCode='" + warehouseCode + '\'' +
                    ", wmsOrderCode='" + wmsOrderCode + '\'' +
                    "logisticsCode='" + items.getLogisticsCode() + '\'' +
                    ", expressCode='" + items.getExpressCode() + '\'' +
                    ", qty=" + items.getQty() +
                    '}';
        }else {
            return " WmDoConfirmDto{" +
                    "deliveryOrderCode='" + deliveryOrderCode + '\'' +
                    "expressCodeChild='" + expressCodeChild + '\'' +
                    ", warehouseCode='" + warehouseCode + '\'' +
                    ", wmsOrderCode='" + wmsOrderCode + '\'' +
                    "logisticsCode='" +
                    ", expressCode='"+
                    ", qty=" +
                    '}';
        }

    }
}
