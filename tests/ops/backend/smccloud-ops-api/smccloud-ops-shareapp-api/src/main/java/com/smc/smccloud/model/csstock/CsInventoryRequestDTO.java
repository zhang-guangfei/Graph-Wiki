package com.smc.smccloud.model.csstock;

import com.smc.smccloud.core.model.adapter.DataAuthoritySearchCondition;
import com.smc.smccloud.core.model.page.Page;
import lombok.Data;

/**
 * 委托在库库存查询
 *
 * @author wsf
 * @version 1.0
 * @date 2022/1/10 18:13
 */
@Data
public class CsInventoryRequestDTO {
    /**
     * 库房代码
     */
    private String warehouseCode;
    /**
     * 型号
     */
    private String modelNo;

    /**
     * 设备bom
     */
    private String bomNo;

    /**
     * 客户
     */
    private String customerNo;

    /**
     * 用户编码
     */
    private String userNo;

    /**
     * 项目号
     */
    private String projectNo;

    /**
     * 可订货数
     */
    private Integer orderQuantity;

    /**
     * 代理店
     */
    private String agentNo;



    private Page page;

    /**
     * 用户的查询权限(客户、部门、行业、用户)
     */
    private DataAuthoritySearchCondition dataAuthoritySearchCondition;

}
