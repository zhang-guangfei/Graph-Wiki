package com.smc.smccloud.model.csstock;

import lombok.Data;

/**
 * 查询代理点库存信息
 *
 * @author wsf
 * @version 1.0
 * @date 2022/1/7 10:58
 */
@Data
public class CsInventoryVO {

    // 仓库号
    private String warehouseCode;
    private String modelNo;
    private String warehouseName;
    private Integer quantity;
    private String customerNo;
    private String customerName;

    private String userNo;
    private String userName;

    private String bomNo;
    private String projectNo;
    // 仓位号(与仓库号一致)
    private String cwcode;

    // 在库数
    private Integer onHouseNum;

    // 预约数
    private Integer appointmentNum;

    private Integer orderQuantity;


    private Integer prepareQuantity;

}
