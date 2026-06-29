package com.sales.ops.db.dao;

import com.sales.ops.db.entity.Maxcode;
import com.sales.ops.db.entity.MaxcodeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MaxcodeMapper {
    long countByExample(MaxcodeExample example);

    int deleteByExample(MaxcodeExample example);

    int deleteByPrimaryKey(String opttype);

    int insert(Maxcode record);

    int insertSelective(Maxcode record);

    List<Maxcode> selectByExample(MaxcodeExample example);

    Maxcode selectByPrimaryKey(String opttype);

    int updateByExampleSelective(@Param("record") Maxcode record, @Param("example") MaxcodeExample example);

    int updateByExample(@Param("record") Maxcode record, @Param("example") MaxcodeExample example);

    int updateByPrimaryKeySelective(Maxcode record);

    int updateByPrimaryKey(Maxcode record);
}