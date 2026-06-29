package com.smc.smccloud.model.product;

import lombok.Data;

/**
 * Author: B90034
 * Date: 2022-05-09 09:24
 * Description: 型号备注信息
 */
@Data
public class ProductRemark {

    /**
     * 型号
     */
    private String modelNo;

    /**
     * 是否错误型号
     */
    private String isError;

    /**
     * 是否偶数订货型号
     */
    private String isEven;


    /**
     * 限制销售品
     */
    private String isRestrict;

    /**
     * 收敛品
     */
    private  String isEos;

    /**
     * 最小包装数量
     */
    private Integer minPackUnit;

}