package com.smc.smccloud.model.bindata;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author edp02 @Date 2021/10/6 13:27
 *
 */

@Data
@TableName("Bindata")
public class BindataDO {

    @TableId(value = "id" , type = IdType.AUTO)
    private Integer id;

    /**
     * BIN库存类型: 1-BIN; 2-GSS; 3-非BIN; 4-客户BIN;
     */
    @TableField(value = "stockType")
    private Integer stockType; //库存类型(BIN,非BIN,GSS)

    @TableField(value = "warehouse_code")
    private String warehouseCode; //库房代码

    @TableField(value = "ModelNo")
    private String modelNo;  //型号

    @TableField(value = "CustomerNo")
    private String customerNo;  //客户代码

    @TableField(value = "Property_ID")
    private Long propertyID;  //库存属性ID

    @TableField(value = "QtyBin")
    private Integer qtyBin;  //BIN数量

    @TableField(value = "BinCell")
    private Integer binCell;  //BIN数

//    @TableField(value = "ClassCode")
//    private String classCode;  //
    @TableField(value = "binType")
    private String binType; //bin类型 BIN1,BIN2,BIN3

    @TableField(value = "CaseType")
    private String caseType;  //箱型

    @TableField(value = "ProdType")
    private String prodType;  //产品类别

    @TableField(value = "createTime", fill = FieldFill.INSERT)
    private Date createTime;  //登录日期

    @TableField(value = "updateTime", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;  //更新日期

    @TableField(value = "PositionNo")
    private String positionNo;  //位置代码

    @TableField(value = "MeshType")
    private String meshType;  //网筐类型

    @TableField(value = "InCaseQty")
    private Integer inCaseQty;  //箱入数

    @TableField(value = "Adjustable")
    private String adjustable;  //是否可调

    @TableField(value = "delFlag")
    private Integer delFlag;

    @TableField(value = "LastdelFlag")
    private Integer lastdelFlag;

    @TableField(value = "safeQty") //安全在可
    private Integer safeQty;

    @TableField(value = "freq") //频率
    private Integer freq;

    @TableField(value = "mean") //月用量
    private Integer mean;

    @TableField(value = "SetLevel")
    private String setLevel;  //设置等级

    @TableField(value = "NewLevel")
    private String newLevel;  //新等级

    @TableField(value = "NewFreq") //新频率
    private Integer newFreq;

    @TableField(value = "NewMean") //新用量
    private Integer newMean;

    @TableField(value = "po_type")
    private String poType;  //采购分类

    @TableField(value = "SupplierCode")
    private String supplierCode;  //供应商

    @TableField(value = "orderType")
    private String orderType;  //订单类别

    @TableField(value = "ProdSeri")
    private String prodSeri;  //公/英制区分

    @TableField(value = "StateRange")
    private String stateRange;  //辖区

    @TableField(value = "minPackageQty")
    private Integer minPackageQty;  //最小包装数量

    @TableField(value = "SetFreq")
    private Integer setFreq;  //设定频率

    @TableField(value = "DirectPurchase")
    private Integer directPurchase;  //直采1

    @TableField(value = "directDelivery")
    private Integer directDelivery;  //直送1

    @TableField(value = "AutoRepl")
    private Integer autoRepl;  //自动周转补货

    @TableField(value = "EPrice")
    private BigDecimal eprice;  //E价

    @TableField(value = "ECode")
    private String ecode;  //ECode

    @TableField(value = "ModelSeries")
    private String modelSeries;  //产品资料

    @TableField(value = "ppl")
    private String ppl;  //ppl代码

    @TableField(value = "project_no")
    private String projectNo;  //项目号

    @TableField(value = "inventory_type_code")
    private String inventoryTypeCode;  //库存类型代码

    @TableField(value = "group_customer_no")
    private String groupCustomerNo;  //客户群号

    @TableField(value = "origin")
    private String origin;  //原产地

    @TableField(value = "loginDate")
    private Date loginDate; //首次登记时间

    @TableField(value = "ReplQty")
    private Integer replQty;  //待补货数量

    @TableField(value = "SetSupplierCode")
    private String setSupplierCode; //自己设置供应商

    @TableField("centre_flag")
    private Integer centreFlag; // 中央仓标识 0非中央处管理 1中央仓管理

    @TableField(exist = false)
    private String centerFlagName;
    @TableField(exist = false)
    private List<String> client;

}
