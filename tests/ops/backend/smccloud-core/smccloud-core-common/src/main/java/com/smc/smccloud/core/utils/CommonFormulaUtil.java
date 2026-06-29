package com.smc.smccloud.core.utils;

import java.math.BigDecimal;

/**
 * Author: B90034
 * Date: 2022-06-28 09:06
 * Description: 常用计算公式工具类
 */
public class CommonFormulaUtil {

    /**
     * 计算E率 = (不含税价格/E价格) -1
     *
     * @param noTaxPrice 不含税价格
     * @param EPrice     E价格
     * @return E率
     */
    public static BigDecimal calcDiscount(BigDecimal noTaxPrice, BigDecimal EPrice) {

        if (noTaxPrice == null || noTaxPrice.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }

        if (EPrice == null || EPrice.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        } else {
            return noTaxPrice.divide(EPrice, 3, BigDecimal.ROUND_HALF_UP).subtract(BigDecimal.ONE);
        }
    }

    /**
     * 保留x位小数
     * @param noTaxPrice
     * @param EPrice
     * @param decCount  保留decCount位小数
     * @return
     */
    public static BigDecimal calcDiscountForcustomCount(BigDecimal noTaxPrice, BigDecimal EPrice,int decCount) {

        if (noTaxPrice == null || noTaxPrice.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }

        if (EPrice == null || EPrice.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        } else {
            return noTaxPrice.divide(EPrice, decCount, BigDecimal.ROUND_HALF_UP).subtract(BigDecimal.ONE);
        }
    }

    /**
     * 计算不含税价格 = 含税价格/(1+税率)
     *
     * @param price 含税价格
     * @param tax   税率
     * @return 不含税价格
     */
    public static BigDecimal calcNoTaxPrice(BigDecimal price, BigDecimal tax) {

        if (price == null || price.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }

        if (tax == null || tax.compareTo(BigDecimal.ZERO) == 0) {
            tax = new BigDecimal("0.13");
        }
        return price.divide(tax.add(BigDecimal.ONE), 3, BigDecimal.ROUND_HALF_UP);
    }

    public static void main(String[] args) {
//        BigDecimal bigDecimal = calcDiscount(new BigDecimal("230.09"), new BigDecimal("0.13"));
//        System.out.println("bigDecimal = " + bigDecimal);

        BigDecimal noTaxPrice = calcNoTaxPrice(BigDecimal.ZERO, new BigDecimal("0.13"));
        System.out.println("noTaxPrice = " + noTaxPrice);

        BigDecimal bigDecimal = calcDiscount(noTaxPrice, new BigDecimal("10.00"));
        System.out.println("bigDecimal = " + bigDecimal);

    }
}
