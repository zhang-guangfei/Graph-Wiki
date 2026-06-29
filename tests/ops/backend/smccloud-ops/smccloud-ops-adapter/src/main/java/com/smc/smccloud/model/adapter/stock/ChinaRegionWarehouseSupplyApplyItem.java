package com.smc.smccloud.model.adapter.stock;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel(description = "专备申请子表实体")
public class ChinaRegionWarehouseSupplyApplyItem implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 4989738131583779867L;


    @ApiModelProperty(value = "申请号")
    private String id;

    @ApiModelProperty(value = "申请明细ID")
    private int itemId;

    @ApiModelProperty(value = "型号")
    private String modelNo;

    @ApiModelProperty(value = "申请数量")
    private int applyQuantity;

    @ApiModelProperty(value = "E价格")
    private BigDecimal ePrice;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "审批数量")
    private int auditQuantity;

    @ApiModelProperty(value = "订单号")
    private String orderNo;

    @ApiModelProperty(value = "bomNo")
    private String bomNo;

    @ApiModelProperty(value = "项目代码")
    private String projectCode;

    @ApiModelProperty("rohs10")
    private Boolean rohs10;

    @ApiModelProperty("期望货期")
    private Date hopeDeliveryDate;

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