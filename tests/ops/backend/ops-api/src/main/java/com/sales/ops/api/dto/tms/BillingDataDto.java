package com.sales.ops.api.dto.tms;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：7.4 TMS推送账单数据
 * @date ：Created in 2021/12/27 16:20
 */
public class BillingDataDto implements Serializable {

    private static final long serialVersionUID = 3455124074002434256L;

    private String billingNo;    //账单编号
    private String carrierId;    //承运人
    private String billMonth;    //账单月份
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date billDateFM;    //结算开始日期
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date billDateTO;    //结算结束日期
    private String statusName;    //账单审核状态
    private String totalAmount;    //明细汇总金额
    private String adjustedAmount;    //调整金额
    private String adjustNotes;    //调整原因
    private String totalBillingAmount;    //账单总金额
    private String settlementBranchId;    //结算机构
    private String taxRate;    //税率%
    private String tariffID;    //合同编号
    private String paymentMethodName;    //支付方式
    private String send_flag;    //标记
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date send_time;    //推送时间
    private String send_result;    //结果
    private String udf01;    //自定义01
    private String udf02;    //自定义02
    private String udf03;    //自定义03
    private String udf04;    //自定义04
    private String udf05;    //自定义05
    private String sysId;    //Datahub流水号

    public String getBillingNo() {
        return billingNo;
    }

    public void setBillingNo(String billingNo) {
        this.billingNo = billingNo;
    }

    public String getCarrierId() {
        return carrierId;
    }

    public void setCarrierId(String carrierId) {
        this.carrierId = carrierId;
    }

    public String getBillMonth() {
        return billMonth;
    }

    public void setBillMonth(String billMonth) {
        this.billMonth = billMonth;
    }

    public Date getBillDateFM() {
        return billDateFM;
    }

    public void setBillDateFM(Date billDateFM) {
        this.billDateFM = billDateFM;
    }

    public Date getBillDateTO() {
        return billDateTO;
    }

    public void setBillDateTO(Date billDateTO) {
        this.billDateTO = billDateTO;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getAdjustedAmount() {
        return adjustedAmount;
    }

    public void setAdjustedAmount(String adjustedAmount) {
        this.adjustedAmount = adjustedAmount;
    }

    public String getAdjustNotes() {
        return adjustNotes;
    }

    public void setAdjustNotes(String adjustNotes) {
        this.adjustNotes = adjustNotes;
    }

    public String getTotalBillingAmount() {
        return totalBillingAmount;
    }

    public void setTotalBillingAmount(String totalBillingAmount) {
        this.totalBillingAmount = totalBillingAmount;
    }

    public String getSettlementBranchId() {
        return settlementBranchId;
    }

    public void setSettlementBranchId(String settlementBranchId) {
        this.settlementBranchId = settlementBranchId;
    }

    public String getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(String taxRate) {
        this.taxRate = taxRate;
    }

    public String getTariffID() {
        return tariffID;
    }

    public void setTariffID(String tariffID) {
        this.tariffID = tariffID;
    }

    public String getPaymentMethodName() {
        return paymentMethodName;
    }

    public void setPaymentMethodName(String paymentMethodName) {
        this.paymentMethodName = paymentMethodName;
    }

    public String getSend_flag() {
        return send_flag;
    }

    public void setSend_flag(String send_flag) {
        this.send_flag = send_flag;
    }

    public Date getSend_time() {
        return send_time;
    }

    public void setSend_time(Date send_time) {
        this.send_time = send_time;
    }

    public String getSend_result() {
        return send_result;
    }

    public void setSend_result(String send_result) {
        this.send_result = send_result;
    }

    public String getUdf01() {
        return udf01;
    }

    public void setUdf01(String udf01) {
        this.udf01 = udf01;
    }

    public String getUdf02() {
        return udf02;
    }

    public void setUdf02(String udf02) {
        this.udf02 = udf02;
    }

    public String getUdf03() {
        return udf03;
    }

    public void setUdf03(String udf03) {
        this.udf03 = udf03;
    }

    public String getUdf04() {
        return udf04;
    }

    public void setUdf04(String udf04) {
        this.udf04 = udf04;
    }

    public String getUdf05() {
        return udf05;
    }

    public void setUdf05(String udf05) {
        this.udf05 = udf05;
    }

    public String getSysId() {
        return sysId;
    }

    public void setSysId(String sysId) {
        this.sysId = sysId;
    }
}
