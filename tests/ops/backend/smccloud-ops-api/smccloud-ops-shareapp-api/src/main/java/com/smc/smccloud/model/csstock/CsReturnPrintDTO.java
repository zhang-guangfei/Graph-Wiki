package com.smc.smccloud.model.csstock;

import lombok.Data;

/**
 * @author edp04
 * @title: CsReturnPrintDTO
 * @date 2022/05/18 11:10
 */
@Data
public class CsReturnPrintDTO {
    //订单号
    private String orderNo;
    private String modelNo;
    // 退货数量
    private Integer returnQty;
    private String remark;
}
