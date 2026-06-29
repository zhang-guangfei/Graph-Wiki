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
@TableName("ops_as_pd_compensate_data")
public class OpsAsPdCompensateDataDO implements Serializable {

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
     * 盘点票号(预留字段)
     */
    @TableField("pd_bill_no")
    private String pdBillNo;

    /**
     * 数据类型1 样品未结转，2已发货未推财务，3财务补偿数据，4调拨在途，5制造在途
     */
    @TableField("pd_data_type")
    private String pdDataType;

    /**
     * 数据来源1WMS，2OPS ，3财务系统，调拨在途和制造在途WMS和OPS均有自己的数据
     */
    @TableField("pd_data_source")
    private String pdDataSource;

    /**
     * 完整单号
     */
    @TableField("order_no")
    private String orderNo;

    /**
     * 主单号
     */
    @TableField("rorder_no")
    private String rorderNo;

    /**
     * 项号
     */
    @TableField("item_no")
    private Integer itemNo;

    /**
     * 拆分项号
     */
    @TableField("split_item_no")
    private Integer splitItemNo;

    /**
     * doid
     */
    @TableField("do_id")
    private String doId;

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

    @TableField(exist = false)
    private Integer quantity;

    @TableField("create_user")
    private String createUser;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_user")
    private String updateUser;

    @TableField("update_time")
    private Date updateTime;



}
