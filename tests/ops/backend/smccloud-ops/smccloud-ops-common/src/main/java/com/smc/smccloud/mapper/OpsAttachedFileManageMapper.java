package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.OpsAttachedFileManageDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author smc
 * @since 2023-10-10
 */
@Mapper
@DS("opsdb")
public interface OpsAttachedFileManageMapper extends BaseMapper<OpsAttachedFileManageDO> {

}
