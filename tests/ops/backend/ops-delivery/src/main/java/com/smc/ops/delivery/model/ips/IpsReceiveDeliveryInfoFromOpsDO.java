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
 * @since 2025-06-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ips_receive_delivery_info_from_ops")
public class IpsReceiveDeliveryInfoFromOpsDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 采购单元即采购方
     */
    @TableField("purchase_unit_code")
    private String purchaseUnitCode;

    /**
     * SMCcode
     */
    @TableField("smccode")
    private String smccode;

    /**
     * 收货地址编码
     */
    @TableField("receiving_address_code")
    private String receivingAddressCode;

    /**
     * 业务系统源id，OPS，PMS，IPS、AIMS、JP_AS400、O_P新采购系统、ThreeC 海外三国
     */
    @TableField("source_system")
    private String sourceSystem;

    /**
     * 采购单号
     */
    @TableField("order_no")
    private String orderNo;

    /**
     * 采购项号
     */
    @TableField("order_Item")
    private String orderItem;

    /**
     * 交付状态，建议参考日本
     */
    @TableField("identification_code")
    private String identificationCode;

    /**
     * 计划交付批次号
     */
    @TableField("delivery_plan_batch_no")
    private String deliveryPlanBatchNo;

    /**
     * 单据处理说明
     */
    @TableField("order_status_description")
    private String orderStatusDescription;

    /**
     * 型号
     */
    @TableField("model")
    private String model;

    /**
     * 采购数量
     */
    @TableField("quantity")
    private Double quantity;

    /**
     * 残数
     */
    @TableField("ord_remaining_qty")
    private Double ordRemainingQty;

    /**
     * 完纳标识：0未完纳；1已完纳
     */
    @TableField("is_checked")
    private String isChecked;

    /**
     * 发运数量
     */
    @TableField("shipped_qty")
    private Double shippedQty;

    /**
     * 供应商接单号
     */
    @TableField("supplier_sales_order_no")
    private String supplierSalesOrderNo;

    /**
     * 供应商接单项号
     */
    @TableField("supplier_sales_order_item")
    private String supplierSalesOrderItem;

    /**
     * 供应商操作时间
     */
    @TableField("supplier_operate_time")
    private Date supplierOperateTime;

    /**
     * 是否分纳发货
     */
    @TableField("is_split_ship")
    private String isSplitShip;

    /**
     * 供应商出库区分
     */
    @TableField("supplier_expinv_code")
    private String supplierExpinvCode;

    /**
     * 供应商出库区分说明
     */
    @TableField("supplier_expinv_remark")
    private String supplierExpinvRemark;

    /**
     * 拟定发货方式
     */
    @TableField("promise_shipping_method")
    private String promiseShippingMethod;

    /**
     * 实际供应商
     */
    @TableField("fact_supplier")
    private String factSupplier;

    /**
     * 实际生产商
     */
    @TableField("fact_manufacturer")
    private String factManufacturer;

    /**
     * 生产商接单号
     */
    @TableField("fact_manufacturer_order")
    private String factManufacturerOrder;

    /**
     * 生产商项号
     */
    @TableField("fact_manufacturer_item")
    private String factManufacturerItem;

    /**
     * 供应商预计完工日,2025-02-06修改为字符串类型
     */
    @TableField("promise_date_from_supplier_H")
    private String promiseDateFromSupplierH;

    /**
     * 供应商预计入库日,2025-02-06修改为字符串类型
     */
    @TableField("promise_date_from_supplier_W")
    private String promiseDateFromSupplierW;

    /**
     * 供应商预计发运日,2025-02-06修改为字符串类型
     */
    @TableField("promise_date_from_supplier_A")
    private String promiseDateFromSupplierA;

    /**
     * 供应商预计送达日
     */
    @TableField("promise_date_from_supplier_arrived")
    private String promiseDateFromSupplierArrived;

    /**
     * 供应商返信理由分类码
     */
    @TableField("reason_code_from_supplier")
    private String reasonCodeFromSupplier;

    /**
     * 供应商返信理由
     */
    @TableField("reason_from_supplier")
    private String reasonFromSupplier;

    /**
     * 计划交付批次号
     */
    @TableField("delivery_fact_batch_no")
    private String deliveryFactBatchNo;

    /**
     * 供应商实际完工日
     */
    @TableField("fact_date_from_supplier_H")
    private String factDateFromSupplierH;

    /**
     * 供应商实际入库日
     */
    @TableField("fact_date_from_supplier_W")
    private String factDateFromSupplierW;

    /**
     * 供应商实际发运日
     */
    @TableField("fact_date_from_supplier_A")
    private String factDateFromSupplierA;

    /**
     * 供应商实际送达日
     */
    @TableField("fact_date_from_supplier_arrived")
    private String factDateFromSupplierArrived;

    /**
     * 实际发货方式
     */
    @TableField("shipping_method")
    private String shippingMethod;

    /**
     * 承运商
     */
    @TableField("express_company")
    private String expressCompany;

    /**
     * 运单号
     */
    @TableField("express_no")
    private String expressNo;

    /**
     * 运输方式
     */
    @TableField("trans_type")
    private String transType;

    /**
     * 形式发票号
     */
    @TableField("invoice_no")
    private String invoiceNo;

    /**
     * 含税单价
     */
    @TableField("price")
    private Double price;

    /**
     * 税率
     */
    @TableField("tax_rate")
    private Double taxRate;

    /**
     * 未税单价
     */
    @TableField("untax_price")
    private Double untaxPrice;

    /**
     * 箱号
     */
    @TableField("skid_no")
    private String skidNo;

    /**
     * barcode
     */
    @TableField("barcode")
    private String barcode;

    /**
     * 重量
     */
    @TableField("weightkg")
    private Double weightkg;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 附加信息
     */
    @TableField("addtion_info")
    private String addtionInfo;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 创建者
     */
    @TableField("create_user")
    private String createUser;

    /**
     * 删除标识：0正常；1删除
     */
    @TableField("delflag")
    private Integer delflag;

    /**
     * 发票批次号
     */
    @TableField("invoice_batch_no")
    private String invoiceBatchNo;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;


}
