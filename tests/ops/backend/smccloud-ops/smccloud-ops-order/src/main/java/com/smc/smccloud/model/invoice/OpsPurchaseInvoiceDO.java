//package com.smc.smccloud.model.invoice;
//
//import com.baomidou.mybatisplus.annotation.IdType;
//import com.baomidou.mybatisplus.annotation.TableField;
//import com.baomidou.mybatisplus.annotation.TableId;
//import lombok.Data;
//
//@Data
//public class OpsPurchaseInvoiceDO {
//
//    @TableId(value = "id", type = IdType.AUTO)
//    private String id;
//
//    @TableField(value = "poNo")
//    private String poNo;
//
//    @TableField(value = "lineItem")
//    private String lineItem;
//
//    @TableField(value = "orderNo")
//    private String orderNo;
//
//    @TableField(value = "itemNo")
//    private String itemNo;
//
//    @TableField(value = "splitItemNo")
//    private String splitItemNo;
//
//    @TableField(value = "modelNo")
//    private String modelNo;
//
//    @TableField(value = "quantity")
//    private String quantity;
//
//    @TableField(value = "stdPrice")
//    private String stdPrice;
//
//    @TableField(value = "transType")
//    private String transType;
//    @TableField(value = "purchaseDate")
//    private String purchaseDate;
//    @TableField(value = "hopeDeliveryDate")
//    private String hopeDeliveryDate;
//
//    @TableField(value = "supplierId")
//    private String supplierId;
//
//    @TableField(value = "stateCode")
//    private String stateCode;
//
//    @TableField(value = "specMark")
//    private String specMark;
//
//    @TableField(value = "receiveWarehouseId")
//    private String receiveWarehouseId;
//
//    @TableField(value = "remark")
//    private String remark;
//
//    @TableField(value = "hopeExportDate")
//    private String hopeExportDate;
//    @TableField(value = "inqNo")
//    private String inqNo;
//
//    @TableField(value = "shikomiAnswerNo")
//    private String shikomiAnswerNo;
//
//    @TableField(value = "deptNo")
//    private String deptNo;
//
//    @TableField(value = "deliveryFlag")
//    private String deliveryFlag;
//
//    @TableField(value = "smcCode")
//    private String smcCode;
//
//    @TableField(value = "hsCode")
//    private String hsCode;
//
//    @TableField(value = "greenCode")
//    private String greenCode;
//
//    @TableField(value = "customerNo")
//    private String customerNo;
//
//    @TableField(value = "userNo")
//    private String userNo;
//
//    @TableField(value = "locationNo")
//    private String locationNo;
//
//    @TableField(value = "vipCode")
//    private String vipCode;
//
//    @TableField(value = "salesInfoNo")
//    private String salesInfoNo;
//
//    @TableField(value = "purchaseType")
//    private String purchaseType;
//
//    @TableField(value = "supplierPartNo")
//    private String supplierPartNo;
//
//    @TableField(value = "importFobPriceOriginal")
//    private String importFobPriceOriginal;
//
//    @TableField(value = "importCurrencyId")
//    private String importCurrencyId;
//
//    @TableField(value = "jpSplitVT")
//    private String jpSplitVT;
//
//    @TableField(value = "operatorId")
//    private String operatorId;
//
//    @TableField(value = "cnNo")
//    private String cnNo;
//
//    @TableField(value = "invoiceId")
//    private String invoiceId;
//
//    @TableField(value = "invoiceNo")
//    private String invoiceNo;
//
//    @TableField(value = "replyOrderNo")
//    private String replyOrderNo;
//
//    @TableField(value = "replyOrderDate")
//    private String replyOrderDate;
//
//    @TableField(value = "replyExportDate")
//    private String replyExportDate;
//
//    @TableField(value = "qtyTrans")
//    private String qtyTrans;
//
//    @TableField(value = "qtyReceive")
//    private String qtyReceive;
//
//    @TableField(value = "qtyImport")
//    private String qtyImport;
//
//    @TableField(value = "impdate")
//    private String impdate;
//
//    @TableField(value = "dlvModDate1")
//    private String dlvModDate1;
//
//    @TableField(value = "dlvModDate2")
//    private String dlvModDate2;
//
//    @TableField(value = "dlvModDate3")
//    private String dlvModDate3;
//
//    @TableField(value = "dlvModDate1Time")
//    private String dlvModDate1Time;
//
//    @TableField(value = "dlvModDate2Time")
//    private String dlvModDate2Time;
//
//    @TableField(value = "dlvModDate3Time")
//    private String dlvModDate3Time;
//
//    @TableField(value = "reasonRemark")
//    private String reasonRemark;
//
//    @TableField(value = "transTypeMod")
//    private String transTypeMod;
//
//    @TableField(value = "dlvAnswerNo1")
//    private String dlvAnswerNo1;
//
//    @TableField(value = "dlvAnswerNo2")
//    private String dlvAnswerNo2;
//
//    @TableField(value = "produceFactory")
//    private String produceFactory;
//
//    @TableField(value = "produceHolon")
//    private String produceHolon;
//
//    @TableField(value = "errdescription")
//    private String errdescription;
//
//    @TableField(value = "imDateTheory")
//    private String imDateTheory;
//
//    @TableField(value = "imDateInFact")
//    private String imDateInFact;
//
//    @TableField(value = "beginProduceDate")
//    private String beginProduceDate;
//
//    @TableField(value = "barCode")
//    private String barCode;
//
//    @TableField(value = "caseNo")
//    private String caseNo;
//
//    @TableField(value = "sendTime")
//    private String sendTime;
//
//    @TableField(value = "updateTime")
//    private String updateTime;
//
//    @TableField(value = "serverremark")
//    private String serverremark;
//
//}
