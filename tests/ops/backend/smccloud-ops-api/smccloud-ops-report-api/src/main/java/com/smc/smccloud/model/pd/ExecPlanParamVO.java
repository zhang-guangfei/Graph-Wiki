package com.smc.smccloud.model.pd;

import com.smc.smccloud.core.model.page.Page;
import lombok.Data;

/**
 * @Author lyc
 * @Date 2024/11/4 15:13
 * @Descripton TODO
 */
@Data
public class ExecPlanParamVO {
    private String execDate;
    private Integer execFlag;
    private Page page;
}
