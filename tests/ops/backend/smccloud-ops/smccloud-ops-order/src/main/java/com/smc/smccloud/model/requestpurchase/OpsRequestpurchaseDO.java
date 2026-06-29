package com.smc.smccloud.model.requestpurchase;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author smc
 * @since 2022-07-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ops_requestPurchase")
public class OpsRequestpurchaseDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 请购单号
     */
    @TableField("orderNo")
    private String orderNo;

    /**
     * 状态代码
     */
    @TableField("stateCode")
    private String stateCode;

    /**
     * 合并采购标识，是否合并
     */
    @TableField("mergeflag")
    private Boolean mergeflag;

    /**
     * 客户代码
     */
    @TableField("customerNo")
    private String customerNo;

    /**
     * 用户代码
     */
    @TableField("userNo")
    private String userNo;

    /**
     * 部门代码
     */
    @TableField("deptNo")
    private String deptNo;

    /**
     * 收货地所在营业所代码
     */
    @TableField("apply_dept_no")
    private String applyDeptNo;

    /**
     * 交货期问询号
     */
    @TableField("inqNo")
    private String inqNo;

    /**
     * 订单类别 同rcvdetail
     */
    @TableField("ordType")
    private String ordType;

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
     * 标准价
     */
    @TableField("stdPrice")
    private Double stdPrice;

    /**
     * 阀汇流板标识
     */
    @TableField("specMark")
    private String specMark;

    /**
     * SHIKOMI批复号
     */
    @TableField("shikomiAnswerNo")
    private String shikomiAnswerNo;

    /**
     * Shikomi残数
     */
    @TableField("shikomiRemainQty")
    private Integer shikomiRemainQty;

    /**
     * 期望货期
     */
    @TableField("hopeDeliveryDate")
    private String hopeDeliveryDate;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 营业情报号
     */
    @TableField("salesInfoNo")
    private String salesInfoNo;

    /**
     * 请购时间
     */
    @TableField("requestTime")
    private String requestTime;

    /**
     * 产品特殊规格tag
     */
    @TableField("productTag")
    private String productTag;

    /**
     * 产品特殊规则tag说明
     */
    @TableField("productTagRemark")
    private String productTagRemark;

    /**
     * 请购仓库
     */
    @TableField("requestWarehouseId")
    private String requestWarehouseId;

    /**
     * 下单日期
     */
    @TableField("orderDate")
    private String orderDate;

    /**
     * 供应商代码
     */
    @TableField("supplierId")
    private String supplierId;

    /**
     * 采购仓库
     */
    @TableField("purchaseWarehouseId")
    private String purchaseWarehouseId;

    /**
     * 运输方式
     */
    @TableField("transType")
    private String transType;

    /**
     * 指定制造出荷日
     */
    @TableField("hopeExportDate")
    private String hopeExportDate;

    /**
     * 是否为多段
     */
    @TableField("isLot")
    private Boolean isLot;

    /**
     * 拦截原因
     */
    @TableField("interceptMsg")
    private String interceptMsg;

    /**
     * 净重
     */
    @TableField("netWeight")
    private Double netWeight;

    /**
     * 1：不使用shikomi，也无需判断；0：默认，有则使用
     */
    @TableField("notUseShikomi")
    private Boolean notUseShikomi;

    /**
     * 放行原因
     */
    @TableField("releaseReason")
    private String releaseReason;

    /**
     * 是否业务已修改
     */
    @TableField("isEdited")
    private Boolean isEdited;

    /**
     * 关务系统返回的进口产品类别
     */
    @TableField("productType")
    private Integer productType;

    /**
     * 库存分类：顾客在库，战略在库、通用在库
     */
    @TableField("inventoryTypeCode")
    private String inventoryTypeCode;

    /**
     * PPL代码
     */
    @TableField("ppl")
    private String ppl;

    /**
     * 项目号
     */
    @TableField("projectCode")
    private String projectCode;

    /**
     * 客户群代码
     */
    @TableField("groupCustomerNo")
    private String groupCustomerNo;

    /**
     * 物流管理模块标识（接单反馈时调用使用）
     */
    @TableField("wmTag")
    private String wmTag;

    /**
     * 供应商型号
     */
    @TableField("supplierPartNo")
    private String supplierPartNo;

    /**
     * 进口FOB价（原币种）
     */
    @TableField("importFobPriceOriginal")
    private Double importFobPriceOriginal;

    /**
     * 币种代码
     */
    @TableField("importCurrencyId")
    private String importCurrencyId;

    /**
     * 2022-1-22 新增，业务人员备注信息
     */
    @TableField("serverremark")
    private String serverremark;

    /**
     * 2022-1-24 新增：日本在库
     */
    @TableField("supplierInventory")
    private Integer supplierInventory;

    /**
     * 客户订单号
     */
    @TableField("corderNO")
    private String corderNO;

    /**
     * 是否偶数订货
     */
    @TableField("iseven")
    private Boolean iseven;

    /**
     * 最小包装单位
     */
    @TableField("minpackunit")
    private Integer minpackunit;

    /**
     * 超长超宽型号标识，MDM导入
     */
    @TableField("nonstandard_sized_product")
    private String nonstandardSizedProduct;

    /**
     * 入库数量
     */
    @TableField("qtyImport")
    private Integer qtyImport;

    /**
     * 检验检疫类别
     */
    @TableField("inspectionType")
    private String inspectionType;

    @TableField("inventoryPropertyId")
    private Long inventoryPropertyId;

    @TableField("warehouseType")
    private String warehouseType;

    @TableField("updateTime")
    private String updateTime;

    @TableField("itemNo")
    private Integer itemNo;

    @TableField("purchaseType")
    private String purchaseType;

    @TableField("smcCode")
    private String smcCode;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("shikomiRelease")
    private String shikomiRelease;

    @TableField("industryCode")
    private String industryCode;

    @TableField("hsCode")
    private String hsCode;

    @TableField("splitItemNo")
    private Integer splitItemNo;

    @TableField("finishTime")
    private String finishTime;


}
