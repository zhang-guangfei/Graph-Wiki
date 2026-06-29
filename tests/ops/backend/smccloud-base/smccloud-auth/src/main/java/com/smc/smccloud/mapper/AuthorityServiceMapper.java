package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.authority.Authority;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;

@Mapper
@DS("opscmm")
public interface AuthorityServiceMapper extends BaseMapper<Authority> {

    @Select("SELECT DISTINCT A.* FROM sales_sys_user_role UR,sales_sys_role R,sales_sys_role_AUTHORITY RA,sales_sys_authority A " +
            " WHERE UR.ROLE_ID = R.ID AND R.ID=RA.ROLE_ID " +
            "  AND RA.AUTHORITY_ID = A.ID " +
            " AND A.TYPE='resource'" +
            " AND UR.USER_ID = #{userId}")
    List<Authority> getByUserId(@Param("userId") String userId);

    /**
     * 根据组名称获取用户对应的权限
     * @return
     */
    /*@Select("<script>" +
            "SELECT DISTINCT A.* " +
            " FROM sales_sys_group_role SGR, sales_sys_role R,sales_sys_role_AUTHORITY RA,sales_sys_authority A " +
            " WHERE SGR.ROLE_ID = R.ID AND R.ID=RA.ROLE_ID " +
            "  AND RA.AUTHORITY_ID = A.ID" +
            "  AND A.TYPE='resource' AND" +
            "SGR.GROUP_ID in" +
            "<foreach item='item' index='index' collection='groupIds' open='(' separator=',' close=')'>" +
            "#{item}" +
            "</foreach>" +
            "</script>"
           )*/
    @Select("<script>" +
            "SELECT DISTINCT A.* " +
            " FROM sales_sys_group_role SGR, sales_sys_role R,sales_sys_role_AUTHORITY RA,sales_sys_authority A \n" +
            " WHERE SGR.ROLE_ID = R.ID AND R.ID=RA.ROLE_ID " +
            "  AND RA.AUTHORITY_ID = A.ID " +
            "  AND A.TYPE='resource' AND " +
            "  <trim prefix='(' prefixOverrides='AND|OR' suffix=')' > " +
            " <foreach collection='groupIds' item='item' > " +
            " OR SGR.GROUP_ID = #{item} " +
            " </foreach> " +
            " </trim>" +
            "</script>")
    List<Authority> getByGroupIds(@Param("groupIds") List<String> groupIds);


    @Select("SELECT ID, NAME, TYPE, CODE, STATUS, PID, CREATETIME FROM sales_sys_role_AUTHORITY SRA, sales_sys_authority SA WHERE SRA.AUTHORITY_ID = SA.ID AND SRA.ROLE_ID = #{roleId} AND SA.TYPE!='common' ORDER BY SA.CODE ASC")
     Set<Authority> getByRoleId(@Param("roleId") String roleId);

}
