package com.smc.smccloud.model.invoice;

import com.baomidou.mybatisplus.annotation.*;
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
 * @author B90034
 * @since 2021-12-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("imp_invoice_master")
public class ImpInvoiceMasterDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id")
    private Long id;

    /**
     * 原始发票号
     */
    @TableField("invoice_no")
    private String invoiceNo;

    @TableField("supplier_code")
    private String supplierCode;

    /**
     * 数量
     */
    @TableField("total_qty")
    private Integer totalQty;

    /**
     * 1-预计到货;
     * 2-已到货;
     * 9-删除;
     */
    @TableField("status")
    private Integer status;

    /**
     * 金额
     */
    @TableField("amount")
    private BigDecimal amount;

    /**
     * 包装件数
     */
    @TableField("box_qty")
    private Integer boxQty;

    /**
     * 报关单号
     */
    @TableField("declaration_no")
    private String declarationNo;

    @TableField("order_qty")
    private Integer orderQty;

    /**
     * 出港日期
     */
    @TableField("ship_date")
    private Date shipDate;

    /**
     * 通关时间
     */
    @TableField("customs_date")
    private Date customsDate;

    /**
     * 毛重
     */
    @TableField("gross_weight")
    private Double grossWeight;

    /**
     * 净重
     */
    @TableField("weight")
    private Double weight;

    /**
     * 关税
     */
    @TableField("customs_fee")
    private BigDecimal customsFee;

    /**
     * 增值税
     */
    @TableField("vat_fee")
    private BigDecimal vatFee;

    /**
     * 其他费用
     */
    @TableField("other_fee")
    private BigDecimal otherFee;

    /**
     * 货币代码
     */
    @TableField("currency")
    private String currency;

    /**
     * 汇率
     */
    @TableField("exchange_rate")
    private BigDecimal exchangeRate;

    /**
     * 人民币金额
     */
    @TableField("amount_rmb")
    private BigDecimal amountRmb;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableField("update_user")
    private String updateUser;

    @TableField("create_user")
    private String createUser;

    /**
     * 运输方式
     */
    @TableField("trans_type")
    private String transType;

    @TableField("trans_fee")
    private BigDecimal transFee;

    /**
     * 预计到货日期- expectReceiptDate
     */
    @TableField("prearrive_Date")
    private Date preArriveDate;

    @TableField("remark")
    private String remark;
    /**
     * 发票号
     */
    @TableField("cinvoice_no")
    private String cInvoiceNo;

    /**
     * 发货地(可用于区分供应商)
     */
    @TableField("shipment")
    private String shipment;

    /**
     * 到货地(可用于区分收货仓库)
     */
    @TableField("plantMark")
    private String plantMark;

    @TableField("invoice_date")
    private Date invoiceDate;

    /**
     * 到货仓库
     */
    @TableField("arrived_warehouse_code")
    private String arrivedWarehouseCode;

    /**
     * 最终收货仓库
     */
    @TableField("receive_warehouse_code")
    private  String receiveWarehouseCode;

    /**
     * 实际达到/收货日期
     */
    @TableField("arrive_date")
    private Date arriveDate;

    @TableField("receive_date")
    private Date receiveDate;

    /**
     * 数据导入状态: 1-已导入发票数据, 2-已导入分包数据，3-两者都导入
     */
    @TableField("data_type")
    private Integer dataType;

    @TableField("bargain_type")
    private String bargainType;

    @TableField("excise_tax")
    private BigDecimal exciseTax;

    @TableField("gw_stateCode")
    private String gwStateCode;


    // 到港时间
    @TableField("port_arrivedate")
    private Date portArrivedate;

    // 报关状态
    @TableField("gw_state")
    private String gwState;

    @TableField("pay_day")
    private Integer payDay;

    //  海关发出时间
    @TableField("customs_shipdate")
    private Date customsShipdate;

    @TableField("ship_amount")
    private BigDecimal  shipAmount;

    @TableField("confirm_date")
    private Date confirmDate;

    /**
     * 发票类型（2064）: 1-日本; 2-三国; 3-国内工厂; 4-三方; 5-国内增值发票; 9-其他
     */
    @TableField("invoice_type")
    private Integer invoiceType;
}
