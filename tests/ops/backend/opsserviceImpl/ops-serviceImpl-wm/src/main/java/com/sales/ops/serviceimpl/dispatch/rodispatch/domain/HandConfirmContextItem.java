package com.sales.ops.serviceimpl.dispatch.rodispatch.domain;

import com.sales.ops.db.entity.*;
import com.sales.ops.dto.flux.HandItem;
import com.sales.ops.dto.inventory.OpsDoDto;
import com.sales.ops.dto.inventory.OpsRoDto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author C12961
 * @date 2023/5/8 16:32
 */
public class HandConfirmContextItem {

    private String doId;
    private String roId;
    private OpsDoDto dbckDto;
    private OpsRoDto dbrkDto;
    private int handQty;
    private List<HandItem> handItems;
    private List<OpsInventoryMove> moveList;


    public HandConfirmContextItem(String doId, int handQty, List<HandItem> handItems) {
        this.doId = doId;
        this.handQty = handQty;
        this.handItems = handItems;
        this.moveList = new ArrayList<>();
    }

    public void setDoAndRo(OpsDoDto opsDoDto, OpsRoDto opsRoDto) {
        this.dbckDto = opsDoDto;
        this.dbrkDto = opsRoDto;
        this.roId = opsRoDto.getOpsRo().getRoId();
    }

    public void setRo(OpsRoDto opsRoDto) {
        this.dbrkDto = opsRoDto;
        this.roId = opsRoDto.getOpsRo().getRoId();
    }

    public String getDoId() {
        return doId;
    }

    public String getRoId() {
        return roId;
    }

    public OpsDo getDBCK() {
        return this.dbckDto.getOpsDo();
    }

    public OpsRo getDBRK() {
        return this.dbrkDto.getOpsRo();
    }

    public OpsDoItem getDBCKItem() {
        return this.dbckDto.getDoItem();
    }

    public OpsRoItem getDBRKItem() {
        return this.dbrkDto.getOpsRoItem();
    }

    public OpsDoDto getDbckDto() {
        return dbckDto;
    }

    public int getHandQty() {
        return handQty;
    }

    public List<OpsInventoryMove> getMoveList() {
        return moveList;
    }

    public void setMoveList(List<OpsInventoryMove> moveList) {
        this.moveList = moveList;
    }

    public void addMove(OpsInventoryMove move) {
        this.moveList.add(move);
    }

    public List<HandItem> getHandItems() {
        return handItems;
    }
}
