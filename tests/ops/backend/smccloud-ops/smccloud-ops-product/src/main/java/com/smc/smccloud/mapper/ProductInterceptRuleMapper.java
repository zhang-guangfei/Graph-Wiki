package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.product.ProductInterceptRuleDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author edp04
 * @title: ProductInterceptRuleMapper
 * @date 2022/05/20 14:54
 */
@Mapper
@DS("opsdb")
public interface ProductInterceptRuleMapper extends BaseMapper<ProductInterceptRuleDO> {
}
