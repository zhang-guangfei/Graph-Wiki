package com.sales.ops.dto.order;

import com.sales.ops.db.entity.OpsPco;
import com.sales.ops.db.entity.OpsPcoItem;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：加工指令和明细
 * @date ：Created in 2021/10/28 8:21
 */
public class OpsPcoAddItemDto implements Serializable {

    private static final long serialVersionUID = 8658582502442402467L;

    private OpsPco opsPco;

    private List<OpsPcoItem> list;

    private Map<String,String> yuKuMap;//<subModelNo,pcoId> 越库 上预约用

    private String yuKuRoId;//越库用 传roId

    private Long wmOrderTaskId;

    private String doId;//加工单的交易出库单doId

    public OpsPco getOpsPco() {
        return opsPco;
    }

    public void setOpsPco(OpsPco opsPco) {
        this.opsPco = opsPco;
    }

    public List<OpsPcoItem> getList() {
        return list;
    }

    public void setList(List<OpsPcoItem> list) {
        this.list = list;
    }

    public Long getWmOrderTaskId() {
        return wmOrderTaskId;
    }

    public void setWmOrderTaskId(Long wmOrderTaskId) {
        this.wmOrderTaskId = wmOrderTaskId;
    }

    public Map<String, String> getYuKuMap() {
        return yuKuMap;
    }

    public void setYuKuMap(Map<String, String> yuKuMap) {
        this.yuKuMap = yuKuMap;
    }

    public String getYuKuRoId() {
        return yuKuRoId;
    }

    public void setYuKuRoId(String yuKuRoId) {
        this.yuKuRoId = yuKuRoId;
    }

    public String getDoId() {
        return doId;
    }

    public void setDoId(String doId) {
        this.doId = doId;
    }

    @Override
    public String toString() {
        return "OpsPcoAddItemDto{" +
                "opsPco=" + opsPco.getPcoId() +
                ", yuKuMap=" + yuKuMap.toString() +
                ", yuKuRoId='" + yuKuRoId + '\'' +
                ", wmOrderTaskId=" + wmOrderTaskId +
                '}';
    }
}
