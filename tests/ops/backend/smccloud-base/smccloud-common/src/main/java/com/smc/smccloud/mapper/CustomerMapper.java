package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.Customer.CustomerDO;
import com.smc.smccloud.model.customer.CustomerVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author smc
 * @since 2022-01-12
 */
@Mapper
@DS("opsdb")
public interface CustomerMapper extends BaseMapper<CustomerDO> {

    @Select("SELECT TOP 10 customer_no as customerNo, name, HRUnitId, PSNSMCID as psnSMCID " +
            "FROM ops_customer WHERE customer_no like CONCAT(#{customerNo},'%') OR name like CONCAT(#{customerNo},'%')")
    List<CustomerVO> queryCustomer(@Param("customerNo") String customerNo);

    @Select("select customer_no,name,ename from ops_customer where ISNULL(name, '') <> '' and  ISNULL(ename, '') = '' and ISNULL(alias_ename, '') = '' ")
    List<CustomerDO> queryEnameIsNullList();

}
