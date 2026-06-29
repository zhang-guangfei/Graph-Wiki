package com.smc.smccloud.model.pd;

import com.smc.smccloud.core.model.page.Page;
import lombok.Data;

/**
 * @Author lyc
 * @Date 2023/6/7 16:24
 * @Descripton TODO
 */
@Data
public class OpsAsPdBatchRequestVO {

    private String pdBatchNo;

    private String pdState;

    // private String

    private Page page;

    private String pdStartTime;

    private String pdDataEndTime;

    private String createUser;

}
