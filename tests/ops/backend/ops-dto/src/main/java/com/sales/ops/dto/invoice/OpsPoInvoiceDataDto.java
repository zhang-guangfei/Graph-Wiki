package com.sales.ops.dto.invoice;


import java.util.List;

/**
 * @author B91717
 * @date 2024/03/20
 * ops_po 表集合
 */

public class OpsPoInvoiceDataDto {

    private Long deliveryInvoiceId; // 导入时总表的ID
    private String sourceType; // 导入来源类别，方便进行分组

    private OpsPoDeliveryInvoiceLogFromSupplierDto opsPoDeliveryInvoiceLogFromSupplierDto; //delivery总表
    private List<OpsPoDeliveryInvoiceBarcodeFromSupplierDto> opsPoDeliveryInvoiceBarcodeFromSupplierDtos; // barcode表

    private OpsPoInvoicePriceLogFromSupplierDto opsPoInvoicePriceLogFromSupplierDto; // price总表
    private List<OpsPoInvoicePriceDetailLogFromSupplierDto> opsPoInvoicePriceDetailLogFromSupplierDtos; //  price明细表

    public Long getDeliveryInvoiceId() {
        return deliveryInvoiceId;
    }

    public void setDeliveryInvoiceId(Long deliveryInvoiceId) {
        this.deliveryInvoiceId = deliveryInvoiceId;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public OpsPoDeliveryInvoiceLogFromSupplierDto getOpsPoDeliveryInvoiceLogFromSupplierDto() {
        return opsPoDeliveryInvoiceLogFromSupplierDto;
    }

    public void setOpsPoDeliveryInvoiceLogFromSupplierDto(OpsPoDeliveryInvoiceLogFromSupplierDto opsPoDeliveryInvoiceLogFromSupplierDto) {
        this.opsPoDeliveryInvoiceLogFromSupplierDto = opsPoDeliveryInvoiceLogFromSupplierDto;
    }

    public List<OpsPoDeliveryInvoiceBarcodeFromSupplierDto> getOpsPoDeliveryInvoiceBarcodeFromSupplierDtos() {
        return opsPoDeliveryInvoiceBarcodeFromSupplierDtos;
    }

    public void setOpsPoDeliveryInvoiceBarcodeFromSupplierDtos(List<OpsPoDeliveryInvoiceBarcodeFromSupplierDto> opsPoDeliveryInvoiceBarcodeFromSupplierDtos) {
        this.opsPoDeliveryInvoiceBarcodeFromSupplierDtos = opsPoDeliveryInvoiceBarcodeFromSupplierDtos;
    }

    public OpsPoInvoicePriceLogFromSupplierDto getOpsPoInvoicePriceLogFromSupplierDto() {
        return opsPoInvoicePriceLogFromSupplierDto;
    }

    public void setOpsPoInvoicePriceLogFromSupplierDto(OpsPoInvoicePriceLogFromSupplierDto opsPoInvoicePriceLogFromSupplierDto) {
        this.opsPoInvoicePriceLogFromSupplierDto = opsPoInvoicePriceLogFromSupplierDto;
    }

    public List<OpsPoInvoicePriceDetailLogFromSupplierDto> getOpsPoInvoicePriceDetailLogFromSupplierDtos() {
        return opsPoInvoicePriceDetailLogFromSupplierDtos;
    }

    public void setOpsPoInvoicePriceDetailLogFromSupplierDtos(List<OpsPoInvoicePriceDetailLogFromSupplierDto> opsPoInvoicePriceDetailLogFromSupplierDtos) {
        this.opsPoInvoicePriceDetailLogFromSupplierDtos = opsPoInvoicePriceDetailLogFromSupplierDtos;
    }
}
