package com.smc.smccloud.model.product;

import com.smc.smccloud.core.model.page.BaseQuery;
import lombok.Data;

import java.util.List;

/**
 * @author wuweidong
 * @create 2024/1/12 08:47
 * @description
 */
@Data
public class ProductInterceptRuleRequest extends BaseQuery {
   private   String name;
   private Integer status;
   private String modelNo;
   private List<String> modelNos;
   private  Integer applyType;
}
