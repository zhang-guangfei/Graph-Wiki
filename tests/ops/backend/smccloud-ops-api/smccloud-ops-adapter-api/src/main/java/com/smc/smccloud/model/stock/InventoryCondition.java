package com.smc.smccloud.model.stock;

import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class InventoryCondition implements Serializable {

    //库存所属 N:在库 M:在途 S:供应商
    private String inventoryScope;
    private String warehouseCode;
    private List<String> modelNoList;
    private String inventoryType;//库存类别
    private String customerNo;
    private List<String> customerList;

    public List<String> getAllCustomerNo() {
        if (StringUtils.isNotBlank(customerNo)) {
            List<String> list = new ArrayList<>();
            list.add(customerNo);
            return list;
        }
        if (customerList == null) {
            return new ArrayList<>();
        }
        return customerList;
    }

    public boolean hasCustomerNo() {
        return CollectionUtils.isNotEmpty(getAllCustomerNo());
    }

}

