package com.smc.smccloud.model.order;

import com.sales.ops.dto.zd.ZDPageShowOrderBindInvDto;
import lombok.Data;

/**
 * @Author lyc
 * @Date 2023/10/8 10:33
 * @Descripton TODO
 */
public class ZdWithOrderModifyVO extends ZDPageShowOrderBindInvDto {
    private String batchNo;
    private String optUserNo;
    private String applyNo;

    public ZdWithOrderModifyVO() {
    }

    public ZdWithOrderModifyVO(Long invId, String invTableType, Integer qty, Boolean exceptionOrNot, Boolean assModleFlag, Long itemInvId, Boolean dbOrNot, String dbDoId, String orderId, String orderItem, Integer num, String doId, String pcoId, Integer pcoItem, String modelNo, String inventoryTypeCode, String warehouseCode, String pageShowInvTableType, Boolean actionFlag, String actionMsg) {
        super(invId, invTableType, qty, exceptionOrNot, assModleFlag, itemInvId, dbOrNot, dbDoId, orderId, orderItem, num, doId, pcoId, pcoItem, modelNo, inventoryTypeCode, warehouseCode, pageShowInvTableType, actionFlag, actionMsg);
    }

    public ZdWithOrderModifyVO(Long invId, String invTableType, Integer qty, Boolean exceptionOrNot, Boolean assModleFlag, Long itemInvId, Boolean dbOrNot, String dbDoId, String orderId, String orderItem, Integer num, String doId, String pcoId, Integer pcoItem, String modelNo, String inventoryTypeCode, String warehouseCode, String pageShowInvTableType, Boolean actionFlag, String actionMsg, String batchNo, String optUserNo, String applyNo) {
        super(invId, invTableType, qty, exceptionOrNot, assModleFlag, itemInvId, dbOrNot, dbDoId, orderId, orderItem, num, doId, pcoId, pcoItem, modelNo, inventoryTypeCode, warehouseCode, pageShowInvTableType, actionFlag, actionMsg);
        this.batchNo = batchNo;
        this.optUserNo = optUserNo;
        this.applyNo = applyNo;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getOptUserNo() {
        return optUserNo;
    }

    public void setOptUserNo(String optUserNo) {
        this.optUserNo = optUserNo;
    }

    public String getApplyNo() {
        return applyNo;
    }

    public void setApplyNo(String applyNo) {
        this.applyNo = applyNo;
    }
}
