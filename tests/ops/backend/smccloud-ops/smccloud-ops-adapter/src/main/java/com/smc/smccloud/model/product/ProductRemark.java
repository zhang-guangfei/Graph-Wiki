package com.smc.smccloud.model.product;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * Author: B90034
 * Date: 2022-05-09 09:24
 * Description: 型号备注信息
 */
@Data
public class ProductRemark {

    @TableId(value = "ModelNo", type = IdType.INPUT)
    private String modelNo;

//    @TableField("ECode")
//    private String ECode;
//
//    @TableField("ChineseName")
//    private String chineseName;
//
//    @TableField("EnglishName")
//    private String englishName;
//
//    @TableField("TypeId")
//    private String typeId;
//
//    @TableField("ClassifyCode1")
//    private String classifyCode1;
//
//    @TableField("ClassifyCode2")
//    private String classifyCode2;
//
//    @TableField("ClassifyCode3")
//    private String classifyCode3;
//
//    @TableField("CompetitivenessID")
//    private String competitivenessID;
//
//    @TableField("DesignTypeId")
//    private String designTypeId;
//
//    @TableField("category")
//    private String category;
//
//    @TableField("is_restrict")
//    private String isRestrict;

//    /**
//     * 是否收敛品
//     */
//    @TableField("is_eos")
//    private String isEos;

    /**
     * 是否错误型号
     */
    @TableField("is_error")
    private String isError;

//    @TableField("long_lead_time")
//    private String longLeadTime;
//
//    @TableField("is_overweight")
//    private String isOverWeight;
//
//    @TableField("is_overlength")
//    private String isOverLength;
//
//    @TableField("ShipType")
//    private String shipType;
//
//    @TableField("bucklingSign")
//    private String bucklingSign;
//
//    @TableField("manifoldsign")
//    private String manifoldSign;
//
//    @TableField("is_ATEX")
//    private String isATEX;

    /**
     * 是否偶数订货型号
     */
    @TableField("is_Even")
    private String isEven;

//    @TableField("is_Security")
//    private String isSecurity;
//
//    @TableField("ROHS")
//    private String ROHS;
//
//    @TableField("Large_size")
//    private String largeSize;
//
//    @TableField("is_deleted")
//    private Boolean isDeleted;
//
//    @TableField("created_date")
//    private Date createdDate;
//
//    @TableField("created_user")
//    private String createdUser;
//
//    @TableField("UpdateTime")
//    private Date UpdateTime;
//
//    @TableField("updated_user")
//    private String updatedUser;
//
//    @TableField("min_pack_unit")
//    private Integer minPackUnit;
//
//    @TableField("Remark")
//    private String remark;
//
//    @TableField("OuterBoxPartNo")
//    private String outerBoxPartNo;
//
//    @TableField("OuterBoxQuantity")
//    private Integer outerBoxQuantity;
//
//    @TableField("nonstandard_sized_product")
//    private String nonstandardSizedProduct;

}
