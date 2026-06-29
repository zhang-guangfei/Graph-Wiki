package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.github.yulichang.base.MPJBaseMapper;
import com.smc.smccloud.model.RcvDetailDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


@DS("opsdb")
@Mapper
public interface RcvdetailMapper extends MPJBaseMapper<RcvDetailDO> {

    @Select("select * from ops_core.dbo.rcvdetail where rorder_fno = #{orderNoFno}")
    RcvDetailDO findRcvDetailInfo(@Param("orderNoFno") String orderNoFno);

}
