package com.smc.smccloud.model.product;

import lombok.Data;

@Data
public class ProductForShikomiVO {
    private  String modelNo;
    /**
     * 原产地代码
     */
    private  String origin;
    /**
     * 供应商
     */
    private  String supplier;


    /**
     * 原产地名称
     */
    private  String originName;
    /**
     * 供应商名称
     */
    private  String supplierName;

    /**
     * 收敛品
     */
    private  boolean isEosModel;

    /**
     * bin产品
     */
    private  boolean isBinModel;

}
