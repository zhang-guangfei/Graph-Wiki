package com.smc.smccloud.mapper.pd;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.pd.OpsAsPdBatchDO;
import com.smc.smccloud.model.pd.OpsAsPdBatchRequestVO;
import com.smc.smccloud.model.pd.OpsAsPdBatchVO;
import com.smc.smccloud.model.pd.OpsPdAdjustDO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author smc
 * @since 2023-06-07
 */
@Mapper
@DS("reportdb")
public interface OpsAsPdBatchMapper extends BaseMapper<OpsAsPdBatchDO> {

    @Select("<script>" +
            " select * from ops_report.dbo.ops_as_pd_batch " +
            "<trim prefix='WHERE' suffixOverrides='AND |OR' >" +
            "<if test='dto.pdBatchNo != null and dto.pdBatchNo != \"\" '> " +
            " pd_batch_no like #{dto.pdBatchNo} and  " +
            "</if> " +
            "<if test='dto.pdState != null and dto.pdState != \"\" '> " +
            " pd_state = #{dto.pdState} and  " +
            "</if> " +
            "<if test='dto.createUser != null and dto.createUser != \"\" '> " +
            " create_user like #{dto.createUser} and  " +
            "</if> " +
            "</trim>"+
            "</script>")
    List<OpsAsPdBatchVO> listOpsAsPdBatch(@Param("dto") OpsAsPdBatchRequestVO requestVO);


    @Select("<script>" +
            "select top(20) pd_batch_no from ops_as_pd_batch " +
            "<trim prefix='WHERE' suffixOverrides='AND |OR' >" +
            "<if test='dto != null and dto != \"\" '> " +
            " pd_batch_no like #{dto}  " +
            "</if> " +
            "</trim>" +
            " order by pd_batch_no desc "+
            "</script>")
    List<OpsAsPdBatchVO> listOpsAsPdBatchNo(@Param("dto") String batchNo);


    @Select("<script>" +
            "select top(20) pd_batch_no from ops_pd_adjust " +
            "<trim prefix='WHERE' suffixOverrides='AND |OR' >" +
            "<if test='dto != null and dto != \"\" '> " +
            " pd_batch_no like #{dto}  " +
            "</if> " +
            "</trim>"+
            " group by pd_batch_no " +
            "</script>")
    List<OpsPdAdjustDO> listPdAdjustBatchNo(@Param("dto") String batchNo);

    @Delete("delete from ops_as_pd_batch where pd_batch_no = #{pdBatchNo}")
    void cleanPdData(@Param("pdBatchNo") String pdBatchNo);


    @Select("select pd_batch_no from ops_report.dbo.ops_as_pd_batch where pd_batch_no like 'PD%'  group by pd_batch_no order by pd_batch_no desc")
    List<String> getLastPdBatchNo();

    @Select("select pd_batch_no from ops_report.dbo.ops_as_pd_batch_yd where pd_batch_no like 'YPD%'  group by pd_batch_no order by pd_batch_no desc")
    List<String> getLastYdPdBatchNo();


}
