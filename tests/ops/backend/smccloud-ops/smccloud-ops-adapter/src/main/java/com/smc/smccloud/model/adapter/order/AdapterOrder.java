package com.smc.smccloud.model.adapter.order;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.smc.smccloud.model.fileupload.FileUpload;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@ApiModel(description = "出库单适配器中间类")
public class AdapterOrder implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -3907693695249270017L;

    @ApiModelProperty(value = "直销门户出库单号", required = true)
    private String no;

    @ApiModelProperty(value = "客户代码", required = true)
    private String customerno;

    @ApiModelProperty(value = "交货地点", required = true)
    private String dlvsite;

    @ApiModelProperty(value = "交货方式", required = true)
    private String dlvtype;

    /**
     * 1、货齐出货，0、随到随发
     */
    @ApiModelProperty(value = "是否货齐出货", required = true)
    private String dlventire;

    /**
     * 0-SMC负担（乙方负担）, 1-客户负担（甲方负担）, 2-渠道负担
     */
    @ApiModelProperty(value = "运费负担", required = true)
    private String transfee;

    @ApiModelProperty(value = "交易主体", required = true)
    private String tradecompanyid;

    @ApiModelProperty(value = "处理日期", required = true)
    private Date workday;

    @ApiModelProperty(value = "型号", required = true)
    private String modelno;

    @ApiModelProperty(value = "数量", required = true)
    private Integer quantity;

    @ApiModelProperty(value = "单价", required = true)
    private BigDecimal price;

    @ApiModelProperty(value = "交货日期", required = true)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    private Date dlvdate;

    @ApiModelProperty(value = "E率", required = true)
    private BigDecimal discount;

    @ApiModelProperty(value = "外勤担当", required = true)
    private String empsale;

    @ApiModelProperty(value = "内勤代码", required = true)
    private String empoffice;

    @ApiModelProperty(value = "部门代码", required = true)
    private String deptno;

    /// Add by bug-10665 回滚
//    @ApiModelProperty(value = "HL代码")
//    private String hlCode;

    /**
     * zxjs("7", "直销寄售"), wtzk("6", "代销"), zcdd("0", "普通"), hbdd("后补订单", "后补订单");
     */
    @ApiModelProperty(value = "订单类别,直销寄售标识", required = true)
    private String typecode;

    @ApiModelProperty(value = "物流发货日", required = true)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    private Date warehousesenddate;

    /**
     * 据字典有正常订单、替换Airtac订单、替换Festo订单、替换CKD订单、替换国产订单、替换其他订单
     */
    @ApiModelProperty(value = "订货区分", required = true)
    private String rcvclassify;

    @ApiModelProperty(value = "E价格", required = true)
    private BigDecimal eprice;

    @ApiModelProperty(value = "发货地址信息", required = true)
    private DeliveryAddressInfo addressInfo;

    @ApiModelProperty(value = "阀与汇流板标识，是否组装标识，阀 1 板2 正常0、必填项", required = true)
    private String specmark;

    @ApiModelProperty(value = "特价号")
    private String spcprice;

    @ApiModelProperty(value = "SHIKOMI")
    private String shikomi;

    @ApiModelProperty(value = "客户产品名称")
    private String cproductname;

    @ApiModelProperty(value = "营业情报号")
    private String presaleorderno;

    @ApiModelProperty(value = "报价单代码")
    private String quotationno;

    @ApiModelProperty(value = "用户代码")
    private String userno;

    @ApiModelProperty(value = "采购单号")
    private String purchaseno;

    @ApiModelProperty(value = "合同代码--自动订货时写入样品等申请号")
    private String contractno;

    @ApiModelProperty(value = "合同订单号")
    private String hddno;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "接收状态 暂未传值")
    private String status;

    @ApiModelProperty(value = "项目代码")
    private String prjcode;

    @ApiModelProperty(value = "出库代码")
    private String stockcode;

    // 物料号，客户型号
    @ApiModelProperty(value = "用户产品代码，物料号，客户型号")
    private String cproductno;

    @ApiModelProperty(value = "最终用户价格")
    private BigDecimal priceEnduser;

    @ApiModelProperty(value = "是否有合同 暂未传值")
    private String cttflag;

    @ApiModelProperty(value = "竞争对手 暂未传值")
    private String opponent;

    @ApiModelProperty(value = "发货地址id")
    private String addressId;

    @ApiModelProperty(value = "订单号,ERP生成 暂未传值")
    private String rorderno;

    @ApiModelProperty(value = "项号,ERP生成 暂未传值")
    private String recno;

    @ApiModelProperty(value = "项号,ERP生成 暂未传值")
    private Short rorderitem;

    @ApiModelProperty(value = "无价格标识，暂未传值")
    private String noprice;

    @ApiModelProperty(value = "金额，无需要传")
    private BigDecimal account;

    @ApiModelProperty(value = "海外订货运输方式，预留字段，暂未传值")
    private String ordtranstype;

    @ApiModelProperty(value = "国内运输方式，暂未传值")
    private String transchannel;

    @ApiModelProperty(value = "使用库房类型: 0-正常, 1-使用自有在库, 2-不使用服务备库")
    private Integer useWarehouseType;

    @ApiModelProperty(value = "rohs10")
    private Boolean rohs10;

    @ApiModelProperty(value = "组装指令")
    private Boolean assemblyInstruction;

    @ApiModelProperty(value = "BOM号")
    private String bomNo;

    @ApiModelProperty(value = "最终用户")
    private String enduser;

    /**
     * 用户含税单价
     */
    private BigDecimal userPrice;

    /**
     * 用户E率
     */
    private BigDecimal userErate;


    @ApiModelProperty(value = "客户群信息")
    private List<SalesCustomerClusterBind> customerClusterBinds;

    // 如果有最低售价 必须有附件
    @ApiModelProperty(value = "是否有最低售价")
    private Boolean hasLowestPrice;

    @ApiModelProperty(value = "附件")
    private List<FileUpload> fileList;

    @ApiModelProperty(value = "行业代码")
    private String industryId;

    // 0 直销 1 代销 2 经销
    @ApiModelProperty(value = "客户类型")
    private String customerType;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 催促申请号
     */
    private String inqbApplyNo;

    /**
     * 是否指定空运
     */
    private Boolean appointAirTrans = false;

    private boolean specialModelFlag = false;

    /**
     * 赊销标识
     */
    private Boolean creditSaleClientFlag;
}
