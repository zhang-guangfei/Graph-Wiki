package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.authority.RoleAuthority;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@DS("opscmm")
@Mapper
public interface RoleAuthorityMapper extends BaseMapper<RoleAuthority>
{
    @Delete("delete from sales_sys_role_authority where ROLE_ID = #{roleId} ")
    void deleteByRoleId(@Param("roleId") String roleId);
}
