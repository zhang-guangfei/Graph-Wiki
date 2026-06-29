package com.sales.ops.dto.ips;

import java.util.Date;

/**
 * @description:
 * "customerName" :"",//客户名称
 * "customerOrderNo": "",//日本 订单号
 * "customerItemNo":"",//日本订单项号
 * "customerKogo":"",//日本工号
 * "customerShelfNo":"",//日本货架号
 * "customerRemarks ":"",//日本备注
 * "issueDate":"",//作成日
 * "customerSerialNo ":"",//日本连编号
 * "accumOrder ":"",//累计受注
 * "shikomiNo": "",//SHIKOMI号
 * "customerReserved ": "",//重要管理客户识别
 * "warehouseId": "",//仓库ID
 * "tradeStyle": "",//贸易区分
 * "customerBarcode": "",//日本入库号
 * "customerConfigNo ": "",//日本部署
 * "customerArrangeNo": "",//日本手配
 * "customerArrangeItem": "",//日本手配项号
 * "memo": "",//备忘录编号
 * "customerRemarks": "",//荷姿/ 备注
 * "simpleModel": "",//简易品番
 * "customerShelfNo2": "",//日本货架号2
 * "labelBarcode": "",//条型码
 * "contractNo": "",//订货合同号
 * "contractRemnant": "",//订货合同剩余数量
 * "goodsCode ": "",//制品号
 * "duty": "",//税号
 * "originQualify ": "",//原产资格
 * "priorInformationNumber": "",//先前情报号
 * "priorInformationLineNumber ": "",//先前情报线
 * "originQualifySign": "",//原产资格记号
 * "customerProductName": "",//客户品名
 * "arrangeNo": "",// 手配号
 * "arrangeItem": "",// 手配项号
 * "shikomiNo": "",
 * @author: B91717
 * @time: 2024/12/17 16:33
 */
public class IpsCustomerInfoDto {
    private String inqbNo; // // INQB号
    private String priceClassification; // 贸易方式 装运条款；当前默认为C，A=F.O.B, B=C&F, C=C.I.F,  D=C&I, E=EXW, F=EXG, G=BWT 目前均写C
    private String customerName; // 客户名称
    private String customerOrderNo; // 日本 订单号
    private String customerItemNo; // 日本订单项号
    private String customerKogo; // 日本工号
    private String customerShelfNo; // 日本货架号
    private String customerRemarks; // 日本备注
    private String issueDate; // 作成日
    private Integer customerSerialNo; // 日本连编号
    private String accumOrder; // 累计受注
    private String shikomiNo; // SHIKOMI号
    private String customerReserved; // 重要管理客户识别
    private String warehouseId; // 仓库ID
    private String tradeStyle; // 贸易区分
    private String customerBarcode; // 日本入库号
    private String customerConfigNo; // 日本部署
    private String customerArrangeNo; // 日本手配
    private String customerArrangeItem; // 日本手配項号
    private String memo; // 备忘录编号
    private String remarks; // 荷姿/ 备注
    private String simpleModel; // 简易品番
    private String customerShelfNo2; // 日本货架号2
    private String labelBarcode; // 条型码
    private String contractNo; // 订货合同号
    private Integer contractRemnant; // 订货合同剩余数量
    private String goodsCode; // 制品号
    private String duty; // 税号
    private String originQualify; // 原产资格
    private String priorInformationNumber; // 先前情报号
    private String priorInformationLineNumber; // 先前情报线
    private String originQualifySign; // 原产资格記号
    private String customerProductName; // 客户品名
    private String arrangeNo; // 手配号
    private String arrangeItem; // 手配項号
    private String jpScatteredNo; // 日本依赖号
    private String jpEmailAddress; // 日本采购担当邮箱
    private String salesInformationNo; //  营业情报号
    private String companyCode; // 公司别代码
    private String customerOrderType;

    private String customerServerremark; // 业务备注，OPS需要使用

    private String paymentDay; // bug18001增加一个账期的指标paymentDay标准为四位

    private String customerNo; // bug19576 采购发单给老生管对于客户代码的传值

    private String prepareOrderNo; // bug20023 采购发单给老生管对于准备单号

    private Date customerDeliveryDate; // bug20877 WTSR2026000464-客户交货期推送给制造系统

    public Date getCustomerDeliveryDate() {
        return customerDeliveryDate;
    }

    public void setCustomerDeliveryDate(Date customerDeliveryDate) {
        this.customerDeliveryDate = customerDeliveryDate;
    }

    public String getPrepareOrderNo() {
        return prepareOrderNo;
    }

