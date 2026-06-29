package com.smc.smccloud.mapper.csstock;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.csstock.CsModelBalanceDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;

/**
 * <p>
 *  月结信息
 * </p>
 *
 * @author smc
 * @since 2022-01-13
 */
@Mapper
public interface CsBalanceMapper extends BaseMapper<CsModelBalanceDO> {


    void deleteMonthData(Date monthDate);
}
