package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.adapter.order.SMSOrder;
import com.smc.smccloud.model.adapter.order.SMSSearchListInfo;
import com.smc.smccloud.model.order.RcvOrderViewDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author lyc
 * @Date 2022/7/4 15:47
 * @Descripton TODO
 */

@DS("opsdb")
@Mapper
public interface RcvorderViewMapper extends BaseMapper<RcvOrderViewDO> {

}
