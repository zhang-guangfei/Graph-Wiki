package com.smc.ops.delivery.model.samplebal;

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
public class OpsSamplebalDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 发票号
     */
    @TableField("InvoiceNo")
    private String invoiceno;

    /**
     * 客户代码
     */
    @TableField("CustomerNo")
    private String customerno;

    /**
     * 接单号
     */
    @TableField("ROrderNo")
    private String rorderno; // orderNo

    /**
     * 型号
     */
    @TableField("ModelNo")
    private String modelno;

    /**
     * 数量
     */
    @TableField("Quantity")
    private Integer quantity;

    /**
     * 子项型号
     */
    @TableField("SubModelNo")
    private String submodelno;

    /**
     * 子项数量
     */
    @TableField("SubQty")
    private Integer subqty;

    /**
     * 结转部门
     */
    @TableField("DeptDesc")
    private String deptdesc;  // 结转部门 描述(名称)

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
    private BigDecimal taxamount;

    /**
     * 处理日期
     */
    @TableField("OptDate")
    private Date optdate;

    /**
     * 样品申请方式
     */
    @TableField("AppType")
    private String apptype;

    /**
     * 结转方式
     */
    @TableField("BalType")
    private String baltype;

    /**
     * 拆分标识
     */
    @TableField("ProdFlag")
    private String prodflag;

    /**
     * 工厂产品标识
     */
    @TableField("ProdCode")
    private String prodcode;

    /**
     * 处理状态
     */
    @TableField("OptCode")
    private String optcode;

    /**
     * E分类码
     */
    @TableField("ECode")
    private String ecode;

    /**
     * 备注
     */
    @TableField("Remark")
    private String remark;

    /**
     * 营业所
     */
    @TableField("DeptNo")
    private String deptno;

    /**
     * 出库日期
     */
    @TableField("Expdate")
    private Date expdate;

    /**
     * 中文名称
     */
    @TableField("ModelInChn")
    private String modelinchn;

    /**
     * 入库日期
     */
    @TableField("InDate")
    private Date indate;

    /**
     * 订单类别
     */
    @TableField("OrdType")
    private String ordtype;

    /**
     * 申请号
     */
    @TableField("ApplyCode")
    private String applycode; // applyNo

    @TableField(value = "CreateTime",fill = FieldFill.INSERT )
    private Date createtime;

    @TableField("OptTime")
    private Date opttime;

    @TableId(value = "Id", type = IdType.AUTO)
    private Long id;

    @TableField("Username")
    private String username;

    @TableField("Price_apply")
    private BigDecimal priceApply;

    @TableField("StockCode")
    private String stockcode;

    /**
     * 展览展示品实物所在营业所
     */
    @TableField("RcvDeptNo")
    private String rcvdeptno;

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
