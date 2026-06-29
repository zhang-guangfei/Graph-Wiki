package com.smc.smccloud.model.pd;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author smc
 * @since 2023-08-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ops_pd_warehouse_config")
public class OpsPdWarehouseConfigDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 仓库代码
     */
    @TableField("warehouse_code")
    private String warehouseCode;

    /**
     * 仓库盘点担当编码
     */
    @TableField("warehouse_personNo")
    private String warehousePersonNo;

    /**
     * 担当名称
     */
    @TableField("warehouse_personName")
    private String warehousePersonName;


}
