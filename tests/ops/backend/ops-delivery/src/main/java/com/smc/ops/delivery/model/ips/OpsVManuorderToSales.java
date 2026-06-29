package com.smc.ops.delivery.model.ips;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("Manufacture.dbo.OPS_V_ManuOrderToSales")
public class OpsVManuorderToSales {

    @TableField("id")
    private Integer id;

    /**
     * 型号
     */
    @TableField("modelNo")
    private String modelNo;

    /**
     * 数量
     */
    @TableField("quantity")
    private Integer quantity;

    /**
     * 国别代码，营业不会使用
     */
    @TableField("SMCCode")
    private String SMCCode;

    /**
     * 交货期
     */
    @TableField("Dlvydate")
    private Date dlvydate;

    /**
     * 运输方式
     */
    @TableField("TransType")
    private String transType;

    /**
     * 工序代码
     */
    @TableField("processcode")
    private String processcode;

    /**
     * 制造订单号
     */
    @TableField("Orderno")
    private String orderno;

    /**
     * 处理状态
     */
    @TableField("optStatus")
    private String optStatus;

    /**
     * 订货区分
     */
    @TableField("ExpInvCode")
    private String expInvCode;

    /**
     * 发票区分：生产：0； 非生产：1
     */
    @TableField("Produce")
    private Integer produce;

    /**
     * 阀板标识
     */
    @TableField("isGroup")
    private Integer isGroup;

    /**
     * 客户名称
     */
    @TableField("cstmName")
    private String cstmName;

    /**
     * 发货地址
     */
    @TableField("DlvAddress")
    private String dlvAddress;

    /**
     * 联系人
     */
    @TableField("contactPsn")
    private String contactPsn;

    /**
     * 电话
     */
    @TableField("TeleNo")
    private String teleNo;

    /**
     * 项号
     */
    @TableField("itemno")
    private String itemno;

    /**
     * 客户编码
     */
    @TableField("customerNo")
    private String customerNo;

    @TableField("insertDate")
    private Date insertDate;

    /**
     * 营业取消原因
     */
    @TableField("SalesCancelReason")
    private String salesCancelReason;

    /**
     * 营业取消状态 0未取消  1已取消
     */
    @TableField("SalesCancelStatus")
    private Integer salesCancelStatus;

    /**
     * 营业取消时间
     */
    @TableField("SalesCancelTime")
    private Date salesCancelTime;

    /**
     * 营业处理备注说明
     */
    @TableField("SalesRemark")
    private String salesRemark;

    /**
     * 营业处理状态码：0订单待受理，1订单受理中，2订单受理成功 ，3订单受理失败
     */
    @TableField("SalesStatus")
    private Integer salesStatus;

    /**
     * 营业反馈时间
     */
    @TableField("SalesUpdateTime")
    private Date salesUpdateTime;

    @TableField("SalesOrderNo")
    private String salesOrderNo;

    @TableField("SalesDeliveryTime")
    private Date salesDeliveryTime;

    @TableField("SalesOrderNo_JP")
    private String salesordernoJp;

    /**
     * 制造品名
     */
    @TableField("product_name")
    private String productName;

    /**
     * 未税价格
     */
    @TableField("unitprice")
    private BigDecimal unitprice;

    /**
     * 税率
     */
    @TableField("taxRate")
    private BigDecimal taxRate;

    /**
     * 含税价格
     */
    @TableField("unitPricewithTax")
    private BigDecimal unitPricewithTax;

    @TableField("userNo")
    private String userNo;

    @TableField("expectedArrivalDate")
    private Date expectedArrivalDate;


    @TableField("expectedArrivalinvNo")
    private String expectedArrivalinvNo;
}
