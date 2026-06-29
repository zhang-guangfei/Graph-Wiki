package com.smc.smccloud.model.inventory;

import lombok.Data;

/**
 * Author: B90034
 * Date: 2022-02-25 08:54
 * Description:
 */
@Data
public class InventoryChangeResult {

    private String fromNo;

    private Integer fromId;

    private String modelNo;

    private boolean result;

    private String message;
}
