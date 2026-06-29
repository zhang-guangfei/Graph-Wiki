package com.sales.ops.api.dto.wms;

import java.io.Serializable;
import java.util.List;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：WMS - OPS  5.2入库确认回传
 * @date ：Created in 2021/11/2 9:41
 */
public class OpsRoConfirmDto implements Serializable {

    private static final long serialVersionUID = -458357162684996171L;
    private String receiveOrderCode; //物流收货指令号 TRUE String(20)
    private String warehouseCode; //仓库ID TRUE String(30)
    private String wmsOrderCode; //WMS单号 TRUE String(30)
    private String receiveCode; //收货编码 TRUE String(30)
    private String scanType; //收货方式(扫箱/扫托) TRUE String(30)
    private Integer qty; //实收数量 TRUE String(30)
    private String inventoryType; //质量状态  ZP=正品, CC=残次, JS=机损, XS=箱损, 默认为ZP TRUE String(30)

    private List<OpsRoItemConfirmDto> itemList;

    private String msgId;//消息幂等性用

    public String getReceiveCode() {
        return receiveCode;
    }

    public void setReceiveCode(String receiveCode) {
        this.receiveCode = receiveCode;
    }

    public String getScanType() {
        return scanType;
    }

    public void setScanType(String scanType) {
        this.scanType = scanType;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getInventoryType() {
        return inventoryType;
    }

    public void setInventoryType(String inventoryType) {
        this.inventoryType = inventoryType;
    }

    public List<OpsRoItemConfirmDto> getItemList() {
        return itemList;
    }

    public void setItemList(List<OpsRoItemConfirmDto> itemList) {
        this.itemList = itemList;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getReceiveOrderCode() {
        return receiveOrderCode;
    }

    public void setReceiveOrderCode(String receiveOrderCode) {
        this.receiveOrderCode = receiveOrderCode;
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
