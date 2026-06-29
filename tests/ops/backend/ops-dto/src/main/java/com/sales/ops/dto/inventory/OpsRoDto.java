package com.sales.ops.dto.inventory;

import com.sales.ops.db.entity.OpsRo;
import com.sales.ops.db.entity.OpsRoItem;
import com.sales.ops.db.entity.OpsRoItemInventory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author C02483
 * @version 1.0
 * @description: TODO
 * @date 2021/12/30 14:23
 */
public class OpsRoDto {
    private OpsRo opsRo;
    private OpsRoItem opsRoItem;
    private List<OpsRoItemInventory> roItemInventoryList;

    public OpsRoDto() {
    }

    public OpsRoDto(OpsRo opsRo, OpsRoItem opsRoItem, List<OpsRoItemInventory> roItemInventoryList) {
        this.opsRo = opsRo;
        this.opsRoItem = opsRoItem;
        this.roItemInventoryList = roItemInventoryList;
    }

    public OpsRoDto(OpsRo opsRo, OpsRoItem opsRoItem, OpsRoItemInventory roItemInventory) {
        this.opsRo = opsRo;
        this.opsRoItem = opsRoItem;
        this.roItemInventoryList = new ArrayList<>();
        this.roItemInventoryList.add(roItemInventory);
    }

    public OpsRoDto(OpsRo opsRo, OpsRoItem opsRoItem) {
        this.opsRo = opsRo;
        this.opsRoItem = opsRoItem;
    }

    public OpsRo getOpsRo() {
        return opsRo;
    }

    public void setOpsRo(OpsRo opsRo) {
        this.opsRo = opsRo;
    }

    public OpsRoItem getOpsRoItem() {
        return opsRoItem;
    }

    public void setOpsRoItem(OpsRoItem opsRoItem) {
        this.opsRoItem = opsRoItem;
    }

    public List<OpsRoItemInventory> getRoItemInventoryList() {
        return roItemInventoryList;
    }

    public void setRoItemInventoryList(List<OpsRoItemInventory> roItemInventoryList) {
        this.roItemInventoryList = roItemInventoryList;
    }


    public String getRoId() {
        return this.opsRo.getRoId();
    }
}
