package com.sales.ops.db.dao;

import com.sales.ops.db.entity.Temprequisition;
import com.sales.ops.db.entity.TemprequisitionExample;
import com.sales.ops.db.entity.TemprequisitionKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TemprequisitionMapper {
    long countByExample(TemprequisitionExample example);

    int deleteByExample(TemprequisitionExample example);

    int deleteByPrimaryKey(TemprequisitionKey key);

    int insert(Temprequisition record);

    int insertSelective(Temprequisition record);

    List<Temprequisition> selectByExample(TemprequisitionExample example);

    Temprequisition selectByPrimaryKey(TemprequisitionKey key);

    int updateByExampleSelective(@Param("record") Temprequisition record, @Param("example") TemprequisitionExample example);

    int updateByExample(@Param("record") Temprequisition record, @Param("example") TemprequisitionExample example);

    int updateByPrimaryKeySelective(Temprequisition record);

    int updateByPrimaryKey(Temprequisition record);
}