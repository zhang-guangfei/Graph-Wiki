package com.smc.smccloud.mapper.csstock;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.binorder.ModelExpFreqVO;
import com.smc.smccloud.model.borrowstock.BrwMasterDO;
import com.smc.smccloud.model.csstock.CsTmpReturnCalcDO;
import com.smc.smccloud.model.csstock.CsTmpReturnVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Mapper
@DS("sharedb")
public interface CsTmpReturnCalcMapper extends BaseMapper<CsTmpReturnCalcDO> {

    @Select("<script>" +
            "select DeptNo,agent_no,warehouse_code,count(model_no) as models,sum(return_qty) as return_Qty,sum(overTime_qty) as overTime_qty " +
            "  from TMP_CsReturnCalc" +
            "  where overTime_qty>0" +
            "<if test = 'agentNo != null and agentNo != \"\" ' >" +
            " and agent_no = #{agentNo}" +
            "</if>" +
            "<if test = 'warehouseCode != null and warehouseCode != \"\" ' >" +
            " and warehouse_code = #{warehouseCode} " +
            "</if>" +
            "<if test = 'deptNo != null and deptNo != \"\" ' >" +
            " and DeptNo = #{deptNo} " +
            "</if>" +
            "  group by DeptNo,agent_no,warehouse_code" +
            "</script>")
    List<CsTmpReturnVO> listCsTmpReturnData(@Param("agentNo") String agentNo,@Param("warehouseCode") String warehouseCode,@Param("deptNo") String deptNo);

    @Select("select sum(overTime_qty) " +
            "  from TMP_CsReturnCalc" +
            "  where overTime_qty>0 AND warehouse_code = #{warehouseCode} AND agent_no = #{agentNo} AND model_no=#{modelNo}")
    Integer selectOverTimeQty(@Param("warehouseCode") String warehouseCode,@Param("agentNo") String agentNo,@Param("modelNo") String modelNo);

    @Select("SELECT M36,M35,M34,M33,M32,M31 FROM ops_report.dbo.model_exp_freq WHERE ModelType=1 AND StockType=4 AND ModelNo =#{modelNo}  AND StockCode =#{customerNo} ")
    List<ModelExpFreqVO> selectOrderMoths(@Param("modelNo") String modelNo, @Param("customerNo") String customerNo);

    @Select("SELECT count(distinct customer_no) FROM ops_report.dbo.model_exp_detail WHERE " +
            "agent_no =#{agentNo} AND model_no=#{modelNo} AND month_date >=#{startDate} AND month_date<=#{endDate}")
    Integer getCustomersOf6(@Param("agentNo")String agentNo,@Param("modelNo") String modelNo,@Param("startDate") Date startDate,@Param("endDate") Date endDate);

    @Select("SELECT M36+M35+M34+M33+M32+M31 FROM ops_report.dbo.model_exp_freq WHERE StockCode =#{customerNo} " +
            "AND ModelNo=#{modelNo} AND StockType=4 AND ModelType=1")
    BigDecimal getAvgQtyOf6(@Param("customerNo")String customerNo, @Param("modelNo") String modelNo);

}
