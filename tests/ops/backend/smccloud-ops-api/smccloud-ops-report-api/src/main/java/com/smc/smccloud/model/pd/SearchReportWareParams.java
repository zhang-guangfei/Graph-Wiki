package com.smc.smccloud.model.pd;

import com.smc.smccloud.core.model.page.Page;
import lombok.Data;

import java.util.List;

/**
 * @Author lyc
 * @Date 2024/11/12 14:55
 * @Descripton TODO
 */
@Data
public class SearchReportWareParams {

    private String pdBatchNo;

    private String modelNo;

    private List<String> warehouseList;

    private Page page;

}
