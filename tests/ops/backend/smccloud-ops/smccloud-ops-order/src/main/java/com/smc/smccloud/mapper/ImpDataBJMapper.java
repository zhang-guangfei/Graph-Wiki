package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.invoice.ImpDataBJDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author edp02 @Date 2022/4/9 11:16
 */
@Mapper
@DS("gzdb")
public interface ImpDataBJMapper extends BaseMapper<ImpDataBJDO> {

}
