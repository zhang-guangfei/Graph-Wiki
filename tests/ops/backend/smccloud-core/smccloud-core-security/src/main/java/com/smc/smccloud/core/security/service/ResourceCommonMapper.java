package com.smc.smccloud.core.security.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Mapper
@DS("opscmm")
public interface ResourceCommonMapper extends BaseMapper<Resource> {

    /**
     * 通过用戶ID获取用户绑定的资源
     * @return
     */
    @Select("SELECT DISTINCT RE.* FROM sales_sys_user_role UR,sales_sys_role R,sales_sys_role_AUTHORITY RA,sales_sys_authority A,sales_sys_authority_RESOURCE AR,sales_sys_resource RE" +
            " WHERE UR.ROLE_ID = R.ID " +
            " AND R.ID=RA.ROLE_ID " +
            " AND RA.AUTHORITY_ID = A.ID " +
            " AND A.ID = AR.AUTHORITY_ID " +
            " AND AR.RESOURCE_ID = RE.ID " +
            " AND UR.USER_ID = #{userId}")
    public Set<Resource> getByUserId(@Param("userId") String userId);

    /**
     * 获取角色与资源的对应关系
     * @return
     */
    @Select("SELECT DISTINCT R.NAME," +
            "RS.PATTERN FROM sales_sys_role R," +
            "sales_sys_role_AUTHORITY SRA," +
            "sales_sys_authority_RESOURCE SAR," +
            "sales_sys_resource RS " +
            "WHERE R.ID = SRA.ROLE_ID " +
            "AND SRA.AUTHORITY_ID = SAR.AUTHORITY_ID " +
            "AND SAR.RESOURCE_ID = RS.ID")
    public List<Map<String,String>> getRoleAndResource();

}
