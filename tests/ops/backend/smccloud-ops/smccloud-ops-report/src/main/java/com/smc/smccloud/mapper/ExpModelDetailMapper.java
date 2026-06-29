package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.ExpModelDetailDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * @author edp04
 * @title: ExpModelDetailMapper
 * @date 2022/05/11 12:21
 */
@Mapper
@DS("reportdb")
public interface ExpModelDetailMapper extends BaseMapper<ExpModelDetailDO> {


    @Select("SELECT model_no,customer_no,warehouse_code,month_date,sum(qty) as qty,sum(order_number) as orderNumber FROM model_exp_detail\n" +
            " where month_date>=#{fromDate} and month_date<=#{toDate} and agent_no=#{agentNo}\n" +
            " group by customer_no,model_no,month_date,warehouse_code order by model_no")
    List<ExpModelDetailDO> listExpDetail(@Param("agentNo") String agentNo, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

    @Select("SELECT customer_no FROM ops_core.dbo.ops_warehouse where warehouse_type='WT'")
    List<String> listCustomerData();
}
