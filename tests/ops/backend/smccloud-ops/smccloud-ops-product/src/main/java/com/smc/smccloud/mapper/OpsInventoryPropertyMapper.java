package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.inventory.OpsInventoryPropertyDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 在库属性表 Mapper 接口
 * </p>
 *
 * @author smc
 * @since 2021-12-03
 */
@Mapper
@DS("opsdb")
public interface OpsInventoryPropertyMapper extends BaseMapper<OpsInventoryPropertyDO> {
}
