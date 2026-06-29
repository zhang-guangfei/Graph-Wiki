package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.authority.AuthorityResource;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;


@Mapper
@DS("opscmm")
public interface AuthorityResourceMapper extends BaseMapper<AuthorityResource> {
    @Delete("DELETE FROM sales_sys_authority_RESOURCE WHERE AUTHORITY_ID = #{authorityId}")
    void deleteByAuthorityId(String authorityId);
}
