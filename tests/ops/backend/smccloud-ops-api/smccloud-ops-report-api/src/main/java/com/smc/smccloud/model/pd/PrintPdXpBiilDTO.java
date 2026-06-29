package com.smc.smccloud.model.pd;

import lombok.Data;

import java.util.List;

/**
 * @Author lyc
 * @Date 2023/6/17 15:04
 * @Descripton TODO
 */
@Data
public class PrintPdXpBiilDTO {
    private List<PrintPdXpBillVO> titleDataSet;
    private List<PrintPdXpBillVO> detailDataSet;
}
