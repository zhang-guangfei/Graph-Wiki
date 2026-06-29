package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.OpsInventoryDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 库存表 Mapper 接口
 * </p>
 *
 * @author smc
 * @since 2025-06-30
 */
@DS("ops_core_custom_readonly")
@Mapper
public interface OpsInventoryMapper extends BaseMapper<OpsInventoryDO> {


    /**
     * 查询库存最后更新时间
     * @param upTime
     * @return
     */
    @Select("SELECT top(1) modify_time "+
            "  FROM [ops_inventory] where modify_time > DATEADD(MINUTE, -2, #{upTime}) and quantity - prepare_quantity > 0 order by modify_time desc")
    OpsInventoryDO selectInventoryLastUpTime(@Param("upTime") String upTime);

}
