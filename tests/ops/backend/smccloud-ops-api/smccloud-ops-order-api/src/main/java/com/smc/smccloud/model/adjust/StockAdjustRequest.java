package com.smc.smccloud.model.adjust;

import com.smc.smccloud.core.model.page.BaseQuery;
import lombok.Data;

import java.util.Date;

@Data
public class StockAdjustRequest extends BaseQuery {

    private String invoiceNo;

    private String fullOrderNo;

    private String modelNo;

    private Integer optCode;

    private Integer adjustType;

    private Date startTime;

    private Date endTime;
}
