package com.smc.smccloud.model.shikomi;

import lombok.Data;

import java.util.Date;

/**
 * @author edp04
 * @title: ShikomiRegistDateCallbackDTO
 * @date 2022/06/20 16:31
 */
@Data
public class ShikomiRegistDateCallbackDTO {

    // 申请号+项号
    private String applyNo;

    private Date registDate;

    private Integer asseDays;
}
