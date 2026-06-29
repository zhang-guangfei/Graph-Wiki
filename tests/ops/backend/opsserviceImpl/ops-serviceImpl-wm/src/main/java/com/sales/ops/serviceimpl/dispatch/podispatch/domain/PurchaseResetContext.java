package com.sales.ops.serviceimpl.dispatch.podispatch.domain;

import com.sales.ops.db.entity.OpsInventoryMove;
import com.sales.ops.db.entity.OpsPurchaseorder;
import com.sales.ops.db.entity.OpsRequestpurchase;
import com.sales.ops.dto.util.UserDto;

import java.util.List;

/**
 * @author C12961
 * @date 2023/5/16 10:25
 */
public class PurchaseResetContext {
    private UserDto creator;
    private OpsPurchaseorder purchase;
    private List<OpsRequestpurchase> requests;
    private List<OpsInventoryMove> moves;

    public PurchaseResetContext(OpsPurchaseorder purchase, List<OpsRequestpurchase> requests) {
        this.creator = new UserDto("resetForPo");
        this.purchase = purchase;
        this.requests = requests;
    }


    public String getOrderNo() {
        return purchase.getOrderno();
    }

    public Integer getItemNo() {
        return purchase.getItemno();
    }

    public Integer getSplitNo() {
        return purchase.getSplititemno();
    }

    public OpsPurchaseorder getPurchase() {
        return purchase;
    }

    public List<OpsRequestpurchase> getRequests() {
        return requests;
    }

    public void setRequests(List<OpsRequestpurchase> requests) {
        this.requests = requests;
    }

    public UserDto getCreator() {
        return creator;
    }

    public void setMoves(List<OpsInventoryMove> moves) {
        this.moves = moves;
    }

    public List<OpsInventoryMove> getMoves() {
        return moves;
    }
}
