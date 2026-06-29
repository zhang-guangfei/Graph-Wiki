package com.sales.ops.serviceimpl.event.v3.status.entity;

import com.sales.ops.db.entity.OrderStatus;
import com.sales.ops.db.entity.OrderStatusItem;
import com.sales.ops.serviceimpl.event.v3.order.entity.JYCK;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class JYCKStatus {

    private JYCK jyck;

    private OrderStatus orderStatus;
    private List<RelationStatus> relationStatusList;

    public JYCKStatus(JYCK jyck) {
        this.jyck = jyck;
        createOrderStatus();
        this.relationStatusList = new ArrayList<>();
    }

    private void createOrderStatus() {
        this.orderStatus = new OrderStatus();
        orderStatus.setOrderId(jyck.getOrderId());
        orderStatus.setOrderItem(jyck.getOrderItem());
        orderStatus.setSplitNo(jyck.getNum());
        orderStatus.setPcoItem(jyck.getAssNum());
        orderStatus.setSplitType(jyck.getDoSource().splitType().getSplitType());
        orderStatus.setWarehouseCode(jyck.getWarehouseCode());
        orderStatus.setModelno(jyck.getModelno());
        orderStatus.setWmOrderId(jyck.getJyDoId());
        orderStatus.setQty(jyck.getQty());
        orderStatus.setQtyIn(jyck.getReadyQty());
        orderStatus.setQtyOut(jyck.getOutQty());
        orderStatus.setWlDate(jyck.getWlDate());
        orderStatus.setCreTime(new Date());
        orderStatus.setCreator("OrderStatus生成器");
        orderStatus.setModifyTime(new Date());
        orderStatus.setModifier("OrderStatus生成器");
    }


    public void addRelationStatus(RelationStatus relationStatus){
        this.relationStatusList.add(relationStatus);
    }


    public List<OrderStatusItem> getOrderStatusItem(){
        return this.relationStatusList.stream().map(item -> item.getOrderStatusItem()).collect(Collectors.toList());
    }

}
