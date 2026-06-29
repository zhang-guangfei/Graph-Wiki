package com.smc.smccloud.model.shikomi;

import lombok.Data;

/**
 * @author edp04
 * @title: ShikomiInspectionCallbackDTO
 * @date 2022/06/15 16:02
 */
@Data
public class ShikomiInspectionCallbackDTO {

    private String applyNo;

    private Integer statusCode;

    private Integer type;
}
