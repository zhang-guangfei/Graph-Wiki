package com.smc.smccloud.model.supplier;

import com.smc.smccloud.core.model.page.Page;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author lyc
 * @Date 2022/6/8 14:48
 * @Descripton TODO
 */
@Data
public class SupplierRequest {

    private String id;
    // 供应商名称
    private String name;
    // 公司id
    private String companyId;

    private String fstTransTypeId;

    private Page page;

    private String order = "asc";

}
