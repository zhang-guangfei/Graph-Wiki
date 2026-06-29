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
 * @author smc
 * @since 2022-04-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("salesinvoice_mid_info")
public class SalesInvoiceMidInfoDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 退货手续费
     */
    @TableField("feerate")
    private BigDecimal feeRate;

    /**
     * TH退货，YS样品转销售
     */
    @TableField("type")
    private String type;

    /**
     * 更新时间
     */
    @TableField(value = "updatetime", fill = FieldFill.UPDATE)
    private Date updateTime;

    /**
     * 是否新写入，未处理数据
     */
    @TableField("isnew")
    private String isNew;

    /**
     * 退货申请号
     */
    @TableField("applyno")
    private String applyNo;

    /**
     * 可退货数量
     */
    @TableField("canbackquantity")
    private Integer canBackQuantity;

    /**
     * 是否退回专备 0不退回 1 退回
     */
    @TableField("tocustomerstock")
    private String toCustomerStock;

    /**
     * 用于有手续费的订单，自动生成一条费用类的发票数据，用于记录新发票
     */
    @TableField("neworderno")
    private String newOrderNo;

    @TableField("modelno")
    private String modelNo;

    @TableField("customerno")
    private String customerNo;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("remark")
    private String remark;

    @TableField("inserttime")
    private Date insertTime;

    @TableField("tradecompanyid")
    private String tradeCompanyId;

    @TableField("quantity")
    private Integer quantity;

    @TableField("userno")
    private String userNo;

    @TableField("price")
    private BigDecimal price;

    @TableField("deptno")
    private String deptNo;

    @TableField("orderno")
    private String orderNo;

    @TableField("state_code")
    private Integer stateCode;

    @TableField("applyno_item")
    private Integer applynoItem;

    @TableField("rorder_no")
    private String rorderNo;

    @TableField("rorder_item")
    private Integer rorderItem;


    @TableField("end_user")
    private String endUser;

    @TableField("feeamount")
    private BigDecimal feeamount;

}
