package com.smc.smccloud.model.adapter.order;

import com.smc.smccloud.model.order.SelectCommonEntitty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Author lyc
 * @Date 2024/8/13 10:46
 * @Descripton TODO
 */
@Data
public class UpOrderInfoVO {

    // 能否可更改货期
    private Boolean canChangeDeliveryDate = false;

    // 最晚客户货期
    private Date latestDeliveryDate;

    // 能否可以变更普通特发
    private Boolean canUpSpec = false;

    // 能否变更承运商
    private Boolean canUpCarrier = false;

    private List<SelectCommonEntitty> carrierList;

    // 能否变更收货地址
    private Boolean canUpAddress = false;

    // 能否子项特发
    private Boolean canUpSpecItem = false;

    // 能否组装指令
    private Boolean canUpAssemblyInstruction = false;



}
