package com.sales.ops.dto.inventory;

import com.sales.ops.db.entity.OpsPco;
import com.sales.ops.db.entity.OpsPcoItem;
import com.sales.ops.db.entity.OpsPcoItemInventory;

import java.util.List;

/**
 * @author C02483
 * @version 1.0
 * @description: 加工单
 * @date 2021/10/2 17:07
 */
public class CrePcoByInventoryDto {
    private OpsPco opsPco;
    private List<OpsPcoItem>  opsPcoItems;
    private List<OpsPcoItemInventory> itemInventorys;

    public CrePcoByInventoryDto(OpsPco opsPco, List<OpsPcoItem>  opsPcoItems, List<OpsPcoItemInventory> itemInventorys) {
        this.opsPco = opsPco;
        this.opsPcoItems = opsPcoItems;
        this.itemInventorys = itemInventorys;
    }

    public OpsPco getOpsPco() {
        return opsPco;
    }

    public void setOpsPco(OpsPco opsPco) {
        this.opsPco = opsPco;
    }

    public List<OpsPcoItem> getOpsPcoItems() {
        return opsPcoItems;
    }

    public void setOpsPcoItems(List<OpsPcoItem> opsPcoItems) {
        this.opsPcoItems = opsPcoItems;
    }

    public List<OpsPcoItemInventory> getItemInventorys() {
        return itemInventorys;
    }

    public void setItemInventorys(List<OpsPcoItemInventory> itemInventorys) {
        this.itemInventorys = itemInventorys;
    }
}
