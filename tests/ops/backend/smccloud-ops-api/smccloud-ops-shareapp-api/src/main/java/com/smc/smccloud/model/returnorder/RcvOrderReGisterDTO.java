package com.smc.smccloud.model.returnorder;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @Author lyc
 * @Date 2022/4/22 15:08
 * @Descripton TODO
 */
@Data
public class RcvOrderReGisterDTO {

    @NotEmpty
    private String applyNo; // 申请号
    private Date dlvDate; // 收货日期
    private Boolean isRcvTogether; // 整单接收
    private Boolean isAssOrder; // 组装订单
    @NotEmpty
    private String orderNo;
    @NotEmpty
    private String modelNo;

    @NotNull
    private Integer applyNumber; // 申请数量
    private Integer goodNumber; // 良品数量
    private Integer badNumber; // 坏品数量
    private String expressNo; // 快递单号

    private String warehouseCode;
    private String requestWarehouseCodeName;

    private Long id;

    private String locationNo;


}
