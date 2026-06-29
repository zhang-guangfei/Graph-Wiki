package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.shikomi.ShikomiBudgetDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author smc
 * @since 2024-03-25
 */
@Mapper
@DS("sharedb")
public interface ShikomiBudgetMapper extends BaseMapper<ShikomiBudgetDO> {

}
