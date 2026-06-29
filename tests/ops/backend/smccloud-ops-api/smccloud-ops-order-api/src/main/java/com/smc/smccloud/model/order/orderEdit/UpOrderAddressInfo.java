package com.smc.smccloud.model.order.orderEdit;

import com.smc.smccloud.model.adapter.order.DeliveryAddressInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author lyc
 * @Date 2022/10/20 14:51
 * @Descripton 变更发货地址实体
 */
@Data
public class UpOrderAddressInfo implements Serializable {

    private static final long serialVersionUID = -1242493206302174690L;

    // 订单号(10位)
    @ApiModelProperty(value = "订单号")
    private String orderNo;

    // 操作人
    private String loginUserId;

    // 收货地址信息实体
    private DeliveryAddressInfo address;
}
