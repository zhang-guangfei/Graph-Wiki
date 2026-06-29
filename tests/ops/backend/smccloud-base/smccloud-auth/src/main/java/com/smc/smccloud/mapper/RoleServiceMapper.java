package com.smc.smccloud.mapper;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.login.Role;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
@DS("opscmm")
public interface RoleServiceMapper extends BaseMapper<Role>
{
    @Select("SELECT R.* FROM sales_sys_user_role UR, sales_sys_role R WHERE R.ID = UR.ROLE_ID AND USER_ID = #{userId}")
    List<Role> getByUserId(@Param("userId") String userId);

    @Select("SELECT * FROM sales_sys_role WHERE 1 = 1 ORDER BY CREATENAME DESC")
    List<Role> findAll();

    @Delete("delete from sales_sys_role where ID = #{id}")
    void deleteByRoleId(@Param("id") String id);
}
