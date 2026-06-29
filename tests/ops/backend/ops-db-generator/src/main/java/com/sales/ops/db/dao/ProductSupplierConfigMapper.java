package com.sales.ops.db.dao;

import com.sales.ops.db.entity.ProductSupplierConfig;
import com.sales.ops.db.entity.ProductSupplierConfigExample;
import com.sales.ops.db.entity.ProductSupplierConfigKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductSupplierConfigMapper {
    long countByExample(ProductSupplierConfigExample example);

    int deleteByExample(ProductSupplierConfigExample example);

    int deleteByPrimaryKey(ProductSupplierConfigKey key);

    int insert(ProductSupplierConfig record);

    int insertSelective(ProductSupplierConfig record);

    List<ProductSupplierConfig> selectByExample(ProductSupplierConfigExample example);

    ProductSupplierConfig selectByPrimaryKey(ProductSupplierConfigKey key);

    int updateByExampleSelective(@Param("record") ProductSupplierConfig record, @Param("example") ProductSupplierConfigExample example);

    int updateByExample(@Param("record") ProductSupplierConfig record, @Param("example") ProductSupplierConfigExample example);

    int updateByPrimaryKeySelective(ProductSupplierConfig record);

    int updateByPrimaryKey(ProductSupplierConfig record);
}