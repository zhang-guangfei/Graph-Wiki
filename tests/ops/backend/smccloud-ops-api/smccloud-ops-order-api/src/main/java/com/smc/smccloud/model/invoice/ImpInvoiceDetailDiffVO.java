package com.smc.smccloud.model.invoice;

import com.smc.smccloud.core.model.page.BaseQuery;
import lombok.Data;
import java.math.BigDecimal;

/**
 * TODO
 *
 * @author wsf
 * @version 1.0
 * @date 2022/1/6 14:36
 */
@Data
public class ImpInvoiceDetailDiffVO {


    /**
     * 订单号
     */
    private  String orderNo;
    /**
     * 数量
     */
    private  Integer quantity;
    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 分包订单号
     */
    private  String packOrderNo;
    /**
     * 分包数量
     */
    private  Integer packQuantity;
    /**
     * 分包金额
     */
    private BigDecimal packAmount;

}
