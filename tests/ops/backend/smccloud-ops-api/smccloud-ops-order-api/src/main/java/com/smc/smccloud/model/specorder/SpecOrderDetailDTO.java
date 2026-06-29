package com.smc.smccloud.model.specorder;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class SpecOrderDetailDTO {

    private Integer id;

    /**
     * 型号
     */
    private String modelNo;

    /**
     * 数量
     */
    private Integer quantity;

    /**
     * 出库货币单价
     */
    private BigDecimal orgPrice;

    /**
     * 出口货币
     */
    private String orgCurrency;

    /**
     * 单价
     */
    private BigDecimal price;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 交货期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date dlvDate;

    /**
     * 制造指定出荷日
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date manuDlvDate;

    /**
     * 项号
     */
    private Integer orderItem;

    /**
     * 客户型号
     */
    private String cproductNo;

    private BigDecimal eprice;//E价

    private String remark;

    private String complaintNo;

    private String transType;

    private String specMark;

}
