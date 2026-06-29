package com.smc.ops.delivery.model.poImps;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author smc
 * @since 2024-02-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ops_po_delivery_invoice_barcode_from_supplier")
public class OpsPoDeliveryInvoiceBarcodeFromSupplierDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("create_user")
    private String createUser;

    @TableField("source_id")
    private Long sourceId;

    @TableField("hs_code")
    private String hsCode;

    @TableField("receive_warehouse_id")
    private String receiveWarehouseId;

    @TableField("quantity")
    private Integer quantity;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("case_no")
    private String caseNo;

    @TableField("pid")
    private Long pid;

    @TableField("sn_code")
    private String snCode;

    @TableField("pono")
    private String pono;

    @TableField("business_exec_time")
    private Date businessExecTime;

    @TableField("net_weight")
    private BigDecimal netWeight;

    @TableField("del_flag")
    private Integer delFlag;

    @TableField("delivery_time")
    private Date deliveryTime;

    @TableField("model_no")
    private String modelNo;

    @TableField("barcode")
    private String barcode;

    @TableField("ship_time")
    private Date shipTime;

    @TableField("update_time")
    private Date updateTime;

    @TableField("supplier_order_no")
    private String supplierOrderNo;

    @TableField("transtype_code")
    private String transtypeCode;

    @TableField("update_user")
    private String updateUser;

    @TableField("supplier_line_item_no")
    private String supplierLineItemNo;

    @TableField("create_time")
    private Date createTime;

    @TableField("supplier_system_exec_time")
    private Date supplierSystemExecTime;

    @TableField("line_item")
    private Integer lineItem;

    @TableField("source_type")
    private String sourceType;

    @TableField("rosh_code")
    private String roshCode;

    /**
     * origin
     * product_code
     * shelf_code
     * imp_order_no
     */
    // 原产国
    @TableField("origin")
    private String origin;
    @TableField("product_code")
    private String productCode;
    @TableField("shelf_code")
    // 货架号
    private String shelfCode;
    // 原始导入订单号+‘-’+项号
    @TableField("imp_order_no")
    private String impOrderNo;
    @TableField("from_id")
    private Long fromId;

    // 生产商代码
    @TableField("manufacturer_code")
    private String manufacturerCode;

    @TableField("supplier_code")
    private String supplierCode;

    @TableField("sub_code")
    private String subCode;

}
