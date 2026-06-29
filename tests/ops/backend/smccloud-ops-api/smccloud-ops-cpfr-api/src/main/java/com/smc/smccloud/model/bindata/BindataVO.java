package com.smc.smccloud.model.bindata;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author edp02 @Date 2021/10/6 14:44
 */
@Data
public class BindataVO implements Serializable {

    private static final long serialVersionUID = -5058062631296818592L;

    private Integer id;

    private String binType;

    /**
     * BIN库存类型: 1-BIN; 2-GSS; 3-非BIN; 4-客户BIN;
     */
    private  Integer stockType;

    private String warehouseCode;

    private String modelNo;

    private String customerNo;

    private Long propertyID;  //库存属性ID

    private Integer qtyBin;

    private Integer binCell;

//    private String classCode;

    private String caseType;

    private String prodType;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;

    private String positionNo;

    private String meshType;

    private Integer inCaseQty;

    private String adjustable;

    private Integer delFlag;

    private Integer lastdelFlag;

    /**
     * 安全库存
     */
    private Integer safeQty;

    /**
     * 频率
     */
    private Integer freq;

    /**
     * 月用量
     */
    private Integer mean;

    private String setLevel;  //设置等级

    private String newLevel;  //新等级

    private Integer newFreq;

    private Integer newMean;

    private String poType;  //采购分类

    private String supplierCode;  //供应商

    private String orderType;  //订单类别

    private String prodSeri;  //产品系列

    private String stateRange;  //辖区

    private Integer minPackageQty;  //最小包装数量

    private Integer setFreq;  //设定频率

    private Integer directPurchase;  //直采1

    private Integer directDelivery;  //直送1

    private Integer autoRepl;  //自动周转补货

    private BigDecimal eprice;  //E价

    private String ecode;  //ECode

    private String modelSeries;  //产品资料

    private String ppl;  //ppl代码

    private String projectNo;  //项目号

    private String inventoryTypeCode;  //库存类型代码

    private String groupCustomerNo;  //客户群号

    private String origin;  //原产地

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date loginDate; //首次登记时间

    private Integer replQty; //待补货数量

    /**
     * 自己设置的供应商
     */
    private String setSupplierCode;

    private Integer centreFlag;

    private String centerFlagName;

    private List<String> client;
}
