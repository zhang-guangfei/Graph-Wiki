package com.smc.smccloud.model.adapter.stock;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel(description = "在库补库申请子表实体")
public class BinDataApplyItem implements Serializable {

    private static final long serialVersionUID = -1757370566057705787L;

    @ApiModelProperty(value = "申请号")
    private String id;

    @ApiModelProperty(value = "申请明细ID")
    private int itemId;

    @ApiModelProperty(value = "型号")
    private String modelNo;

    @ApiModelProperty(value = "发注点(需求中未提及)")
    private Integer binUnitQuantity;

    @ApiModelProperty(value = "申请数量")
    private int applyQuantity;

    @ApiModelProperty(value = "申请价格(需求中未提及)")
    private BigDecimal applyPrice;

    @ApiModelProperty(value = "E价格")
    private BigDecimal ePrice;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "预测订单号")
    private String preSaleOrderId;

    @ApiModelProperty(value = "是否有日本特价(需求中未提及)，默认写为0")
    private String japanSpecialPrice;

    @ApiModelProperty(value = "特价id(需求中未提及)")
    private String specialPriceId;

    @ApiModelProperty(value = "审批数量")
    private int auditQuantity;

    @ApiModelProperty(value = "审批bin数量")
    private Integer auditBinUnitQuantity;

    @ApiModelProperty(value = "订单号")
    private String orderNo;

    @ApiModelProperty(value = "是否为组装拆分产品")
    private Boolean isAssemblyProduct;

    @ApiModelProperty(value = "k90在库(需求中未提及)")
    private int binDataInK90;

    @ApiModelProperty(value = "k30在库(需求中未提及)")
    private int binDataInK30;

    @ApiModelProperty(value = "在库数量(需求中未提及)")
    private int stockQuantity;

    @ApiModelProperty(value = "客户在库数量(需求中未提及)")
    private int customerStockQuantity;

    @ApiModelProperty(value = "历史销售数量(需求中未提及)")
    private int hisSaleQuantity;

    @ApiModelProperty(value = "历史销售频率(需求中未提及)")
    private int hisSaleFrequency;

    @ApiModelProperty(value = "设备BOM号(PPL号)")
    private String bomNo;

    @ApiModelProperty(value = "项目号")
    private String projectCode;

    @ApiModelProperty(value = "期望货期")
    private Date hopeDeliveryDate;

    @ApiModelProperty("rohs10")
    private Boolean rohs10;

    /**
     * 取值: 正常, 板, 阀
     */
    @ApiModelProperty("组装标识")
    private String assemble;

    /**
     * 取值: 空运, 海运, 快船
     */
    @ApiModelProperty("运输方式")
    private String transType;

}