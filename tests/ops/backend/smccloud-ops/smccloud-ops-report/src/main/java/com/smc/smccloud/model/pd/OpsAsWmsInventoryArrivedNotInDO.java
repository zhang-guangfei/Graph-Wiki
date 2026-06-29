package com.smc.smccloud.model.pd;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
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
 * @since 2023-06-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ops_as_wms_inventory_arrived_not_in")
public class OpsAsWmsInventoryArrivedNotInDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 盘点批次号
     */
    @TableField("pd_batch_no")
    private String pdBatchNo;

    /**
     * wms系统提供发票号
     */
    @TableField("wms_sys_invoice_no")
    private String wmsSysInvoiceNo;

    /**
     * wms系统提供仓库代码
     */
    @TableField("wms_sys_warehouse_code")
    private String wmsSysWarehouseCode;

    /**
     * wms系统提供是否已入完
     */
    @TableField("wms_sys_is_all")
    private String wmsSysIsAll;

    /**
     * 物流提供发票号
     */
    @TableField("l_invoice_no")
    private String lInvoiceNo;

    /**
     * 物流提供仓库代码
     */
    @TableField("l_warehouse_code")
    private String lWarehouseCode;

    /**
     * 物流提供是否已入完
     */
    @TableField("l_is_all")
    private String lIsAll;

    /**
     * 物流确认结果0物流现场没有 1物流有
     */
    @TableField("logistics_confirm")
    private String logisticsConfirm;

    /**
     * 箱号
     */
    @TableField("barcode")
    private String barcode;

    /**
     * 型号
     */
    @TableField("model_no")
    private String modelNo;

    /**
     * 拍号
     */
    @TableField("case_no")
    private String caseNo;

    /**
     * 账簿数
     */
    @TableField("bill_qty")
    private Integer billQty;

    @TableField("create_user")
    private String createUser;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_time")
    private Date updateTime;

    @TableField("update_user")
    private String updateUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @TableField(exist = false)
    private Date createTime2;

    // 数据类型 1 实体货架库存，2 过渡库位库存，3 采购到货未入，4 调拨到货未入，5退货到货未入
    @TableField("pd_data_type")
    private String pdDataType;

    /**
     * 完整订单号
     */
    @TableField("order_no")
    private String orderNo;

    @TableField("data_resource")
    private String dataResource;

    @TableField("is_ass")
    private String isAss;

}
