package com.smc.smccloud.mapper.binorder;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.binorder.ModelExpDetailDO;
import com.smc.smccloud.model.binorder.ModelExpDetailVO;
import com.smc.smccloud.model.binorder.ModelExpFreqRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author smc
 * @since 2021-10-25
 */
@Mapper
@DS("opsreport")
public interface ModelExpDetailMapper extends BaseMapper<ModelExpDetailDO> {

    @Select("select  warehouse_code, model_no,month_date,count(distinct customer_no) as cstmQty,sum(qty) as qty,sum(order_number) as order_number " +
            " from [ops_report].[dbo].[model_exp_detail]" +
            " where warehouse_code=#{stockcode} and model_no=#{modelNo}  and month_date>=#{monthdate}" +
            " group by warehouse_code, model_no,month_date order by month_date desc ")
    List<ModelExpDetailDO> listModeldetail(@RequestBody ModelExpFreqRequest request);

    @Select("select  warehouse_code, model_no,month_date,count(distinct customer_no) as cstmQty,sum(qty) as qty,sum(order_number) as order_number " +
            " from [ops_report].[dbo].[model_exp_detail]" +
            " where job_code=#{stockcode} and model_no=#{modelNo}  and month_date>=#{monthdate}" +
            " group by warehouse_code, model_no,month_date order by month_date desc ")
    List<ModelExpDetailDO> listModeldetailByJob(@RequestBody ModelExpFreqRequest request);
}
