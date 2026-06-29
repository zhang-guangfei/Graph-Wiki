package com.smc.smccloud.model.binorder;

import com.smc.smccloud.core.model.page.BaseQuery;
import lombok.Data;

import java.util.Date;

/**
 * @Author edp02 @Date 2021/11/10 15:02
 */
@Data
public class BinOrderAppRequestDTO extends BaseQuery {
    private Long appId;
    private Long calcId;
    private String warehouseCode;
    private Date fromdate;
    private Date todate;
    private Integer status;
    private String customerNo;
    private Long propertyID;
    private Integer applyType;
}
