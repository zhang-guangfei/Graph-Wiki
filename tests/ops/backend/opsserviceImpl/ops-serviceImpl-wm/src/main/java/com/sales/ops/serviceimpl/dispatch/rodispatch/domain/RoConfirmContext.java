package com.sales.ops.serviceimpl.dispatch.rodispatch.domain;

import cn.hutool.core.util.StrUtil;
import com.sales.ops.db.entity.OpsRo;
import com.sales.ops.db.entity.OpsRoItem;
import com.sales.ops.dto.inventory.OpsRoDto;
import com.sales.ops.dto.inventory.WmRoConfirmDto;
import com.sales.ops.dto.util.UserDto;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author C12961
 * @date 2023/4/27 8:06
 */
public class RoConfirmContext {

    private final String roId;
    private final String modelNo;
    private final String warehouseCode;
    private final String invoiceNo;
    private final Long invoiceId;
    private final String barCode;
    private final String scanType;
    private final String caseNo;
    private final int barCodeQty;// 一箱的数量
    private final UserDto userDto;
    private OpsRoDto opsRoDto;

    private List<RoConfirmContextItem> items;

    public RoConfirmContext(WmRoConfirmDto wmRoConfirmDto) {
        this.roId = wmRoConfirmDto.getReceiveOrderCode();
        this.warehouseCode = wmRoConfirmDto.getWarehouseCode();
        this.modelNo = wmRoConfirmDto.getModelNo();// 型号
        this.invoiceNo = wmRoConfirmDto.getInvoiceNo();
        this.invoiceId = wmRoConfirmDto.getInvoiceId();
        this.scanType = wmRoConfirmDto.getScanType();
        this.barCode = wmRoConfirmDto.getReceiveCode();// 箱码
        // 【bug修复】12898 计算前先转大写 C12961 2023-12-20
        this.caseNo = StringUtils.equals(wmRoConfirmDto.getScanType(), "2") ? StrUtil.removePrefix(this.barCode.toUpperCase(), this.invoiceNo) : null;
        this.barCodeQty = Integer.parseInt(wmRoConfirmDto.getQty());
        this.userDto = new UserDto(wmRoConfirmDto.getUsername());
        this.items = new ArrayList<>();
    }

    public String getRoType() {
        return opsRoDto.getOpsRo().getRoType();
    }

    public void addItems(RoConfirmContextItem item) {
        this.items.add(item);
    }


    public String getRoId() {
        return roId;
    }


    public OpsRoDto getOpsRoDto() {
        return opsRoDto;
    }

    public OpsRo getRo() {
        return this.opsRoDto.getOpsRo();
    }

    public OpsRoItem getRoItem() {
        return this.opsRoDto.getOpsRoItem();
    }

    public void setOpsRoDto(OpsRoDto opsRoDto) {
        this.opsRoDto = opsRoDto;
    }


    public void refreshRoDto(OpsRo opsRo, OpsRoItem roItem) {
        this.opsRoDto.setOpsRo(opsRo);
        this.opsRoDto.setOpsRoItem(roItem);
    }


    public String getModelNo() {
        return modelNo;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public String getBarCode() {
        return barCode;
    }

    public String getScanType() {
        return scanType;
    }

    public String getCaseNo() {
        return caseNo;
    }

    public int getBarCodeQty() {
        return barCodeQty;
    }


    public UserDto getUserDto() {
        return userDto;
    }

    public List<RoConfirmContextItem> getItems() {
        return items;
    }

}
