package com.smc.smccloud.model.pd;

import com.smc.smccloud.core.model.page.Page;
import lombok.Data;

import java.util.List;

/**
 * @Author lyc
 * @Date 2024/11/7 12:51
 * @Descripton TODO
 */
@Data
public class OpsAsPdReportWareParams {

    private List<String> warehouseList;

    private String modelNo;

    private String pdBatchNo;

    private Page page;

}
