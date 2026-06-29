package com.sales.ops.dto.zd;

import com.sales.ops.db.entity.OpsInventory;

import java.io.Serializable;

/**
 * 转定在库 展示该订单可用在库
 */
public class ZDInventoryDto implements Serializable {
    private static final long serialVersionUID = -5971951243761718060L;
    private OpsInventory inventory;
    //大口标识
    private Boolean dk;

    // 风险标识
    private Boolean risk =  false;

    public Boolean getRisk() {
        return risk;
    }

    public void setRisk(Boolean risk) {
        this.risk = risk;
    }

    public OpsInventory getInventory() {
        return inventory;
    }

    public void setInventory(OpsInventory inventory) {
        this.inventory = inventory;
    }

    public Boolean getDk() {
        return dk;
    }

    public void setDk(Boolean dk) {
        this.dk = dk;
    }
}
