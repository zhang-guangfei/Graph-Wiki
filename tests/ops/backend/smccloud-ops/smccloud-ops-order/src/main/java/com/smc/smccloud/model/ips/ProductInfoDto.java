package com.smc.smccloud.model.ips;

import lombok.Data;

/**
 * @description:
 * @author: B91717
 * @time: 2024/11/4 18:32
 */
@Data
public class ProductInfoDto {
    // {“普通品标识：是否”，，"3C产品标识"，"化学品标识","法检品标识"，"冷干机、温控器、干燥机"},
    private String rohsCode; // greenMark，绿标 环保标识：H;G;P（非空且不在标识清单内时，退单）

    private String hscode; // hscode

    private String eac;

//    private String ordinaryProductFlag;
//    private String cCProductFlag;
//    private String chemicalProductFlag;
//    private String lawInspectionProductFlag;
//    private String specialEquipment;
}
