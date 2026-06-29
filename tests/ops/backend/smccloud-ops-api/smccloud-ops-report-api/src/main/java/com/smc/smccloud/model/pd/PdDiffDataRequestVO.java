package com.smc.smccloud.model.pd;

import com.smc.smccloud.core.model.page.Page;
import lombok.Data;

import java.util.List;

/**
 * @Author lyc
 * @Date 2023/6/22 15:28
 * @Descripton TODO
 */
@Data
public class PdDiffDataRequestVO {
    private String pdBillType;
    private List<String> warehouseCodes;
    private String warehouseType;
    private String pdBillNo;
    private String modelNo;
    // 1 型号差异 2 数量差异
    private List<String> diffTypes;
    private String diffType;
    private Page page;
}
