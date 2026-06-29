package com.smc.smccloud.model.stock;

import lombok.Data;

/**
 * @Description: 回调门户传参实体
 * @Author B98075
 * @Date 2023/08/10
 */
@Data
public class DealReturnOpsParamVO {

    /**
     * 1-战略在库申请
     */
    private Integer applyType;

    private DealReturnOpsParam dealReturnOpsParam;
}
