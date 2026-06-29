package com.sales.ops.dto.order;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2023/8/17 14:42
 */
public class FinishPoDto implements Serializable {
    private static final long serialVersionUID = -4293527250352359154L;

    //采购单号
    private String pOrderNo;
    //采购单项号
    private Integer pOrderItem;
    //采购单拆分号
    private Integer pSplitNo;

    public FinishPoDto(String pOrderNo,Integer pOrderItem,Integer pSplitNo){
        this.pOrderNo = pOrderNo;
        this.pOrderItem = pOrderItem;
        this.pSplitNo = pSplitNo;
    }

    public String getpOrderNo() {
        return pOrderNo;
    }

    public void setpOrderNo(String pOrderNo) {
        this.pOrderNo = pOrderNo;
    }

    public Integer getpOrderItem() {
        return pOrderItem;
    }

    public void setpOrderItem(Integer pOrderItem) {
        this.pOrderItem = pOrderItem;
    }

    public Integer getpSplitNo() {
        return pSplitNo;
    }

    public void setpSplitNo(Integer pSplitNo) {
        this.pSplitNo = pSplitNo;
    }
}
