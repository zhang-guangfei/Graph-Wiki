package com.smc.smccloud.model.adapter.order;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderDetailVO {

    private String order_no;

    private BigDecimal price;

    private String model_no;

    private int quantity;

    private Date order_date;

    private String cmodel_no;

    private String corder_no;

    private String user_no;

    private String exp_stock;

    private Date csend_date;

    private String remark;

    private BigDecimal tax_rate;

    private Date ship_date;

    private String status_desc;

    private String status;

    private String order_psn;

    private String express_no;

    private String model_name;

    private BigDecimal eprice;

    private BigDecimal agent_salesprice;

    private String projectNo;

    private String endUserNo;

    private String endUserName;
}
