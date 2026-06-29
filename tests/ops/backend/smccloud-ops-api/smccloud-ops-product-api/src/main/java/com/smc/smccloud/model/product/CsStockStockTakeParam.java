package com.smc.smccloud.model.product;

import com.smc.smccloud.core.model.adapter.DataAuthoritySearchCondition;
import com.smc.smccloud.core.model.page.Page;
import lombok.Data;

/**
 * @Author lyc
 * @Date 2022/4/21 8:38
 * @Descripton TODO
 */
@Data
public class CsStockStockTakeParam {

    private String customerNo;

    private String warehouseCode;

    private Page page;

    /**
     * 用户的查询权限(客户、部门、行业、用户)
     */
    private DataAuthoritySearchCondition dataAuthoritySearchCondition;

}
