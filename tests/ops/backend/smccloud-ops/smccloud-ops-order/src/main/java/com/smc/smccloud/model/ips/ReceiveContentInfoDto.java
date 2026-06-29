package com.smc.smccloud.model.ips;

import lombok.Data;

/**
 * @description: 附加信息，contentinfo 字段
 * @author: B91717
 * @time: 2024/11/4 18:30
 */
@Data
public class ReceiveContentInfoDto {
    private ProductInfoDto productInfo;

    private PackageInfoDto packageInfo;

    private DeliveryInfoDto deliveryInfo;

    private CustomerInfoDto customerInfo;

    private EnduserInfoDto enduserInfo;

    private CancelInfoDto cancelInfo;

    private AssembleOrderDto assembleOrder;

    private  String drawingNo; // // 图纸版本号,新采购系统

}