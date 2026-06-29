package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.expdetail.ExpdetailDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author lyc
 * @Date 2022/8/27 10:21
 * @Descripton TODO
 */
@DS("opsreaddb")
@Mapper
public interface ExpdetailMapperReadOnlyMapper extends BaseMapper<ExpdetailDO> {
}
