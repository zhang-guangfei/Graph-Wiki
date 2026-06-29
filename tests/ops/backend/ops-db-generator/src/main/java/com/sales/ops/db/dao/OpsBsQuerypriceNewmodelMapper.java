package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsBsQuerypriceNewmodel;
import com.sales.ops.db.entity.OpsBsQuerypriceNewmodelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsBsQuerypriceNewmodelMapper {
    long countByExample(OpsBsQuerypriceNewmodelExample example);

    int deleteByExample(OpsBsQuerypriceNewmodelExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OpsBsQuerypriceNewmodel record);

    int insertSelective(OpsBsQuerypriceNewmodel record);

    List<OpsBsQuerypriceNewmodel> selectByExample(OpsBsQuerypriceNewmodelExample example);

    OpsBsQuerypriceNewmodel selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OpsBsQuerypriceNewmodel record, @Param("example") OpsBsQuerypriceNewmodelExample example);

    int updateByExample(@Param("record") OpsBsQuerypriceNewmodel record, @Param("example") OpsBsQuerypriceNewmodelExample example);

    int updateByPrimaryKeySelective(OpsBsQuerypriceNewmodel record);

    int updateByPrimaryKey(OpsBsQuerypriceNewmodel record);
}