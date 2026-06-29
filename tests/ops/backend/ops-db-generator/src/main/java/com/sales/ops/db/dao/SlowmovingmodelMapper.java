package com.sales.ops.db.dao;

import com.sales.ops.db.entity.Slowmovingmodel;
import com.sales.ops.db.entity.SlowmovingmodelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SlowmovingmodelMapper {
    long countByExample(SlowmovingmodelExample example);

    int deleteByExample(SlowmovingmodelExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Slowmovingmodel record);

    int insertSelective(Slowmovingmodel record);

    List<Slowmovingmodel> selectByExample(SlowmovingmodelExample example);

    Slowmovingmodel selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Slowmovingmodel record, @Param("example") SlowmovingmodelExample example);

    int updateByExample(@Param("record") Slowmovingmodel record, @Param("example") SlowmovingmodelExample example);

    int updateByPrimaryKeySelective(Slowmovingmodel record);

    int updateByPrimaryKey(Slowmovingmodel record);
}