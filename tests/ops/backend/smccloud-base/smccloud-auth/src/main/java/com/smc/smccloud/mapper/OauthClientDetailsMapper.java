package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.OauthClient.OauthClientDetails;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
@DS("opscmm")
public interface OauthClientDetailsMapper extends BaseMapper<OauthClientDetails> {

    @Delete("delete from oauth_client_details where client_id = #{clientId} ")
    void delete(@Param("clientId") String clientId);
}
