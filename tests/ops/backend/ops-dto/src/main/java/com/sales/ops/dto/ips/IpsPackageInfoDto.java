package com.sales.ops.dto.ips;

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
public class IpsPackageInfoDto {
    private String packType;
    private String boxType;
    private String boxFixedQty;
    private String packStyle;
    private String mediumBoxModel;
    private Integer mediumBoxFixedQuantity;
    private String smallBoxModel;
    private Integer smallBoxFixedQuantity;

    public String getPackType() {
        return packType;
    }

    public void setPackType(String packType) {
        this.packType = packType;
    }

    public String getBoxType() {
        return boxType;
    }

    public void setBoxType(String boxType) {
        this.boxType = boxType;
    }

    public String getBoxFixedQty() {
        return boxFixedQty;
    }

    public void setBoxFixedQty(String boxFixedQty) {
        this.boxFixedQty = boxFixedQty;
    }

    public String getPackStyle() {
        return packStyle;
    }

    public void setPackStyle(String packStyle) {
        this.packStyle = packStyle;
    }

    public String getMediumBoxModel() {
        return mediumBoxModel;
    }

    public void setMediumBoxModel(String mediumBoxModel) {
        this.mediumBoxModel = mediumBoxModel;
    }

    public Integer getMediumBoxFixedQuantity() {
        return mediumBoxFixedQuantity;
    }

    public void setMediumBoxFixedQuantity(Integer mediumBoxFixedQuantity) {
        this.mediumBoxFixedQuantity = mediumBoxFixedQuantity;
    }

    public String getSmallBoxModel() {
        return smallBoxModel;
    }

    public void setSmallBoxModel(String smallBoxModel) {
        this.smallBoxModel = smallBoxModel;
    }

    public Integer getSmallBoxFixedQuantity() {
        return smallBoxFixedQuantity;
    }

    public void setSmallBoxFixedQuantity(Integer smallBoxFixedQuantity) {
        this.smallBoxFixedQuantity = smallBoxFixedQuantity;
    }
}
