package com.smc.smccloud.mapper.prestock;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.prestock.PreorderAccountApplyDetailDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author smc
 * @since 2024-08-02
 */
@DS("sharedb")
@Mapper
public interface PreorderAccountApplyDetailMapper extends BaseMapper<PreorderAccountApplyDetailDO> {

}
