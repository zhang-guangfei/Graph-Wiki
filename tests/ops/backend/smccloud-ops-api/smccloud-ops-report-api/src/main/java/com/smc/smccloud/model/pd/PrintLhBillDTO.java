package com.smc.smccloud.model.pd;

import lombok.Data;

import java.util.List;

/**
 * @Author lyc
 * @Date 2023/6/20 17:54
 * @Descripton TODO
 */
@Data
public class PrintLhBillDTO {
    private List<PrintLhBillVO> titleDataSet;
    private List<PrintLhBillVO> detailDataSet;
}
