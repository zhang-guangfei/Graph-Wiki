package com.smc.smccloud.model.pd;

import com.smc.smccloud.core.model.page.Page;
import lombok.Data;

import java.util.List;

/**
 * @Author lyc
 * @Date 2024/8/8 8:39
 * @Descripton TODO
 */
@Data
public class PdAdjustParamVO {

    private List<String> warehouseCodes;

    private String modelNo;

    private Page page;

    private String pdBatchNo;

    private String handStatus;

}
