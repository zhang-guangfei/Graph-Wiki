package com.smc.smccloud.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName(value = "product_price")
public class ProductPriceDO {

    @TableField(value = "modelNo")
    private  String modelNo;
    @TableField(value = "minQuantity")
    private  Integer minQuantity;
    @TableField(value = "eprice")
    private BigDecimal eprice;
    @TableField(value = "is_deleted")
    private  Boolean isDeleted;
}
