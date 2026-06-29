package com.smc.ops.delivery.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.sales.ops.db.entity.OpsOrderUpdateLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * 14562 C14717 20240704
 */
@Mapper
@DS("opsdb")
public interface OpsOrderUpdateLogMapper {

    @Select("SELECT orderid,order_item,result,params,create_time  FROM [dbo].[ops_order_update_log] where result like " +
            "CONCAT(#{name},'%') and create_time >= #{beginDate} and create_time <= #{endDate}")
    List<OpsOrderUpdateLog> selectOpsOrderUpdateLogList(@Param("name") String name, @Param("beginDate") String begingDate, @Param("endDate") String endDate);


}
