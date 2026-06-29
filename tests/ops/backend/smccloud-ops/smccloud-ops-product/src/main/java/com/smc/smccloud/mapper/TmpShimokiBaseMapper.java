package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.shikomi.TmpShikomiBaseDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author smc
 * @since 2024-03-28
 */
@DS("sharedb")
@Mapper
public interface TmpShimokiBaseMapper extends BaseMapper<TmpShikomiBaseDO> {

    @Select(" truncate table tmp_shikomi_base ")
    void truncateTable();
}
