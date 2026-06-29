package com.smc.smccloud.mapper.binorder;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.binorder.ModelExpFreqDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author smc
 * @since 2021-10-23
 */
@Mapper
@DS("opsreport")
public interface ModelExpFreqMapper extends BaseMapper<ModelExpFreqDO> {

    @Select("{ call imp_rcvorder }")
    @Options(statementType = StatementType.CALLABLE)
    String calimpRcvOrder();

    @Select("{ call calc_modelExpFreq }")
    @Options(statementType = StatementType.CALLABLE)
    String calcmodelExpFreq();

    @Select("{ call calc_modelExpFreq_whole(@modelType=#{modelType, mode=IN, jdbcType=INTEGER})}")
    @Options(statementType = StatementType.CALLABLE)
    String calcModelExpFreqByWhole(@Param("modelType") int modelType);
    @Select("{ call calc_modelExpFreq_by_warehouse(@modelType=#{modelType, mode=IN, jdbcType=INTEGER})}")
    @Options(statementType = StatementType.CALLABLE)
    String calcModelExpFreqByWareHouse(@Param("modelType") int modelType);

    @Select("{ call calc_modelExpFreq_by_customer(@modelType=#{modelType, mode=IN, jdbcType=INTEGER})}")
    @Options(statementType = StatementType.CALLABLE)
    String calcModelExpFreqByCustomer(@Param("modelType") int modelType);

    @Select("{ call calc_modelExpFreq_by_sub_warehouse(@modelType=#{modelType, mode=IN, jdbcType=INTEGER})}")
    @Options(statementType = StatementType.CALLABLE)
    String calcModelExpFreqBySubWarehouse(@Param("modelType") int modelType);

    @Select("{ call calc_modelExpFreq_by_customGroupCode(@modelType=#{modelType, mode=IN, jdbcType=INTEGER})}")
    @Options(statementType = StatementType.CALLABLE)
    String calcModelExpFreqByCustomGroupCode(@Param("modelType") int modelType);


    @Select("{ call calcModelExpFreqUpdateModelInfo}")
    @Options(statementType = StatementType.CALLABLE)
    String calcModelExpFreqUpdateModelInfo();


    @Select("{call #{procName}}")
    @Options(statementType = StatementType.CALLABLE)
    String execModelFreqProc(@Param("procName") String procName);

    @Select(" select distinct StockCode,ModelType from model_exp_freq where StockType<=2")
    List<ModelExpFreqDO> getExpFreqStockType();

    @Select("<script> " +
            " select modelno, StockCode,AvgQtyof8 from model_exp_freq where "+
            "<if test = 'list != null and  list.size() &gt; 0' >" +
            "  <foreach collection='list' item='item' index='index' open='(' separator='or' close=')'> " +
            "   (modelno=#{item.modelNo} and StockCode=#{item.stockCode})  " +
            "  </foreach>" +
            "</if>"+ " and ModelType=1 "+
            " </script>")
    List<ModelExpFreqDO> getModelExpFreqForAvgQty(@Param("list")List<ModelExpFreqDO> doList);

    @Select("<script> " +
            " select *From (" +
            " select modelno, AvgQtyOf12 ,MonthsOf12, ROW_NUMBER() over(partition by modelno order by modeltype) as idx from model_exp_freq where  modelno in "+
            "<if test = 'modelNos != null and  modelNos.size() &gt; 0' >" +
            "  <foreach collection='modelNos' item='item' index='index' open='(' separator=',' close=')'> " +
            "   #{item}   " +
            "  </foreach>" +
            "</if>"+ " and StockCode='All' and  ModelType in ('1','2') ) A where idx=1"+
            " </script>")
    List<ModelExpFreqDO> getModelExpFreqForAvgQty12(@Param("modelNos")List<String> modelNos);

