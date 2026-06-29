package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsOrderDelivery;
import com.sales.ops.db.entity.OpsOrderDeliveryExample;
import com.sales.ops.db.entity.OpsOrderDeliveryKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsOrderDeliveryMapper {
    long countByExample(OpsOrderDeliveryExample example);

    int deleteByExample(OpsOrderDeliveryExample example);

    int deleteByPrimaryKey(OpsOrderDeliveryKey key);

    int insert(OpsOrderDelivery record);

    int insertSelective(OpsOrderDelivery record);

    List<OpsOrderDelivery> selectByExample(OpsOrderDeliveryExample example);

    OpsOrderDelivery selectByPrimaryKey(OpsOrderDeliveryKey key);

    int updateByExampleSelective(@Param("record") OpsOrderDelivery record, @Param("example") OpsOrderDeliveryExample example);

    int updateByExample(@Param("record") OpsOrderDelivery record, @Param("example") OpsOrderDeliveryExample example);

    int updateByPrimaryKeySelective(OpsOrderDelivery record);

    int updateByPrimaryKey(OpsOrderDelivery record);
}