package com.smc.smccloud.model.csstock;

import lombok.Data;

import java.util.Date;

/**
 * 打印月报告
 *
 * @author wsf
 * @version 1.0
 * @date 2022/2/10 10:51
 */
@Data
public class PrintCsBalanceVO {

    /**
     * 型号
     */
    private String modelNo;

    /**
     * 初期数量;
     */
    private Date monthDate;

    /**
     * 预约数
     */
    private Integer openingQty;

    /**
     * 入库数
     */
    private Integer inQty;

    /**
     * 出库数
     */
    private Integer outQty;

    /**
     * 退货数
     */
    private Integer returnQty;

    /**
     * 月末结余数量
     */
    private Integer balanceQty;

    /**
     * 把当前的库存记录下,不用显示出来
     */
    private Integer onhandQty;

    /**
     * 备注
     */
    private String remark;


}
