package com.sales.ops.utils;

import com.sales.ops.common.until.StringPhoneUtil;
import com.sales.ops.db.entity.*;
import com.sales.ops.dto.inventory.OpsDoDto;
import com.sales.ops.dto.inventory.OpsRoDto;
import com.sales.ops.enums.*;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;

/**
 * @author C12961
 * @date 2023/3/2 10:23
 */
public class WmOrderFactory {


    public static OpsRo createCGDBRKRo(OpsRequestpurchase opsRequestpurchase) {
        String roId = WmOrderNoFactory.DBRK_ID(opsRequestpurchase.getOrderno(), opsRequestpurchase.getItemno(),
                PoNoUtil.getSplitNo(opsRequestpurchase));
        OpsRo opsRo = new OpsRo();
        opsRo.setRoId(roId);
        opsRo.setRoSource("");
        opsRo.setOrderId(opsRequestpurchase.getOrderno());
        opsRo.setOrderItem(opsRequestpurchase.getItemno() + "");
        if (null != opsRequestpurchase.getSplititemno()) {
            opsRo.setNum(opsRequestpurchase.getSplititemno());
        } else {
            opsRo.setNum(0);
        }
        opsRo.setAssNum(0);
        opsRo.setExtNum(0);
        opsRo.setRoType(RoTypeEnum.CGDBRK.getType());
        opsRo.setWarehouseCode(opsRequestpurchase.getRequestwarehouseid());
        opsRo.setRoStatus(0);// 初始
        opsRo.setTransType("");// 运输方式
        opsRo.setCarried("");// 到货承运商
        opsRo.setExpressCode("");// 到货承运商
        opsRo.setCustomerNo(opsRequestpurchase.getCustomerno());
        opsRo.setInvoiceNo("");// 到货发票号
        opsRo.setSupplierId("");// 到货供应商
        opsRo.setDelflag(0);
        opsRo.setCreator("补库调拨");
        opsRo.setCreTime(new Date());
        return opsRo;
    }

    // 创建采购调拨出库(采购发单时调用)
    public static OpsDo createCGDBCKDo(OpsRequestpurchase requestPurchase, OpsWarehouse gatherWarehouse) {
        String doId = WmOrderNoFactory.DBCK_ID(requestPurchase.getOrderno(), requestPurchase.getItemno(),
                PoNoUtil.getSplitNo(requestPurchase));
        OpsDo opsDo = new OpsDo();
        opsDo.setDoId(doId);
        opsDo.setOrderId(requestPurchase.getOrderno());
        opsDo.setOrderItem(requestPurchase.getItemno().toString());
        Integer num = Optional.ofNullable(requestPurchase.getSplititemno()).orElse(0);
        opsDo.setNum(num);
        opsDo.setAssNum(0);
        opsDo.setExtNum(0);
        opsDo.setDoSource(DoSourceEnum.ALL.getType());
        opsDo.setDoType(DoTypeEnum.CGDBCK.getType());
        opsDo.setWarehouseCode(requestPurchase.getPurchasewarehouseid());
        opsDo.setGatherWarehouseCode(gatherWarehouse.getWarehouseCode());

        // bugid:11445 c14717 2023/07/26 上层代码封装
        opsDo.setProvince(gatherWarehouse.getProvince());
        opsDo.setCity(gatherWarehouse.getCity());
        opsDo.setDistrict(gatherWarehouse.getDistrict());
        opsDo.setStreet(gatherWarehouse.getAdress());
        opsDo.setAddress(gatherWarehouse.getAdress());
        opsDo.setLinkman(gatherWarehouse.getLinkman());
        if (!org.springframework.util.StringUtils.isEmpty(gatherWarehouse.getLinkMobile())) {// 手机号
            if (StringPhoneUtil.isMobil(gatherWarehouse.getLinkMobile())) {
                opsDo.setMobile(gatherWarehouse.getLinkMobile());
            }
        }
        if (!org.springframework.util.StringUtils.isEmpty(gatherWarehouse.getLinkPhone())) {
            // 是电话 且不是手机号 存电话
            if (StringPhoneUtil.isPhone(gatherWarehouse.getLinkPhone())
                    && !StringPhoneUtil.isMobil(gatherWarehouse.getLinkPhone())) {
                opsDo.setPhone(gatherWarehouse.getLinkPhone());
            }
        }
        opsDo.setPostcode(gatherWarehouse.getPostCode() + "");
        opsDo.setCustomerNo(requestPurchase.getCustomerno());
        opsDo.setCorderNo(requestPurchase.getCorderno());
        opsDo.setDoStatus(0);// 初始
        opsDo.setCarried("");// 指定承运商 dbck不指定承运商
        opsDo.setRemark(requestPurchase.getRemark()); // 指定备注内容
        opsDo.setVersion(0);
        opsDo.setDelflag(0);
        opsDo.setCreator("补库调拨");
        opsDo.setCreTime(new Date());
        opsDo.setWaitType(DoWaitTypeEnum.WaitCG.getType());
        opsDo.setHopeDate(requestPurchase.getHopeexportdate());
        opsDo.setWlDate(requestPurchase.getHopeexportdate());
        opsDo.setDeptNo(requestPurchase.getDeptno());// 营业所代码
        return opsDo;
    }

