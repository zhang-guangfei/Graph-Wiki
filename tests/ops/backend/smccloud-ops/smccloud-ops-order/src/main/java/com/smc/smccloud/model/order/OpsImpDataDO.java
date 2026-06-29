package com.smc.smccloud.model.order;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author edp04
 * @title: OpsImpDataDO
 * @date 2022/07/13 17:51
 */
@Data
@TableName("ops_impdata")
public class OpsImpDataDO {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(value = "invoiceId")
    private Integer invoiceId;

    @TableField(value = "invoiceNo")
    private String invoiceNo;

    @TableField(value = "poNo")
    private String poNo;

    @TableField(value = "lineItem")
    private Integer lineItem;

    @TableField(value = "orderNo")
    private String orderNo;

    @TableField(value = "itemNo")
    private Integer itemNo;

    @TableField(value = "splitItemNo")
    private Integer splitItemNo;

    @TableField(value = "modelNo")
    private String modelNo;

    @TableField(value = "quantity")
    private Integer quantity;

    @TableField(value = "barCode")
    private String barCode;

    @TableField(value = "caseNo")
    private String caseNo;

    @TableField(value = "supplierId")
    private String supplierId;

    @TableField(value = "greenCode")
    private Integer greenCode;

    @TableField(value = "receiveWarehouseId")
    private String receiveWarehouseId;

    @TableField(value = "receiveDate")
    private Date receiveDate;

    @TableField(value = "impTime")
    private Date impTime;

    @TableField(value = "stateCode")
    private Integer stateCode;

    @TableField(value = "insertTime")
    private Date insertTime;

    @TableField(value = "updateTime")
    private Date updateTime;

    @TableField(value = "supplierExpDate")
    private Date supplierExpDate;

    @TableField(value = "operate")
    private Integer operate;

}
