package com.sales.ops.db.dao;

import com.sales.ops.db.entity.RcvdetailAss;
import com.sales.ops.db.entity.RcvdetailAssExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RcvdetailAssMapper {
    long countByExample(RcvdetailAssExample example);

    int deleteByExample(RcvdetailAssExample example);

    int deleteByPrimaryKey(Long id);

    int insert(RcvdetailAss record);

    int insertSelective(RcvdetailAss record);

    List<RcvdetailAss> selectByExample(RcvdetailAssExample example);

    RcvdetailAss selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") RcvdetailAss record, @Param("example") RcvdetailAssExample example);

    int updateByExample(@Param("record") RcvdetailAss record, @Param("example") RcvdetailAssExample example);

    int updateByPrimaryKeySelective(RcvdetailAss record);

    int updateByPrimaryKey(RcvdetailAss record);
}