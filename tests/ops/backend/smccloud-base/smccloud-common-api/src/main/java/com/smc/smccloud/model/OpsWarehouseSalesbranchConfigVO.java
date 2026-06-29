package com.smc.smccloud.model;

import lombok.Data;

/**
 * @author wuweidong
 * @create 2023/7/21 11:14
 * @description
 */
@Data
public class OpsWarehouseSalesbranchConfigVO {

    private Integer id;

    /**
     * 营业所代码/代理店
     */
    private String salesBranchId;

    /**
     * 仓库编码
     */
    private String warehouseCode;

    /**
     * 优先级(数字越小优先级越大)
     */
    private Integer priority;

    /**
     * 描述
     */
    private String description;

    /**
     * 交货期
     */
    private Integer deliveryDay;

    /**
     * 删除标识：0未删除,1删除
     */

    private Integer delFlag;

}
