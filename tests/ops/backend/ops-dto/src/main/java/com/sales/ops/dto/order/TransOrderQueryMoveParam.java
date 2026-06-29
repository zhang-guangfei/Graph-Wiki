package com.sales.ops.dto.order;

import java.io.Serializable;
import java.util.List;

/**
 * @author C12961
 * @date 2022/11/27 15:12
 */
public class TransOrderQueryMoveParam implements Serializable {


    // 可为空,默认返回全部仓库
    private List<String> warehouseCode;
    // 型号，必填
    private String modelno;
    /**
     * 库存状态 必填至少一项
     * 生产中: P;
     * 采购在途: T1;
     * 调拨在途: T3;
     * 退货在途: T4;
     * 到货未入库: W
     */
    private List<String> statusCode;
    /**
     * 关联单号类型 必填至少一项
     * 0: 88开头的先行在库补库单
     * 1: 99开头的bin补库单
     * 2: 其他订单
     */
    private List<Integer> associateNoType;
    /**
     * 在途来源 必填至少一项
     * 0：采购；
     * 1：调拨；
     * 2：退货
     */
    private List<String> sourceType;


    public List<String> getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(List<String> warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno;
    }

    public List<String> getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(List<String> statusCode) {
        this.statusCode = statusCode;
    }

    public List<Integer> getAssociateNoType() {
        return associateNoType;
    }

    public void setAssociateNoType(List<Integer> associateNoType) {
        this.associateNoType = associateNoType;
    }

    public List<String> getSourceType() {
        return sourceType;
    }

    public void setSourceType(List<String> sourceType) {
        this.sourceType = sourceType;
    }
}
