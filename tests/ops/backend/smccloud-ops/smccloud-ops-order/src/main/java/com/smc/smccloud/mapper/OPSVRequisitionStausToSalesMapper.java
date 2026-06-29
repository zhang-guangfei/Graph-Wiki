package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.VSalesManuorder.OPSVRequisitionStausToSalesDO;
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
public interface OPSVRequisitionStausToSalesMapper extends BaseMapper<OPSVRequisitionStausToSalesDO> {


    /**
     * 根据接单日期查询工厂返现
     * @param lastDate
     * @return
     */
    @Select(" select 订单号 as orderNo,订单项号 as itemNo, 型号 as modelNo ,数量 as quantity , 接单日期 as orderDate , 预计发运日A as esDlvDate, " +
            "  开始生产日期 as startProductionDate , 备注 as remark , 发票号 as invoiceNo , 客户代码 as customerNo, 装箱日期 as packDate, " +
            "  出货状态 as shipStatus, 订单类型 as orderType, 更新日期 as updateTime, 生产Holon as poHolon,staus ,[依赖号] as refOrderNo, " +
            " 返信状态 as returnMsgStatus, 不确定返信 as notSureReturnDate,预计生产完成日H as expectedProductionCompletionDateH,预计入物流日W as expectedLogisticsArrivalDateW " +
            " from OPS_V_RequisitionStausToSales with(nolock) " +
            " where 接单日期 >= #{lastDate} order by 接单日期 DESC")
    List<OPSVRequisitionStausToSalesDO> listCNOrderReplyByReceiveTime(@Param("lastDate") String lastDate);

    /**
     * 查询中国工厂订单返信
     * @param lastDate
     * @return
     */
    @Select(" select 订单号 as orderNo,订单项号 as itemNo, 型号 as modelNo ,数量 as quantity , 接单日期 as orderDate , 预计发运日A as esDlvDate, \n" +
            "  开始生产日期 as startProductionDate , 备注 as remark , 发票号 as invoiceNo , 客户代码 as customerNo, 装箱日期 as packDate,\n" +
            "\t出货状态 as shipStatus, 订单类型 as orderType, 更新日期 as updateTime, 生产Holon as poHolon,staus,[依赖号] as refOrderNo," +
            " 返信状态 as returnMsgStatus, 不确定返信 as notSureReturnDate,预计生产完成日H as expectedProductionCompletionDateH,预计入物流日W as expectedLogisticsArrivalDateW " +
            " from OPS_V_RequisitionStausToSales with(nolock) where 返信状态 != '未返信' and  更新日期 >= #{lastDate}")
    List<OPSVRequisitionStausToSalesDO> findReqStausToSalesListByUpDate(@Param("lastDate") String lastDate);


    /**
     * 查询工厂订单返信 --无更新日期 无开始生产日
     * @return
     */
    @Select(" select 订单号 as orderNo,订单项号 as itemNo, 型号 as modelNo ,数量 as quantity , 接单日期 as orderDate , 预计发运日A as esDlvDate, \n" +
            "  开始生产日期 as startProductionDate , 备注 as remark , 发票号 as invoiceNo , 客户代码 as customerNo, 装箱日期 as packDate,\n" +
            "\t出货状态 as shipStatus, 订单类型 as orderType, 更新日期 as updateTime, 生产Holon as poHolon,staus,[依赖号] as refOrderNo, " +
            " 返信状态 as returnMsgStatus, 不确定返信 as notSureReturnDate, 预计生产完成日H as expectedProductionCompletionDateH,预计入物流日W as expectedLogisticsArrivalDateW " +
            " from OPS_V_RequisitionStausToSales with(nolock) where 返信状态 != '未返信' and  [更新日期] is null and [开始生产日期] is null")
    List<OPSVRequisitionStausToSalesDO> listCNOrderReplyByNoUpdateTime();



//    /**
//     * 无接口引用 供测试用
//     * @param orderNo
//     * @return
//     */
    @Select(" select 订单号 as orderNo,订单项号 as itemNo, 型号 as modelNo ,数量 as quantity , 接单日期 as orderDate , 预计交货期 as esDlvDate, \n" +
            "  开始生产日期 as startProductionDate , 备注 as remark , 发票号 as invoiceNo , 客户代码 as customerNo, 装箱日期 as packDate,\n" +
            "\t出货状态 as shipStatus, 订单类型 as orderType, 更新日期 as updateTime, 生产Holon as poHolon,staus, " +
            " 返信状态 as returnMsgStatus, 不确定返信 as notSureReturnDate " +
            " from OPS_V_RequisitionStausToSales where 订单号 = #{orderNo} order by 接单日期 DESC")
    List<OPSVRequisitionStausToSalesDO> findReqStausToSalesListByOrderNo(@Param("orderNo") String orderNo);

}
