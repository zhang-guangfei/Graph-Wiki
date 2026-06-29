package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OverseaModelPrice;
import com.sales.ops.db.entity.OverseaModelPriceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OverseaModelPriceMapper {
    long countByExample(OverseaModelPriceExample example);

    int deleteByExample(OverseaModelPriceExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OverseaModelPrice record);

    int insertSelective(OverseaModelPrice record);

    List<OverseaModelPrice> selectByExample(OverseaModelPriceExample example);

    OverseaModelPrice selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OverseaModelPrice record, @Param("example") OverseaModelPriceExample example);

    int updateByExample(@Param("record") OverseaModelPrice record, @Param("example") OverseaModelPriceExample example);

    int updateByPrimaryKeySelective(OverseaModelPrice record);

    int updateByPrimaryKey(OverseaModelPrice record);
}