package com.smc.smccloud.model.VSalesManuorder;

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
 * @author smc
 * @since 2022-02-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class VSalesManuorderVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * 型号
     */
    private String modelNo;

    /**
     * 数量
     */
    private Integer quantity;

    /**
     * 国别代码，营业不会使用
     */
    private String SMCCode;

    /**
     * 交货期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dlvydate;

    /**
     * 运输方式
     */
    private String transType;

    /**
     * 工序代码
     */
    private String processcode;

    /**
     * 制造订单号
     */
    private String orderno;

    /**
     * 处理状态
     */
    private String optStatus;

    /**
     * 订货区分
     */
    private String expInvCode;

    /**
     * 发票区分：生产：0； 非生产：1
     */
    private Integer produce;

    /**
     * 阀板标识
     */
    private Integer isGroup;

    /**
     * 客户名称
     */
    private String cstmName;

    /**
     * 发货地址
     */
    private String dlvAddress;

    /**
     * 联系人
     */
    private String contactPsn;

    /**
     * 电话
     */
    private String teleNo;

    /**
     * 项号
     */
    private String itemno;

    /**
     * 客户编码
     */
    private String customerNo;

    private String insertDate;

    /**
     * 营业取消原因
     */
    private String salesCancelReason;

    /**
     * 营业取消状态 0未取消  1已取消
     */
    private Integer salesCancelStatus;

    /**
     * 营业取消时间
     */
    private String salesCancelTime;

    /**
     * 营业处理备注说明
     */
    private String salesRemark;

    /**
     * 营业处理状态码：0订单待受理，1订单受理中，2订单受理成功 ，3订单受理失败
     */
    private Integer salesStatus;

    /**
     * 营业反馈时间
     */
    private Date salesUpdateTime;

    private String salesOrderNo;

    private Date salesDeliveryTime;

    private String salesordernoJp;

    /**
     * 制造品名
     */
    private String productName;

    /**
     * 未税价格
     */
    private BigDecimal unitprice;

    /**
     * 税率
     */
    private BigDecimal taxRate;

    /**
     * 含税价格
     */
    private BigDecimal unitPricewithTax;

    /**
     * 关联旧单号
     */
    private String userNo;

    private String endUser;

    /**
     * 采购方代码
     */
    private String purchaseUnitCode;

    /**
     * PSI对应主单号和项号
     */
    private String extOrderNo;
    private String extOrderItem;

    private String remark;

}
