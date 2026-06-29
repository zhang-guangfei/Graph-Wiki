package com.smc.smccloud.model.receiveorder;

import lombok.Data;

/**
 * @Author lyc
 * @Date 2022/2/24 13:10
 * @Descripton TODO
 */
@Data
public class RcvDetailDTO {
    private String rorderFno; // 完整订单号
    private Integer status; // 状态
    private Integer isCancel; // 是否可以取消 0 否 1 可以
    private Integer isUpdate; // 是否可以修改 0 否 1 可以
}
