package com.sales.ops.event.publisher.entity;



import java.util.Objects;
import java.util.Optional;

/**
 * @author C12961
 * @date 2023/2/17 9:34
 */
public class OrderNoInfo {

    String orderNo;
    Integer itemNo;
    Integer splitNo;

    public OrderNoInfo(String orderNo, Integer itemNo, Integer splitNo) {
        this.orderNo = orderNo;
        this.itemNo = itemNo;
        this.splitNo = splitNo;
    }

    public OrderNoInfo(String orderNo, String itemNo, Integer splitNo) {
        this.orderNo = orderNo;
        this.itemNo = Integer.valueOf(itemNo);
        this.splitNo = splitNo;
    }

    public OrderNoInfo() {
    }

    public OrderNoInfo setSplitNoNotNull(Integer splitNo) {
        this.splitNo = Optional.ofNullable(splitNo).orElse(0);
        return this;
    }

    public OrderNoInfo setSplitNoNotNull(String splitNo) {
        this.splitNo = Optional.ofNullable(Integer.valueOf(splitNo)).orElse(0);
        return this;
    }


    public String getOrderNo() {
        return orderNo;
    }

    public OrderNoInfo setOrderNo(String orderNo) {
        this.orderNo = orderNo;
        return this;
    }

    public Integer getItemNo() {
        return itemNo;
    }

    public OrderNoInfo setItemNo(Integer itemNo) {
        this.itemNo = itemNo;
        return this;
    }

    public OrderNoInfo setItemNo(String itemNo) {
        this.itemNo = Integer.valueOf(itemNo);
        return this;
    }

    public Integer getSplitNo() {
        return splitNo;
    }

    public OrderNoInfo getRorderFno(){
        return new OrderNoInfo(orderNo, itemNo, 0);
    }


    // 三段拼接，为空或0则不继续拼
    public String getFullNo() {
        if (splitNo != null && splitNo != 0) {
            return String.join("-", orderNo, itemNo.toString(), splitNo.toString());
        } else if (itemNo != null && itemNo != 0) {
            return String.join("-", orderNo, itemNo.toString());
        }
        return orderNo;
    }

    public Integer getSplitNoNotNull() {
        return Optional.ofNullable(splitNo).orElse(0);
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderNoInfo)) return false;
        OrderNoInfo that = (OrderNoInfo) o;
        return Objects.equals(getOrderNo(), that.getOrderNo()) && Objects.equals(getItemNo(), that.getItemNo()) && Objects.equals(getSplitNo(), that.getSplitNo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOrderNo(), getItemNo(), getSplitNo());
    }


    @Override
    public String toString() {
        return getFullNo();
    }
}
