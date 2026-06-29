package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.product.ProductPhysicsDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author edp04
 * @title: ProductPhysicsMapper
 * @date 2022/10/13 08:51
 */
@Mapper
@DS("opsdb")
public interface ProductPhysicsMapper extends BaseMapper<ProductPhysicsDO> {
}
