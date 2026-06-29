package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.smc.smccloud.model.login.Resource;
import com.smc.smccloud.model.login.ResourceDO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
@DS("opscmm")
public interface ResourceMapper extends BaseMapper<ResourceDO> {

    @Delete("DELETE FROM sales_sys_resource WHERE CODE LIKE CONCAT(#{code},'%')")
    void deleteByCode(@Param("code") String code);



}
