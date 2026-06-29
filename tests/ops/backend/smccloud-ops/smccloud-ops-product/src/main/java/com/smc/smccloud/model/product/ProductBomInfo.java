package com.smc.smccloud.model.product;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;

/**
 * Author: B90034
 * Date: 2022-05-09 11:58
 * Description:
 */
@Data
public class ProductBomInfo {

    /**
     * 拆分版本号
     */
    @TableField("bomId")
    private Long bomId;

    /**
     * 拆分型号
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
     * 更新时间
     */
    @TableField("UpdateTime")
    private Date updateTime;
}
