package com.sales.ops.serviceimpl.inventory.factory;

import cn.hutool.core.date.DateUtil;
import com.sales.ops.db.entity.*;
import com.sales.ops.enums.*;
import com.sales.ops.serviceimpl.dispatch.rodispatch.domain.ReturnConfirmContextItem;
import com.sales.ops.utils.PoNoUtil;

/**
 * @author C12961
 * @date 2023/3/3 14:37
 */
public class InventoryFactory {

    /**
     * @description 采购接单时，创建生产中库存
     * @author C12961
     * @date 2023/3/3 14:40
     */
    public static OpsInventoryMove productInventory(OpsRequestpurchase request, OpsPurchaseorder purchase, Long propertyId) {
        OpsInventoryMove inventory = new OpsInventoryMove();
        inventory.setSignWarehouseCode(purchase.getReceivewarehouseid());// 采购仓
        inventory.setWarehouseCode(request.getRequestwarehouseid());// 请购仓
        inventory.setInventoryStatus(InventoryStatusEnum.PRODUCE.getCode());// 生产中P
        inventory.setQaStatus(QAStatusEnum.NORMAL.getType());// 质量状态：良品0
        inventory.setQuantity(request.getQuantity());// 数量：请购数量
        inventory.setPoQty(purchase.getQuantity());//采购数量：请购数量 // TODO ?? 此处填请购数量还是采购数量？我觉得是采购数量
        inventory.setPrepareQuantity(0);// 预占数量：0
        inventory.setModelno(purchase.getModelno());
        inventory.setInventoryPropertyId(propertyId);
        inventory.setOrderno(request.getOrderno());// 请购单号
        inventory.setItemno(request.getItemno());// 请购单项号
        inventory.setSplititemno(PoNoUtil.getSplitNo(request));// 请购单拆分单号，为空填0
        inventory.setPrice(request.getStdprice());        // 该字段为请购原始值不转1（用于后期分析数据使用）
        inventory.setSupplierid(purchase.getSupplierid());//采购单供应商
        inventory.setAssociateNo(purchase.getOrderno());// 采购单号
        inventory.setAssociateNoItem(purchase.getItemno());// 采购单号Item
        inventory.setAssociateNoSplitno(PoNoUtil.getSplitNo(purchase));// 采购单号，为空填0
        inventory.setSourceType(SourceTypeEnum.PURCHASE.getType());// 来源：采购0
        inventory.setGreencode(purchase.getGreencode());// 绿色标记值：H
        inventory.setOptStatus(OptStatusEnum.PURCHASE_ACCEPT.getCode());// 状态：采购接单1
        return inventory;
    }

    /**
     * @description 合并采购单产生的未分配的生产中库存，需要单独创建，不需要预占
     * @author C12961
     * @date 2023/3/6 9:36
     */
    public static OpsInventoryMove productInventory(OpsPurchaseorder purchase, int leftQty) {
        OpsInventoryMove inventory = new OpsInventoryMove();
        inventory.setSignWarehouseCode(purchase.getReceivewarehouseid());// 采购仓
        inventory.setWarehouseCode(purchase.getReceivewarehouseid());// 采购仓
        inventory.setInventoryStatus(InventoryStatusEnum.PRODUCE.getCode());// 生产中P
        inventory.setQaStatus(QAStatusEnum.NORMAL.getType());//质量状态：良品0
        inventory.setQuantity(leftQty);// 采购单的剩余未分配数量
        inventory.setPrepareQuantity(0);// 无预占
        inventory.setPoQty(purchase.getQuantity());// 采购数量
        inventory.setModelno(purchase.getModelno());
        inventory.setInventoryPropertyId(1L);// 通用库存
        inventory.setOrderno(null);
        inventory.setItemno(null);
        inventory.setSplititemno(null);
        inventory.setPrice(null);
        inventory.setSupplierid(purchase.getSupplierid());//采购单供应商
        inventory.setAssociateNo(purchase.getOrderno());// 采购单号
        inventory.setAssociateNoItem(purchase.getItemno());// 采购单号Item
        inventory.setAssociateNoSplitno(PoNoUtil.getSplitNo(purchase));// 采购单号，为空填0
        inventory.setSourceType(SourceTypeEnum.PURCHASE.getType());// 来源：采购0
        inventory.setGreencode(purchase.getGreencode());// 绿色标记值：H
        inventory.setOptStatus(OptStatusEnum.PURCHASE_ACCEPT.getCode());// 状态：采购接单1
        inventory.setRemark("因合并采购,生成的行数据");
        return inventory;
    }

