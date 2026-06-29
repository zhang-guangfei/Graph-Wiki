package com.smc.smccloud.model.pd;

import lombok.Data;

import java.util.List;

/**
 * @Author lyc
 * @Date 2023/6/23 14:09
 * @Descripton TODO
 */
@Data
public class PrintWtBillDataDTO {
    private List<PrintWtBillDataVO> titleDataSet;
    private List<PrintWtBillDataVO> detailDataSet;
}
