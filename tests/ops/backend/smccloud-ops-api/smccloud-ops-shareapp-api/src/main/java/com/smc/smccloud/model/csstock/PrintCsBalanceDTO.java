package com.smc.smccloud.model.csstock;

import lombok.Data;

import java.util.List;

/**
 * TODO
 *
 * @author wsf
 * @version 1.0
 * @date 2022/2/15 9:50
 */
@Data
public class PrintCsBalanceDTO {

    private List<PrintCsBalanceVO> csBalanceData;
}
