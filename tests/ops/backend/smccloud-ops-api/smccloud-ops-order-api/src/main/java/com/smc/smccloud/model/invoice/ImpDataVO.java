package com.smc.smccloud.model.invoice;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author edp02 @Date 2022/2/21 16:23
 */
@Data
public class ImpDataVO {

    private Long id;
    /**
     * imp_invoice_master.id
     */
    private Long invoiceId;

    /**
     * 发票号
     */
    private String invoiceNo;
    /**
     * 客户代码
     */
    private String customerNo;

    /**
     *
     */
    private String prodcountry;
    /**
     *
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date impdate;

    private Integer optCode;

    private String rohs;

    private Integer imptype;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 型号
     */
    private String modelNo;

    /**
     * 数量
     */
    private Integer quantity;

    /**
     * 箱号
     */
    private String caseNo;

    /**
     * 条码
     */
    private String barcode;

    private String supplierCode;


    /**
     * 订单类型
     */
    private Integer orderType;

    private String remark;



    private String createUser;


    private String updateUser;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date createTime;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date updateTime;
}
