package com.smc.smccloud.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author C18117
 * @title: SalesDataModiDataDO
 * @date 2022/10/28 09:22
 */
@Data
@TableName("Salesdata_modidata")
public class SalesDataModiDataDO {

    @TableField(value = "RorderNO")
    private String rorderNO;

    @TableField(value = "ModelNo")
    private String modelNo;

    @TableField(value = "Quantity")
    private Integer quantity;

    @TableField(value = "Eprice_Lot")
    private BigDecimal epriceLot;

    @TableField(value = "EPrice_base_lot")
    private BigDecimal ePriceBaseLot;

    @TableField(value = "Quantity_Lot")
    private Integer quantityLot;

    @TableField(value = "Updatetime")
    private Date updateTime;

    @TableField(value = "UserName")
    private String userName;

}
