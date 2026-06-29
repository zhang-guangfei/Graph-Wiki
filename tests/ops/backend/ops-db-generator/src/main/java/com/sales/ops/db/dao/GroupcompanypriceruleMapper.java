package com.sales.ops.db.dao;

import com.sales.ops.db.entity.Groupcompanypricerule;
import com.sales.ops.db.entity.GroupcompanypriceruleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GroupcompanypriceruleMapper {
    long countByExample(GroupcompanypriceruleExample example);

    int deleteByExample(GroupcompanypriceruleExample example);

    int insert(Groupcompanypricerule record);

    int insertSelective(Groupcompanypricerule record);

    List<Groupcompanypricerule> selectByExample(GroupcompanypriceruleExample example);

    int updateByExampleSelective(@Param("record") Groupcompanypricerule record, @Param("example") GroupcompanypriceruleExample example);

    int updateByExample(@Param("record") Groupcompanypricerule record, @Param("example") GroupcompanypriceruleExample example);
}