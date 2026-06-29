package com.smc.smccloud.model.shikomi;

import com.smc.smccloud.core.model.page.Page;
import lombok.Data;

import java.util.List;

/**
 * @Author lyc
 * @Date 2024/3/26 15:29
 * @Descripton TODO
 */
@Data
public class ShikomiBudgetRequest {

    private String batchNo;

    private List<String> supplierCode;

    private String modelNo;

    private String shikomiNo;

    private String belongCustomerNo;

    private List<String> deptNo;

    private String indCode;

    private List<String> classCode;

    private String applyNo;

    private String applicantNo;

    private List<String> inspectStatus;

    private List<String> inspectType;

    private List<String> inspectConfirmType;

    private Page page;

}
