package com.sales.ops.db.dao;

import com.sales.ops.db.entity.VGOrdersalesCn;
import com.sales.ops.db.entity.VGOrdersalesCnExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VGOrdersalesCnMapper {
    long countByExample(VGOrdersalesCnExample example);

    int deleteByExample(VGOrdersalesCnExample example);

    int insert(VGOrdersalesCn record);

    int insertSelective(VGOrdersalesCn record);

    List<VGOrdersalesCn> selectByExample(VGOrdersalesCnExample example);

    int updateByExampleSelective(@Param("record") VGOrdersalesCn record, @Param("example") VGOrdersalesCnExample example);

    int updateByExample(@Param("record") VGOrdersalesCn record, @Param("example") VGOrdersalesCnExample example);
}