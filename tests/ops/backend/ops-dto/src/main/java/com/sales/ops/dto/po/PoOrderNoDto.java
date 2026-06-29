package com.sales.ops.dto.po;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2026/2/10 14:16
 */
public class PoOrderNoDto {

    private String orderno;

    private Integer itemno;

    private Integer splititemno;

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public Integer getItemno() {
        return itemno;
    }

    public void setItemno(Integer itemno) {
        this.itemno = itemno;
    }

    public Integer getSplititemno() {
        return splititemno;
    }

    public void setSplititemno(Integer splititemno) {
        this.splititemno = splititemno;
    }
}
