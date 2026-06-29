package com.smc.smccloud.model.purchase;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @Author edp02 @Date 2022/5/27 10:30
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OpsPurchaseInvoiceVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 日本PO号
     */
    private String poNo;

    /**
     * 一般为1，组装时主单+子项号为采购单号、linitem为子项号，集装阀为主单号为采购单号、linitem为子项号
     */
    private Integer lineItem;

    /**
     * 请购单号
     */
    private String orderNo;

    /**
     * 型号
     */
    private String modelNo;

    /**
     * 数量
     */
    private Integer quantity;

    /**
     * 标准价
     */
    private Double stdPrice;

    /**
     * 运输方式

    private String transType;

    /**
     * 采购日期
     */
    private String purchaseDate;

    /**
     * 期望货期
     */
    private String hopeDeliveryDate;

    /**
     * 供应商代码
     */
    private String supplierId;

    /**
     * 状态
     */
    private String stateCode;

    /**
     * 阀汇流板标识
     */
    private String specMark;

    /**
     * 收货库房
     */
    private String receiveWarehouseId;

    /**
     * 备注
     */
    private String remark;

    /**
     * 指定制造出荷日
     */
    private String hopeExportDate;

    /**
     * 交货期问询号
     */
    private String inqNo;

    /**
     * SHIKOMI批复号
     */
    private String shikomiAnswerNo;

    /**
     * 部门代码
     */
    private String deptNo;

    /**
     * 订单区分1一时通过品,2 库存
     */
    private String deliveryFlag;

    /**
     * 国别代码
     */
    private String smcCode;

    /**
     * 海关申报号
     *
    private String hsCode;

    /**
     * 绿标标识
     */
    private String greenCode;

    /**
     * 客户代码
     */
    private String customerNo;

    /**
     * 用户代码
     */
    private String userNo;

    /**
     * 货架号
     */
    private String locationNo;

    /**
     * 营业情报号
     */
    private String salesInfoNo;

    /**
     * 供应商型号
     */
    private String supplierPartNo;

    /**
     * 进口FOB价（原币种）
     */
    private Double importFobPriceOriginal;

    /**
     * 币种代码
     */
    private String importCurrencyId;

    /**
     * 日本不接受整型号，拆分后传输标记
     */
    private Integer jpSplitVT;

    /**
     * 采购操作者
     */
    private String operatorId;

    /**
     * 北京工厂CN号
     */
    private String cnNo;

    /**
     * 发票号
     */
    private String invoiceNo;

    /**
     * 供应商订单号（手配号）
     */
    private String replyOrderNo;

    /**
     * 接单时间
     */
    private String replyOrderDate;

    /**
     * 供应商回复货期
     */
    private String replyExportDate;

    /**
     * 在途数量
     */
    private Integer qtyTrans;

    /**
     * 到货数量
     */
    private Integer qtyReceive;

    /**
     * 入库数量
     */
    private Integer qtyImport;

    /**
     * 入库日期
     */
    private String impdate;

    /**
     * 变更交货期1
     */
    private String dlvModDate1;

    /**
     * 变更交货期2
     */
    private String dlvModDate2;

    /**
     * 变更交货期3
     */
    private String dlvModDate3;

    /**
     * 变更交货期1的时间
     */
    private String dlvModDate1Time;

    /**
     * 变更交货期2的时间
     */
    private String dlvModDate2Time;

    /**
     * 变更交货期3的时间
     */
    private String dlvModDate3Time;

    /**
     * 交货期变更原因
     */
    private String reasonRemark;

    /**
     * 变更后的运输方式
     */
    private String transTypeMod;

    /**
     * 催促号1
     */
    private String dlvAnswerNo1;

    /**
     * 催促号2
     */
    private String dlvAnswerNo2;

    /**
     * 生产工厂
     */
    private String produceFactory;

    /**
     * 生产holon
     */
    private String produceHolon;

    /**
     * 错误说明
     */
    private String errdescription;

    /**
     * 理论入库日
     */
    private String imDateTheory;

    /**
     * 实际入库时间
     */
    private String imDateInFact;

    /**
     * 开始生产时间
     */
    private String beginProduceDate;

    /**
     * 写入日期
     */
    private String sendTime;

    /**
     * 更新日期
     */
    private String updateTime;

    /**
     * 2022-1-22 新增，业务人员备注信息
     */
    private String serverremark;

    private Long invoiceId;

    private String caseNo;

    private String barCode;

    private String vipCode;

    private Integer splitItemNo;

    private Integer itemNo;

    private String purchaseType;

}