    public static OpsDoDto createAdjustDoAndItem(String orderId, Integer orderItem, OpsInventory inventory, int qty) {
        OpsDo opsDo = new OpsDo();
        OpsDoItem opsDoItem = new OpsDoItem();
        opsDo.setOrderId(orderId);
        opsDo.setOrderItem(orderItem.toString());
        opsDo.setNum(0);
        opsDo.setAssNum(0);
        String doId = WmOrderNoFactory.DO_ADJUST(orderId, orderItem);
        opsDo.setDoId(doId);
        opsDo.setDoSource(DoSourceEnum.ALL.getType());
        opsDo.setDoType(DoTypeEnum.TZCK.getType());
        opsDo.setWarehouseCode(inventory.getWarehouseCode());
        opsDo.setGatherWarehouseCode(inventory.getWarehouseCode());
        opsDo.setDoStatus(DoStatusEnum.FINISH.getStatus());
        opsDo.setWaitType(DoWaitTypeEnum.OK.getType());
        opsDo.setDelflag(1);
        opsDoItem.setDoId(opsDo.getDoId());
        opsDoItem.setDoItem(1);
        opsDoItem.setModelno(inventory.getModelno());
        opsDoItem.setQty(qty);
        opsDoItem.setOutQty(qty);
        OpsDoItemInventory opsDoItemInventory = new OpsDoItemInventory();
        opsDoItemInventory.setDoId(opsDo.getDoId());
        opsDoItemInventory.setDoItem(opsDoItem.getDoItem());
        opsDoItemInventory.setInventoryId(inventory.getInventoryId());
        opsDoItemInventory.setInventoryTableType(InventoryTableTypeEnum.NORMAL.getType());
        opsDoItemInventory.setUseQty(qty);
        opsDoItemInventory.setOutQty(qty);
        OpsDoDto opsDoDto = new OpsDoDto(opsDo, opsDoItem, Collections.singletonList(opsDoItemInventory));
        return opsDoDto;
    }

    public static OpsRoDto createAdjustRoAndItem(String invoiceNo, String orderId, Integer orderItem, OpsInventory inventory, int qty) {
        OpsRo opsRo = new OpsRo();
        OpsRoItem opsRoItem = new OpsRoItem();
        opsRo.setInvoiceNo(invoiceNo);
        opsRo.setOrderId(orderId);
        opsRo.setOrderItem(orderItem.toString());
        opsRo.setNum(0);
        opsRo.setAssNum(0);
        String roId = WmOrderNoFactory.RO_ADJUST(Optional.ofNullable(invoiceNo).orElse(""), orderId, orderItem);
        opsRo.setRoId(roId);
        opsRo.setRoSource(RoSourceEnum.ALL.getType());
        opsRo.setRoType(RoTypeEnum.TZRK.getType());
        opsRo.setRoStatus(RoStatusEnum.FINISH.getStatus());
        opsRo.setWarehouseCode(inventory.getWarehouseCode());
        opsRo.setDelflag(1);
        opsRoItem.setRoId(opsRo.getRoId());
        opsRoItem.setRoItem(1);
        opsRoItem.setModelno(inventory.getModelno());
        opsRoItem.setQty(qty);
        opsRoItem.setRecQty(qty);
        opsRoItem.setDelflag(1);
        OpsRoItemInventory opsRoItemInventory = new OpsRoItemInventory();
        opsRoItemInventory.setRoId(opsRo.getRoId());
        opsRoItemInventory.setRoItem(opsRoItem.getRoItem());
        opsRoItemInventory.setInventoryId(inventory.getInventoryId());
        opsRoItemInventory.setQty(qty);
        opsRoItemInventory.setRecQty(qty);
        opsRoItemInventory.setDelflag(1);
        OpsRoDto roDto = new OpsRoDto(opsRo, opsRoItem, Collections.singletonList(opsRoItemInventory));
        return roDto;
    }


}
