package com.smc.smccloud.model.trans;

import com.smc.smccloud.core.model.page.BaseQuery;
import lombok.Data;

import java.util.Date;

@Data
public class TransOrderRequest extends BaseQuery {

    private String transNo;

    private Integer transType;

    private String modelNo;

    private String fromNo;

    private Integer fromType;

    private Integer status;

    private String warehouseCode;
    private Integer warhouseType;

    private Integer dateType;
    private Date beginDate;
    private Date endDate;
}
