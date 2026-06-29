package com.smc.smccloud.model.prestock;

import lombok.Data;

/**
 * @author edp04
 * @title: ProductBomDetailVO
 * @date 2022/06/13 10:07
 */
@Data
public class ProductBomDetailVO {

    private String id;

    /**
     * product_bom.bomId
     */
    private String bomId;

    /**
     * 型号
     */
    private String modelNo;

    /**
     * 数量
     */
    private Integer quantity;

    private Boolean status;

    /**
     * 方案
     */
    private String program;

    private String itemNo;

    /**
     * 缺省标识:0:3S非缺省方案;1:3S缺省方案;2:业务维护
     */
    private String defaultIdentify;
}
