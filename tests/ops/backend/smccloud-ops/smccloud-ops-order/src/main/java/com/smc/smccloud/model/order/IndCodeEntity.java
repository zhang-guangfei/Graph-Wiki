package com.smc.smccloud.model.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author lyc
 * @Date 2022/8/5 10:46
 * @Descripton TODO
 */
@Data
public class IndCodeEntity {

    // 完整订单号(业务号)
    private String orderNo;
    // 客户
    private String customerNo;
    // 客户名称
    private String customerName;

    // 用户代码
    private String userNo;
    // 用户名称
    private String userName;

    // 最终用户
    private String endUser;
    // 最终用户名称
    private String endUserName;

    // 订单时间
    private Date rordDate;

    // 型号
    private String modelNo;

    // 数量
    private Integer quantity;

    @ApiModelProperty(value = "单价")
    private BigDecimal price;

    @ApiModelProperty(value = "用户单价")
    private BigDecimal priceUser;

    @ApiModelProperty(value = "最终用户单价")
    private BigDecimal priceEndUser;

    // e率
    private BigDecimal discount;

    // 客户交货期
    private Date dlvDate;
    // 原交货期 --- 客户交货期
    private Date cdlvDate;

    // 竞争对手
    private String opponent;

    // BOM号
    private String pplNo;

    // 项目号
    private String projectNo;

    // shikomi_no号
    private String shikomiNo;

    // 客户po号
    private String corderNo;

    // 处理状态
    private Integer status;
    // 处理状态名称
    private String statusName;

    private String cproductNo;

    private BigDecimal ePrice;
    private BigDecimal taxRate;

    private BigDecimal ntaxPice;

    private String mainOrderNo;

    // 出库区分类别（N：在库 ，T：在途 ，CG： 采购）
    private String stockType;
    // 出库区分类别名称
    private String stockTypeName;

    // 出库代码
    private String stockCode;

    // 出库区分的库存类别：顾客在库，战略在库、通用在库;出库区分
    private String inventoryTypeCode;
    // 出库区分的库存类别名称
    private String inventoryTypeCodeName;

    // 备注
    private String remark;

    // 报价单
    private String quotationNo;

    private String hlCode;
    // hl名称
    private String hlCodeName;

    private String hrUnitId;

    private Boolean intercept;

    private Date interceptTime;

    private String rorderNo;

    // 客户担当
    private String empSale;
    
    // 客户担当名称
    private String empSaleName;

    private String employeeNo;

    private String createId;

    private String createUserName;

    private Date shipTime; // 发货日期

    private String productName; // 产品名称

    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date expectedDeliveryTime;

}
