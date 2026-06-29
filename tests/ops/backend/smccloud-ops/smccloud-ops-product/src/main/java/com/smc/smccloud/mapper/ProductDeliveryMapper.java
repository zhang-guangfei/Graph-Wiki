package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.product.ProductDeliveryDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * Author: B90034
 * Date: 2022-01-21 11:35
 * Description:
 */
@DS("opsdb")
@Mapper
public interface ProductDeliveryMapper extends BaseMapper<ProductDeliveryDO> {
}
