package com.smc.smccloud.model.adapter.stock;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author wuweidong
 * @create 2024/1/8 13:33
 * @description
 */
@Data
@ApiModel(description = "先行在库决算-门户接口类")
public class PreOrderAccountSales {
    @ApiModelProperty(value = "明细ID")
    private Long id;

    @ApiModelProperty(value = "inventoryId")
    private Long inventoryId;

    @ApiModelProperty(value = "整订单号")
    private String orderNo;

    @ApiModelProperty(value = "入库时间")
    private Date roDate;

    @ApiModelProperty(value = "申请决算数")
    private Integer approveCountQty;
    @ApiModelProperty(value = "申请延期数")
    private Integer approveDelayQty;
    @ApiModelProperty(value = "申请延期日期")
    private Integer approveDelayDate;

    @ApiModelProperty(value = "更新状态")
    private Integer countState;

    @ApiModelProperty(value = "更新者")
    private Integer modifier;
    @ApiModelProperty(value = "更新时间")
    private Integer modifyTime;
}
