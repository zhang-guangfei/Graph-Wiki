package com.sales.ops.dto.order;

import com.sales.ops.db.entity.OpsDo;
import com.sales.ops.db.entity.OpsDoItem;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：到货指令和明细
 * @date ：Created in 2021/10/28 8:20
 */
public class OpsDoAndItemDto implements Serializable {

    private static final long serialVersionUID = 3230781877675574748L;

    private OpsDo opsDo;

    private Long wmOrderTaskId;

    private String yuekuDoId;//越库用 上预约用

    private String yuKuRoId;//越库用 传roId

    private String warehouseCodeName;//仓库名字

    private List<OpsDoItem> list;

    public OpsDo getOpsDo() {
        return opsDo;
    }

    public void setOpsDo(OpsDo opsDo) {
        this.opsDo = opsDo;
    }

    public List<OpsDoItem> getList() {
        return list;
    }

    public void setList(List<OpsDoItem> list) {
        this.list = list;
    }

    public Long getWmOrderTaskId() {
        return wmOrderTaskId;
    }

    public void setWmOrderTaskId(Long wmOrderTaskId) {
        this.wmOrderTaskId = wmOrderTaskId;
    }

    public String getYuekuDoId() {
        return yuekuDoId;
    }

    public void setYuekuDoId(String yuekuDoId) {
        this.yuekuDoId = yuekuDoId;
    }

    public String getYuKuRoId() {
        return yuKuRoId;
    }

    public void setYuKuRoId(String yuKuRoId) {
        this.yuKuRoId = yuKuRoId;
    }

    public String getWarehouseCodeName() {
        return warehouseCodeName;
    }

    public void setWarehouseCodeName(String warehouseCodeName) {
        this.warehouseCodeName = warehouseCodeName;
    }

    @Override
    public String toString() {
        return "OpsDoAndItemDto{" +
                "opsDo=" + opsDo.getDoId() +
                ", wmOrderTaskId=" + wmOrderTaskId +
                ", yuekuDoId='" + yuekuDoId +
                ", yuKuRoId='" + yuKuRoId +
                '}';
    }
}
