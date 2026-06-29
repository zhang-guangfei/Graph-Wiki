package com.sales.ops.dto.common;

import java.io.Serializable;

/**
 * @author ：C14717
 * @version: $bugid:17799
 * @description：
 * @date ：Created in 2025/6/13 10:39
 */
public class BomSelectParam implements Serializable {

    private static final long serialVersionUID = -2593892743149363349L;
    private String customerCode; // 客户代码
    private String modelNo;

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo;
    }
}
