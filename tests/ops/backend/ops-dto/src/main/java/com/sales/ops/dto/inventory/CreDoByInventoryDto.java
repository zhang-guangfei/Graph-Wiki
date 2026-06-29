package com.sales.ops.dto.inventory;

import com.sales.ops.db.entity.OpsDo;
import com.sales.ops.db.entity.OpsDoItem;
import com.sales.ops.db.entity.OpsDoItemInventory;

import java.util.List;

/**
 * @author C02483
 * @version 1.0
 * @description: 依据库存信息创建DO
 * @date 2021/10/2 11:01
 */
public class CreDoByInventoryDto {

    private OpsDo opsDo;
    private OpsDoItem opsDoItem;
    private List<OpsDoItemInventory> itemInventorys;


    public CreDoByInventoryDto(OpsDo opsDo, OpsDoItem opsDoItem, List<OpsDoItemInventory> itemInventorys) {
        this.opsDo = opsDo;
        this.opsDoItem = opsDoItem;
        this.itemInventorys = itemInventorys;
    }

    public CreDoByInventoryDto() {
    }

    public OpsDo getOpsDo() {
        return opsDo;
    }

    public void setOpsDo(OpsDo opsDo) {
        this.opsDo = opsDo;
    }

    public OpsDoItem getOpsDoItem() {
        return opsDoItem;
    }

    public void setOpsDoItem(OpsDoItem opsDoItem) {
        this.opsDoItem = opsDoItem;
    }

    public List<OpsDoItemInventory> getItemInventorys() {
        return itemInventorys;
    }

    public void setItemInventorys(List<OpsDoItemInventory> itemInventorys) {
        this.itemInventorys = itemInventorys;
    }
}
