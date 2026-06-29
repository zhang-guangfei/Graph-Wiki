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
 * @since 2023-06-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ops_as_finance_blance_data")
public class OpsAsFinanceBlanceDataDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 盘点批次号
     */
    @TableField("pd_batch_no")
    private String pdBatchNo;

    /**
     * 仓库代码(预留字段)
     */
    @TableField("warehouse_code")
    private String warehouseCode;

    /**
     * 型号
     */
    @TableField("model_no")
    private String modelNo;

    /**
     * 财务结存数量
     */
    @TableField("balance_qty")
    private Integer balanceQty;

    @TableField("create_user")
    private String createUser;

    @TableField("create_time")
    private Date createTime;

    /**
     * 版本
     */
    @TableField("version")
    private String version;

    @TableField("update_user")
    private String updateUser;

    @TableField("update_time")
    private Date updateTime;

    // 1 自动化 2 广州制造
    @TableField("data_source")
    private String dataSource;


}
