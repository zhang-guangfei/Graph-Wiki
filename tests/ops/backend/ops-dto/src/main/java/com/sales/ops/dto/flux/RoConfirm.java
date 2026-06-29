package com.sales.ops.dto.flux;

import java.util.List;

/**
 * @author C02483
 * @version 1.0
 * @description: 收货到货确认
 * @date 2022/1/23 21:56
 */
public class RoConfirm {
    private String flag;
    private String message;
    private String warehouseId;
    private List<RoConfirmItem> optlist;

    public RoConfirm(String flag,String warehouseId, String message, List<RoConfirmItem> optlist) {
        this.flag = flag;
        this.warehouseId = warehouseId;
        this.message = message;
        this.optlist = optlist;
    }

    public RoConfirm() {

    }


    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public List<RoConfirmItem> getOptlist() {
        return optlist;
    }

    public void setOptlist(List<RoConfirmItem> optlist) {
        this.optlist = optlist;
    }
}
