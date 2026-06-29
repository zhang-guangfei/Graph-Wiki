package com.smc.smccloud.model.prestock;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Author: B90034
 * Date: 2021-12-30 10:51
 * Description: [ops_core].[dbo].[product_bom_detail]
 */
@Data
@TableName("product_bom_detail")
public class ProductBomDetailDO {

    @TableId(value = "id")
    private String id;

    /**
     * product_bom.bomId
     */
    @TableField("bomId")
    private String bomId;

    /**
     * 型号
     */
    @TableField("ModelNo")
    private String modelNo;

    /**
     * 数量
     */
    @TableField("Quantity")
    private Integer quantity;

    @TableField("Classify")
    private Boolean Classify;


    @TableField(exist = false)
    private Boolean status;

    /**
     * 方案
     */
    @TableField(exist = false)
    private String program;

    @TableField(exist = false)
    private String itemNo;

    /**
     * 缺省标识:0:3S非缺省方案;1:3S缺省方案;2:业务维护
     */
    @TableField(exist = false)
    private String defaultIdentify;

    @TableField(exist = false)
    private String UpdateTime;

    @TableField(exist = false)
    private String updateUser;

}
