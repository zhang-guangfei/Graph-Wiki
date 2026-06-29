package com.smc.smccloud.model.stock;

import lombok.Data;

import java.util.List;

@Data
public class InventoryListDto {

    private String modelNo;
    private List<InventoryDto> inventoryList;
    private boolean bin;

    public InventoryListDto(String modelNo, List<InventoryDto> inventoryList, boolean bin) {
        this.modelNo = modelNo;
        this.inventoryList = inventoryList;
        this.bin = bin;
    }

}

