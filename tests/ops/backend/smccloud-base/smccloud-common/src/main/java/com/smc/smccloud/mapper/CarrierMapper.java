package com.smc.smccloud.mapper;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.CarrierDO;
import org.apache.ibatis.annotations.Mapper;


/**
 * @Author lyc
 * @Date 2022/4/7 9:05
 * @Descripton TODO
 */
@Mapper
@DS("opsdb")
public interface CarrierMapper extends BaseMapper<CarrierDO> {


}
