package com.sales.ops.dto.inventory;

import java.util.List;

public class InventoryListForMenhu {

    private String modelNo;
    private List<InventoryDtoForMenhu> inventoryList;
    private boolean bin;

    public InventoryListForMenhu(String modelNo, List<InventoryDtoForMenhu> inventoryList, boolean bin) {
        this.modelNo = modelNo;
        this.inventoryList = inventoryList;
        this.bin = bin;
    }

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo;
    }

    public List<InventoryDtoForMenhu> getInventoryList() {
        return inventoryList;
    }

    public void setInventoryList(List<InventoryDtoForMenhu> inventoryList) {
        this.inventoryList = inventoryList;
    }

    public boolean isBin() {
        return bin;
    }

    public void setBin(boolean bin) {
        this.bin = bin;
    }
}

