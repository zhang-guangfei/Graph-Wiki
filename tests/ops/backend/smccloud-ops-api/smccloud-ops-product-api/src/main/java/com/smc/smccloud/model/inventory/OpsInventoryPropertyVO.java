package com.smc.smccloud.model.inventory;

import lombok.Data;

import java.util.Date;

/**
 * @Author edp02 @Date 2021/12/3 13:33
 */
@Data
public class OpsInventoryPropertyVO {

    /**
     * 主键ID
     */
    private Long inventoryPropertyId;

    /**
     * 库存分类：顾客在库，战略在库、通用在库
     */
    private String inventoryTypeCode;

    /**
     * 客户号
     */
    private String customerNo;

    /**
     * PPL代码
     */
    private String ppl;

    /**
     * 项目号
     */
    private String projectCode;

    /**
     * 客户群代码
     */
    private String groupCustomerNo;

    /**
     * 增加版本号更新库存，防止并发数量异常更新
     */
    private Long version;

    /**
     * 删除标识：0未删除,1删除
     */
    private Integer delflag;

    /**
     * 创建时间
     */
    private Date creTime;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 修改时间
     */
    private Date modifyTime;

    /**
     * 修改人
     */
    private String modifier;

    /**
     * 营业情报号
     */
    private String salesInfoNo;

    /**
     * 营业情报号过期日期
     */
    private Date expDate;

}
