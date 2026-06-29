package com.smc.smccloud.model.VSalesManuorder;

import lombok.Data;

import java.util.Date;

/**
 * @Author lyc
 * @Date 2022/4/2 15:45
 * @Descripton TODO
 */
@Data
public class OPSVRequisitionStausOtherToSalesVO
{
    private Long IssueID;
    private String customerNo;
    private String orderNo;
    private String itemNo;
    private String modelNo;
    private Integer quantity;
    private Date poShipDate;
    private String calReason;
    private Date updateDate;
    private String status;
    private String orderType;

}
