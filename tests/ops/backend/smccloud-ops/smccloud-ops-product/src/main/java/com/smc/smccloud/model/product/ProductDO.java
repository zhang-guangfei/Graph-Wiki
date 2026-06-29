package com.smc.smccloud.model.product;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * Author: B90034
 * Date: 2022-01-26 15:49
 * Description:
 */
@Data
@TableName("product")
public class ProductDO {

    @TableId(value = "ModelNo", type = IdType.INPUT)
    private String modelNo;

    @TableField("ECode")
    private String eCode;

    @TableField("ChineseName")
    private String chineseName;

    @TableField("EnglishName")
    private String englishName;

    @TableField("TypeId")
    private String typeId;

    @TableField("ClassifyCode1")
    private String classifyCode1;

    @TableField("ClassifyCode2")
    private String classifyCode2;

    @TableField("ClassifyCode3")
    private String classifyCode3;

    @TableField("CompetitivenessID")
    private String competitivenessId;

    @TableField("DesignTypeId")
    private String designTypeId;

    @TableField(exist = false)
    private String category;

    @TableField(exist = false)
    private String isRestrict;

    @TableField("is_eos")
    private Boolean isEos;

    @TableField(exist = false)
    private String isError;

    @TableField(exist = false)
    private String longLeadTime;

    @TableField(exist = false)
    private String isOverWeight;

    @TableField(exist = false)
    private String isOverLength;

    @TableField(exist = false)
    private String ShipType;

    @TableField(exist = false)
    private String bucklingSign;

    @TableField(exist = false)
    private String manifoldSign;

    @TableField(exist = false)
    private String isATEX;

    @TableField("is_Even")
    private String isEven;

    @TableField("is_Security")
    private String isSecurity;

    @TableField("ROHS")
    private String ROHS;

    @TableField(exist = false)
    private String largeSize;

    @TableField("is_deleted")
    private Boolean isDeleted;

    @TableField(exist = false)
    private Date createdDate;

    @TableField(exist = false)
    private String createdUser;

    @TableField(exist = false)
    private Date UpdateTime;

    @TableField(exist = false)
    private String updatedUser;

    /**
     * 最小包装数量
     */
    @TableField("min_pack_unit")
    private Integer minPackUnit;

    @TableField("Remark")
    private String remark;

    @TableField("OuterBoxPartNo")
    private String outerBoxPartNo;

    @TableField("OuterBoxQuantity")
    private Integer outerBoxQuantity;

    // 超长超宽型号标识，MDM导入
    @TableField("nonstandard_sized_product")
    private String nonstandardSizedProduct;
}
