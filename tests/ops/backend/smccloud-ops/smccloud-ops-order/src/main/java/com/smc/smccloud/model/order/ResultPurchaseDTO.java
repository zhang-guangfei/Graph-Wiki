package com.smc.smccloud.model.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@EqualsAndHashCode(callSuper = false)
public class ResultPurchaseDTO {

    private String orderId;
    private String orderItem;
    private String modelno;
    private Integer quantity;
    private String stockType;
    private String stockCode;
    private String inventoryTypeCode;
    private String orderNo;
    private Integer itemNo;
    private Integer splitItemNo;
    private String poStateCode;
    private String invoiceNo;
    private String supplierNo;
    private String supplierOrderNo;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM")
    @DateTimeFormat(pattern = "yyyy-MM")
    private String deliveryTime;
    private String binflag;


}
