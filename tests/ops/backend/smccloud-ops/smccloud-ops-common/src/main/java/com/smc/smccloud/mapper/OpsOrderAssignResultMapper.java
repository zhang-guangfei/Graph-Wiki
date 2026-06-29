package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.OpsOrderAssignResultDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author smc
 * @since 2023-06-25
 */
@Mapper
@DS("opsdb")
public interface OpsOrderAssignResultMapper extends BaseMapper<OpsOrderAssignResultDO> {

    @Select("select * from ops_order_assign_result where order_no =#{orderNo} and order_item=#{itemNo} and delflag=0")
    List<OpsOrderAssignResultDO> findOrderAssignResultList(@Param("orderNo") String orderNo, @Param("itemNo") Integer itemNo);
}
