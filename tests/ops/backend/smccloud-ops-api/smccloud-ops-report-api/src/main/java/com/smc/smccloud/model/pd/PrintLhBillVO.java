package com.smc.smccloud.model.pd;

import lombok.Data;

/**
 * @Author lyc
 * @Date 2023/6/20 17:52
 * @Descripton TODO
 */
@Data
public class PrintLhBillVO {
    private String billQty;
    private String againSureQty;
    private String shelvesNo;
    private String locationNo;
    private String modelNo;
}
