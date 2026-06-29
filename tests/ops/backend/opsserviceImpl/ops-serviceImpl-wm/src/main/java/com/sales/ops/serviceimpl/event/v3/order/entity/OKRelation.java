package com.sales.ops.serviceimpl.event.v3.order.entity;


import com.sales.ops.db.entity.*;
import com.sales.ops.enums.InventoryStatusEnum;
import com.sales.ops.enums.InventoryTableTypeEnum;
import com.sales.ops.enums.InventoryTypeEnum;
import lombok.Data;

import java.util.Optional;

@Data
public class OKRelation extends Relation {
    // 库存Id
    private Long inventoryId;
    // 当前库存的所在仓库
    private String warehouseCode;
    // 库存属性
    private InventoryTypeEnum propertyCode;
    // 关联单号（交易出库单号）
    private String associateNo;


    // 数据来源
    private OpsDo jyckDo;

    public OKRelation() {}

    public OKRelation(OpsDo jyckDo, OpsDoItemInventory inv) {
        super.wmOrderId = inv.getDoId(); // 库存所绑定的物流指令
        super.invTable = InventoryTableTypeEnum.getEnumByType(inv.getInventoryTableType()); // 库存的在库在途状态
        super.invId = inv.getInventoryId();// 库存id
        super.useQty = Optional.ofNullable(inv.getUseQty()).orElse(0); // 当前关联关系预占库存的数量
        super.outQty = Optional.ofNullable(inv.getOutQty()).orElse(0);
        this.jyckDo = jyckDo;
    }

    public OKRelation(OpsDo jyckDo, OpsPcoItemInventory pcoInv) {
        super.wmOrderId = pcoInv.getPcoId();
        super.invTable = InventoryTableTypeEnum.getEnumByType(pcoInv.getInventoryTableType());
        super.invId = pcoInv.getInventoryId();
        super.useQty = Optional.ofNullable(pcoInv.getUseQty()).orElse(0);
        super.outQty = Optional.ofNullable(pcoInv.getOutQty()).orElse(0);
        this.jyckDo = jyckDo;
    }

    public void fillInventoryInfo(OpsInventory normal, OpsInventoryProperty property) {
        this.inventoryId = normal.getInventoryId(); // 库存id
        super.invStatus = InventoryStatusEnum.getEnumByType(normal.getInventoryStatus()); // 库存状态N
        this.warehouseCode = normal.getWarehouseCode();// 库存所在仓库
        this.propertyCode = InventoryTypeEnum.parse(property.getInventoryTypeCode());// 库存属性
        this.associateNo = jyckDo.getDoId();// 当前关联关系的关联单号
    }

    @Override
    public String getInventoryTypeCode() {
        return this.propertyCode != null ? propertyCode.getType() : null;
    }


}
