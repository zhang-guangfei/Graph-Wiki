package com.smc.smccloud.model.binorder;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class BinOrderDetailVO {
    
    private Integer confirmQty;

    /**
     * 采购数量
     */
    private Integer poQty;

    /**
     * 调拨数量
     */
    private Integer transQty;

    /**
     * 0-采购填(K,B,U),1-调拨-9-拆分数量
     */
    private String orderType;

    /**
     * 订向库房
     */
    private String orderStockCode;

    /**
     * 通用在库数量
     */
    private Integer stockQty;

    /**
     * 变动系数
     */
    private Double diffVal;

    /**
     * 自动化bin数量
     */
    private Integer qtybinAll;

    /**
     * 自动化bin数
     */
    private Integer bincellAll;

    /**
     * 与bin应该备库的数量差
     */
    private Integer binDiffQty;

    /**
     * 相差的bin个数
     */
    private Double binDiffNum;

    /**
     * 最小起订量
     */
    private Integer minOrderQty;

    /**
     * 最小包装单位
     */
    private Integer minPackQty;

    /**
     * 标准交货期
     */
    private Integer stdLeadTime;

    /**
     * 建议备库月数
     */
    private Integer recommMonths;

    /**
     * 型号属性-1:标准品, 2:简易特注品,3:一般特注品, 4: 集成型号, 5:维修费
     */
    private String modelType;

    /**
     * 型号分类,气缸、阀、小物等
     */
    private String modelClass;

    private BigDecimal eprice;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;

    private Integer boxQty;

    private String transType;

    private Integer maxStockQty;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    private String remark;

    private Double months;

    private String orderNo;

    private String updateUser;

    private String binClass;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date maxDlvdate;

    private String calcType;

    private Integer qtybin;


    private Integer ordingQty;

    private Integer appId;

    private Long id;

    private Integer orderPoint;

    private Double stockMonths;

    private Integer curmthsalesqty;

    private Integer status;

    private Integer orderQty;

    private Double avgDiff;

    private Integer bincell;

    private Integer modelSpecFlag;

    private String modelNo;

    private Long calcId;

    private String boxNo;

    private Double mean;
    private Integer freq;


    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dlvDate;

    private Double weight;

    private Integer specStockQty;

    private Integer updNo;

    private Integer specOrdingQty;

    private String warehouseCode;

    private Double stdevval;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    private String ecode;

    private String supplierCode;

    private String createUser;

    private Integer customers;

    private String poType;

    private Integer directpurchase;

    private Long propertyId;

    private String ppl;  //ppl代码

    private String projectNo;  //项目号

    private String inventoryTypeCode;  //库存类型代码

    private String groupCustomerNo;  //客户群号

    private String customerNo;  //客户代码

    private Integer stockQtyAll;  //总在库

    private Integer meanAll;  //总月均

    private BigDecimal stockMonthsAll;  //总月均

    private String mainSupplierCode;  //原产地

    private String setOrderType;  //订单类型

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date lastDlvdate;  //最后交货期

    private  Integer itemNo;

    private Long fromId;

    /**
     * 经过的采购的物流中心
     */
    private  String poLogisWarehouseCode;

    private Integer preQty;  //被预约数

    //0只采购，1/2 系统自选
    private Integer replenishmentMethod;

    private int centerFlag;

    private String centerWarehouse;

    private String client;

    private String excessQty;

}
