package com.sales.ops.dto.poDeliver;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 采购发货查询 DTO
 * 
 * @author SMC892N
 */
public class PoDeliverQueryDto {


    private String orderNo;
    private Integer itemNo;
    private Integer splitNo;

    /**
     * 采购状态 (ops_purchaseInvoice.stateCode)
     */
    private String stateCode;

    /**
     * 供应商 (ops_purchaseInvoice.supplierId)
     */
    private String supplierId;

    /**
     * 运输方式 (ops_purchaseInvoice.transType)
     */
    private String transType;

    /**
     * 指定制造出荷日 (ops_purchaseInvoice.hopeExportDate)
     */
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    private Date hopeExportDate;

    /**
     * 供应商接单号 (ops_purchaseInvoice.replyOrderNo)
     */
    private String replyOrderNo;

    /**
     * 供应商接单日 (ops_purchaseInvoice.replyOrderDate)
     */
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    private Date replyOrderDate;

    /**
     * 采购日期 (ops_purchaseInvoice.purchaseDate)
     */
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    private Date purchaseDate;

    /**
     * 出库区分 (ops_purchaseInvoice.producefactory)
     */
    private String produceFactory;

    /**
     * 返信纳期 (ops_purchaseInvoice.replyExportDate)
     */
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    private Date replyExportDate;

    /**
     * 开始生产日 (ops_purchaseInvoice.beginProduceDate)
     */
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    private Date beginProduceDate;

    /**
     * 实际完工日 (ops_po_delivery_fact.delivery_time_H)
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    private Date deliveryTimeH;

    /**
     * 实际入库日 (ops_po_delivery_fact.delivery_time_W)
     */
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    private Date deliveryTimeW;

    /**
     * 发票号 (ops_purchaseInvoice.invoiceNo)
     */
    private String invoiceNo;

    /**
     * 发票 ID (ops_purchaseInvoice.invoiceId)
     */
    private Long invoiceId;

    /**
     * 报关日 (ops_purchaseInvoice.customs_date)
     */
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    private Date customsDate;

    /**
     * 预计到货日 (imp_invoice_master.pre_arrive_date)
     */
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    private Date preArriveDate;

    /**
     * 发票入库时间 (imp_invoice_master.confirm_date)
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    private Date confirmDate;

    /**
     * 到货数量 (imp_invoice_detail.quantity)
     */
    private Integer quantity;

    /**
     * 发票状态 (imp_invoice_master.status)
     */
    private Integer invoiceStatus;

    /**
     * 签收时间 (imp_invoice_master.arrive_date)
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    private Date arriveDate;

    /**
     * 收货仓 (imp_invoice_master.arrived_warehouse_code)
     */
    private String arrivedWarehouseCode;

    /**
     * 实际出港日 (imp_invoice_master.ship_date)
     */
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    private Date shipDate;

    /**
     * 实际出库日 (ops_po_delivery_fact.delivery_time_A)
     */
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    private Date deliveryTimeA;

    /**
     * 详细状态 (ops_purchaseInvoice.detailStatusCode)
     */
    private String detailStatusCode;

    /**
     * 状态描述 (ops_purchaseInvoice.detailStatusCode)
     */
    private String statusDescription;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getItemNo() {
        return itemNo;
    }

    public void setItemNo(Integer itemNo) {
        this.itemNo = itemNo;
    }

    public Integer getSplitNo() {
        return splitNo;
    }

    public void setSplitNo(Integer splitNo) {
        this.splitNo = splitNo;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public Date getHopeExportDate() {
        return hopeExportDate;
    }

    public void setHopeExportDate(Date hopeExportDate) {
        this.hopeExportDate = hopeExportDate;
    }

    public String getReplyOrderNo() {
        return replyOrderNo;
    }

    public void setReplyOrderNo(String replyOrderNo) {
        this.replyOrderNo = replyOrderNo;
    }

    public Date getReplyOrderDate() {
        return replyOrderDate;
    }

    public void setReplyOrderDate(Date replyOrderDate) {
        this.replyOrderDate = replyOrderDate;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getProduceFactory() {
        return produceFactory;
    }

    public void setProduceFactory(String produceFactory) {
        this.produceFactory = produceFactory;
    }

    public Date getReplyExportDate() {
        return replyExportDate;
    }

    public void setReplyExportDate(Date replyExportDate) {
        this.replyExportDate = replyExportDate;
    }

    public Date getBeginProduceDate() {
        return beginProduceDate;
    }

    public void setBeginProduceDate(Date beginProduceDate) {
        this.beginProduceDate = beginProduceDate;
    }

    public Date getDeliveryTimeH() {
        return deliveryTimeH;
    }

    public void setDeliveryTimeH(Date deliveryTimeH) {
        this.deliveryTimeH = deliveryTimeH;
    }

    public Date getDeliveryTimeW() {
        return deliveryTimeW;
    }

    public void setDeliveryTimeW(Date deliveryTimeW) {
        this.deliveryTimeW = deliveryTimeW;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Date getCustomsDate() {
        return customsDate;
    }

    public void setCustomsDate(Date customsDate) {
        this.customsDate = customsDate;
    }

    public Date getPreArriveDate() {
        return preArriveDate;
    }

    public void setPreArriveDate(Date preArriveDate) {
        this.preArriveDate = preArriveDate;
    }

    public Date getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(Date confirmDate) {
        this.confirmDate = confirmDate;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(Integer invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    public Date getArriveDate() {
        return arriveDate;
    }

    public void setArriveDate(Date arriveDate) {
        this.arriveDate = arriveDate;
    }

    public String getArrivedWarehouseCode() {
        return arrivedWarehouseCode;
    }

    public void setArrivedWarehouseCode(String arrivedWarehouseCode) {
        this.arrivedWarehouseCode = arrivedWarehouseCode;
    }

    public Date getShipDate() {
        return shipDate;
    }

    public void setShipDate(Date shipDate) {
        this.shipDate = shipDate;
    }

    public Date getDeliveryTimeA() {
        return deliveryTimeA;
    }

    public void setDeliveryTimeA(Date deliveryTimeA) {
        this.deliveryTimeA = deliveryTimeA;
    }

    public String getDetailStatusCode() {
        return detailStatusCode;
    }

    public void setDetailStatusCode(String detailStatusCode) {
        this.detailStatusCode = detailStatusCode;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    /**
     * 日期格式化辅助方法 (供 SpEL 表达式使用)
     * 
     * @param pattern 格式模式，如 "yyyy-MM-dd" 或 "yyyy-MM-dd HH:mm:ss"
     * @return 格式化后的字符串
     */
    public String formatDate(String pattern) {
        // 这个方法会被 SpEL 在上下文中自动调用
        // 但我们需要一个更好的方式，见下面的说明
        return null;
    }

    /**
     * 静态日期格式化方法 (供 SpEL 表达式使用)
     * 模板中使用：#{formatDate(hopeExportDate,'yyyy-MM-dd')}
     * 
     * @param date 日期对象
     * @param pattern 格式模式，如 "yyyy-MM-dd" 或 "yyyy-MM-dd HH:mm:ss"
     * @return 格式化后的字符串
     */
    public static String formatDate(Date date, String pattern) {
        if (date == null || pattern == null) {
            return "";
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            return sdf.format(date);
        } catch (Exception e) {
            return date.toString(); // 降级处理
        }
    }
}
