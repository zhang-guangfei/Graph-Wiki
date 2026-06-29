package com.smc.smccloud.mapper;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.OrganizationRelationDO;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author smc
 * @since 2022-03-04
 */
@Mapper
@DS("opsdb")
public interface OrganizationRelationMapper extends BaseMapper<OrganizationRelationDO> {


}
