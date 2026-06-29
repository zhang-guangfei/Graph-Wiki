package com.sales.ops.db.dao;

import com.sales.ops.db.entity.ShikomiModel;
import com.sales.ops.db.entity.ShikomiModelExample;
import com.sales.ops.db.entity.ShikomiModelKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ShikomiModelMapper {
    long countByExample(ShikomiModelExample example);

    int deleteByExample(ShikomiModelExample example);

    int deleteByPrimaryKey(ShikomiModelKey key);

    int insert(ShikomiModel record);

    int insertSelective(ShikomiModel record);

    List<ShikomiModel> selectByExample(ShikomiModelExample example);

    ShikomiModel selectByPrimaryKey(ShikomiModelKey key);

    int updateByExampleSelective(@Param("record") ShikomiModel record, @Param("example") ShikomiModelExample example);

    int updateByExample(@Param("record") ShikomiModel record, @Param("example") ShikomiModelExample example);

    int updateByPrimaryKeySelective(ShikomiModel record);

    int updateByPrimaryKey(ShikomiModel record);
}