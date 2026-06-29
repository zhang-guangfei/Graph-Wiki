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
@TableName("ips_receive_invoice_master_from_ops")
public class IpsReceiveInvoiceMasterFromOpsDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 发票ID
     */
    @TableField("invoice_batch_no")
    private String invoiceBatchNo;

    /**
     * 箱单批次条数
     */
    @TableField("invoice_batch_barcode_num")
    private Integer invoiceBatchBarcodeNum;

    /**
     * 采购方
     */
    @TableField("purchase_unit_code")
    private String purchaseUnitCode;

    /**
     * 采购来源系统，O OPS系统，M 制造PMS系统，O_P新采购系统，I_P集团内采购系统
     */
    @TableField("source_system")
    private String sourceSystem;

    /**
     * 开票主体
     */
    @TableField("invoice_issuer")
    private String invoiceIssuer;

    /**
     * 供应商代码
     */
    @TableField("supplier_no")
    private String supplierNo;

    /**
     * 发票号
     */
    @TableField("invoice_no")
    private String invoiceNo;

    /**
     * 开票日期
     */
    @TableField("invdate")
    private Date invdate;

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
     * 发票数据状态，0数据未就绪，1数据已就绪
     */
    @TableField("state")
    private String state;

    /**
     * 发票对应总数量
     */
    @TableField("qty_sum")
    private Double qtySum;

    /**
     * 箱数
     */
    @TableField("box_qty")
    private Double boxQty;

    /**
     * 发票对应订单总数
     */
    @TableField("order_num")
    private Double orderNum;

    /**
     * 发票对应总毛重
     */
    @TableField("weight_sum")
    private Double weightSum;

    /**
     * 开始通关时间
     */
    @TableField("customs_start_date")
    private Date customsStartDate;

    /**
     * 通关结束时间
     */
    @TableField("customs_end_date")
    private Date customsEndDate;

    /**
     * 到港时间
     */
    @TableField("port_arrival_date")
    private Date portArrivalDate;

    /**
     * 关税
     */
    @TableField("customs_tax")
    private Double customsTax;

    /**
     * 运费
     */
    @TableField("shipping_cost")
    private Double shippingCost;

    /**
     * 增值税
     */
    @TableField("vat_tax")
    private Double vatTax;

    /**
     * 其他税费
     */
    @TableField("other_tax")
    private Double otherTax;

    /**
     * 消费税
     */
    @TableField("consumption_tax")
    private Double consumptionTax;

    /**
     * 币种ID
     */
    @TableField("currency_id")
    private String currencyId;

    /**
     * 币种编码
     */
    @TableField("currency_code")
    private String currencyCode;

    /**
     * 汇率
     */
    @TableField("exchange_rate")
    private Double exchangeRate;

    /**
     * 发票金额
     */
    @TableField("invoice_amount")
    private Double invoiceAmount;

    /**
     * 原始发票金额
     */
    @TableField("src_invoice_amount")
    private Double srcInvoiceAmount;

    /**
     * 原始发票号
     */
    @TableField("src_invoice_no")
    private String srcInvoiceNo;

    /**
     * 贸易方式：FOB、CIF、FCA
     */
    @TableField("trade_method")
    private String tradeMethod;

    /**
     * 付款方式
     */
    @TableField("pay_type")
    private String payType;

    /**
     * 发票类型：0 增值税发票；1普票
     */
    @TableField("invoice_type")
    private String invoiceType;

    /**
     * 补充信息
     */
    @TableField("addtion_info")
    private String addtionInfo;

    /**
     * 关务系统的发票状态
     */
    @TableField("gw_invoice_status")
    private String gwInvoiceStatus;

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


}
