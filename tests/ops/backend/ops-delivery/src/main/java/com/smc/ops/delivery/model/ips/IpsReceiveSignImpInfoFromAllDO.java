package com.smc.ops.delivery.model.ips;

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
 * @since 2025-12-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ips_receive_sign_imp_info_from_all")
public class IpsReceiveSignImpInfoFromAllDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 来源系统：PMS、AIMS、OPS、NPS
     */
    @TableField("source_system")
    private String sourceSystem;

    /**
     * 供应方系统
     */
    @TableField("supplier_system")
    private String supplierSystem;

    /**
     * 原采购单号
     */
    @TableField("src_order_no")
    private String srcOrderNo;

    /**
     * 原采购项号
     */
    @TableField("src_order_Item")
    private String srcOrderItem;

    /**
     * IPS采购单号
     */
    @TableField("order_no")
    private String orderNo;

    /**
     * IPS采购项号
     */
    @TableField("order_Item")
    private String orderItem;

    /**
     * 型号
     */
    @TableField("model_no")
    private String modelNo;

    /**
     * 0签收数据，1入库数据
     */
    @TableField("data_type")
    private String dataType;

    /**
     * 对应操作的数量
     */
    @TableField("qty")
    private Double qty;

    /**
     * 偏差量
     */
    @TableField("deviation_qty")
    private Double deviationQty;

    /**
     * 偏差原因
     */
    @TableField("deviation_reason")
    private String deviationReason;

    /**
     * 操作状态，0未签收\未入库，1已签收\已入库，
     */
    @TableField("process_status")
    private String processStatus;

    /**
     * 操作地点
     */
    @TableField("process_place")
    private String processPlace;

    /**
     * 操作时间
     */
    @TableField("process_date")
    private Date processDate;

    /**
     * 操作担当工号
     */
    @TableField("processor_code")
    private String processorCode;

    /**
     * 补充信息is_checked 
     */
    @TableField("addition_info")
    private String additionInfo;

    /**
     * 写入时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 写入人
     */
    @TableField("create_user")
    private String createUser;

    /**
     * 发票号（有新采购系统的送货单号就用送货单号、没有就由供应方系统生成形式发票号）
     */
    @TableField("invoice_no")
    private String invoiceNo;

    @TableField("invoice_batch_no")
    private String invoiceBatchNo;

}
