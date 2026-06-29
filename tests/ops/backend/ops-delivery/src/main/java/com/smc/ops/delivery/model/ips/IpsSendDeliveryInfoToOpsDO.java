package com.smc.ops.delivery.model.ips;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * [0E232FF0-B466-11cf-A24F-00AA00A3EFFF, 1.00]	Begin DesignProperties = 	   Begin PaneConfigurations = 	      Begin PaneConfiguration = 0	         NumPanes = 4	         Configuration = "(H (1[40] 4[20] 2[20] 3) )"	      End	      Begin PaneConfiguration = 1	         NumPanes = 3	         Configuration = "(H (1 [50] 4 [25] 3))"	      End	      Begin PaneConfiguration = 2	         NumPanes = 3	         Configuration = "(H (1 [50] 2 [25] 3))"	      End	      Begin PaneConfiguration = 3
 * </p>
 *
 * @author smc
 * @since 2025-12-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ips_send_delivery_info_to_ops")
public class IpsSendDeliveryInfoToOpsDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("promise_date_from_supplier_arrived")
    private String promiseDateFromSupplierArrived;

    @TableField("smccode")
    private String smccode;

    @TableField("order_status_description")
    private String orderStatusDescription;

    @TableField("supplier_operate_time")
    private Date supplierOperateTime;

    @TableField("fact_date_from_supplier_H")
    private String factDateFromSupplierH;

    @TableField("is_checked")
    private String isChecked;

    @TableField("invoice_no")
    private String invoiceNo;

    @TableField("format_promise_date_A")
    private String formatPromiseDateA;

    @TableField("fact_date_from_supplier_arrived")
    private String factDateFromSupplierArrived;

    @TableField("promise_shipping_method")
    private String promiseShippingMethod;

    @TableField("express_no")
    private String expressNo;

    @TableField("is_split_ship")
    private String isSplitShip;

    @TableField("barcode")
    private String barcode;

    @TableField("supplier_sales_order_item")
    private String supplierSalesOrderItem;

    @TableField("id")
    private Long id;

    @TableField("promise_date_from_supplier_W")
    private String promiseDateFromSupplierW;

    @TableField("create_time")
    private Date createTime;

    @TableField("tax_rate")
    private BigDecimal taxRate;

    @TableField("src_order_no")
    private String srcOrderNo;

    @TableField("receiving_address_code")
    private String receivingAddressCode;

    @TableField("fact_manufacturer_order")
    private String factManufacturerOrder;

    @TableField("delivery_fact_batch_no")
    private String deliveryFactBatchNo;

    @TableField("order_Item")
    private String orderItem;

    @TableField("fact_date_from_supplier_W")
    private String factDateFromSupplierW;

    @TableField("ord_remaining_qty")
    private Integer ordRemainingQty;

//    @TableField("send_file_path")
//    private String sendFilePath;

    @TableField("remark")
    private String remark;

    @TableField("reason_code_from_supplier")
    private String reasonCodeFromSupplier;

    @TableField("shipping_method")
    private String shippingMethod;

    @TableField("delivery_plan_batch_no")
    private String deliveryPlanBatchNo;

    @TableField("model")
    private String model;

    @TableField("weightkg")
    private BigDecimal weightkg;

    @TableField("supplier_expinv_code")
    private String supplierExpinvCode;

    @TableField("source_system")
    private String sourceSystem;

    @TableField("fact_supplier")
    private String factSupplier;

    @TableField("trans_type")
    private String transType;

    @TableField("src_order_Item")
    private String srcOrderItem;

    @TableField("reason_from_supplier")
    private String reasonFromSupplier;

    @TableField("invoice_batch_no")
    private String invoiceBatchNo;

    @TableField("fact_manufacturer_item")
    private String factManufacturerItem;

    @TableField("quantity")
    private Integer quantity;

    @TableField("price")
    private BigDecimal price;

    @TableField("identification_code")
    private String identificationCode;

    @TableField("promise_date_from_supplier_A")
    private String promiseDateFromSupplierA;

    @TableField("create_user")
    private String createUser;

    @TableField("untax_price")
    private Double untaxPrice;

    @TableField("promise_date_accurate")
    private String promiseDateAccurate;

    @TableField("supplier_sales_order_no")
    private String supplierSalesOrderNo;

    @TableField("fact_date_from_supplier_A")
    private String factDateFromSupplierA;

    @TableField("express_company")
    private String expressCompany;

    @TableField("shipped_qty")
    private Integer shippedQty;

    @TableField("fact_manufacturer")
    private String factManufacturer;

    @TableField("skid_no")
    private String skidNo;

    @TableField("delflag")
    private Integer delflag;

    @TableField("order_no")
    private String orderNo;

    @TableField("promise_date_from_supplier_H")
    private String promiseDateFromSupplierH;

    @TableField("addtion_info")
    private String addtionInfo;

    @TableField("supplier_expinv_remark")
    private String supplierExpinvRemark;

    @TableField("purchase_unit_code")
    private String purchaseUnitCode;


}
