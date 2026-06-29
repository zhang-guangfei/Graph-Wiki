package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.InventoryRelationOrderViewDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author smc
 * @since 2024-11-19
 */
@Mapper
@DS("opsdb")
public interface InventoryRelationOrderViewMapper extends BaseMapper<InventoryRelationOrderViewDO> {

}
