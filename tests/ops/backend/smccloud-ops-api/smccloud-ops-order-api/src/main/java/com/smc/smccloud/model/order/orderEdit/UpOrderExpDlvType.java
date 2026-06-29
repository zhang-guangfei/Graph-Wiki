package com.smc.smccloud.model.order.orderEdit;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author lyc
 * @Date 2022/10/20 16:08
 * @Descripton 变更特发实体
 */
@Data
public class UpOrderExpDlvType implements Serializable {

    private static final long serialVersionUID = -1242493206302174690L;

    // 订单号(10位)
    @ApiModelProperty(value = "订单号")
    private String orderNo;

    // 操作人
    private String loginUserId;

    // 特发/普通
    private String specialNormal;

}
