package com.sales.ops.dto.order;

import java.io.Serializable;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：委托在库出库用
 * @date ：Created in 2022/5/28 11:08
 */
public class OpsWTInventoryDTO implements Serializable {

    private static final long serialVersionUID = -313897429899210862L;
    private Long inventoryId ;
    private String inventoryType ;

    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

    public String getInventoryType() {
        return inventoryType;
    }

    public void setInventoryType(String inventoryType) {
        this.inventoryType = inventoryType;
    }
}
