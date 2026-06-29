package com.smc.smccloud.model.adapter.order;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.smc.smccloud.model.order.OrderNoInfo;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author wuweidong
 * @create 2022/11/4 10:20
 * @description
 */
@Data
public class POOrderNOVO {
    public POOrderNOVO(){

    }
    /**
     * 关务订单号
     */
    private  String orderNo;

    /**
     * 关务项号
     */
    private  String orderItem;
    /**
     * 采购单
     */
    private String poNo;
    /**
     * 采购项
     */
    private int lineItem;

    private String modelNo;
    private int quantity;
    /**
     * 剩余入库数量= quantity-qtyImport
     */
    private int leftQuantity;
    private String stateCode;
    /**
     * 供应商
     */
    private String supplierId;
    /**
     * 收货仓库
     */
    private String receiveWarehouseId;
    /**
     * 采购日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date purchaseDate;

 public POOrderNOVO(String orderNO,String orderItem)
    {
        this.orderNo=orderNO;
        this.orderItem=orderItem;
        this.convertPOorderNo();
    }
    public String convertPOorderNo() {
        OrderNoInfo orderNoInfo=new OrderNoInfo().convertJPOrder(this.orderNo,this.orderItem);
      this.poNo=orderNoInfo.getPoNo();
      this.lineItem=orderNoInfo.getPoItemNo();
      return  orderNoInfo.getFullOrderNo();
    }


}
