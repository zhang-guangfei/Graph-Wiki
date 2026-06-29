package com.sales.ops.dto.zd;

import com.sales.ops.db.entity.OpsInventoryMove;

import java.io.Serializable;

/**
 * 转定在途 展示该订单可用在途
 */
public class ZDInventoryMoveDto implements Serializable {

    private static final long serialVersionUID = -7385886267869884623L;
    private OpsInventoryMove inventory;
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

    public OpsInventoryMove getInventory() {
        return inventory;
    }

    public void setInventory(OpsInventoryMove inventory) {
        this.inventory = inventory;
    }

    public Boolean getDk() {
        return dk;
    }

    public void setDk(Boolean dk) {
        this.dk = dk;
    }
}
