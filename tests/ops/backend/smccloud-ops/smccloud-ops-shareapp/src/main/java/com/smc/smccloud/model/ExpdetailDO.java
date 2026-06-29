package com.smc.smccloud.model;

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
 * @since 2022-01-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("expdetail")
public class ExpdetailDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 发票号
     */
    @TableField("invoice_no")
    private String invoiceNo;

    /**
     * 出库单号
     */
    @TableField("delivery_no")
    private String deliveryNo;

    /**
     * 订单号
     */
    @TableField("order_no")
    private String orderNo;

    /**
     * 订单项号
     */
    @TableField("item_no")
    private Integer itemNo;

    /**
     * 完整订单号
     */
    @TableField("order_fno")
    private String orderFno;

    /**
     * 型号
     */
    @TableField("model_no")
    private String modelNo;

    /**
     * 发货数量
     */
    @TableField("quantity")
    private Integer quantity;

    /**
     * 货物条码
     */
    @TableField("barcode")
    private String barcode;

    /**
     * 客户代码
     */
    @TableField("customer_no")
    private String customerNo;

    /**
     * 用户代码
     */
    @TableField("user_no")
    private String userNo;

    /**
     * 发货日期
     */
    @TableField("ship_date")
    private Date shipDate;

    /**
     * 快递单号
     */
    @TableField("express_no")
    private String expressNo;

    /**
     * 快递公司
     */
    @TableField("express_company")
    private String expressCompany;

    /**
     * 发货仓库 （物流）
     */
    @TableField("warehouse_code")
    private String warehouseCode;

    /**
     * 单价
     */
    @TableField("price")
    private BigDecimal price;

    /**
     * 写入状态1
     */
    @TableField("opt_code")
    private Integer optCode;

    /**
     * 客户订单号
     */
    @TableField("corder_no")
    private String corderNo;

    /**
     * 客户型号
     */
    @TableField("cmodel_no")
    private String cmodelNo;

    /**
     * 箱号
     */
    @TableField("case_no")
    private String caseNo;

    /**
     * 重量KG
     */
    @TableField("weight")
    private Double weight;

    /**
     * 订单类型
     */
    @TableField("order_type")
    private Integer orderType;

    /**
     * 开票抽取标识，默认为0，抽取完写1
     */
    @TableField("invoice_flag")
    private String invoiceFlag;

    /**
     * 开票抽取时间，开票抽取完回写
     */
    @TableField("invoice_time")
    private String invoiceTime;

    /**
     * 签收时间
     */
    @TableField("sign_time")
    private Date signTime;

    /**
     * 出库区分
     */
    @TableField("stock_code")
    private String stockCode;

    /**
     * 客户货期
     */
    @TableField("dlv_date")
    private Date dlvDate;

    /**
     * 合同订单号
     */
    @TableField("orOrderNo")
    private String orOrderNo;

    /**
     * 发货状态
     */
    @TableField("sign_status")
    private Integer signStatus;

    /**
     * 发货当担
     */
    @TableField("sender")
    private String sender;

    /**
     * 交货地点 1-直发客户; 2-直发营业; 3-自提
     */
    @TableField("dlv_site")
    private String dlvSite;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("create_time")
    private Date createTime;

    @TableField("create_user")
    private String createUser;

    @TableField("update_user")
    private String updateUser;

    @TableField("update_time")
    private Date updateTime;

    @TableField("dept_no")
    private String deptNo;

    // 签收单号
    @TableField("sign_order_no")
    private String signOrderNo;

    // 收货地址
    @TableField("dlv_address")
    private String dlvAddress;

    // 收货人
    @TableField("ContactPsn")
    private String contactPsn;

    @TableField("end_user")
    private String endUser;

}
