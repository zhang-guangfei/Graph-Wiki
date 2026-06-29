package com.smc.smccloud.model.receiveorder;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class OrderInvoiceVO {

    private String invoiceNo;//发票号

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date invoiceDate;//开票日期

    private String modelNo;

    private Integer quantity;

    private String rorderNo;

    private Integer rorderItem;

    private String rorderFno;
}
