package com.smc.smccloud.model.invoice;

import lombok.Data;

import java.util.Date;

/**
 * 修改预计到达时间
 *
 * @author wsf
 * @version 1.0
 * @date 2021/12/2 15:35
 */
@Data
public class ImpInvoiceArriveDateRequest {

    /**
     * 到达类型，1预计到达 2实际到达
     */
    private Integer arriveFlag;
    private Integer id;
    private String invoiceNo;
    /**
     * 预计到达
     */
    private Date preArriveDate;
    /**
     * 实际到达
     */
    private Date arriveDate;
    private Integer status;
    private String remark;
}
