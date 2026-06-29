package com.sales.ops.dto.inventory;

import com.sales.ops.db.entity.OpsRoBarcode;

import java.io.Serializable;
import java.util.List;

/**
 * @author ：C14717
 * @version: $ 出库项目明细
 * @description：
 * @date ：Created in 2022/4/15 15:22
 */
public class WmDoBarcodeDto implements Serializable {

    private static final long serialVersionUID = -2784641048469202526L;


    private String warehouseCode;  //仓库号
    private String consigneeId;//客户代码 wms用客户代码为组 下发expdetail barcode
    private List<WmDoBarcodeItemDto> barCode; //barcode 箱码明细

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public List<WmDoBarcodeItemDto> getBarCode() {
        return barCode;
    }

    public void setBarCode(List<WmDoBarcodeItemDto> barCode) {
        this.barCode = barCode;
    }

    public String getConsigneeId() {
        return consigneeId;
    }

    public void setConsigneeId(String consigneeId) {
        this.consigneeId = consigneeId;
    }

    @Override
    public String toString() {
        return "WmDoBarcodeDto{" +
                "warehouseCode='" + warehouseCode + '\'' +
                ", consigneeId='" + consigneeId + '\'' +
                ", barCode=" + barCode.toString() +
                '}';
    }
}
