package com.smc.smccloud.model.inventory;

import lombok.Data;

import java.util.Date;

/**
 * @Author lyc
 * @Date 2022/4/7 14:50
 * @Descripton TODO
 */
@Data
public class OpsInventoryVO {

    private Long inventoryId;

    /**
     * 仓库代码
     */
    private String warehouseCode;

    /**
     * 库存状态[正常在库:N, 限定:X]
     */
    private String inventoryStatus;

    /**
     * 在库数量
     */
    private Integer quantity;

    /**
     * 数量单位
     */
    private String unit;

    /**
     * 质量状态: 0-良品, 1-不良品, 2-未检品
     */
    private String qaStatus;

    /**
     * 出库占用库存
     */
    private Integer prepareQuantity;

    /**
     * 型号
     */
    private String modelNo;

    /**
     * 归属ID
     */
    private Long inventoryPropertyId;
    /**
     * 分类别
     */
    private  String classCode;

    /**
     * 采购价格
     */
    private String price;

    /**
     * 入库时间
     */
    private Date inTime;

    private Long version;

    /**
     * 删除标识：0未删除,1删除
     */
    private Integer delFlag;

    private Date creTime;

    private String creator;

    private Date modifyTime;

    private String modifier;

}
