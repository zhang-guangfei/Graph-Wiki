package com.smc.smccloud.model.pd;

import com.smc.smccloud.core.model.page.Page;
import lombok.Data;

/**
 * @Author lyc
 * @Date 2023/12/19 10:36
 * @Descripton TODO
 */
@Data
public class PdReportParamVO {
    private String modelNo;
    private Page page;

    private String pdBatchNo;
}
