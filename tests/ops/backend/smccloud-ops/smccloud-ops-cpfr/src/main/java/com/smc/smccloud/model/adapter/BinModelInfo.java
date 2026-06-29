package com.smc.smccloud.model.adapter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel(description = "客户在库补库型号库存及销售等信息")
public class BinModelInfo implements Serializable {

    private static final long serialVersionUID = -3833895043625517297L;

    @ApiModelProperty(value = "型号")
    private String modelNo;

    @ApiModelProperty(value = "北京BIN数")
    private int binQuantityBJ = 0;

    @ApiModelProperty(value = "常州BIN数")
    private int binQuantityCZ = 0;

    @ApiModelProperty(value = "上海BIN数")
    private int binQuantitySH = 0;

    @ApiModelProperty(value = "广州BIN数")
    private int binQuantityGZ = 0;

    @ApiModelProperty(value = "公共在库数")
    private int inventoryQuantity = 0;

    @ApiModelProperty(value = "顾客在库数")
    private int customerQuantity = 0;

    @ApiModelProperty(value = "历史销售额:该型号该客户前十二个月销售额")
    private BigDecimal saleAmount = BigDecimal.ZERO;

    @ApiModelProperty(value = "历史销售数量：前十二个月销售数量")
    private int saleQuantity = 0;

    @ApiModelProperty(value = "销售频率：前十二个月平均销售数量")
    private BigDecimal saleRate = BigDecimal.ZERO;

    @ApiModelProperty(value = "业务建议发注点")
    private Integer applyHairNoteQuantity;

    @ApiModelProperty(value = "业务建议补库数量")
    private Integer qtyOrd;

    @ApiModelProperty(value = "客户代码")
    private String customerNo;

    @ApiModelProperty(value = "客户名称")
    private String customerName;

    /**
     * 设备bom号
     */
    private String bomNo;

    /**
     * 项目号
     */
    private String projectCode;

    /**
     * 库存类型代码
     */
    private String inventoryTypeCode;

    /**
     * 客户群号
     */
    private String groupCustomerNo;

    /**
     * 客户在库在途数
     */
    private int customerInOrderingQuantity;

    /**
     * 在库可用月数
     */
    private int stockCanUseMonthQuantity;

    /**
     * 可用月数
     */
    private int canUseMonthQuantity;

//	/**
//	 * 期望交货期
//	 */
//	private int hopeDeliveryDate;
}