package com.smc.ops.delivery.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


@Mapper
@DS("opsdb")
public interface CommonMapper {

    @Select("select Top(1) id from supplier where name like #{name} order by len(name) asc")
    String getSupplierCodeByName(@Param("name") String name);

    @Select("SELECT id FROM currency where AbbrName=#{currency}")
    String getCurrencyIdByName(@Param("currency") String currency);

    @Select("select name from supplier where id=#{id}")
    String getSupplierNameByid(@Param("id") String id);

    @Select("SELECT companyId FROM supplier WHERE id=#{id}")
    String getCompanyIdById(@Param("id") String id);
}
