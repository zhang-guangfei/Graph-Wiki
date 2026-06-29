package com.smc.smccloud.mapper.returnorder;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.returnorder.ReturnOrderDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author smc
 * @since 2021-11-06
 */
@Mapper
@DS("sharedb")
public interface ReturnOrderMapper extends BaseMapper<ReturnOrderDO> {

}
