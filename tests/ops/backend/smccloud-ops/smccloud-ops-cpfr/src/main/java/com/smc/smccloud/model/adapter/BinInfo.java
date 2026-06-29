package com.smc.smccloud.model.adapter;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "bin信息")
public class BinInfo implements Serializable {

    private static final long serialVersionUID = 4468801280164800983L;

    @ApiModelProperty(value = "型号")
    private String modelNo;

    @ApiModelProperty(value = "库房代码")
    private String stockCode;

    @ApiModelProperty(value = "客户代码")
    private String customerNo;

    @ApiModelProperty(value = "BIN数量")
    private String qtyBin;

    @ApiModelProperty(value = "BIN数")
    private String binCell;

    @ApiModelProperty(value = "库房名称")
    private String stockName;

    // 安全在库值
    private Integer safeQty;

}
