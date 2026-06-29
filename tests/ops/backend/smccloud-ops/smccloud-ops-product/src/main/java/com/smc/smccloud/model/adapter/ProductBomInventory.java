package com.smc.smccloud.model.adapter;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;

/**
 * Author: B90034
 * Date: 2022-05-09 11:38
 * Description:
 */
@Data
public class ProductBomInventory {

    /**
     * 拆分版本号
     */
    @TableField("bomId")
    private Long bomId;

    /**
     * 型号
     */
    @TableField("ModelNo")
    private String modelNo;

    /**
     * 数量
     */
    @TableField("Quantity")
    private int quantity;

    /**
     * 优先级
     */
    @TableField("Priority_level")
    private String priorityLevel;

    /**
     * 产地
     */
    private String orgCountry;

    /**
     * 有效库存
     */
    private int canUseQuantity;

    /**
     * 更新时间
     */
    @TableField(exist = false)
    private Date updateTime;
}
