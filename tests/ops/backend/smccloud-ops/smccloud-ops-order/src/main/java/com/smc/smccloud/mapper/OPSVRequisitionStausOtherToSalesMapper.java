package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.VSalesManuorder.OPSVRequisitionStausOtherToSalesDO;
import com.smc.smccloud.model.VSalesManuorder.OpsVManuorderToSales;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author lyc
 * @Date 2022/4/2 15:48
 * @Descripton TODO
 */
@Mapper
@DS("cmdb")
public interface OPSVRequisitionStausOtherToSalesMapper extends BaseMapper<OPSVRequisitionStausOtherToSalesDO> {


    @Select("select IssueID as IssueID, 客户代码 as customerNo, 订单号 as orderNo, 订单项号 as itemNo," +
            " 型号 as modelNo , 数量 as quantity, 订单发行日期 as poShipDate , 取消原因 as calReason,更新日期 as updateDate," +
            " Staus as status ,订单类型 as orderType  " +
            "from Manufacture.dbo.OPS_V_RequisitionStausOtherToSales with(nolock) " +
            " WHERE 更新日期 <= #{currentDate} and 更新日期 > #{startDate} \n" +
            " ORDER BY 更新日期 desc ")
    List<OPSVRequisitionStausOtherToSalesDO> findReqStausOtherToSalesListByDate(@Param("startDate") String startDate,@Param("currentDate") String currentDate);

}
