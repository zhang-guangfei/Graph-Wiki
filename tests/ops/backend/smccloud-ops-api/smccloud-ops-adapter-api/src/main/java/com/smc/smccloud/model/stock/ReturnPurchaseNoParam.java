package com.smc.smccloud.model.stock;

import lombok.Data;

/**
 * Author: B90034
 * Date: 2022-07-21 09:25
 * Description:
 */
@Data
public class ReturnPurchaseNoParam {

    /**
     * 申请号
     */
    private String applyNo;

    /**
     * 类型: 1-在库补库ZK, 2-分库补库FK, 3-委托在库WT
     */
    private Integer type;

    /**
     * 项号
     */
    private Integer itemNo;

    /**
     * 采购单号（多项以','拼接）
     */
    private String purchaseNo;

    /**
     * 调拨单号（多项以','拼接）
     */
    private String transferNos;

    /**
     * 预占单号
     */
    private String prepareOrderNos;
}
