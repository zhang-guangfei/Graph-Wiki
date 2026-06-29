package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.OpsInventoryWithHitrateDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 库存表 Mapper 接口
 * </p>
 *
 * @author smc
 * @since 2024-12-25
 */
@Mapper
@DS("reportdb")
public interface OpsInventoryWithHitrateMapper extends BaseMapper<OpsInventoryWithHitrateDO> {

}
