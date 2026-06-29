package com.smc.smccloud.model.pd;

import lombok.Data;

import java.util.List;

/**
 * @Author lyc
 * @Date 2023/6/23 10:24
 * @Descripton TODO
 */
@Data
public class PrintArriveNotInBillDTO {
    private List<PrintArriveNotInBillVO> titleDataSet;
    private List<PrintArriveNotInBillVO> detailDataSet;
}