    /**
     * @description HandConfirm时，调拨出库产生调拨在途
     * @author C12961
     * @date 2023/5/18 14:04
     */
    public static OpsInventoryMove transferInventory(OpsDo dbck, OpsDoItem dbckItem, long inventoryPropertyId, Integer qty, String invoiceNo,Long invoiceId) {
        //do调拨出库类型组装选择,do第四个字段，不是组装选第三字段
        int splitNo = DoSourceEnum.ASSModelNo.getType().equals(dbck.getDoSource()) ? dbck.getAssNum() : dbck.getNum();
        OpsInventoryMove inventory = new OpsInventoryMove();
        inventory.setInvoiceno(invoiceNo);
        inventory.setInvoiceid(invoiceId);
        inventory.setInventoryStatus(InventoryStatusEnum.DBTRANS.getCode());
        inventory.setModelno(dbckItem.getModelno());
        inventory.setQuantity(qty);// 存在分纳出库
        inventory.setPrepareQuantity(0);// bug 10001 C12961预约数不添加，后续创建关联关系时添加
        inventory.setWarehouseCode(dbck.getGatherWarehouseCode());
        inventory.setSignWarehouseCode(dbck.getGatherWarehouseCode());
        inventory.setInventoryPropertyId(inventoryPropertyId);
        inventory.setOrderno(dbck.getOrderId());
        inventory.setItemno(Integer.valueOf(dbck.getOrderItem()));
        inventory.setSplititemno(splitNo);
        inventory.setAssociateNo(dbck.getOrderId());
        inventory.setAssociateNoItem(Integer.valueOf(dbck.getOrderItem()));
        inventory.setAssociateNoSplitno(splitNo);
        inventory.setQaStatus(QAStatusEnum.NORMAL.getType());
        inventory.setPrice(dbckItem.getPrice());
        inventory.setSourceType(SourceTypeEnum.DB.getType());
        inventory.setPoQty(dbckItem.getOutQty());
        inventory.setOptStatus(OptStatusEnum.PURCHASE_ACCEPT.getCode());
        inventory.setGreencode(dbckItem.getGreenCode());
        return inventory;
    }


    public static OpsInventoryMove returnInventory(ReturnConfirmContextItem item, long propertyId) {
        OpsInventoryMove tinventoryMove = new OpsInventoryMove();
        tinventoryMove.setWarehouseCode(item.getWarehouseCode());
        tinventoryMove.setSignWarehouseCode(item.getWarehouseCode());
        tinventoryMove.setInventoryStatus(InventoryStatusEnum.THTRANS.getCode());
        tinventoryMove.setQuantity(item.getQty());
        tinventoryMove.setQaStatus(QAStatusEnum.NORMAL.getType());
        tinventoryMove.setPrepareQuantity(0);
        tinventoryMove.setModelno(item.getModelNo());
        tinventoryMove.setInventoryPropertyId(propertyId);
        // begin bug:8857 B28029 2022-12-01 退货申请号改成订单号
        tinventoryMove.setAssociateNo(item.getOrderNo());
        tinventoryMove.setAssociateNoItem(item.getOrderItem());
        tinventoryMove.setAssociateNoSplitno(item.getSplitItemNo());
        // end
        tinventoryMove.setOrderno(item.getOrderNo());
        tinventoryMove.setItemno(item.getOrderItem());
        tinventoryMove.setSplititemno(item.getSplitItemNo());// 订单拆分项
        tinventoryMove.setSupplierid(item.getCustomerNo());// 退货供应商代码填写客户代码
        tinventoryMove.setInvoiceno(item.getInvoiceNo());
        tinventoryMove.setInvoiceid(item.getInvoiceId());
        tinventoryMove.setPrereceivedate(DateUtil.date()); // 预计到达时间默认当天
        tinventoryMove.setSourceType(SourceTypeEnum.RETURN.getType());
        tinventoryMove.setOptStatus(OptStatusEnum.PURCHASE_ACCEPT.getCode());
        tinventoryMove.setPoQty(item.getQty());
        return tinventoryMove;
    }

}
