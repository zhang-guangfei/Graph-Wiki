package com.smc.smccloud.mapper.binorder;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.binorder.ModelExpFreqByJobDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;
import java.util.Map;

/**
 * @author wuweidong
 * @create 2023/6/8 13:56
 * @description
 */
@Mapper
@DS("opsreport")
public interface ModelExpFreqByJobMapper extends BaseMapper<ModelExpFreqByJobDO> {

    @Select("{#{Result,mode=OUT,jdbcType=INTEGER}=call dbo.calc_modelExpFreq_by_job(#{jobid,mode=IN,jdbcType=INTEGER},#{master,mode=IN,jdbcType=INTEGER})}")
    @Options(statementType = StatementType.CALLABLE)
    void updateModelExpFreqByJob(Map<String, Object> map);

    @Select("  declare @begindate datetime " +
            "  declare @EndDate datetime " +
            "  exec [dbo].[GetLastMonthRange] 36 ,@begindate OUTPUT, @EndDate OUTPUT " +
            " exec calc_modelExpFreq_updModelInfo_by_job #{jobId} , @enddate")
   void calcModelExpFreqUpdModelInfoByJob (@Param("jobId") Long jobId);

    @Select("{#{Result,mode=OUT,jdbcType=INTEGER}=call dbo.calc_modelExpFreq_updModelInfo_by_job(#{jobId,mode=IN,jdbcType=INTEGER},#{endDate,mode=IN,jdbcType=DATE })}")
    @Options(statementType = StatementType.CALLABLE)
    void calcModelExpFreqUpdModelInfoByJobII (Map<String, Object> map);

    @Select("{#{Result,mode=OUT,jdbcType=INTEGER}=call dbo.GetLastMonthRange(#{months,mode=IN,jdbcType=INTEGER},#{beginDate,mode=OUT,jdbcType=DATE },#{endDate,mode=OUT,jdbcType=DATE })}")
    @Options(statementType = StatementType.CALLABLE)
    void getLastMonthRange (Map<String, Object> map);



    /**
     * 获取ID范围和总数(用于TOP分页)
     */
    @Select("<script>" +
            "SELECT MIN(id) AS minId, MAX(id) AS maxId, COUNT(*) AS totalCount " +
            "FROM model_exp_freq_by_job WITH (NOLOCK) " +
            "<where>" +
            "  <if test='JobId != null  '>" +
            "    AND JobId = #{JobId}" +
            "  </if>" +
            "  <if test='warehouseCode != null and warehouseCode  != \"\"  '>" +
            "    AND warehouseCode = #{warehouseCode} " +
            "  </if>" +
            "  <if test='modelType != null and modelType  != \"\"  '>" +
            "    AND modelType = #{modelType} " +
            "  </if>" +
            "   <if test='modelNos != null and modelNos.size() &gt; 0 '>" +
            "   and modelno in " +
            "   <foreach collection= 'modelNos' item= 'modelno' separator= ', ' open= '( ' close= ') '> " +
            "      #{modelno}" +
            "   </foreach>" +
            "   </if>" +
            "</where>" +
            "</script>")
    Map<String, Object> getIdRange(Long JobId, String warehouseCode, String modelType, List<String> modelNos);

    /**
     * TOP分页查询(大数据量用,高效)
     */
    @Select("<script>" +
            "SELECT TOP ${limit} * " +
            "FROM model_exp_freq_by_job WITH (NOLOCK) " +
            "<where>" +
            "  <if test='JobId != null  '>" +
            "    AND JobId = #{JobId}" +
            "  </if>" +
            "  <if test='warehouseCode != null and warehouseCode  != \"\"  '>" +
            "    AND warehouseCode = #{warehouseCode} " +
            "  </if>" +
            "  <if test='modelType != null and modelType  != \"\"  '>" +
            "    AND modelType = #{modelType} " +
            "  </if>" +
            "   <if test='modelNos != null and modelNos.size() &gt; 0 '>" +
            "   and modelno in " +
            "   <foreach collection= 'modelNos' item= 'modelno' separator= ', ' open= '( ' close= ') '> " +
            "      #{modelno}" +
            "   </foreach>" +
            "   </if>" +
            "  AND id &gt;= #{startId} AND id &lt;= #{endId}" +
            "</where>" +
            "ORDER BY id" +
            "</script>")
    List< ModelExpFreqByJobDO> selectByIdRange(Long JobId, String warehouseCode, String modelType, List<String> modelNos,
                                         @Param("startId") Long startId,
                                         @Param("endId") Long endId,
                                         @Param("limit") int limit);



}
