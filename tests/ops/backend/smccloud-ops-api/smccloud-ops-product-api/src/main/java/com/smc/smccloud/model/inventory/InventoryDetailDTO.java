package com.smc.smccloud.model.inventory;

import lombok.Data;

/**
 * @Author lyc
 * @Date 2022/4/11 14:50
 * @Descripton TODO
 */
@Data
public class InventoryDetailDTO {


    /**
     * 序号
     */
    private String no;

    private String modelNo;

    /**
     * 货架号
     */
    private String localtionNo;

    /**
     * 账簿数量
     */
    private Integer accountBooksNum;
    /**
     * 实物数量
     */
    private Integer entityNum;

    /**
     * 确认数量
     */
    private Integer confirmNum;

    /**
     * 备注
     */
    private String remark;


    private String customerNo;

    private String customerName;
    // 客户类型名称
    private String customerTypeName;

}
