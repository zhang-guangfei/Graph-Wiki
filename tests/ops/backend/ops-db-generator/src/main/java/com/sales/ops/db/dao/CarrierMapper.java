package com.sales.ops.db.dao;

import com.sales.ops.db.entity.Carrier;
import com.sales.ops.db.entity.CarrierExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CarrierMapper {
    long countByExample(CarrierExample example);

    int deleteByExample(CarrierExample example);

    int deleteByPrimaryKey(String carrierid);

    int insert(Carrier record);

    int insertSelective(Carrier record);

    List<Carrier> selectByExample(CarrierExample example);

    Carrier selectByPrimaryKey(String carrierid);

    int updateByExampleSelective(@Param("record") Carrier record, @Param("example") CarrierExample example);

    int updateByExample(@Param("record") Carrier record, @Param("example") CarrierExample example);

    int updateByPrimaryKeySelective(Carrier record);

    int updateByPrimaryKey(Carrier record);
}