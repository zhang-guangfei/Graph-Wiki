package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
@DS("opsdb")
public interface OpsOrderDelayMapper {


    @Select("exec dbo.cal_delay_order")
    void calDelayOrder();

}
