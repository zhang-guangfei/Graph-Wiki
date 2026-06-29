package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.invoice.SalesImpDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author edp02 @Date 2022/7/12 9:58
 */
@DS("costdb")
@Mapper
public interface SalesImpMapper extends BaseMapper<SalesImpDO> {

}
