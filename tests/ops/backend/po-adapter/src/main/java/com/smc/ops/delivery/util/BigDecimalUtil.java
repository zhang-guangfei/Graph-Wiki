package com.smc.ops.delivery.util;

import java.math.BigDecimal;

public class BigDecimalUtil {


    private final static String ONE_HUNDRED = "100";
    private final static String ONE_THOUSAND = "1000";

    /**
     * 加法
     *
     * @param obj1
     * @param obj2
     * @return
     */
    public static BigDecimal add(Object obj1, Object obj2) {
        String v1 = String.valueOf(obj1);
        String v2 = String.valueOf(obj2);
        if (v1.equals("null") && v2.equals("null")) {
            return BigDecimal.ZERO;
        }
        if (v1.equals("null") && !v2.equals("null")) {
            return new BigDecimal(v2);
        }
        if (!v1.equals("null") && v2.equals("null")) {
            return new BigDecimal(v1);
        }
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.add(b2);
    }

    /**
     * 减法
     *
     * @param obj1
     * @param obj2
     * @return
     */
    public static BigDecimal sub(Object obj1, Object obj2) {
        String v1 = String.valueOf(obj1);
        String v2 = String.valueOf(obj2);
        if (v1.equals("null") && v2.equals("null")) {
            return BigDecimal.ZERO;
        }
        if (v1.equals("null") && !v2.equals("null")) {
            return new BigDecimal(v2).negate();
        }
        if (!v1.equals("null") && v2.equals("null")) {
            return new BigDecimal(v1);
        }
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.subtract(b2);
    }

    /**
     * 乘法 使用String.valueOf(Object obj)转换为String, 判null条件改为：if
     * (!objStr.equals("null"))
     *
     * @param obj1
     * @param obj2
     * @return
     */
    public static BigDecimal mul(Object obj1, Object obj2) {
        String v1 = String.valueOf(obj1);
        String v2 = String.valueOf(obj2);
        if (v1.equals("null") || v2.equals("null")) {
            return BigDecimal.ZERO;
        }
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.multiply(b2);
    }

    /**
     * 除法 使用String.valueOf(Object obj)转换为String, 判null条件改为：if
     * (!objStr.equals("null"))
     *
     * @param obj1 被除数
     * @param obj2 除数
     * @return
     */
    public static BigDecimal div(Object obj1, Object obj2, int scale) {
        String v1 = String.valueOf(obj1);
        String v2 = String.valueOf(obj2);
        if (v1.equals("null") || v2.equals("null")) {
            return BigDecimal.ZERO;
        }
        BigDecimal b2 = new BigDecimal(v2);
        if (b2.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        BigDecimal b1 = new BigDecimal(v1);

        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 乘以100
     *
     * @param obj1
     * @return
     */
    public static BigDecimal multiplyByOneHundred(Object obj1) {
        return mul(obj1, ONE_HUNDRED);
    }

    /**
     * 乘以1000
     *
     * @param obj1
     * @return
     */
    public static BigDecimal multiplyByOneThousand(Object obj1) {
        return mul(obj1, ONE_THOUSAND);
    }

    /**
     * 除以100
     *
     * @param obj1
     * @return
     */
    public static BigDecimal divideByOneHundred(Object obj1) {
        return div(obj1, ONE_HUNDRED, BigDecimal.ROUND_UNNECESSARY);
    }

    /**
     * 除以1000
     *
     * @param obj1
     * @return
     */
    public static BigDecimal divideByOneThousand(Object obj1) {
        return div(obj1, ONE_THOUSAND, BigDecimal.ROUND_UNNECESSARY);
    }
}
