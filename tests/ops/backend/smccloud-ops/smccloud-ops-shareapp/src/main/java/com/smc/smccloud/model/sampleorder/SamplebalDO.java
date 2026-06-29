package com.smc.smccloud.model.sampleorder;

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
 * @since 2021-11-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sample_bal")
public class SamplebalDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 发票号
     */
    @TableField("InvoiceNo")
    private String invoiceNo;

    /**
     * 客户代码
     */
    @TableField("CustomerNo")
    private String customerNo;

    /**
     * 接单号
     */
    @TableField("ROrderNo")
    private String rorderNo; // orderNo

    /**
     * 型号
     */
    @TableField("ModelNo")
    private String modelNo;

    /**
     * 数量
     */
    @TableField("Quantity")
    private Integer quantity;

    /**
     * 子项型号
     */
    @TableField("SubModelNo")
    private String subModelNo;

    /**
     * 子项数量
     */
    @TableField("SubQty")
    private Integer subQty;

    /**
     * 结转部门
     */
    @TableField("DeptDesc")
    private String deptDesc;  // 结转部门 描述(名称)

    /**
     * 单价
     */
    @TableField("Price")
    private BigDecimal price;

    /**
     * 金额
     */
    @TableField("Amount")
    private BigDecimal amount;

    /**
     * 税额
     */
    @TableField("TaxAmount")
    private BigDecimal taxAmount;

    /**
     * 处理日期
     */
    @TableField("OptDate")
    private Date optDate;

    /**
     * 样品申请方式
     */
    @TableField("AppType")
    private String appType;

    /**
     * 结转方式
     */
    @TableField("BalType")
    private String balType;

    /**
     * 拆分标识
     */
    @TableField("ProdFlag")
    private String prodFlag;

    /**
     * 工厂产品标识
     */
    @TableField("ProdCode")
    private String prodCode;

    /**
     * 处理状态
     */
    @TableField("OptCode")
    private String optCode;

    /**
     * E分类码
     */
    @TableField("ECode")
    private String eCode;

    /**
     * 备注
     */
    @TableField("Remark")
    private String remark;

    /**
     * 营业所
     */
    @TableField("DeptNo")
    private String deptNo;

    /**
     * 出库日期
     */
    @TableField("Expdate")
    private Date expDate;

    /**
     * 中文名称
     */
    @TableField("ModelInChn")
    private String modelinchn;

    /**
     * 入库日期
     */
    @TableField("InDate")
    private Date inDate;

    /**
     * 订单类别
     */
    @TableField("OrdType")
    private String ordType;

    /**
     * 申请号
     */
    @TableField("ApplyCode")
    private String applyCode; // applyNo

    @TableField(value = "CreateTime",fill = FieldFill.INSERT )
    private Date createtime;

    @TableField("OptTime")
    private Date optTime;

    @TableId(value = "Id", type = IdType.AUTO)
    private Long id;

    @TableField("Username")
    private String userName;

    @TableField("Price_apply")
    private BigDecimal priceApply;

    @TableField("StockCode")
    private String stockCode;

    @TableField(exist = false)
    private String deptName;

    @TableField(exist = false)
    private String hlCodeName;

    /**
     * 展览展示品实物所在营业所
     */
    @TableField("RcvDeptNo")
    private String rcvDeptNo;

    @TableField(exist = false)
    private String rcvDeptName;

    // 索赔号
    @TableField("claim_no")
    private String claimNo;

    // 索赔金额
    @TableField("claim_amount")
    private String claimAmount;

    // 索赔公司
    @TableField("express_company")
    private String expressCompany;

    @TableField("source")
    private String source;

    @TableField("warehouse_code")
    private String warehouseCode;

    @TableField("expdetail_id")
    private String expdetailId;

    @TableField("user_no")
    private String userNo;
}
