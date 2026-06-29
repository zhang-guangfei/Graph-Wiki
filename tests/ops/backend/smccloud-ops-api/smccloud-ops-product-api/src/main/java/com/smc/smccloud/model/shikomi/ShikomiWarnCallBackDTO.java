package com.smc.smccloud.model.shikomi;

import lombok.Data;

/**
 * @author C18117
 * @title: ShikomiRemainDTO
 * @date 2023/08/09 14:21
 */
@Data
public class ShikomiWarnCallBackDTO {

    private String shikomiNo;

    private String modelNo;

    private Integer qtyWarning;
}
