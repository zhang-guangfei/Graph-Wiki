package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.login.UserRole;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@DS("opscmm")
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {
    @Delete("delete from sales_sys_user_role where USER_ID = #{userId} ")
     void deleteByUserId(@Param("userId") String userId);
}
