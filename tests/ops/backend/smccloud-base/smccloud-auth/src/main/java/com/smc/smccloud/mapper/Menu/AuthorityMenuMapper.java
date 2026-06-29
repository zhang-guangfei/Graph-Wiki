package com.smc.smccloud.mapper.Menu;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.authority.AuthorityMenu;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@DS("opscmm")
public interface AuthorityMenuMapper extends BaseMapper<AuthorityMenu> {
}
