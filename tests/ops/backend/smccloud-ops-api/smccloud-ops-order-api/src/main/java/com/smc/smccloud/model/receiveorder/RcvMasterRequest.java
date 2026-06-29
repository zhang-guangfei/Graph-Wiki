package com.smc.smccloud.model.receiveorder;

import lombok.Data;

import java.util.Date;

/**
 * @Author lyc
 * @Date 2022/6/27 14:22
 * @Descripton TODO
 */
@Data
public class RcvMasterRequest {
    private String customerNo;
    private String rorderNo;
    private String purchaseNo;
    private String hlCode;
    private String modelNo;
    private String cproductNo;
    private Date startDate;
    private Date endDate;
    private String strStartDate;
    private String strEndDate;
}
