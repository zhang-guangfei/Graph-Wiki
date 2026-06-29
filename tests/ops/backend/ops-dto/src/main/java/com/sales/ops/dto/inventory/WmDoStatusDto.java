package com.sales.ops.dto.inventory;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sales.ops.dto.util.UserDto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：WMS 5.4 出库发货状态回传
 * @date ：Created in 2021/10/19 16:36
 */
public class WmDoStatusDto implements Serializable {


    private static final long serialVersionUID = -8058607040046076930L;
    private String deliveryOrderCode; //物流指令号

    private String warehouseCode; //仓库ID

    private String wmsOrderCode; //WMS单号

    private String processStatus;//PICK=捡货,PACKAGE= 包装,COLLECTING=待揽收  --去掉20220322 DELIVERED=装箱

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date operateTime;//操作时间

    private String operatorName;//操作人员

    private String msgId; //消息ID，幂等性校验用

    private UserDto userDto;

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

    public String getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(String processStatus) {
        this.processStatus = processStatus;
    }


    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
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
