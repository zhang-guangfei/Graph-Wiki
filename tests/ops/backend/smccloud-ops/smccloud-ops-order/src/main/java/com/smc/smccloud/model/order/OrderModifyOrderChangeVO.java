package com.smc.smccloud.model.order;

import com.sales.ops.dto.order.OrderChangeToInitStatusDto;
import lombok.Data;

/**
 * @Author lyc
 * @Date 2023/8/7 12:01
 * @Descripton TODO
 */
@Data
public class OrderModifyOrderChangeVO extends OrderChangeToInitStatusDto {
    private String batchNo;
    private String optUserNo;
    private String applyNo;
    // 0 处理失败 1 处理成功
    private String handStatus = "0";

    private String handDesc;
}
