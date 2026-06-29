package com.smc.ops.delivery.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.sales.ops.db.entity.OpsRequestpurchaseInterceptConfig;
import com.smc.ops.delivery.model.ModelExpFreqDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2024/7/9 11:16
 */
@Mapper
@DS("reportdb")
public interface LowFrequencyReportdbDao {

    /**
     * bugid: 14635
     * 2. 查询 通过型号和StockCode='ALL' AND AvgQtyOf8 = 0 AND ModelNo ='' 找到ModelType=1和ModelType=2的AvgQtyOf8均为0，找不到的情况默认按0计算
     * @return
     */
    @Select("SELECT ModelNo ,StockCode ,AvgQtyOf8 ,ModelType  from ops_report.dbo.model_exp_freq where StockCode ='ALL' AND ModelNo = #{modelNo} ")
    List<ModelExpFreqDto> getModelExpFreqDtos(@Param("modelNo") String modelNo);

}
