package com.smc.smccloud.model.receiveorder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 
 * </p>
 *
 * @author smc
 * @since 2021-11-02
 */
@Data
public class RcvDetailVO implements Serializable {

    private static final long serialVersionUID = 1L;


    private Long id;
    /**
     * 接单号
     */
    @NotEmpty
    private String rorderNo;

    /**
     * 项号
     */
    @NotNull
    private Integer rorderItem;

    /**
     * 处理状态1
     */
    private Integer status;

    /**
     * 完整订单号 // 自己拼接
     */
    private String rorderFno;

    /**
     * 型号
     */
    @NotEmpty
    private String modelNo;

    /**
     * 数量
     */
    @NotNull
    private Integer quantity;

    /**
     * 单价
     */
    private BigDecimal price;

    /**
     * 最终用户单价
     */
    private BigDecimal priceEndUser;

    /**
     * e价
     */
    private BigDecimal ePrice;

    /**
     * 税率
     */
    private BigDecimal taxRate;

    /**
     * 不含税单价
     */
    private BigDecimal ntaxPice;

    /**
     * 不含税金额
     */
    private BigDecimal ntaxAmount;

    /**
     * 税额
     */
    private BigDecimal taxAmount;

    /**
     * 含税金额
     */
    private BigDecimal amount;

//    /**
//     * 交货方式
//     */
//    private String dlvType;

    /**
     * 折扣率
     */
    private BigDecimal discount;

    /**
     * 交货日期(客户端)
     */
    private Date dlvDate;

    /**
     * 客户的希望交货期
     */
    private Date cdlvDate;

    // 指定物流出库日
    private Date wmsDlvDate;

    /**
     * 特价号
     */
    private String specOfferNo;

    /**
     * 删除状态-1已删除,2删除中,3-退货中
     */
    private Integer deleteStatus;

    /**
     * 订单来源
     */
    private String sourceType;

    /**
     * 客户产品代码
     */
    private String cproductNo;

    /**
     * 拆分标识(0:不拆分；1:数量拆分;  2:型号拆分)
     */
    private String prodFlag;

    /**
     * 出库代码
     */
    private String stockCode;

    /**
     * 出库类型
     */
    private String stockType;

    /**
     * 阀与汇流板标识
     */
    private String specMark;

    /**
     * 备注
     */
    private String remark;

    /**
     * 客户产品名称
     */
    private String productName;

    /**
     * 竞争对手
     */
    private String opponent;

    /**
     * 完成出货时间
     */
    private Date expTime;

    /**
     * 完成发货日期
     */
    private Date shipTime;

    /**
     * 在库货齐数量
     */
    private Integer readyQty;

    /**
     * 已发出货指令数量
     */
    private Integer expQty;

    /**
     * 已退货数量
     */
    private Integer returnedQty;

    /**
     * 已分配数量
     */
    // private Integer allocatedQty;

    /**
     * ppl号
     */
    private String pplNo;

    /**
     * 项目号
     */
    private String projectNo;

    /**
     * shikomi号
     */
    private String shikomiNo;

    /**
     * 发货地址
     */
    private String addressNo;

    /**
     * 客户自定义出货代码(打印在标签上)
     */
    private String customCode;

    /**
     * 更新版本号
     */
    private Integer version;

    /**
     * 处理日期
     */
    private Date updateTime;

    /**
     * 最后的接单处理时间
     */
    private Date processDate;

    /**
     * 营业情报号
     */
    private String salesInfoNo;

    private Date readyTime;

    @NotNull
    private Integer orderType;

    private String updateUser;

    private Date createTime;

    private Date allotTime;

    private String createUser;

    /**
     * 借库号
     */
    private String borrowNo;

    /**
     * 采购数量
     */
    private Integer poQty;

    /**
     * 异常信息
     */
    private String expMsg;

    /**
     * 特殊出货方式
     */

    private Integer expDlvType;

    private String expLinkNo;

    private String corderNo;

    private Boolean intercept;

    private Date interceptTime;

    // 行业代码
    private String industryId;

    // 客户类型
    private String customerType;

    private String carrierId;

}
