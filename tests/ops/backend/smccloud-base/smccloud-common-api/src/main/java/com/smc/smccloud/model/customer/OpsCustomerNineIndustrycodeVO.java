package com.smc.smccloud.model.customer;

import lombok.Data;

import java.util.Date;

/**
 * @Author lyc
 * @Date 2022/8/4 12:17
 * @Descripton TODO
 */
@Data
public class OpsCustomerNineIndustrycodeVO {

    private static final long serialVersionUID = 1L;

    /**
     * 客户代码
     */
    private String CustomerNo;

    /**
     * 序号
     */
    private Integer OrderNumber;

    /**
     * 三位行业代码
     */
    private String IndustryMediamCode;

    /**
     * 四位行业代码
     */
    private String Industry4;

    /**
     * 五六位行业代码
     */
    private String Industry56;

    /**
     * 权重占比
     */
    private Integer IndustryPercent;

    private String CreatedUser;

    private Date UpdatedTime;

    private Date CreatedTime;

    private String UpdatedUser;

}
