package com.sales.ops.db.dao;

import com.sales.ops.db.entity.Shoplist;
import com.sales.ops.db.entity.ShoplistExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ShoplistMapper {
    long countByExample(ShoplistExample example);

    int deleteByExample(ShoplistExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Shoplist record);

    int insertSelective(Shoplist record);

    List<Shoplist> selectByExample(ShoplistExample example);

    Shoplist selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Shoplist record, @Param("example") ShoplistExample example);

    int updateByExample(@Param("record") Shoplist record, @Param("example") ShoplistExample example);

    int updateByPrimaryKeySelective(Shoplist record);

    int updateByPrimaryKey(Shoplist record);
}