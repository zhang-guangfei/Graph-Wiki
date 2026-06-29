package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.inventory.GPInventoryDO;
import org.apache.ibatis.annotations.Mapper;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author smc
 * @since 2023-03-17
 */
@Mapper
@DS("gzfddb")
public interface GPInventoryMapper extends BaseMapper<GPInventoryDO> {

}
