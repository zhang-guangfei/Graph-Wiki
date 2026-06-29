package com.smc.smccloud.model.csstock;

import com.smc.smccloud.core.model.page.BaseQuery;
import com.smc.smccloud.core.model.page.Page;
import lombok.Data;

/**
 * @Author lyc
 * @Date 2022/4/13 10:16
 * @Descripton
 */
@Data
public class CalcReturnRequest extends BaseQuery {

    private String agentNo;

    private String warehouseCode;

    private String modelNo;

    private Integer applyType;

    private Boolean overTimeQty;
}
