package com.smc.smccloud.model.sampleorder;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * @Author lyc
 * @Date 2022/1/6 11:40
 * @Descripton TODO
 */
@Data
public class SampleOrderParams {

    @Valid
    @NotEmpty
    private String[] applyIds;

    private String remark;
}
