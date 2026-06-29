package com.smc.smccloud.model.OrderSales;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smc.smccloud.model.fileupload.FileUpload;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class OrderSalesDetailVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 项号
     */
    private Integer rorderItem;  

    /**
     * 交货方式: 0-按地址集约; 1-按订单集约; 2-按用户集约;
     */
    private String dlvType; 

    /**
     * 备注
     */
    private String remark;

    /**
     * 发送标识
     */
    private String sendOut;

    /**
     * 接收状态
     */
    private String status;  

    /**
     * 发送日期
     */
    private Date sendDay;

    /**
     * 型号
     */
    @NotNull
    private String modelNo; 

    /**
     * 数量
     */
    @NotNull
    private Integer quantity; 

    /**
     * 单价
     */
    @NotNull
    private BigDecimal price;

    /**
     * 最终用户单价
     */
    private BigDecimal priceEndUser; 

    /**
     * 交货日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dlvDate; 

    /**
     * 订货区分
     */
    private String rcvClassify;

    /**
     * 出库代码
     */
    private String stockCode; 

    /**
     * 用户产品代码
     */
    private String cproductNo; 

    /**
     * 海外订货运输方式
     */
    private String ordTransType;

    /**
     * 特价号
     */
    private String spcPrice; 

    /**
     * E率
     */
    private BigDecimal discount;

    /**
     * 无价格标识
     */
    private String noPrice;

    /**
     * 金额
     */
    private BigDecimal account; 

    /**
     * 阀与汇流板标识
     */
    private String specMark; 

    /**
     * 项号
     */
    private String recNo;

    /**
     * 明细备注
     */
    private String detailMark;

    /**
     * 营业所物流发货日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date warehouseSendDate;

    /**
     * 客户产品名称
     */
    private String cproductName;

    /**
     * 竞争对手
     */
    private String opponent;
    
    /**
     * 收货地址编号
     */
    private String addressNo;

    /**
     * 客户期望的交货期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date cdlvDate; 

    /**
     * 客户订单号
     */
    private String corderNo;

    /**
     * 客户自定义出货代码(打印在标签上)
     */

    private String customCode; 

    private Date createTime;

    private String updateUser;

    private String rorderNo;

    private BigDecimal ePrice; 

    private Date updateTime;

    /**
     * SHiKoMi号
     */
    private String shikomiNo;

    /**
     * 营业情报号
     */
    private String salesInfoNo; 

    private String pplNo;

    private Long id;

    private String fullOrderNo;

    private String projectNo;

    private String createUser;

    /**
     * 特殊出货方式
     */
    private Integer expDlvType;

    private String expLinkNo;

    /**
     * 合同订单号
     */
    private String orOrderNo;

    /**
     * 门户发注号
     */
    private String PreSalesOrderNo;

    /**
     * 用户价格
     */
    private BigDecimal userPrice;

    /**
     * 用户E率
     */
    private Double userEdiscount;

    // ops原始订单号
    private String opsOrderNo;

    // 如果有最低售价 必须有附件
    // 是否有最低售价
    private Boolean hasLowestPrice;

     // 附件
     private List<FileUpload> fileList;

    // 行业代码
    private String industryId;

    // 客户类型
    private String customerType;

    // 文件类型
    private String fileType;

    private String inqbApplyNo;

    private boolean specialModelFlag = false;

}
