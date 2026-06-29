package com.smc.smccloud.mapper.csstock;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.csstock.CsReturnApplyDO;
import com.smc.smccloud.model.csstock.CsReturnCalcVo;
import com.smc.smccloud.model.csstock.CsReturnDetailDO;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;

/**
 * <p>
 *  退货 接口
 * </p>
 *
 * @author smc
 * @since 2021-11-03
 */
@Mapper
public interface CsReturnDetailMapper extends BaseMapper<CsReturnDetailDO> {


}
