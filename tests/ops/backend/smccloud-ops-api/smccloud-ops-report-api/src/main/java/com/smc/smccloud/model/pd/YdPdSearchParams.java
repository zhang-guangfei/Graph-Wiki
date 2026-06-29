package com.smc.smccloud.model.pd;

import com.smc.smccloud.core.model.page.Page;
import lombok.Data;

/**
 * @Author lyc
 * @Date 2024/11/4 11:39
 * @Descripton TODO
 */
@Data
public class YdPdSearchParams {

    private String pdBatchNo;

    private String status;

    private String pdStartTime;

    private String pdDataEndTime;

    private Page page;

}
