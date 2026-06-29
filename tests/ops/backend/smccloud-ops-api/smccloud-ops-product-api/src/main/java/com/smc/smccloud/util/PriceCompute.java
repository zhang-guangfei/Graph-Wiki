package com.smc.smccloud.util;

import java.math.BigDecimal;

/**
 *  (废弃)
 *  ntax_price=price/(1+tax_rate) 四舍五入保留2位
 *   ntax_amount=ntax_price*quantity
 *   tax_amount=ntax_price*tax_rate*quantity  四舍五入保留2位
 *
 *
 *  amount=quantity*price 保留四位
 * ntax_amount=amount/ (1+tax_rate) 保留四位
 * tax_amount=amount-ntax_amount 保留四位
 * ntax_pice=price/1+tax_rate 保留两位
 */

public class PriceCompute {

    /**
     *  不含税单价（ntaxPrice） 保留两位小数 四舍五入
     * @param price 单价
     * @param taxRate 税率
     * @return
     */
    public static BigDecimal unitPriceExcludingTax(BigDecimal price, BigDecimal taxRate){
       return price.divide(new BigDecimal(1).add(taxRate),2,BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 计算不含税金额(ntaxAmount)
     * @param amount
     * @param taxRate
     * @return
     */
    public static BigDecimal ntaxAmount(BigDecimal amount, BigDecimal taxRate){
        return amount.divide(new BigDecimal(1).add(taxRate),4,BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 计算税额(taxAmount)
     * @param amount 金额
     * @param ntaxAmount 不含税金额
     * @return
     */
   public static BigDecimal taxAmount(BigDecimal amount, BigDecimal ntaxAmount){
       return amount.subtract(ntaxAmount);
   }


    public static void main(String[] args) {
        BigDecimal bigDecimal = unitPriceExcludingTax(new BigDecimal("6.9700"), new BigDecimal("0.13"));
        System.out.println("bigDecimal = " + bigDecimal);
    }



}
