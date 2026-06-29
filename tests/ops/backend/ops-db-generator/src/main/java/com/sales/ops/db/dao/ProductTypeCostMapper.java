package com.sales.ops.db.dao;

import com.sales.ops.db.entity.ProductTypeCost;
import com.sales.ops.db.entity.ProductTypeCostExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductTypeCostMapper {
    long countByExample(ProductTypeCostExample example);

    int deleteByExample(ProductTypeCostExample example);

    int insert(ProductTypeCost record);

    int insertSelective(ProductTypeCost record);

    List<ProductTypeCost> selectByExampleWithBLOBs(ProductTypeCostExample example);

    List<ProductTypeCost> selectByExample(ProductTypeCostExample example);

    int updateByExampleSelective(@Param("record") ProductTypeCost record, @Param("example") ProductTypeCostExample example);

    int updateByExampleWithBLOBs(@Param("record") ProductTypeCost record, @Param("example") ProductTypeCostExample example);

    int updateByExample(@Param("record") ProductTypeCost record, @Param("example") ProductTypeCostExample example);
}