package com.smc.smccloud.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 
 * </p>
 *
 * @author smc
 * @since 2022-10-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("SalesData")
public class SalesDataDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID号
     */
    @TableId(value = "Id", type = IdType.AUTO)
    private Integer Id;

    /**
     * 发票号
     */
    @TableField("InvoiceNo")
    private String InvoiceNo;

    /**
     * 接单号
     */
    @TableField("RorderNo")
    private String RorderNo;

    /**
     * 客户代码
     */
    @TableField("CustomerNo")
    private String CustomerNo;

    /**
     * 用户代码
     */
    @TableField("UserNo")
    private String UserNo;

    /**
     * 型号
     */
    @TableField("ModelNo")
    private String ModelNo;

    /**
     * 数量
     */
    @TableField("Quantity")
    private Integer Quantity;

    /**
     * 不含税金额
     */
    @TableField("NTaxAmount")
    private BigDecimal NTaxAmount;

    /**
     * 税额
     */
    @TableField("TaxAmount")
    private BigDecimal TaxAmount;

    /**
     * 金额
     */
    @TableField("Amount")
    private BigDecimal Amount;

    /**
     * 处理日期
     */
    @TableField("OptDate")
    private String OptDate;

    /**
     * 处理状态
     */
    @TableField("OptCode")
    private String OptCode;

    /**
     * 客户类别
     */
    @TableField("CustType")
    private String CustType;

    /**
     * 部门代码
     */
    @TableField("DeptNo")
    private String DeptNo;

    /**
     * 拆分标识
     */
    @TableField("ProdFlag")
    private String ProdFlag;

    /**
     * e分类码
     */
    @TableField("ECode")
    private String ECode;

    /**
     * E价人民币
     */
    @TableField("EPriceRMB")
    private BigDecimal EPriceRMB;

    /**
     * E金额
     */
    @TableField("EPRMBAmount")
    private BigDecimal EPRMBAmount;

    /**
     * 操作区分
     */
    @TableField("OptFlag")
    private String OptFlag;

    /**
     * BNS单价
     */
    @TableField("BNSPrice")
    private BigDecimal BNSPrice;

    /**
     * BNS金额
     */
    @TableField("BNSAmount")
    private BigDecimal BNSAmount;

    /**
     * 进口单价
     */
    @TableField("ImpprodPrice")
    private BigDecimal ImpprodPrice;

    /**
     * 进口金额
     */
    @TableField("ImpProdAmount")
    private BigDecimal ImpProdAmount;

    /**
     * 订单类别
     */
    @TableField("OrdType")
    private String OrdType;

    /**
     * 发票类别:[0]增值税票;[2] 普通票
     */
    @TableField("Invtype")
    private String Invtype;

    /**
     * 客户类别（直销-1，代理-2，经销-3）
     */
    @TableField("CstmType")
    private String CstmType;

    /**
     * 开票地-0北京
     */
    @TableField("Stateflag")
    private String Stateflag;

    /**
     * 日本发票号
     */
    @TableField("InvoiceNo_JP")
    private String invoicenoJp;

    /**
     * HL编码
     */
    @TableField("HLCode")
    private String HLCode;

    /**
     * 折扣未税
     */
    @TableField("NTaxDiscountAmt")
    private BigDecimal NTaxDiscountAmt;

    /**
     * 折扣税额
     */
    @TableField("TaxDiscountAmt")
    private BigDecimal TaxDiscountAmt;

    /**
     * HR系统6位编码，临时中转用20190620
     */
    @TableField("HRUnitId")
    private String HRUnitId;

    /**
     * 扩展发票号，目前用于广州暂估导入
     */
    @TableField("ext_InvoiceNo")
    private String extInvoiceno;

    @TableField("warehouse_code")
    private String warehouseCode;

    @TableField("orderno")
    private String orderno;

    @TableField("itemno")
    private Integer itemno;

    @TableField("warehouse_type")
    private String warehouseType;

    @TableField("TradeCompanyId")
    private String TradeCompanyId;

    @TableField("gz_custtype")
    private String gzCusttype;

    @TableField("bz")
    private String bz;

    @TableField("price_enduser")
    private BigDecimal priceEnduser;

    @TableField("invoicecode")
    private String invoicecode;


}
