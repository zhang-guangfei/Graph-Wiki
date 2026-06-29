package com.smc.smccloud.model.csstock;

import lombok.Data;

/**
 * @Author lyc
 * @Date 2022/4/10 15:12
 * @Descripton TODO
 */
@Data
public class CsExpDetailConfirmRequest {

    private String[] expOrderNo;

    private String warehouseCode;

}
