package com.sales.ops.api.dto.wms;

import java.io.Serializable;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：WMS - OPS  5.2入库确认回传 //todo 预留
 * @date ：Created in 2021/11/2 9:41
 */
public class OpsRoItemConfirmDto implements Serializable {

    private static final long serialVersionUID = -3295169233570841349L;


    private String receiveCode; //收货编码 TRUE String(30)
    private String scanType; //收货方式(扫箱/扫托) TRUE String(30)
    private Integer qty; //实收数量 TRUE String(30)
    private String inventoryType; //质量状态  ZP=正品, CC=残次, JS=机损, XS=箱损, 默认为ZP TRUE String(30)

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
}
