package com.smc.smccloud.model.ips;

import lombok.Data;

/**
 * @description: // 包装信息
 * "packType" : "", //网筐类型
 * "boxType":"",//外箱箱型
 * "boxFixedQty":"",//外箱装箱数量
 * "packStyle": "",//捆包代码
 * "mediumBoxModel: "",//中箱箱型
 * "mediumBoxFixedQuantity: "",//中箱箱型数量
 * "smallBoxModel: "",//小箱箱型
 * "smallBoxFixedQuantity: ""//小箱箱型数量
 * @author: B91717
 * @time: 2024/11/4 18:32
 */
@Data
public class PackageInfoDto {
    private String packType;
    private String boxType;
    private Integer boxFixedQty;
    private String packStyle;
    private String mediumBoxModel;
    private Integer mediumBoxFixedQuantity;
    private String smallBoxModel;
    private Integer smallBoxFixedQuantity;
}
