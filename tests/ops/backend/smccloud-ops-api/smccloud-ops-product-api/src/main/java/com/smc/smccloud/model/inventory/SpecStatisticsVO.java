package com.smc.smccloud.model.inventory;

import com.smc.smccloud.core.model.adapter.DataAuthoritySearchCondition;
import lombok.Data;

/**
 * @Author lyc
 * @Date 2022/10/26 10:43
 * @Descripton TODO
 */
@Data
public class SpecStatisticsVO {

    // 仓库代码
    private String warehouseCode;

    // 仓库名称
    private String warehouseCodeName;

    // 营业所
    private String deptNo;

    private String deptName;

    private String modelNo;

    // 有效在库数量
    private int spValidQty;

    // 待入库数量
    private int waitInQty;

    // 运输中数量
    private int transportQty;

    // 生产在途数量
    private int productQty;

    // 订货中数量
    private int canOrderQty;

    // 库存状态
    private String inventoryStatus;

    private String customerNo;

    private String customerName;

    // 权限实体
    private DataAuthoritySearchCondition dataAuthoritySearchCondition;



}
