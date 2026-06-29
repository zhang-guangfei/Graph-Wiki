package com.smc.smccloud.mapper.pd;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.pd.OpsAsPdThreeReportAnlysisyDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author smc
 * @since 2023-12-19
 */
@Mapper
@DS("reportdb")
public interface OpsAsPdThreeReportAnlysisyMapper extends BaseMapper<OpsAsPdThreeReportAnlysisyDO> {

}
