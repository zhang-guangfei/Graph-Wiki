package com.sales.ops.dto.util;

import com.sales.ops.db.entity.*;
import com.sales.ops.dto.purchase.PoReplyInfoDto;
import com.sales.ops.enums.DoSourceEnum;
import com.sales.ops.enums.DoTypeEnum;

import java.util.Objects;
import java.util.Optional;

/**
 * @author C12961
 * @date 2023/2/17 9:34
 */
//todo 修改14位
public class OrderNoInfo {

    String orderNo;
    Integer itemNo;
    Integer splitNo;

    public OrderNoInfo(String orderNo, Integer itemNo, Integer splitNo) {
        this.orderNo = orderNo;
        this.itemNo = itemNo;
        this.splitNo = splitNo;
    }

    public OrderNoInfo(String orderNo, String itemNo, Integer splitNo) {
        this.orderNo = orderNo;
        this.itemNo = Integer.valueOf(itemNo);
        this.splitNo = splitNo;
    }

    public OrderNoInfo() {
    }

    public OrderNoInfo setSplitNoNotNull(Integer splitNo) {
        this.splitNo = Optional.ofNullable(splitNo).orElse(0);
        return this;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public OrderNoInfo setOrderNo(String orderNo) {
        this.orderNo = orderNo;
        return this;
    }

    public Integer getItemNo() {
        return itemNo;
    }

    public OrderNoInfo setItemNo(Integer itemNo) {
        this.itemNo = itemNo;
        return this;
    }

    public OrderNoInfo setItemNo(String itemNo) {
        this.itemNo = Integer.valueOf(itemNo);
        return this;
    }

    public Integer getSplitNo() {
        return splitNo;
    }

    public OrderNoInfo getRorderFno() {
        return new OrderNoInfo(orderNo, itemNo, 0);
    }

    public static OrderNoInfo getFromPco(OpsPco pco, OpsPcoItem pcoItem) {
        return new OrderNoInfo().setOrderNo(pco.getOrderId()).setItemNo(pco.getOrderItem())
                .setSplitNoNotNull(pcoItem.getPcoItem());
    }
    public static OrderNoInfo getFromPco(OpsPco pco, OpsPcoItemInventory pcoItem) {
        return new OrderNoInfo().setOrderNo(pco.getOrderId()).setItemNo(pco.getOrderItem())
                .setSplitNoNotNull(pcoItem.getPcoItem());
    }

    public static OrderNoInfo getFromTKCK(OpsDo opsDo) {
        return new OrderNoInfo().setOrderNo(opsDo.getOrderId()).setItemNo(opsDo.getOrderItem());
    }

    public static OrderNoInfo getFromItemInv(OpsPcoItemInventory pcoItemInventory) {
        return new OrderNoInfo().setOrderNo(pcoItemInventory.getPcoId()).setItemNo(pcoItemInventory.getPcoItem())
                .setSplitNoNotNull(0);
    }

    public static OrderNoInfo getFromItemInv(OpsDoItemInventory doItemInventory) {
        return new OrderNoInfo().setOrderNo(doItemInventory.getDoId()).setItemNo(1).setSplitNoNotNull(0);
    }

    public OrderNoInfo setSplitNo(Integer splitNo) {
        this.splitNo = splitNo;
        return this;
    }

    public static OrderNoInfo getFromJYCK(OpsDo jyck) {
        OrderNoInfo orderNoInfo = new OrderNoInfo().setOrderNo(jyck.getOrderId()).setItemNo(jyck.getOrderItem());
        if (DoSourceEnum.ASSModelNo.getType().equals(jyck.getDoSource())) {
            return orderNoInfo.setSplitNo(null);
        } else {
            return orderNoInfo.setSplitNo(jyck.getNum());
        }
    }

    public static OrderNoInfo getFromDo(OpsDo opsDo) {
        OrderNoInfo orderNoInfo = new OrderNoInfo().setOrderNo(opsDo.getOrderId()).setItemNo(opsDo.getOrderItem());
        if (DoTypeEnum.JYCK.getType().equals(opsDo.getDoType())) {
            // 如果型号拆分，则代表所有的子型号，为null
            if (DoSourceEnum.isAssModel(opsDo.getDoSource())) {
                orderNoInfo.setSplitNo(null);
            } else {
                orderNoInfo.setSplitNo(opsDo.getNum());
            }
        } else if (DoTypeEnum.isDB(opsDo.getDoType())) {// 调拨类型
            // 如果型号拆分，要读取assnum
            if (DoSourceEnum.isAssModel(opsDo.getDoSource())) {
                orderNoInfo.setSplitNo(opsDo.getAssNum());
            } else {
                orderNoInfo.setSplitNo(opsDo.getNum());
            }
        } else {// 其他类型
            orderNoInfo.setSplitNo(opsDo.getNum());
        }
        return orderNoInfo;
    }

    public static OrderNoInfo getFromRequest(OpsRequestpurchase request) {
        return new OrderNoInfo().setOrderNo(request.getOrderno()).setItemNo(request.getItemno())
                .setSplitNoNotNull(request.getSplititemno());
    }

    public static OrderNoInfo getFromPurchase(OpsPurchaseorder purchase) {
        return new OrderNoInfo().setOrderNo(purchase.getOrderno()).setItemNo(purchase.getItemno())
                .setSplitNoNotNull(purchase.getSplititemno());
    }

    // bug12769采购返信提醒
    public static OrderNoInfo getFromPurchaseReply(PoReplyInfoDto purchase) {
        return new OrderNoInfo().setOrderNo(purchase.getPoOrderNo()).setItemNo(purchase.getPoItemNo())
                .setSplitNoNotNull(purchase.getPoSplitItemNo());
    }

    // bug12769采购返信延期提醒
/*
    public static OrderNoInfo getFromPurchaseDelay(PoDelayInfoDto purchase) {
        return new OrderNoInfo().setOrderNo(purchase.getPoOrderNo()).setItemNo(purchase.getPoItemNo())
                .setSplitNoNotNull(purchase.getPoSplitItemNo());
    }
*/

    public static OrderNoInfo getFromMove(OpsInventoryMove move) {
        return new OrderNoInfo().setOrderNo(move.getOrderno()).setItemNo(move.getItemno())
                .setSplitNoNotNull(move.getSplititemno());
    }

    public static OrderNoInfo getAssociateNoFromMove(OpsInventoryMove move) {
        return new OrderNoInfo().setOrderNo(move.getAssociateNo()).setItemNo(move.getAssociateNoItem())
                .setSplitNoNotNull(move.getAssociateNoSplitno());
    }

    public static OrderNoInfo getFromTKRK(OpsRo opsRo) {
        return new OrderNoInfo().setOrderNo(opsRo.getOrderId()).setItemNo(opsRo.getOrderItem());
    }

    // 三段拼接，为空或0则不继续拼
    public String getFullNo() {
        if (splitNo != null && splitNo != 0) {
            return String.join("-", orderNo, itemNo.toString(), splitNo.toString());
        } else if (itemNo != null && itemNo != 0) {
            return String.join("-", orderNo, itemNo.toString());
        }
        return orderNo;
    }

    public Integer getSplitNoNotNull() {
        return Optional.ofNullable(splitNo).orElse(0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderNoInfo)) return false;
        OrderNoInfo that = (OrderNoInfo) o;
        return Objects.equals(getOrderNo(), that.getOrderNo()) && Objects.equals(getItemNo(), that.getItemNo()) && Objects.equals(getSplitNo(), that.getSplitNo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOrderNo(), getItemNo(), getSplitNo());
    }
}
