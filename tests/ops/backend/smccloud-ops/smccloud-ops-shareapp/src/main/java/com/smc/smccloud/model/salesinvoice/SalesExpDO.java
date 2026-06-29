package com.smc.smccloud.model.salesinvoice;

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
 * @since 2022-07-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("SalesExp")
public class SalesExpDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "Id", type = IdType.AUTO)
    private Long Id;

    @TableField("TradeCompanyId")
    private String tradeCompanyId;

    @TableField("ExpDate")
    private Date expDate;

    @TableField("InvoiceNo")
    private String invoiceNo;

    @TableField("ROrderNo")
    private String rOrderNo;

    @TableField("ECode")
    private String ecode;

    @TableField("ModelNo")
    private String modelNo;

    @TableField("MainQty")
    private Integer mainQty;

    @TableField("SubModel")
    private String subModel;

    @TableField("SubQty")
    private Integer subQty;

    @TableField("ProdFlag")
    private String prodFlag;

    @TableField("SalesAmt")
    private BigDecimal salesAmt;

    @TableField("CustomerNo")
    private String customerNo;

    @TableField("CustType")
    private String custType;

    @TableField("DeptNo")
    private String deptNo;

    @TableField("ExpCode")
    private String expCode;

    @TableField("ProdId")
    private String prodId;

    @TableField("EPriceAmt")
    private BigDecimal epriceAmt;

    @TableField("Price_Cal")
    private BigDecimal priceCal;

    @TableField("UserNo")
    private String userNo;

    @TableField("OrdType")
    private String ordType;

    @TableField("ApplyCode")
    private String applyCode;

    @TableField("DataSource")
    private String dataSource;

    @TableField("HRUnitId")
    private String hrUnitId;

    @TableField("remark")
    private String remark;

    @TableField("cretime")
    private Date cretime;

    @TableField("ProdType")
    private String prodType;

    @TableField("CountryCode")
    private String countryCode;

    /**
     * 出库仓库代码
     */
    @TableField("WarehouseCode")
    private String warehouseCode;

    /**
     * 仓库类别
     */
    @TableField("WarehouseType")
    private String warehouseType;

    @TableField("OwnerCompanyId")
    private String ownerCompanyId;

    @TableField("end_user")
    private String endUser;

}
