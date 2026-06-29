package com.smc.smccloud.util;

import java.math.BigDecimal;

/**
 *  ntax_price=price/(1+tax_rate) 四舍五入保留2位
 *   ntax_amount=ntax_price*quantity
 *   tax_amount=ntax_price*tax_rate*quantity  四舍五入保留2位
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
     * @param ntaxPice 不含税单价
     * @param quantity 数量
     * @return
     */
   public static BigDecimal unitAmountExcludingTax(BigDecimal ntaxPice, Integer quantity){
      return ntaxPice.multiply(new BigDecimal(quantity));
   }

    /**
     * 计算税额(taxAmount)
     * @param ntaxPice 不含税单价
     * @param taxRate 税率
     * @param quantity 数量
     * @return
     */
   public static BigDecimal taxAmount(BigDecimal ntaxPice, BigDecimal taxRate, Integer quantity){
       BigDecimal multiply = ntaxPice.multiply(taxRate).multiply(new BigDecimal(quantity));
       // 保留两位小数 四舍五入
       return multiply.divide(new BigDecimal(1),2,BigDecimal.ROUND_HALF_UP);
   }

}
