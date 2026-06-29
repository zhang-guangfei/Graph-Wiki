package com.smc.smccloud.model.inventory;

import lombok.Data;

@Data
public class InventoryForShikomiApplyVO {
    private  String modelNo;
    /*
     大库在库
     */
    private  Integer qtyOfKStock;
    /*
     委托在库
     */
    private  Integer qtyOfWStock;
    /*
    分库在库
    */
    private  Integer qtyOfSStock;

    /*
    大库在途
    */
    private  Integer ordQtyOfKStock;

    /*
    分库在途
    */
    private Integer ordQtyOfSStock;

    /*
    委托在途
   */
    private Integer ordQtyOfWStock;

    /**
     * shikomi残数
     */
    private  Integer qtyOfShikomi;

    /**
     * 客户专备在库
     */
    private  Integer qtyOfCustomerStock;
    /**
     * 客户专备在途
     */
    private  Integer ordQtyOfCustomerStock;

}
