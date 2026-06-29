package com.sales.ops.dto.inventory;

import com.sales.ops.db.entity.OpsRo;
import com.sales.ops.db.entity.OpsRoItem;
import com.sales.ops.db.entity.OpsRoItemInventory;

import java.util.List;

/**
 * @author C02483
 * @version 1.0
 * @description: 根据库存算法，生成收货指令
 * @date 2021/10/2 13:49
 */
public class CreRoByInventoryDto {

    private OpsRo opsRo;
    private List<OpsRoItem> opsRoItemList;
    private List<OpsRoItemInventory> opsRoItemInventoryList;

    public CreRoByInventoryDto(OpsRo opsRo, List<OpsRoItem> opsRoItemList, List<OpsRoItemInventory> opsRoItemInventoryList) {
        this.opsRo = opsRo;
        this.opsRoItemList = opsRoItemList;
        this.opsRoItemInventoryList = opsRoItemInventoryList;
    }

    public OpsRo getOpsRo() {
        return opsRo;
    }

    public void setOpsRo(OpsRo opsRo) {
        this.opsRo = opsRo;
    }

    public List<OpsRoItem> getOpsRoItemList() {
        return opsRoItemList;
    }

    public void setOpsRoItemList(List<OpsRoItem> opsRoItemList) {
        this.opsRoItemList = opsRoItemList;
    }

    public List<OpsRoItemInventory> getOpsRoItemInventoryList() {
        return opsRoItemInventoryList;
    }

    public void setOpsRoItemInventoryList(List<OpsRoItemInventory> opsRoItemInventoryList) {
        this.opsRoItemInventoryList = opsRoItemInventoryList;
    }
}
