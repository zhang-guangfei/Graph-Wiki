package com.smc.smccloud.model.inventory;

import lombok.Data;

import java.util.List;

@Data
public class InventoryRequestDTO {

    private Long propertyId;

    private String customerNo;

    private String groupCustomerNo;

    private String ppl;

    private String projectNo;

    private String modelNo;

    private List<String> modelNos;

    private String warehouseCode;

    private List<String> warehouseCodes;

    private String userNo;
}
