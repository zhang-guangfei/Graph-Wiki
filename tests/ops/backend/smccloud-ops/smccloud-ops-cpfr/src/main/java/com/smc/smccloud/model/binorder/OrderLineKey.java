package com.smc.smccloud.model.binorder;

/**
 * Task 7B：
 * 采购订单行 key：orderNo + itemNo + splitNo，splitNo 为空时按 0 处理，保持原匹配语义。
 */
public final class OrderLineKey {

    private final String orderNo;
    private final Integer itemNo;
    private final Integer splitNo;

    public OrderLineKey(String orderNo, Integer itemNo, Integer splitNo) {
        this.orderNo = orderNo;
        this.itemNo = itemNo;
        this.splitNo = splitNo == null ? 0 : splitNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderLineKey)) {
            return false;
        }
        OrderLineKey that = (OrderLineKey) o;
        return equals(orderNo, that.orderNo)
                && equals(itemNo, that.itemNo)
                && equals(splitNo, that.splitNo);
    }

    @Override
    public int hashCode() {
        int result = orderNo == null ? 0 : orderNo.hashCode();
        result = 31 * result + (itemNo == null ? 0 : itemNo.hashCode());
        result = 31 * result + (splitNo == null ? 0 : splitNo.hashCode());
        return result;
    }

    private static boolean equals(Object left, Object right) {
        return left == null ? right == null : left.equals(right);
    }
}
