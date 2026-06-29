package com.sales.ops.dto.inventory;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sales.ops.dto.util.UserDto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：WMS 委托出库发货确认参数
 * @date ：Created in 2021/10/19 16:36
 */
public class WmWTDoConfirmDto implements Serializable {

    private static final long serialVersionUID = -3506450358190538087L;

    private String deliveryOrderCode; //物流指令号 必填

    private String warehouseCode; //仓库ID 必填

    private String invoice; //发票号 必填

    private String volume; //长宽高

    private String weight; //重量

    private String sender; //发货担当 必填

    private String boxType;//箱型

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date hangDate;

    private String msgId; //消息ID，幂等性校验用

    private UserDto userDto; //必填

    private WmWTDoItemConfirmDto items; //必填

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

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public WmWTDoItemConfirmDto getItems() {
        return items;
    }

    public void setItems(WmWTDoItemConfirmDto items) {
        this.items = items;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public Date getHangDate() {
        return hangDate;
    }

    public void setHangDate(Date hangDate) {
        this.hangDate = hangDate;
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
}
