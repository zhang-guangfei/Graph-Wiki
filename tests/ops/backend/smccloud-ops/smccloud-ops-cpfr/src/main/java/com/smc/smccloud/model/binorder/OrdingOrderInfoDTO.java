package com.smc.smccloud.model.binorder;

import lombok.Data;

import java.util.Date;

@Data
public class OrdingOrderInfoDTO {
    private Long binDetailId;
    //订单类型0:采购,1:调拨,2:退货
    private String orderType;
    private String orderNo;
    private Integer itemNo;
    private Integer splitNo;
    private String modelNo;
    private String warehouseCode;
    //库存数量
    private Integer quantity;
    private Integer prepareQuantity;
    //下单日期
    private Date orderDate;
    //交货器
    private Date expDate;
    //供应商
    private String supplier;
    //运输方式
    private String transType;
    //已入库数量
    private Integer inQty;

    public String  getOrderKey(){
        return getOrderType()+"-"+getOrderNo()+"-"+getItemNo()+"-"+getSplitNo();
    }
}
