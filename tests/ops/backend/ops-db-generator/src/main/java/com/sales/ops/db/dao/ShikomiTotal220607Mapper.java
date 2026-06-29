package com.sales.ops.db.dao;

import com.sales.ops.db.entity.ShikomiTotal220607;
import com.sales.ops.db.entity.ShikomiTotal220607Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ShikomiTotal220607Mapper {
    long countByExample(ShikomiTotal220607Example example);

    int deleteByExample(ShikomiTotal220607Example example);

    int insert(ShikomiTotal220607 record);

    int insertSelective(ShikomiTotal220607 record);

    List<ShikomiTotal220607> selectByExampleWithBLOBs(ShikomiTotal220607Example example);

    List<ShikomiTotal220607> selectByExample(ShikomiTotal220607Example example);

    int updateByExampleSelective(@Param("record") ShikomiTotal220607 record, @Param("example") ShikomiTotal220607Example example);

    int updateByExampleWithBLOBs(@Param("record") ShikomiTotal220607 record, @Param("example") ShikomiTotal220607Example example);

    int updateByExample(@Param("record") ShikomiTotal220607 record, @Param("example") ShikomiTotal220607Example example);
}