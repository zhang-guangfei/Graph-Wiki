package com.smc.ops.delivery.model.poImps;

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
 * @since 2024-02-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ops_po_delivery_invoice_log_from_supplier")
public class OpsPoDeliveryInvoiceLogFromSupplierDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("gw_invoice_status")
    private String gwInvoiceStatus;

    @TableField("invoice_date")
    private Date invoiceDate;

    @TableField("import_date")
    private Date importDate;

    @TableField("create_time")
    private Date createTime;

    @TableField("act_arrival_time")
    private Date actArrivalTime;

    @TableField("source_id")
    private Long sourceId;

    @TableField("update_user")
    private String updateUser;

    @TableField("gw_invoice_no")
    private String gwInvoiceNo;

    @TableField("customs_declaration_no")
    private String customsDeclarationNo;

    @TableField("ship_date")
    private Date shipDate;



    @TableField("create_user")
    private String createUser;

    @TableField("invoice_no")
    private String invoiceNo;

    @TableField("del_flag")
    private Integer delFlag;

    @TableField("code")
    private String code;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("est_arrival_time")
    private Date estArrivalTime;

    @TableField("customs_date")
    private Date customsDate;

    @TableField("source_type")
    private String sourceType;

    @TableField("update_time")
    private Date updateTime;



    @TableField("smccode")
    private String smccode;
    // 开票主体
    @TableField("issuer_code")
    private String issuerCode;

    // 生产商
    @TableField("purchaser_code")
    private String purchaserCode;
    // 是否关务报关标识(非关务报关写0，关务报关写1)
    @TableField("is_gw")
    private Integer isGw;

    // 到港日期
    @TableField("arrival_port_date")
    private Date arrivalPortDate;

}
