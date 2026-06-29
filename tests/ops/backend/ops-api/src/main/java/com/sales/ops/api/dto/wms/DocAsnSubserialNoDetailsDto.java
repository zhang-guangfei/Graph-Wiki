package com.sales.ops.api.dto.wms;

import java.io.Serializable;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：barcode_to_wms 接口文档4.4
 * @date ：Created in 2021/10/28 16:55
 */
public class DocAsnSubserialNoDetailsDto implements Serializable {

    private static final long serialVersionUID = 1760521419757594272L;

    private String warehouseId;//仓库代码 TRUE String(20)
    private String asnNo;//预期到货通知编号TRUEString(20)
    private String serialNo;//序列号TRUEString(40)
    private String subSerialNo;//子序列号TRUEString(40)
    private String customerId;//客户IDFALSEString(30)
    private String sku;//产品编号FALSEString(50)
    private String qty;//数量FALSEDECIMAL(18,8)
    private String qcResult;//质检结果FALSEString(10)
    private String noteText;//备注FALSEMEDIUMTEXT
    private String udf01;//自定义01FALSEString(500)
    private String udf02;//自定义02FALSEString(500)
    private String udf03;//自定义03FALSEString(500)
    private String udf04;//自定义04FALSEString(500)
    private String udf05;//自定义05FALSEString(500)

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getAsnNo() {
        return asnNo;
    }

    public void setAsnNo(String asnNo) {
        this.asnNo = asnNo;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getSubSerialNo() {
        return subSerialNo;
    }

    public void setSubSerialNo(String subSerialNo) {
        this.subSerialNo = subSerialNo;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getQcResult() {
        return qcResult;
    }

    public void setQcResult(String qcResult) {
        this.qcResult = qcResult;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
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
}
