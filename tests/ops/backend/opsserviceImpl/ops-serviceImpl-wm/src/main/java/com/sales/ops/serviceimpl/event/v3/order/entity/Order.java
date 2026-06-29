package com.sales.ops.serviceimpl.event.v3.order.entity;

import com.sales.ops.db.entity.Rcvdetail;
import com.sales.ops.db.entity.Rcvmaster;
import com.sales.ops.enums.CKTYPEEnum;
import com.sales.ops.enums.InventoryTypeEnum;
import com.sales.ops.enums.OrderStockTypeEnum;
import com.sales.ops.enums.RcvOrderStatusEnum;
import com.sales.ops.serviceimpl.event.v3.status.entity.JYCKStatus;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Data
public class Order {

    private String orderFno;
    private String orderId;
    private Integer orderItem;
    private String orderType;

    private String modelno;
    private Integer quantity;

    private Short status;
    private RcvOrderStatusEnum orderStatusEnum;
    private OrderStockTypeEnum stockTypeEnum;
    private String stockCode;
    private InventoryTypeEnum inventoryTypeEnum;

    private String prodFlag;
    private Date AllotTime;

    private Integer readyQty;
    private Date readyTime;
    private Date esDeliveryTime;

    private Boolean intercept;
    private Date interceptTime;

    private Integer expQty;
    private Date expTime;

    private String carrierId;
    private String expressNo;
    private Date shipTime;

    private Integer returnedQty;
    private Integer invoicedQty;
    private Date invoiceTime;

    // 发货方式
    private CKTYPEEnum dlvEntire;
    // 预计送达日（默认为空，不加载）
    private Date estimatedDeliveryDay;


    private List<JYCK> jycks;
    private List<JYCKStatus> jyckStatusList;





    public Order(Rcvmaster rcvmaster, Rcvdetail rcvdetail) {
        this.dlvEntire = CKTYPEEnum.getCode(rcvmaster.getDlvEntire());
        this.orderType = Optional.ofNullable(rcvdetail.getOrderType()).orElse((short) 0).toString();
        this.orderFno = rcvdetail.getRorderFno();
        this.orderId = rcvdetail.getRorderNo();
        this.orderItem = rcvdetail.getRorderItem();
        this.modelno = rcvdetail.getModelNo();
        this.quantity = rcvdetail.getQuantity();
        this.prodFlag = rcvdetail.getProdFlag();
        this.status =  rcvdetail.getStatus();
        this.readyTime = rcvdetail.getReadyTime();
        this.intercept = Optional.ofNullable(rcvdetail.getIntercept()).orElse(false);
        this.interceptTime = rcvdetail.getInterceptTime();
        this.expQty = rcvdetail.getExpQty();
        this.expTime = rcvdetail.getExpTime();
        this.carrierId = rcvdetail.getCarrierid();
        this.expressNo = rcvdetail.getExpressno();
        this.shipTime = rcvdetail.getShipTime();
    }

    public String getStockType(){
        return stockTypeEnum == null ? null : stockTypeEnum.getType();
    }
    public String getInventoryType(){
        return inventoryTypeEnum == null ? null : inventoryTypeEnum.getType();
    }




}
