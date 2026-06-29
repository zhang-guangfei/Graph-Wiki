package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.product.ProductRestrictCustomerWhitelistDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author smc
 * @since 2022-06-02
 */
@Mapper
@DS("opsdb")
public interface ProductRestrictCustomerWhitelistMapper extends BaseMapper<ProductRestrictCustomerWhitelistDO> {

    @Select("select count(*) from product_restrict_customer_whitelist " +
            "where is_deleted = '0' and modelNo = #{modelNo} and (customerNo = #{customerNo} or customerNo = #{endUser} )")
    int selectCustomerModelCount(@Param("modelNo") String modelNo, @Param("customerNo") String customerNo, @Param("endUser") String endUser);
}
