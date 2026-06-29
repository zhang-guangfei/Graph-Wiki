package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.authority.DataRole;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
@DS("opscmm")
public interface DataRoleMapper extends BaseMapper<DataRole> {

    @Delete("DELETE FROM sales_sys_data_role WHERE ROLE_ID = #{roleId}")
     void deleteByRoleId(@Param("roleId") String roleId);
}
