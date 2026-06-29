package com.smc.smccloud.model.returnorder;

import lombok.Data;

import java.util.List;

/**
 * @Author lyc
 * @Date 2022/2/7 17:16
 * @Descripton TODO
 */

@Data
public class PrintReturnOrderDTO {

    private List<PrintReturnOrderVO> returnOrderData;

}
