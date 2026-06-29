package com.smc.smccloud.model.prestock;

import lombok.Data;

import java.util.List;

/**
 * Author: B90034
 * Date: 2021-11-22 10:36
 * Description:
 */
@Data
public class PreStockResultVO {

    /**
     * prestock_apply.id
     */
    private Long applyId;

    /**
     * prestock_detail.id
     */
    private Long fromId;

    /**
     * 型号
     */
    private String modelNo;

    /**
     * 总数量
     */
    private Integer quantity;

    /**
     * BIN数量
     */
    private Integer qtyBin;

    /**
     * 库存类型
     */
    private String inventoryType;

    /**
     * 频率(bindata.freq: 在历史36个月内的,有下单的月数)
     */
    private Integer freq;

    /**
     * 用量(bindata.mean: 每个月的平均下单数量)
     */
    private Integer mean;
    /**
     * 最小包装数量
     */
    private Integer minPackageQty;
    /**
     * 申请型号每月备库明细
     */
    private List<PreStockApplyMonthlyDetail> monthlyDetails;

    /**
     * 申请型号可出库库房情况
     */
    private List<PreModelStockInfo> inventoryInfos;

}