    @Select("<script> " +
            " select *From (" +
            " select modelno, " +
            " <if test='month !=null and month ==36' > AvgQtyOf36 ,MonthsOf36 </if> " +
            " <if test='month !=null and month ==24'> AvgQtyOf24 ,MonthsOf24 </if> " +
            " <if test='month !=null and month ==12'> AvgQtyOf12 ,MonthsOf12 </if> " +
            " <if test='month !=null and month ==8'> AvgQtyOf8 ,MonthsOf8 </if> " +
            " , ROW_NUMBER() over(partition by modelno order by modeltype) as idx " +
            " from model_exp_freq where  modelno in "+
            "<if test = 'modelNos != null and  modelNos.size() &gt; 0' >" +
            "  <foreach collection='modelNos' item='item' index='index' open='(' separator=',' close=')'> " +
            "   #{item}   " +
            "  </foreach>" +
            "</if>"+ " and StockCode=#{warehouseCode}  and  ModelType in ('1','2') ) A where idx=1"+
            " </script>")
    List<ModelExpFreqDO> getModelExpFreqForAvgQty(@Param("modelNos")List<String> modelNos,@Param("warehouseCode") String warehouseCode,@Param("month") Integer month);

    @Select("<script>" +
            "SELECT ModelNo, StockCode, AvgQtyOf8 FROM model_exp_freq WITH (NOLOCK) " +
            "WHERE ModelType = 1 " +
            "<choose>" +
            "  <when test='modelNos != null and modelNos.size() &gt; 0'>" +
            "    AND ModelNo IN " +
            "    <foreach collection='modelNos' item='item' open='(' separator=',' close=')'>#{item}</foreach>" +
            "  </when>" +
            "  <otherwise>AND 1 = 0</otherwise>" +
            "</choose>" +
            "<choose>" +
            "  <when test='warehouseCodes != null and warehouseCodes.size() &gt; 0'>" +
            "    AND StockCode IN " +
            "    <foreach collection='warehouseCodes' item='item' open='(' separator=',' close=')'>#{item}</foreach>" +
            "  </when>" +
            "  <otherwise>AND 1 = 0</otherwise>" +
            "</choose>" +
            "</script>")
    List<ModelExpFreqDO> selectAvgQtyOf8ByModelNosAndWarehouseCodes(@Param("modelNos") List<String> modelNos,
                                                                     @Param("warehouseCodes") List<String> warehouseCodes);

    /**
     * 获取ID范围和总数(用于TOP分页)
     */
    @Select("<script>" +
            "SELECT MIN(id) AS minId, MAX(id) AS maxId, COUNT(*) AS totalCount " +
            "FROM model_exp_freq WITH (NOLOCK) " +
            "<where>" +
            "  <if test='stockType != null and stockType &gt; 2'>" +
            "    AND stockType = #{stockType} AND modelType = #{modelType}" +
            "  </if>" +
            "  <if test='stockType == null and stockType &lt;= 2'>" +
            "    AND stockCode = #{stockCode} AND modelType = #{modelType}" +
            "  </if>" +
            "</where>" +
            "</script>")
    Map<String, Object> getIdRange(@Param("stockType") Integer stockType,
                                   @Param("stockCode") String stockCode,
                                   @Param("modelType") String modelType);

    /**
     * TOP分页查询(大数据量用,高效)
     */
    @Select("<script>" +
            "SELECT TOP ${limit} * FROM model_exp_freq WITH (NOLOCK) " +
            "<where>" +
            "  <if test='stockType != null and stockType &gt; 2'>" +
            "    AND stockType = #{stockType} AND modelType = #{modelType}" +
            "  </if>" +
            "  <if test='stockType == null or stockType &lt;= 2'>" +
            "    AND stockCode = #{stockCode} AND modelType = #{modelType}" +
            "  </if>" +
            "  AND id &gt;= #{startId} AND id &lt;= #{endId}" +
            "</where>" +
            "ORDER BY id" +
            "</script>")
    List<ModelExpFreqDO> selectByIdRange(@Param("stockType") Integer stockType,
                                         @Param("stockCode") String stockCode,
                                         @Param("modelType") String modelType,
                                         @Param("startId") Long startId,
                                         @Param("endId") Long endId,
                                         @Param("limit") int limit);

    /**
     * 获取类型数量
     */
    @Select("<script>" +
            "SELECT COUNT(*) FROM model_exp_freq WITH (NOLOCK) " +
            "<where>" +
            "  <if test='stockType != null and stockType &gt; 2'>" +
            "    AND stockType = #{stockType} AND modelType = #{modelType}" +
            "  </if>" +
            "  <if test='stockType == null or stockType &lt;= 2'>" +
            "    AND stockCode = #{stockCode} AND modelType = #{modelType}" +
            "  </if>" +
            "</where>" +
            "</script>")
    long countByType(@Param("stockType") Integer stockType,
                     @Param("stockCode") String stockCode,
                     @Param("modelType") String modelType);
}
