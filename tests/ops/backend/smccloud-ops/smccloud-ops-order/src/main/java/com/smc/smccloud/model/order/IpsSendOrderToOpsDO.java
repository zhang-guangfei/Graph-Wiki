package com.smc.smccloud.model.order;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * ips新接单
 * </p>
 *
 * @author smc
 * @since 2024-12-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ips_send_order_to_ops")
public class IpsSendOrderToOpsDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("id")
    private Long id;

    @TableField("purchase_unit_code")
    private String purchaseUnitCode;

    @TableField("supplier_no")
    private String supplierNo;

    @TableField("smccode")
    private String smccode;

    @TableField("order_no")
    private String orderNo;

    @TableField("item_no")
    private String itemNo;

    @TableField("exp_supplier_no")
    private String expSupplierNo;

    @TableField("material_no")
    private String materialNo;

    @TableField("model_no")
    private String modelNo;

    @TableField("model_name")
    private String modelName;

    @TableField("qty")
    private Integer qty;

    @TableField("unit")
    private String unit;

    @TableField("purchase_date")
    private Date purchaseDate;

    @TableField("specified_delivery_date")
    private Date specifiedDeliveryDate;

    @TableField("specified_delivery_way")
    private String specifiedDeliveryWay;

    @TableField("specified_delivery_carrier")
    private String specifiedDeliveryCarrier;

    @TableField("purchase_order_type")
    private String purchaseOrderType;

    @TableField("purchase_business_type")
    private String purchaseBusinessType;

    @TableField("request_department_no")
    private String requestDepartmentNo;

    @TableField("request_department_name")
    private String requestDepartmentName;

    @TableField("request_person_name")
    private String requestPersonName;

    @TableField("request_person_no")
    private String requestPersonNo;

    @TableField("request_person_tele")
    private String requestPersonTele;

    @TableField("request_person_email")
    private String requestPersonEmail;

    @TableField("purchase_time")
    private Date purchaseTime;

    @TableField("plantmark_address")
    private String plantmarkAddress;

    @TableField("receiving_warehouse")
    private String receivingWarehouse;

    @TableField("request_address")
    private String requestAddress;

    @TableField("receiving_address")
    private String receivingAddress;

    @TableField("receiving_connector")
    private String receivingConnector;

    @TableField("receiving_department_teleno")
    private String receivingDepartmentTeleno;

    @TableField("batch_no")
    private String batchNo;

    @TableField("content_info")
    private String contentInfo;

    @TableField("create_time")
    private Date createTime;

    @TableField("create_user")
    private String createUser;

    @TableField("is_need_inspect")
    private String isNeedInspect;


}
