package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.DataTypeDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


@DS("opscmm")
@Mapper
public interface DictDataMapper extends BaseMapper<DataTypeDO> {

    @Select("select * from ops_ui.dbo.tbl_datatype where class_code = #{classCode} and code = #{code}")
    DataTypeDO selDictInfoByClassCodeAndCode(@Param("classCode") String classCode,@Param("code") String code);
}
