package com.smc.smccloud.model.VSalesManuorder;

import com.baomidou.mybatisplus.annotation.TableField;
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
 * @since 2022-02-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("Manufacture.dbo.OPS_V_ManuOrderToSales")
public class VSalesManuorderDO implements Serializable {

    private static final long serialVersionUID = 1L;

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
    private String insertDate;

    /**
     * 营业取消原因
     */
    @TableField("SalesCancelReason")
    private String salesCancelReason;

    /**
     * 营业取消状态 0未取消  1已取消
     */
    // orderState 判断
    @TableField("SalesCancelStatus")
    private Integer salesCancelStatus;

    /**
     * 营业取消时间
     */
    @TableField("SalesCancelTime")
    private String salesCancelTime;

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

    // 写入orderSales的订单号
    @TableField("SalesOrderNo")
    private String salesOrderNo;

    // rcvDetail 的物流发货时间
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

    /**
     * 关联旧单号
     */
    @TableField("userNo")
    private String userNo;
}
