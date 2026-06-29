package com.smc.smccloud.model.pd;

import lombok.Data;

/**
 * @Author lyc
 * @Date 2023/6/17 15:08
 * @Descripton TODO
 */
@Data
public class PrintPdXpBillVO {
    private String shelvesNo;
    private String locationNo;
    private String modelNo;
    private String remark;
    private String pdQty;
    private String pdSurePerson;
    private String orderNo;
    private String factQty;
}
