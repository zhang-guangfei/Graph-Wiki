package com.smc.smccloud.model.order.orderEdit;

import lombok.Data;

/**
 * @Author lyc
 * @Date 2024/1/11 16:12
 * @Descripton TODO
 */

/**
 * {
 *                 "orderNo":"11270052-1",
 *                 "status":"成功",
 *                 "statusDescription":""
 *             },
 *             {
 *                 "orderNo":"11270052-2",
 *                 "status":"失败",
 *                 "statusDescription":"失败原因"
 *             }
 */
@Data
public class UpOrderDlvDateVo {
    private String orderNo;
    private String status;
    private String statusDescription;
}
