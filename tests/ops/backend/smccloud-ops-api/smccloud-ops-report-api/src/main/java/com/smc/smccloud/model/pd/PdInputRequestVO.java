package com.smc.smccloud.model.pd;

import com.smc.smccloud.core.model.page.Page;
import lombok.Data;

import java.util.List;

/**
 * @Author lyc
 * @Date 2023/6/21 9:01
 * @Descripton TODO
 */
@Data
public class PdInputRequestVO {
    private String pdType;
    private String warehouseType;
    private List<String> warehouseCodes;
    private String pdBillNo;
    private String modelNo;
    private String pdInputort;
    private String pdDataType;
    private Page page;
}
