package com.sales.ops.db.dao;

import com.sales.ops.db.entity.YyLongmodelexchange;
import com.sales.ops.db.entity.YyLongmodelexchangeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface YyLongmodelexchangeMapper {
    long countByExample(YyLongmodelexchangeExample example);

    int deleteByExample(YyLongmodelexchangeExample example);

    int insert(YyLongmodelexchange record);

    int insertSelective(YyLongmodelexchange record);

    List<YyLongmodelexchange> selectByExample(YyLongmodelexchangeExample example);

    int updateByExampleSelective(@Param("record") YyLongmodelexchange record, @Param("example") YyLongmodelexchangeExample example);

    int updateByExample(@Param("record") YyLongmodelexchange record, @Param("example") YyLongmodelexchangeExample example);
}