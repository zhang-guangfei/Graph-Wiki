package com.sales.ops.dto.purchase;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 制造订单发送DTO,为接口调用做准备
 */
public class OrderToManuDto implements Serializable {

    // 客户代码
	private String customerCode;

	// 收货客户代码
    private String acceptCustomerCode;

    //接单号
    private String requestNo;

    //子项号
    private  String itemNo;
    //接单型号
    private String requestModel;
    //营业型号
    private String salesModel;
    private String simpleModel;
    //订货数量
    private Integer requestQty;

    //部门代码
    private String salesOfficeCode;
    /**
     * @deprecated  发行日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = Shape.STRING, timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date issueDate;
    //提单日
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = Shape.STRING, timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date instrDlvyDate;
    //运输方式
    private String dlvyWay;
    // 订单类型
    private String  instrType;
    //日本订单号
    private String jpOrderNo;

    //子项号
    private String jpItemNo;

    //货架号
    private String jpShelfNo;

    //GateNo，客户订单:AAA,拆分客户订单:999,补库订单:货位号前两位
    private String gateNo;
    //ZoneMark，采购仓库代码前三位
    private  String zoneMark;
    //客户订单:客户代码,补库订单:货位号前10 位
    private  String sortNo;
    // 日本工号
    private  String jpKoGo;
    //日本备注，打印在指示上,提醒制造现场注意:1.二次电池;2.重点客户:vipcode;3.组装;4.营业所名称;5.>30 的型号全型号写入
    private  String jpRemarks;
    //收货人
    private  String accepter;
    //营业订单类型，1:中国工厂;2:制六;3:北京工厂;4:中国工厂(未使用);5:上海特注(未使用);6:天津工厂
    private  String  requestType;

    private  String purchaseType;

    // 新增操作人
    private  String Operator;

    private String contractNo;

    private String jpSerialNoMark;

    private String sampleModel;

    private BigDecimal boxFixedQty;

    private String boxType;

    private String packType;

    //绿标
    private String greenMark;

    // 2023-10-25 新增最终用户、VIPcode、vipPriority字段

    private String endUser;

    private String vipCode;

    private Integer vipPriority;

    public void setVipPriority(Integer vipPriority) {
        this.vipPriority = vipPriority;
    }

    public Integer getVipPriority() {
        return vipPriority;
    }

    public String getEndUser() {
        return endUser;
    }

    public void setEndUser(String endUser) {
        this.endUser = endUser;
    }

    public String getVipCode() {
        return vipCode;
    }

    public void setVipCode(String vipCode) {
        this.vipCode = vipCode;
    }

    public String getGreenMark() {
        return greenMark;
    }

    public void setGreenMark(String greenMark) {
        this.greenMark = greenMark;
    }

    public OrderToManuDto() {
    }

    public BigDecimal getBoxFixedQty() {
        return boxFixedQty;
    }

    public void setBoxFixedQty(BigDecimal boxFixedQty) {
        this.boxFixedQty = boxFixedQty;
    }


    public String getJpSerialNoMark() {
        return jpSerialNoMark;
    }

    public void setJpSerialNoMark(String jpSerialNoMark) {
        this.jpSerialNoMark = jpSerialNoMark;
    }

    public String getSampleModel() {
        return sampleModel;
    }

    public void setSampleModel(String sampleModel) {
        this.sampleModel = sampleModel;
    }

    public String getBoxType() {
        return boxType;
    }

    public void setBoxType(String boxType) {
        this.boxType = boxType;
    }

    public String getPackType() {
        return packType;
    }

    public void setPackType(String packType) {
        this.packType = packType;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    //提单日
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = Shape.STRING, timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date reqDlvyDate;

    public Date getReqDlvyDate() {
        return reqDlvyDate;
    }

    public void setReqDlvyDate(Date reqDlvyDate) {
        this.reqDlvyDate = reqDlvyDate;
    }

    public String getSalesOfficeCode() {
        return salesOfficeCode;
    }

    public void setSalesOfficeCode(String salesOfficeCode) {
        this.salesOfficeCode = salesOfficeCode;
    }

    public String getOperator() {
        return Operator;
    }

    public void setOperator(String operator) {
        Operator = operator;
    }

    public String getJpOrderNo() {
        return jpOrderNo;
    }

    public void setJpOrderNo(String jpOrderNo) {
        this.jpOrderNo = jpOrderNo;
    }

    public String getJpItemNo() {
        return jpItemNo;
    }

    public void setJpItemNo(String jpItemNo) {
        this.jpItemNo = jpItemNo;
    }

    public String getJpShelfNo() {
        return jpShelfNo;
    }

    public void setJpShelfNo(String jpShelfNo) {
        this.jpShelfNo = jpShelfNo;
    }

    public String getJpKoGo() {
        return jpKoGo;
    }

    public void setJpKoGo(String jpKoGo) {
        this.jpKoGo = jpKoGo;
    }

    public String getJpRemarks() {
        return jpRemarks;
    }

    public void setJpRemarks(String jpRemarks) {
        this.jpRemarks = jpRemarks;
    }

    public String getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(String purchaseType) {
        this.purchaseType = purchaseType;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getAcceptCustomerCode() {
        return acceptCustomerCode;
    }

    public void setAcceptCustomerCode(String acceptCustomerCode) {
        this.acceptCustomerCode = acceptCustomerCode;
    }

    public String getRequestNo() {
        return requestNo;
    }

    public void setRequestNo(String requestNo) {
        this.requestNo = requestNo;
    }

    public String getItemNo() {
        return itemNo;
    }

    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    public String getRequestModel() {
        return requestModel;
    }

    public void setRequestModel(String requestModel) {
        this.requestModel = requestModel;
    }

    public String getSalesModel() {
        return salesModel;
    }

    public void setSalesModel(String salesModel) {
        this.salesModel = salesModel;
    }

    public String getSimpleModel() {
        return simpleModel;
    }

    public void setSimpleModel(String simpleModel) {
        this.simpleModel = simpleModel;
    }

    public Integer getRequestQty() {
        return requestQty;
    }

    public void setRequestQty(Integer requestQty) {
        this.requestQty = requestQty;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getInstrDlvyDate() {
        return instrDlvyDate;
    }

    public void setInstrDlvyDate(Date instrDlvyDate) {
        this.instrDlvyDate = instrDlvyDate;
    }

    public String getDlvyWay() {
        return dlvyWay;
    }

    public void setDlvyWay(String dlvyWay) {
        this.dlvyWay = dlvyWay;
    }

    public String getInstrType() {
        return instrType;
    }

    public void setInstrType(String instrType) {
        this.instrType = instrType;
    }

    public String getGateNo() {
        return gateNo;
    }

    public void setGateNo(String gateNo) {
        this.gateNo = gateNo;
    }

    public String getZoneMark() {
        return zoneMark;
    }

    public void setZoneMark(String zoneMark) {
        this.zoneMark = zoneMark;
    }

    public String getSortNo() {
        return sortNo;
    }

    public void setSortNo(String sortNo) {
        this.sortNo = sortNo;
    }

    public String getAccepter() {
        return accepter;
    }

    public void setAccepter(String accepter) {
        this.accepter = accepter;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

}