    public void setPrepareOrderNo(String prepareOrderNo) {
        this.prepareOrderNo = prepareOrderNo;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getCustomerServerremark() {
        return customerServerremark;
    }

    public void setCustomerServerremark(String customerServerremark) {
        this.customerServerremark = customerServerremark;
    }

    public String getPaymentDay() {
        return paymentDay;
    }

    public void setPaymentDay(String paymentDay) {
        this.paymentDay = paymentDay;
    }

    public String getInqbNo() {
        return inqbNo;
    }

    public void setInqbNo(String inqbNo) {
        this.inqbNo = inqbNo;
    }

    public String getPriceClassification() {
        return priceClassification;
    }

    public void setPriceClassification(String priceClassification) {
        this.priceClassification = priceClassification;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerOrderNo() {
        return customerOrderNo;
    }

    public void setCustomerOrderNo(String customerOrderNo) {
        this.customerOrderNo = customerOrderNo;
    }

    public String getCustomerItemNo() {
        return customerItemNo;
    }

    public void setCustomerItemNo(String customerItemNo) {
        this.customerItemNo = customerItemNo;
    }

    public String getCustomerKogo() {
        return customerKogo;
    }

    public void setCustomerKogo(String customerKogo) {
        this.customerKogo = customerKogo;
    }

    public String getCustomerShelfNo() {
        return customerShelfNo;
    }

    public void setCustomerShelfNo(String customerShelfNo) {
        this.customerShelfNo = customerShelfNo;
    }

    public String getCustomerRemarks() {
        return customerRemarks;
    }

    public void setCustomerRemarks(String customerRemarks) {
        this.customerRemarks = customerRemarks;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public Integer getCustomerSerialNo() {
        return customerSerialNo;
    }

    public void setCustomerSerialNo(Integer customerSerialNo) {
        this.customerSerialNo = customerSerialNo;
    }

    public String getAccumOrder() {
        return accumOrder;
    }

    public void setAccumOrder(String accumOrder) {
        this.accumOrder = accumOrder;
    }

    public String getShikomiNo() {
        return shikomiNo;
    }

    public void setShikomiNo(String shikomiNo) {
        this.shikomiNo = shikomiNo;
    }

    public String getCustomerReserved() {
        return customerReserved;
    }

    public void setCustomerReserved(String customerReserved) {
        this.customerReserved = customerReserved;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getTradeStyle() {
        return tradeStyle;
    }

    public void setTradeStyle(String tradeStyle) {
        this.tradeStyle = tradeStyle;
    }

    public String getCustomerBarcode() {
        return customerBarcode;
    }

    public void setCustomerBarcode(String customerBarcode) {
        this.customerBarcode = customerBarcode;
    }

    public String getCustomerConfigNo() {
        return customerConfigNo;
    }

    public void setCustomerConfigNo(String customerConfigNo) {
        this.customerConfigNo = customerConfigNo;
    }

    public String getCustomerArrangeNo() {
        return customerArrangeNo;
    }

    public void setCustomerArrangeNo(String customerArrangeNo) {
        this.customerArrangeNo = customerArrangeNo;
    }

    public String getCustomerArrangeItem() {
        return customerArrangeItem;
    }

    public void setCustomerArrangeItem(String customerArrangeItem) {
        this.customerArrangeItem = customerArrangeItem;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getSimpleModel() {
        return simpleModel;
    }

    public void setSimpleModel(String simpleModel) {
        this.simpleModel = simpleModel;
    }

    public String getCustomerShelfNo2() {
        return customerShelfNo2;
    }

    public void setCustomerShelfNo2(String customerShelfNo2) {
        this.customerShelfNo2 = customerShelfNo2;
    }

    public String getLabelBarcode() {
        return labelBarcode;
    }

    public void setLabelBarcode(String labelBarcode) {
        this.labelBarcode = labelBarcode;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public Integer getContractRemnant() {
        return contractRemnant;
    }

    public void setContractRemnant(Integer contractRemnant) {
        this.contractRemnant = contractRemnant;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    public String getOriginQualify() {
        return originQualify;
    }

    public void setOriginQualify(String originQualify) {
        this.originQualify = originQualify;
    }

    public String getPriorInformationNumber() {
        return priorInformationNumber;
    }

    public void setPriorInformationNumber(String priorInformationNumber) {
        this.priorInformationNumber = priorInformationNumber;
    }

    public String getPriorInformationLineNumber() {
        return priorInformationLineNumber;
    }

    public void setPriorInformationLineNumber(String priorInformationLineNumber) {
        this.priorInformationLineNumber = priorInformationLineNumber;
    }

    public String getOriginQualifySign() {
        return originQualifySign;
    }

    public void setOriginQualifySign(String originQualifySign) {
        this.originQualifySign = originQualifySign;
    }

    public String getCustomerProductName() {
        return customerProductName;
    }

    public void setCustomerProductName(String customerProductName) {
        this.customerProductName = customerProductName;
    }

    public String getArrangeNo() {
        return arrangeNo;
    }

    public void setArrangeNo(String arrangeNo) {
        this.arrangeNo = arrangeNo;
    }

    public String getArrangeItem() {
        return arrangeItem;
    }

    public void setArrangeItem(String arrangeItem) {
        this.arrangeItem = arrangeItem;
    }

    public String getJpScatteredNo() {
        return jpScatteredNo;
    }

    public void setJpScatteredNo(String jpScatteredNo) {
        this.jpScatteredNo = jpScatteredNo;
    }

    public String getJpEmailAddress() {
        return jpEmailAddress;
    }

    public void setJpEmailAddress(String jpEmailAddress) {
        this.jpEmailAddress = jpEmailAddress;
    }

    public String getSalesInformationNo() {
        return salesInformationNo;
    }

    public void setSalesInformationNo(String salesInformationNo) {
        this.salesInformationNo = salesInformationNo;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getCustomerOrderType() {
        return customerOrderType;
    }

    public void setCustomerOrderType(String customerOrderType) {
        this.customerOrderType = customerOrderType;
    }
}
