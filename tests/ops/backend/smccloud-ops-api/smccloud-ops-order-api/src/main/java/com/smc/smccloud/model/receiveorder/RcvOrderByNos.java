package com.smc.smccloud.model.receiveorder;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * @Author lyc
 * @Date 2022/1/13 9:43
 * @Descripton TODO
 */

@Data
public class RcvOrderByNos {

    @Valid
    @NotEmpty
    private String[] rorderNos;
}
