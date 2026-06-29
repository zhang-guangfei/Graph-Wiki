package com.smc.smccloud.model.csstock;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smc.smccloud.core.model.page.Page;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class CsBalcenQryRequest {
    private String agentNo;
    private String warehouseCode;
    private String modelNo;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM")
    @DateTimeFormat(pattern = "yyyy-MM")
    private Date monthDate;

    private Page page;
}
