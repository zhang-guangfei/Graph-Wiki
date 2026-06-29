package com.smc.smccloud.model.receiveorder;

import lombok.Data;

/**
 * @Author lyc
 * @Date 2022/4/18 15:25
 * @Descripton TODO
 */
@Data
public class CalSMCGuidingPriceEntity {

    private Boolean success;

    private String errorCode;

    private String message;

    private ReferencePrice price;

}
