package com.smc.smccloud.model.SlowMovingModel;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("SlowMovingModel")
public class SlowMovingModelDO {


    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField(value = "modelNo")
    private String modelNo;
    @TableField(value = "quantity")
    private Integer quantity;
    @TableField(value = "lastInDate")
    private Date lastInDate;
    @TableField(value = "lasOutDate")
    private Date lastOutDate;
    @TableField(value = "remark")
    private String remark;
    @TableField(value = "createTime", fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(value = "createUser")
    private String createUser;
    @TableField(value = "updateTime", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    @TableField(value = "updateUser")
    private String updateUser;
    @TableField(value = "status")
    private Integer status;

    @TableField(value = "eprice")
    private BigDecimal eprice; // E价格

    @TableField(value = "lotPrice")
    private BigDecimal lotPrice; // lot价格

    @TableField(value = "warehouseCode")
    private String warehouseCode; // 仓库代码

    @TableField(value = "supplier")
    private String supplier; // 场地

    @TableField(value = "designTypeId")
    private Integer designTypeId; //产品类别

    @TableField(value = "productName")
    private String productName; // 品名

    @TableField(value = "leftQty")
    private Integer leftQty;
}
