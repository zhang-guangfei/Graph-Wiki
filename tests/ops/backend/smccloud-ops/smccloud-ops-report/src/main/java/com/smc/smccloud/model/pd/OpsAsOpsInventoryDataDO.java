package com.smc.smccloud.model.pd;

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
 * @since 2023-06-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ops_as_ops_inventory_data")
public class OpsAsOpsInventoryDataDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 盘点批次号
     */
    @TableField("pd_batch_no")
    private String pdBatchNo;

    /**
     * 仓库代码
     */
    @TableField("warehouse_code")
    private String warehouseCode;

    /**
     * 型号
     */
    @TableField("model_no")
    private String modelNo;

    /**
     * 数量
     */
    @TableField("qty")
    private Integer qty;

    /**
     * 数据类型1 库存数据，2 OPS采购到货未入，3 OPS调拨到货未入，4OPS退货到货未入
     */
    @TableField("data_type")
    private String dataType;

    @TableField("create_user")
    private String createUser;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_user")
    private String updateUser;

    @TableField("update_time")
    private Date updateTime;


}
