package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.OrderSales.OrderSalesDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@DS("sharedb")
@Mapper
public interface OrderSalesMapper extends BaseMapper<OrderSalesDO> {

    /*@Update("UPDATE [ops_sharedb].[dbo].[OrderSales]  SET Status= 1 WHERE id= #{id}")
    int updateStatus(@Param("id") Long id);*/

    @Select("select distinct rorderno from ordersales where status='0' order by rorderno asc")
    List<String> listNoProcessOrderNos();

}
