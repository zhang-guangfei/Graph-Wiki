package com.smc.smccloud.model.inventory;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author smc
 * @since 2023-03-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("GPInventory")
public class GPInventoryDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ModelNo", type = IdType.AUTO)
    private String ModelNo;

    /**
     * 在库可用数量
     */
    @TableField("AvaQty")
    private Integer AvaQty;

    /**
     * 可生产数
     */
    @TableField("ProdQty")
    private Integer ProdQty;

    /**
     * 可组装数量
     */
    @TableField("AssQty")
    private Integer AssQty;

    /**
     * 更新时间
     */
    @TableField("UpdateTime")
    private Date UpdateTime;

    @TableField("StockCode")
    private String StockCode;


}
