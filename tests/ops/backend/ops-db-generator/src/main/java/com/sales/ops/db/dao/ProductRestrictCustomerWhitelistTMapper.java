package com.sales.ops.db.dao;

import com.sales.ops.db.entity.ProductRestrictCustomerWhitelistT;
import com.sales.ops.db.entity.ProductRestrictCustomerWhitelistTExample;
import com.sales.ops.db.entity.ProductRestrictCustomerWhitelistTKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductRestrictCustomerWhitelistTMapper {
    long countByExample(ProductRestrictCustomerWhitelistTExample example);

    int deleteByExample(ProductRestrictCustomerWhitelistTExample example);

    int deleteByPrimaryKey(ProductRestrictCustomerWhitelistTKey key);

    int insert(ProductRestrictCustomerWhitelistT record);

    int insertSelective(ProductRestrictCustomerWhitelistT record);

    List<ProductRestrictCustomerWhitelistT> selectByExample(ProductRestrictCustomerWhitelistTExample example);

    ProductRestrictCustomerWhitelistT selectByPrimaryKey(ProductRestrictCustomerWhitelistTKey key);

    int updateByExampleSelective(@Param("record") ProductRestrictCustomerWhitelistT record, @Param("example") ProductRestrictCustomerWhitelistTExample example);

    int updateByExample(@Param("record") ProductRestrictCustomerWhitelistT record, @Param("example") ProductRestrictCustomerWhitelistTExample example);

    int updateByPrimaryKeySelective(ProductRestrictCustomerWhitelistT record);

    int updateByPrimaryKey(ProductRestrictCustomerWhitelistT record);
}