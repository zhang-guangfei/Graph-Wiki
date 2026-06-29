package com.sales.ops.db.dao;

import com.sales.ops.db.entity.ImpdataDb30;
import com.sales.ops.db.entity.ImpdataDb30Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ImpdataDb30Mapper {
    long countByExample(ImpdataDb30Example example);

    int deleteByExample(ImpdataDb30Example example);

    int insert(ImpdataDb30 record);

    int insertSelective(ImpdataDb30 record);

    List<ImpdataDb30> selectByExample(ImpdataDb30Example example);

    int updateByExampleSelective(@Param("record") ImpdataDb30 record, @Param("example") ImpdataDb30Example example);

    int updateByExample(@Param("record") ImpdataDb30 record, @Param("example") ImpdataDb30Example example);
}