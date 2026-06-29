package com.sales.ops.dto.replacement;

import com.sales.ops.db.entity.OpsDo;
import com.sales.ops.db.entity.OpsDoItem;
import com.sales.ops.dto.inventory.WmDoItemConfirmDto;

import java.io.Serializable;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2024/11/6 15:57
 */
public class DoConfirmDto implements Serializable {
    private static final long serialVersionUID = 5582714873538436454L;

    private OpsDo opsDo;

    private OpsDoItem opsDoItem;

    private WmDoItemConfirmDto wmDoItemConfirmDto;

    public OpsDo getOpsDo() {
        return opsDo;
    }

    public void setOpsDo(OpsDo opsDo) {
        this.opsDo = opsDo;
    }

    public OpsDoItem getOpsDoItem() {
        return opsDoItem;
    }

    public void setOpsDoItem(OpsDoItem opsDoItem) {
        this.opsDoItem = opsDoItem;
    }

    public WmDoItemConfirmDto getWmDoItemConfirmDto() {
        return wmDoItemConfirmDto;
    }

    public void setWmDoItemConfirmDto(WmDoItemConfirmDto wmDoItemConfirmDto) {
        this.wmDoItemConfirmDto = wmDoItemConfirmDto;
    }
}
