package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.PurchaseReplyPushJobDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author smc
 * @since 2025-03-12
 */
@Mapper
@DS("opsdb")
public interface PurchaseReplyPushJobMapper extends BaseMapper<PurchaseReplyPushJobDO> {

}
