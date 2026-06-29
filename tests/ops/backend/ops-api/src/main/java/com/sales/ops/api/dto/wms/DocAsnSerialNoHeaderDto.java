package com.sales.ops.api.dto.wms;

import java.io.Serializable;
import java.util.List;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：ROItem_TO_WMS 接口文档4.3
 * @date ：Created in 2021/10/28 16:54
 */
public class DocAsnSerialNoHeaderDto implements Serializable {

    private static final long serialVersionUID = 8864503341955771465L;

    private List<DocAsnSubserialNoDetailsDto> details;
    private String warehouseId;//仓库代码 TRUE String(20)
    private String customerId;//客户IDFALSE String(30)
    private String docNo;//上游来源订单号TRUE String(20)
    private String serialNo;//序列号TRUE String(40)
    private String sku;//产品编号FALSE String(50)
    private String secondSerialNo;//第二序列号FALSE String(40)
    private Integer qty;//数量FALSE DECIMAL(18,8)
    private String qcFlag;//质检标记FALSE CHAR(1)
    private String partialFlag;//尾箱标记FALSE String(1)
    private Integer qcQty;//质检数量FALSE INT
    private String grossWeight;//重量FALSE DECIMAL(18,8)
    private String cubic;//体积FALSE DECIMAL(18,8)
    private String cartonType;//箱型FALSE String(50)
    private String noteText;//备注FALSE MEDIUMTEXT
    private String udf01;//自定义01FALSE String(500)
    private String udf02;//自定义02FALSE String(500)
    private String udf03;//自定义03FALSE String(500)

    public List<DocAsnSubserialNoDetailsDto> getDetails() {
        return details;
    }

    public void setDetails(List<DocAsnSubserialNoDetailsDto> details) {
        this.details = details;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getDocNo() {
        return docNo;
    }

    public void setDocNo(String docNo) {
        this.docNo = docNo;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getSecondSerialNo() {
        return secondSerialNo;
    }

    public void setSecondSerialNo(String secondSerialNo) {
        this.secondSerialNo = secondSerialNo;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getQcFlag() {
        return qcFlag;
    }

    public void setQcFlag(String qcFlag) {
        this.qcFlag = qcFlag;
    }

    public String getPartialFlag() {
        return partialFlag;
    }

    public void setPartialFlag(String partialFlag) {
        this.partialFlag = partialFlag;
    }

    public Integer getQcQty() {
        return qcQty;
    }

    public void setQcQty(Integer qcQty) {
        this.qcQty = qcQty;
    }

    public String getGrossWeight() {
        return grossWeight;
    }

    public void setGrossWeight(String grossWeight) {
        this.grossWeight = grossWeight;
    }

    public String getCubic() {
        return cubic;
    }

    public void setCubic(String cubic) {
        this.cubic = cubic;
    }

    public String getCartonType() {
        return cartonType;
    }

    public void setCartonType(String cartonType) {
        this.cartonType = cartonType;
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
}
