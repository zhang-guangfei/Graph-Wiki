package com.smc.smccloud.model.enums;


import java.util.ArrayList;
import java.util.List;

/**
 * @Author lyc
 * @Date 2022/7/13 16:01
 * @Descripton TODO
 */
public enum  QueryExpDataNotIncludedCustomer {
    C1D70("C1D70"),
    C1D71("C1D71"),
    C1D72("C1D72"),
    CUSTOMER("99303"),
    CUSTOMER2("C1D70"),
    ;

    private String customerNo;

    QueryExpDataNotIncludedCustomer(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public static List<String> getExpDataNotIncludedCustomerNo() {
        List<String> list = new ArrayList<>();
        for (QueryExpDataNotIncludedCustomer item : QueryExpDataNotIncludedCustomer.values()) {
            list.add(item.getCustomerNo());
        }
        return list;
    }

}
