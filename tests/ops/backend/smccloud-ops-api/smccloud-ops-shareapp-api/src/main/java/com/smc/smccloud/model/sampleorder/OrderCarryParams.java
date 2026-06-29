package com.smc.smccloud.model.sampleorder;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * @Author lyc
 * @Date 2022/1/15 16:02
 * @Descripton TODO
 */

@Data
public class OrderCarryParams {

    @Valid
    @NotEmpty
    private String[] ids;

}
