package com.sales.ops.serviceimpl.dispatch.rodispatch.domain;

import cn.hutool.core.date.DateUtil;
import com.sales.ops.db.entity.OpsRoBarcode;
import com.sales.ops.db.entity.OpsWmOrderTask;
import com.sales.ops.dto.inventory.CreInvMoveForReturnOrderDto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


public class ReturnConfirmContext {
    private List<CreInvMoveForReturnOrderDto> dtoList;
    private String mmddhh;
    private String invoiceNo;
    private Long invoiceId;

    private List<ReturnConfirmContextItem> items;

    public ReturnConfirmContext(List<CreInvMoveForReturnOrderDto> dtoList) {
        this.mmddhh = DateUtil.format(new Date(), "MMddHHmm");
        this.dtoList = dtoList;
        CreInvMoveForReturnOrderDto dto = dtoList.get(0);
        String applyNo = dto.getApplyNo();
        String invoiceNo = applyNo + mmddhh;
        this.invoiceNo = invoiceNo.startsWith("TH") ? invoiceNo : "TH" + invoiceNo;
        this.invoiceId = System.currentTimeMillis();
        this.items = new ArrayList<>();
    }


    public void addItem(ReturnConfirmContextItem item) {
        items.add(item);
    }

    public List<OpsRoBarcode> getBarcodes() {
        return this.getItems().stream().map(ReturnConfirmContextItem::getBarcode).collect(Collectors.toList());
    }


    public List<OpsWmOrderTask> getTasks() {
        List<OpsWmOrderTask> roTask = this.getItems().stream().map(ReturnConfirmContextItem::getRoTask).collect(Collectors.toList());
        List<OpsWmOrderTask> barcodeTask = this.getItems().stream().map(ReturnConfirmContextItem::getBarcodeTask).collect(Collectors.toList());
        List<OpsWmOrderTask> tasks = new ArrayList<>();
        tasks.addAll(roTask);
        tasks.addAll(barcodeTask);
        return tasks;
    }

    public List<CreInvMoveForReturnOrderDto> getDtoList() {
        return dtoList;
    }

    public void setDtoList(List<CreInvMoveForReturnOrderDto> dtoList) {
        this.dtoList = dtoList;
    }

    public String getMmddhh() {
        return mmddhh;
    }

    public void setMmddhh(String mmddhh) {
        this.mmddhh = mmddhh;
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

    public List<ReturnConfirmContextItem> getItems() {
        return items;
    }

    public void setItems(List<ReturnConfirmContextItem> items) {
        this.items = items;
    }
}
