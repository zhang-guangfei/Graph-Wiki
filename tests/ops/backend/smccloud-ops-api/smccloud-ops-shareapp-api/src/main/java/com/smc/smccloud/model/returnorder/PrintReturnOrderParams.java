package com.smc.smccloud.model.returnorder;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author lyc
 * @Date 2022/2/8 8:43
 * @Descripton TODO
 */

@Data
public class PrintReturnOrderParams implements Serializable {
    private String[] applyNo;
}
