package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.Customer.GroupCompanyDO;
import org.apache.ibatis.annotations.Mapper;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author smc
 * @since 2022-04-14
 */
@Mapper
@DS("opsdb")
public interface GroupCompanyMapper extends BaseMapper<GroupCompanyDO> {

}
