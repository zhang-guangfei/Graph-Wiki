package com.sales.ops.dto.inventory;

import com.sales.ops.dto.util.UserDto;

import java.io.Serializable;
import java.util.List;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：WMS 5.6 组装确认回传
 * @date ：Created in 2022/1/12
 */
public class WmPCOConfirmDto implements Serializable {


    private static final long serialVersionUID = 7074743693262053841L;
    private String pcoOrderCode; //加工指令号

    private String warehouseCode; //仓库ID

    private String wmsOrderCode; //WMS单号

    private String modelNo;

    private Integer qty;

    private String msgId; //消息ID，幂等性校验用

    private UserDto userDto;

    public String getPcoOrderCode() {
        return pcoOrderCode;
    }

    public void setPcoOrderCode(String pcoOrderCode) {
        this.pcoOrderCode = pcoOrderCode;
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

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Double qty) {
        this.qty = qty.intValue();
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }
}
