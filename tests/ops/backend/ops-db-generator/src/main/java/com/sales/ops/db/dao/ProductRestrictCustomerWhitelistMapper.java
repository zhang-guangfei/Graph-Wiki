package com.sales.ops.db.dao;

import com.sales.ops.db.entity.ProductRestrictCustomerWhitelist;
import com.sales.ops.db.entity.ProductRestrictCustomerWhitelistExample;
import com.sales.ops.db.entity.ProductRestrictCustomerWhitelistKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductRestrictCustomerWhitelistMapper {
    long countByExample(ProductRestrictCustomerWhitelistExample example);

    int deleteByExample(ProductRestrictCustomerWhitelistExample example);

    int deleteByPrimaryKey(ProductRestrictCustomerWhitelistKey key);

    int insert(ProductRestrictCustomerWhitelist record);

    int insertSelective(ProductRestrictCustomerWhitelist record);

    List<ProductRestrictCustomerWhitelist> selectByExample(ProductRestrictCustomerWhitelistExample example);

    ProductRestrictCustomerWhitelist selectByPrimaryKey(ProductRestrictCustomerWhitelistKey key);

    int updateByExampleSelective(@Param("record") ProductRestrictCustomerWhitelist record, @Param("example") ProductRestrictCustomerWhitelistExample example);

    int updateByExample(@Param("record") ProductRestrictCustomerWhitelist record, @Param("example") ProductRestrictCustomerWhitelistExample example);

    int updateByPrimaryKeySelective(ProductRestrictCustomerWhitelist record);

    int updateByPrimaryKey(ProductRestrictCustomerWhitelist record);
}