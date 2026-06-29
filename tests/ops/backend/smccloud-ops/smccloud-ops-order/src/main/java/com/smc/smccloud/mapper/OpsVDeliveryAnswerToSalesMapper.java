package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.VSalesManuorder.OpsVDeliveryAnswerToSalesDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * @author edp04
 * @title: OpsVDeliveryAnswerToSalesMapper
 * @date 2022/04/26 15:45
 */
@Mapper
@DS("cmdb")
public interface OpsVDeliveryAnswerToSalesMapper extends BaseMapper<OpsVDeliveryAnswerToSalesDO> {

    @Select("SELECT 型号 modelNo, 数量 quantity, 发行日期 shipDate, 营业纳期 dlvDate, 变更纳期 changeDlvDate, 入库号 inStockCode, 出库日期 expDate," +
            " 依赖号 dependencyCode, 备注 remark, 发票号 invoiceNo, 客户代码 customerNo, 装箱日期 packDate, 工厂出口日期 ExportDate, 出货状态 expStatus," +
            " 订单类型 orderType, 生产Holon holon, 返信备注 replayRemark, 订单号 orderNo,订单项号 itemNo FROM dbo.OPS_V_DeliveryAnswerToSales with(nolock) where 1=1")
    List<OpsVDeliveryAnswerToSalesDO> selectData();

}
