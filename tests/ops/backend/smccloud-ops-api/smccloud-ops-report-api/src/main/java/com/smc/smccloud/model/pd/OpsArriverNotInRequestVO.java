package com.smc.smccloud.model.pd;

import com.smc.smccloud.core.model.page.Page;
import lombok.Data;

import java.util.List;

/**
 * @Author lyc
 * @Date 2023/6/13 8:47
 * @Descripton TODO
 */
@Data
public class OpsArriverNotInRequestVO {
    private String invoiceNo;
    private String warehouseType;
    private List<String> warehouseCodes;
    private String logisticsConfirm;
    private String expType;
    private Page page;
    private String pdBatchNo;
}
