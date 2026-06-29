package com.smc.smccloud.model.stockassembly;

import lombok.Data;

import java.util.List;

/**
 * Author: B90034
 * Date: 2021-12-15 10:31
 * Description:
 */
@Data
public class StockAssemblyHandleDTO {

    /**
     * 申请号
     */
    private List<Long> applyIds;

    /**
     * 回复说明
     */
    private String answerText;

    /**
     * 1-审核确认; 3-处理; 4-退回
     */
    private String handleType;
}
