package com.sales.ops.dto.inventory;

import java.io.Serializable;

/**
 * @author ：C14717
 * @version: $ 出库项目明细
 * @description：
 * @date ：Created in 2022/4/15 15:22
 */
public class WmDoBarcodeItemDto implements Serializable {
    private static final long serialVersionUID = 6551812469505990143L;
    private Integer qty; // 箱码数量
    private String barcode; //箱码
    private String wmsOrderCode; //WMS唯一流水号 doid加barcode
    private String deliveryOrderCode;//发货指令 doid
    private String coldDryerNo;//冷干机序列号

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getWmsOrderCode() {
        return wmsOrderCode;
    }

    public void setWmsOrderCode(String wmsOrderCode) {
        this.wmsOrderCode = wmsOrderCode;
    }

    public String getDeliveryOrderCode() {
        return deliveryOrderCode;
    }

    public void setDeliveryOrderCode(String deliveryOrderCode) {
        this.deliveryOrderCode = deliveryOrderCode;
    }

    public String getColdDryerNo() {
        return coldDryerNo;
    }

    public void setColdDryerNo(String coldDryerNo) {
        this.coldDryerNo = coldDryerNo;
    }

    @Override
    public String toString() {
        return "WmDoBarcodeItemDto{" +
                "qty=" + qty +
                ", barcode='" + barcode + '\'' +
                ", wmsOrderCode='" + wmsOrderCode + '\'' +
                ", deliveryOrderCode='" + deliveryOrderCode + '\'' +
                ", coldDryerNo='" + coldDryerNo + '\'' +
                '}';
    }
}
