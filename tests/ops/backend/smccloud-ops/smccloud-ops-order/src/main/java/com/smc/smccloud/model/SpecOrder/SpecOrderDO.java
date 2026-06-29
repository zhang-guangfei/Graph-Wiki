package com.smc.smccloud.model.SpecOrder;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author wjw
 * @since 2021-10-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("spec_order")
public class SpecOrderDO implements Serializable {

    @TableId(value = "id",type=IdType.AUTO)
    private Integer id;

    /**
     * 订单类型
     */
    private Integer orderType;

    /**
     * 客户代码
     */
    private String customerNo;

    /**
     * 用户代码
     */
    private String userNo;

    /**
     * 型号
     */
    private String modelNo;

    /**
     * 数量
     */
    private Integer quantity;

    /**
     * 出库货币单价
     */
    private BigDecimal orgPrice;

    /**
     * 出口货币
     */
    private String orgCurrency;

    /**
     * 单价
     */
    private BigDecimal price;

    /**
     * 部门代码
     */
    private String deptNo;

    /**
     * 下单日期
     */
    private Date orderDate;

    /**
     * 备注
     */
    private String remark;

    /**
     * 交货期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date dlvDate;

    /**
     * 制造指定出荷日
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date manuDlvDate;

    /**
     * 客户订单号
     */
    private String corderNo;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 订单的SMC交易主体
     */
    @TableField("trade_companyId")
    private String tradeCompanyId;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 项号
     */
    private Integer orderItem;

    /**
     * 订单全号
     */
    private String fullOrderNo;

    @TableField(value ="update_time",fill = FieldFill.UPDATE)
    private Date updateTime;

    @TableField(value ="create_time",fill = FieldFill.INSERT)
    private Date createTime;

    private String createUser;

    private String updateUser;

    private String groupNo;

    /**
     * 客户型号
     */
    private String cproductNo;

    /**
     * 出口方式FOB
     */
    private String exportType;

    private BigDecimal eprice;//E价

    @TableField("dlvType")
    private String dlvType;//出货方式

    private String dlvEntire;

    private String receiverAddress;

    private String receiverPhone;

    private String receiverName;

    private String receiverCompany;

    private String exportWarehouse;

    private String complaintNo;

    private String province;

    private String city;

    private String district;

    private String transType;

    private String specMark;

}
