package com.sales.ops.serviceimpl.event.v3.order.entity;


import com.sales.ops.db.entity.*;
import com.sales.ops.enums.InventoryStatusEnum;
import com.sales.ops.enums.InventoryTableTypeEnum;
import com.sales.ops.enums.InventoryTypeEnum;
import com.sales.ops.enums.OptStatusEnum;
import lombok.Data;

import java.util.Date;
import java.util.Optional;

@Data
public class DBRelation extends Relation {

    private Long inventoryId;
    private String fromWarehouseCode;
    private String toWarehouseCode;
    private InventoryTypeEnum propertyCode;
    private OptStatusEnum optStatus;
    private Date optTime;
    private String associateNo;
    private String invoiceNo;
    private Long invoiceId;

    private OpsDo dbDo;

    // 有关联关系的do调拨
    public DBRelation(OpsDoItem doItem, OpsDoItemInventory doItemInv, OpsDo dbDo) {
        super.wmOrderId = doItemInv.getDoId();
        super.invTable = InventoryTableTypeEnum.getEnumByType(doItemInv.getInventoryTableType());
        super.invId = doItemInv.getInventoryId();
        if (invTable == InventoryTableTypeEnum.NORMAL) {
            super.useQty = doItemInv.getUseQty() - Optional.ofNullable(doItemInv.getOutQty()).orElse(0);
        } else {
            super.useQty = doItemInv.getUseQty();
        }
        super.outQty = 0;
        this.dbDo = dbDo;
    }

    // 无关联关系的do调拨
    public DBRelation(OpsDoItem doItem, Integer qty, OpsDo dbDo) {
        super.wmOrderId = dbDo.getDoId();
        super.invTable = InventoryTableTypeEnum.TRANS;
        super.invId = null;
        super.useQty = qty;
        super.outQty = 0;
        this.dbDo = dbDo;
    }



    // 有关联关系的pco调拨
    public DBRelation(OpsPcoItemInventory pcoItemInv, OpsDo dbDo) {
        super.wmOrderId = pcoItemInv.getPcoId();
        super.invTable = InventoryTableTypeEnum.getEnumByType(pcoItemInv.getInventoryTableType());
        super.invId = pcoItemInv.getInventoryId();
        super.useQty = pcoItemInv.getUseQty();
        super.outQty = Optional.ofNullable(pcoItemInv.getOutQty()).orElse(0);
        this.dbDo = dbDo;
    }

    // 有关联关系的do调拨
    public void fillInventoryInfo(OpsInventoryMove move, OpsInventoryProperty property) {
        super.invStatus = InventoryStatusEnum.getEnumByType(move.getInventoryStatus());
        this.inventoryId = move.getInventoryId();
        this.fromWarehouseCode = dbDo.getWarehouseCode();
        this.toWarehouseCode = dbDo.getGatherWarehouseCode();// 可以是move库存的签收仓，也可以是调拨出库的gather_warehouse
        this.propertyCode = InventoryTypeEnum.parse(property.getInventoryTypeCode());
        this.optStatus = OptStatusEnum.getByCode(move.getOptStatus());
        this.optTime = move.getOptTime();
        this.associateNo = dbDo.getDoId();
        this.invoiceNo = move.getInvoiceno();
        this.invoiceId = move.getInvoiceid();
    }

    // 无关联关系的do调拨,还没出的调拨，调拨出outQty=0
    public void fillInventoryInfo(OpsInventory normal, OpsInventoryProperty property) {
        super.invStatus = InventoryStatusEnum.getEnumByType(normal.getInventoryStatus());
        this.inventoryId = normal.getInventoryId();
        this.fromWarehouseCode = dbDo.getWarehouseCode();
        this.toWarehouseCode = dbDo.getGatherWarehouseCode();
        this.propertyCode = InventoryTypeEnum.parse(property.getInventoryTypeCode());
        this.associateNo = dbDo.getDoId();
    }

    // 无关联关系的do调拨,已发货但没move
    public void fillInventoryInfo() {
        super.invStatus = InventoryStatusEnum.REQUEST;
        this.inventoryId = null;
        this.fromWarehouseCode = dbDo.getWarehouseCode();
        this.toWarehouseCode = dbDo.getGatherWarehouseCode();// 可以是move库存的签收仓，也可以是调拨出库的gather_warehouse
        this.propertyCode = null;
        this.optStatus = null;
        this.optTime = null;
        this.associateNo = dbDo.getDoId();
        this.invoiceNo = null;
        this.invoiceId = null;
    }


    @Override
    public String getInventoryTypeCode() {
        return this.propertyCode != null ? propertyCode.getType() : null;
    }

}
