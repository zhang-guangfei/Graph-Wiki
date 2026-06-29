package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.shikomi.ShikomiBudgetForSalesDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author smc
 * @since 2024-08-19
 */
@Mapper
@DS("opsdbforsms")
public interface ShikomiBudgetForSalesMapper extends BaseMapper<ShikomiBudgetForSalesDO> {

}
