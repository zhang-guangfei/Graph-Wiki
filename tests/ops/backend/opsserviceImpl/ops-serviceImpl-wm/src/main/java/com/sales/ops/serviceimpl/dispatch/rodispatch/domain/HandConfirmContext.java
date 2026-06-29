package com.sales.ops.serviceimpl.dispatch.rodispatch.domain;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.common.until.MapContainer;
import com.sales.ops.db.entity.OpsDo;
import com.sales.ops.db.entity.OpsWarehouse;
import com.sales.ops.dto.flux.HandConfirm;
import com.sales.ops.dto.flux.HandItem;
import com.sales.ops.enums.WarehouseTypeEnum;
import com.smc.smccloud.model.csstock.CsImportDataDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author C12961
 * @date 2023/5/8 16:32
 */
public class HandConfirmContext {
    private HandConfirm params;
    private Long invoiceId;
    private MapContainer<String, HandItem> container = new MapContainer<>();
    private List<HandConfirmContextItem> items = new ArrayList<>();
    private Set<String> roIds = new HashSet<>();

    private OpsWarehouse targetWarehouse;

    private List<CsImportDataDTO> csImportDataDTOS = new ArrayList<>();
    private List<OpsDo> jycks = new ArrayList<>();

    public HandConfirmContext(HandConfirm handConfirm) throws OpsException {
        this.invoiceId = System.currentTimeMillis();
        this.params = handConfirm;
        for (HandItem item : handConfirm.getHandlist()) {
            this.container.put(item.getDoid(), item);
        }
        container.forEach((doId, handItemList) -> {
            // 计算此调拨单在这个发票中一共要出几个数量，qty肯定小于调拨单的出库总数
            int qty = handItemList.stream().map(HandItem::getQty).reduce(Integer::sum).get();
            HandConfirmContextItem item = new HandConfirmContextItem(doId, qty, handItemList);
            items.add(item);
        });
    }

    public OpsWarehouse getTargetWarehouse() {
        return this.targetWarehouse;
    }

    public String getWarehouseCode() {
        return this.targetWarehouse.getWarehouseCode();
    }

    public void setTargetWarehouse(OpsWarehouse targetWarehouse) {
        this.targetWarehouse = targetWarehouse;
    }

    public void addRoId(String roId) {
        this.roIds.add(roId);
    }

    public List<String> getRoIds() {
        return new ArrayList<>(this.roIds);
    }

    public HandConfirm getParams() {
        return this.params;
    }

    public String getInvoiceNo() {
        return this.params.getInvoice();
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public List<HandConfirmContextItem> getItems() {
        return this.items;
    }

    public List<CsImportDataDTO> getCsImportDataDTOS() {
        return this.csImportDataDTOS;
    }

    public void addImportData(CsImportDataDTO importDataDTO) {
        this.csImportDataDTOS.add(importDataDTO);
    }

    public boolean isWT() {
        return StringUtils.equals(WarehouseTypeEnum.WT.getHouseTypeCode(), targetWarehouse.getWarehouseType());
    }

    public List<OpsDo> getJycks() {
        return jycks;
    }

}
