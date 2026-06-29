package com.sales.ops.dto.zd;

import com.sales.ops.db.entity.OpsInventory;
import com.sales.ops.db.entity.OpsInventoryMove;

import java.io.Serializable;
import java.util.*;

/**
 * 转定画面获取库存列表
 */
public class ZDInventoryCkByOrderOutDto implements Serializable {
    private static final long serialVersionUID = 3125137559483638558L;
    private List<ZDInventoryDto> inventoryList;
    private List<ZDInventoryMoveDto> inventoryMoveList;

    public ZDInventoryCkByOrderOutDto() {
        this.inventoryList = new ArrayList<>();
        this.inventoryMoveList = new ArrayList<>();
    }

    public List<ZDInventoryDto> getInventoryList() {
        return inventoryList;
    }

    public void setInventoryList(List<ZDInventoryDto> inventoryList) {
        this.inventoryList = inventoryList;
    }

    public List<ZDInventoryMoveDto> getInventoryMoveList() {
        return inventoryMoveList;
    }

    public void setInventoryMoveList(List<ZDInventoryMoveDto> inventoryMoveList) {
        this.inventoryMoveList = inventoryMoveList;
    }

    public void setInvList(OpsInventory inv , Boolean dk,Boolean risk){
        ZDInventoryDto obj = new ZDInventoryDto();
        obj.setInventory(inv);
        obj.setDk(dk);
        obj.setRisk(risk);
        this.inventoryList.add(obj);
    }

    public void setInvMoveList(OpsInventoryMove inv , Boolean dk){
        ZDInventoryMoveDto obj = new ZDInventoryMoveDto();
        obj.setInventory(inv);
        obj.setDk(dk);
        this.inventoryMoveList.add(obj);
    }
}
