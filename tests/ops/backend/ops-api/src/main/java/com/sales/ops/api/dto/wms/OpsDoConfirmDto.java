package com.sales.ops.api.dto.wms;

import java.io.Serializable;
import java.util.List;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：WMS - > OPS  5.3 出库确认回传
 * @date ：Created in 2021/11/2 9:44
 */
public class OpsDoConfirmDto implements Serializable {
    private static final long serialVersionUID = 222172910308394543L;

    private String deliveryOrderCode; //物流指令号 TRUE String(20)
    private String warehouseCode; //仓库ID TRUE String(30)
    private String wmsOrderCode; //WMS单号 TRUE
    private String msgId;//消息幂等性用
    private OpsDoItemConfirmDto items; //多个箱 发多条 指令会有重复数据

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public OpsDoItemConfirmDto getItems() {
        return items;
    }

    public void setItems(OpsDoItemConfirmDto items) {
        this.items = items;
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


}
