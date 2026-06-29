package com.smc.smccloud.model.expmodel;

import lombok.Data;

/**
 * @author edp04
 * @title: model_exp_detail
 * @date 2022/05/11 12:15
 */
@Data
public class ExpModelDetailVO {

    private String id;

    private String warehouseCode;

    private String deptNo;

    private String modelNo;

    private String customerNo;

    private String monthDate;

    private String qty;

    private String updateTime;

    private String orderNumber;

    private String assType;

    private String agentNo;

    private String subWarehouseCode;

    private String bomId;

    private String orderLine;

    private String tradeCompany;

}
