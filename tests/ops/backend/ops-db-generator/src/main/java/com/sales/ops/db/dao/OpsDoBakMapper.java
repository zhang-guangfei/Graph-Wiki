package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsDoBak;
import com.sales.ops.db.entity.OpsDoBakExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsDoBakMapper {
    long countByExample(OpsDoBakExample example);

    int deleteByExample(OpsDoBakExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpsDoBak record);

    int insertSelective(OpsDoBak record);

    List<OpsDoBak> selectByExample(OpsDoBakExample example);

    OpsDoBak selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpsDoBak record, @Param("example") OpsDoBakExample example);

    int updateByExample(@Param("record") OpsDoBak record, @Param("example") OpsDoBakExample example);

    int updateByPrimaryKeySelective(OpsDoBak record);

    int updateByPrimaryKey(OpsDoBak record);
}