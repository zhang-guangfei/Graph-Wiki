package com.sales.ops.serviceimpl.dispatch.dodispatch.service;

import com.sales.ops.common.opsexception.Exceptions;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.dao.OpsCustomerWldateMapper;
import com.sales.ops.db.entity.*;
import com.sales.ops.enums.InventoryTableTypeEnum;
import com.sales.ops.service.wmOrder.BaseDoService;
import com.sales.ops.serviceimpl.inventory.BaseInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author C12961
 * @date 2023/4/6 14:01
 */
@Service
public class GoodsReadyJudgement {

    @Autowired
    private BaseDoService baseDoService;
    @Autowired
    private BaseInventoryService baseInventoryService;
    @Autowired
    private OpsCustomerWldateMapper opsCustomerWldateMapper;

    public boolean isEnoughToCrossForDo(OpsDo opsDo, String invoice) throws OpsException {
        OpsDoItem opsDoItem = baseDoService.getDoItemByDoId(opsDo.getDoId());
        if (null == opsDoItem) {
            throw Exceptions.OpsException("无对应的opsDoItem:doId=>" + opsDo.getDoId());
        }
        // DOItemInventory
        List<OpsDoItemInventory> subdoItemInventoryList = baseDoService.getDoItemInventoryByDoId(opsDo.getDoId(),
                null);
        int itemQty = 0;
        for (OpsDoItemInventory subdoItemInventory : subdoItemInventoryList) {
            // 在途表
            if (InventoryTableTypeEnum.TRANS.getType().equals(subdoItemInventory.getInventoryTableType())) {
                // 在途
                OpsInventoryMove opsInventoryMove = baseInventoryService
                        .getInventoryMoveById(subdoItemInventory.getInventoryId());
                if (Objects.nonNull(opsInventoryMove)) {
                    //在途表只判断当前发票内的数量
                    if (Objects.equals(opsInventoryMove.getInvoiceno(), invoice)) {
                        itemQty = itemQty + subdoItemInventory.getUseQty();
                    }
                }
            }
            // 物流在库表
            if (InventoryTableTypeEnum.NORMAL.getType().equals(subdoItemInventory.getInventoryTableType())) {
                itemQty = itemQty + subdoItemInventory.getUseQty();
            }
        }
        if (opsDoItem.getQty() != itemQty) {
            return false;
        }
        return true;
    }

    public boolean shipByCustomerDate(String customerNo) {
        OpsCustomerWldateExample example = new OpsCustomerWldateExample();
        example.createCriteria().andCustomerNoEqualTo(customerNo).andIsWldateEqualTo(1).andDelFlagEqualTo(0);
        Long count = opsCustomerWldateMapper.countByExample(example);
        return count > 0 ? true : false;
    }


    /**
     * 是否达到物流时间（可越库的时间判断） 当天>=物流发货日期，可越库。 小于发货日不越库
     * @param wlDate 物流时间
     * @return 1到达物流日期 0未到达物流日期
     */
    private boolean isArriveWlDate(Date wlDate) {

        if (null == wlDate) {
            return false;
        }
        int wlDateDay = com.smc.smccloud.core.utils.DateUtil.getDate(new Date()).compareTo(wlDate);
        if (wlDateDay >= 0) {
            return true;
        }
        return false;
    }

}
