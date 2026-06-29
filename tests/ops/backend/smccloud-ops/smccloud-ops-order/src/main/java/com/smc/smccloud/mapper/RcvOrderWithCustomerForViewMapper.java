package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.adapter.order.SMSSearchListInfo;
import com.smc.smccloud.model.order.IndCodeEntity;
import com.smc.smccloud.model.order.RcvOrderViewDO;
import com.smc.smccloud.model.order.RcvOrderWithCustomerForViewDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author lyc
 * @Date 2022/8/24 19:48
 * @Descripton TODO
 */
@Mapper
@DS("opsdb")
public interface RcvOrderWithCustomerForViewMapper extends BaseMapper<RcvOrderWithCustomerForViewDO> {


}
