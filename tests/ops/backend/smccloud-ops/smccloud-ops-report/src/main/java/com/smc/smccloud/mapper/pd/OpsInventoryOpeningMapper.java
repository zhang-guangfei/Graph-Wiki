package com.smc.smccloud.mapper.pd;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.pd.OpsInventoryOpeningDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 库存表 Mapper 接口
 * </p>
 *
 * @author smc
 * @since 2024-09-03
 */
@Mapper
@DS("reportdb")
public interface OpsInventoryOpeningMapper extends BaseMapper<OpsInventoryOpeningDO> {

}
