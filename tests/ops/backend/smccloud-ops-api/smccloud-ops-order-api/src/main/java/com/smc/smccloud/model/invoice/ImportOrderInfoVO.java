package com.smc.smccloud.model.invoice;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 导入入库发票清单数据
 *
 * @author wsf
 * @version 1.0
 * @date 2021/11/24 16:53
 */
@Data
public class ImportOrderInfoVO {
    /**
     * 1-发货数据
     * 2-gss
     * 3-生成中
     */
    private int dataType;
    private String invoiceNo;
    private String orderNo;
    private String fullOrderNo;
    private String poNo;
    private String poItemNo;
    private Integer itemNo;
    private int quantity;
    private Integer splitItemNo;
    private BigDecimal price;
    private String modelNo;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date shippedDate;
    private String shippingMethod;
    private String shippingLabel;
    private double weight;
    private String origin;
    private String modelName;
    private String caseNo;
    /**
     * 59 Product Code
     */
    private String eCode;
    private String shippingNo;
    private String supplierCode;
    private String currency;
    private String orderType;
    private String remark;
    private int status;
    private String originCountry;
    private String unit;
    private String noRef;
    private String transType;
    //public int gzID;
    private int seqNo;
    //public Date promiseDate;

    /**
     * 处理日期
     */
    private Date optDate;
    /**
     * 货架位
     */
    public String shelfNo;
    /// <summary>
    /// 订单总的数量
    /// </summary>
    public int orderTotalQty;
    /// <summary>
    /// 日本待出库数量
    /// </summary>
    public int qtyInExport;
    /// <summary>
    /// 发货状态
    /// </summary>
    public String shipStatus;
    public String jPPromiseDate;
    /// <summary>
    /// J	from Japan
    ///C	from China
    ///E	from ECW (This relates to only Europe.)
    ///U	from US(SMAC)
    /// </summary>
    public String whereCode;
    /*
    hSCode
     */
    public String hSCode;
    /**
     * roHSCode
     */
    public String roHSCode;

    public int Id;
    /// <summary>
    /// 物流交付号
    /// </summary>
    public String impGroupNo;
    /// <summary>
    /// 物流扫描时间
    /// </summary>
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime;
    private Date scandate;
    /// <summary>
    /// 物流收货时间
    /// </summary>
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date recvDate;
    /// <summary>
    /// 制造收货确认时间
    /// </summary>
    //@DateTimeFormat(pattern = "yyyy-MM-dd")
    //@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    //public Date prodRecvdate;
    /// <summary>
    /// 制造收货扫描时间
    /// </summary>
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    //public Date prodScandate;
    /// <summary>
    /// 1-物流交付待制造待收货
    /// 2-制造已收货
    /// 3-制造已扫描
    /// </summary>
    //public int prodScanStatus;
    public int impScan;
    public String cNName;
    public BigDecimal tariffRate;
    public String cNHSCode;
    public BigDecimal amount;
    public String supplierNo;
    public String supplierName;
    public String ownerCode;
    /// <summary>
    /// 备用字段
    /// 暂用于外箱序号（制造部捡货时外箱序号）
    /// </summary>
    public String extInfo;

    public BigDecimal getAmount() {
//        if (price == null) {
//            return BigDecimal.ZERO;
//        }
        return price.multiply(new BigDecimal(quantity));
    }

    /**
     * 原订单号
     */
    private String orFullOrderNo;

}
