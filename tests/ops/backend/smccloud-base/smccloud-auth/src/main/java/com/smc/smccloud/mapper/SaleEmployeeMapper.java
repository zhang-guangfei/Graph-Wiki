package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.login.SaleEmployee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@DS("opscmm")
@Mapper
public interface SaleEmployeeMapper extends BaseMapper<SaleEmployee>
{
       @Select("select * from hr_employee where id = #{username}")
       SaleEmployee selectByUserName(@Param("username") String username);

       @Select("<script>" +
               "select ID , NAME from hr_employee " +
               " <where>\n" +
               " STATUS =1\n" +
               " <if test=\"id != null and id != ''\"> " +
               " AND ID = #{id}\n" +
               " </if>\n" +
               " <if test=\"name != null and name != ''\"> " +
               " AND NAME = #{name} " +
               " </if> " +
               " </where> " +
               "</script>")
       List<SaleEmployee> getShortUsers(@Param("id") String id, @Param("name") String name);

}
