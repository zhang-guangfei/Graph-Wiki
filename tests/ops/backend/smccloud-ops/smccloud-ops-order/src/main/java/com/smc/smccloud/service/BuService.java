package com.smc.smccloud.service;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.BuInterface.BuInvoiceResponse;
import com.smc.smccloud.model.VSalesManuorder.OPSVRequisitionStausOtherToSalesDO;
import com.smc.smccloud.model.VSalesManuorder.OPSVRequisitionStausOtherToSalesVO;
import com.smc.smccloud.model.cnfactory.OrderReplyResponse;

import java.util.List;
import java.util.Map;

/**
 * Author: B90034
 * Date: 2021-12-01 15:25
 * Description: 关务服务接口
 *   制造接口
 */
public interface BuService {

    /**
     * 调用进口发票查询接口
     * @param param plantMark(厂别), invNo(发票号), startTime(起始时间 yyyy-MM-dd HH:mm:ss), endTime(结束时间 yyyy-MM-dd HH:mm:ss)
     *              pageNum, pageSize
     */

    BuInvoiceResponse queryImportInvoiceInfo(Map<String, String> param);


    /**
     * 营业订单返信查询接口
     */
    OrderReplyResponse querySalesOrderReply(List<String> orderNos);

    /**
     * 入库信息回填接口
     * @param param
     */
    OrderReplyResponse updateWarehousingInfo(Map<String, String> param);

    /**
     * 营业订单状态接口（已接单)
     * 从中国工厂导入所有未发货订单状态
     */

    ResultVo<String> importCNFactoryNotSendOrderState();



}
