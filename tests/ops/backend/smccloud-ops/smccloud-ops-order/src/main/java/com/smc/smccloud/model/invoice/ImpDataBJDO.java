package com.smc.smccloud.model.invoice;

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
 * @Author edp02 @Date 2022/4/9 10:36
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ImpData_ops")
public class ImpDataBJDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("InvoiceNo")
    private String invoiceNo;

    @TableField("CaseNo")
    private String caseNo;

    @TableField("OrderNo")
    private String orderNo;

    @TableField("CustomerNo")
    private String customerNo;

    @TableField("ModelNo")
    private String modelNo;

    @TableField("Quantity")
    private Integer quantity;

    @TableField("TransType")
    private String transType;

    @TableField("ImpDate")
    private Date impDate;

    @TableField("ProdCountry")
    private String prodCountry;

    @TableField("OptCode")
    private String optCode;

    @TableField("StateFlag")
    private String stateFlag;

    @TableField("ExpDesc")
    private String expDesc;

    @TableField("BarCode")
    private String barCode;

    @TableField("SmcCode")
    private String smcCode;

    @TableField("GZOrderNo")
    private String gzOrderNo;

    @TableField("DeliveryNO")
    private String deliveryNO;

    @TableField("OrderPurchaseEntity")
    private String orderPurchaseEntity;

    @TableField("source_stockId")
    private String sourcestockId;

    @TableField("logisticsCode")
    private String logisticsCode;

    @TableField("expressCode")
    private String expressCode;

    @TableField("InsertTime")
    private Date InsertTime;

    @TableField("ExtractTime")
    private Date extractTime;

    @TableField("Price")
    private BigDecimal price;

    @TableField("Amount")
    private BigDecimal amount;

    @TableField("AddressId")
    private String addressId;

    @TableField("ShipDate")
    private Date shipDate;

    @TableField("RoHS")
    private String roHS;

    @TableField("UnitWeight")
    private Double unitWeight;

}
