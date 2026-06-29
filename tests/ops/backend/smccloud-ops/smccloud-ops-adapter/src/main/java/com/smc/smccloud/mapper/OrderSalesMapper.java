package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.ordermodify.OrderModifyVO;
import com.smc.smccloud.model.ordersales.OrderSalesDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Author: B90034
 * Date: 2022-02-15 10:25
 * Description:
 */
@DS("sharedb")
@Mapper
public interface OrderSalesMapper extends BaseMapper<OrderSalesDO> {
    @Select(" select apply_no as applyNo,order_no as orderNo,apply_time as applyTime,apply_Psn as applyPsn,reason " +
            " from order_modify where remark like '%com%' and status=8")
    List<OrderModifyVO> listOrderModify();
}
