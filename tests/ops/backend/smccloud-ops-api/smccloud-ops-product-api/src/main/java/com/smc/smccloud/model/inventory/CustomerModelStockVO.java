package com.smc.smccloud.model.inventory;

import lombok.Data;

/**
 * Author: B90034
 * Date: 2022-04-14 16:45
 * Description:
 */
@Data
public class CustomerModelStockVO {

    private String modelNo;

    private String customerNo;

    private int tyAvaQty;

    private int zyAvaQty;

    private int orderingQty;

    private String inventoryTypeCode;

    private String ppl;

    private String projectCode;

}
