package com.smc.smccloud.mapper.prestock;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.prestock.PreorderAccountRecordsDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author smc
 * @since 2024-12-06
 */
@DS("sharedb")
@Mapper
public interface PreorderAccountRecordsMapper extends BaseMapper<PreorderAccountRecordsDO> {

}
