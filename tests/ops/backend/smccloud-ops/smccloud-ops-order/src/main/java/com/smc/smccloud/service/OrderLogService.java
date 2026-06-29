package com.smc.smccloud.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.page.Page;
import com.smc.smccloud.model.OrderLog.OrderLogDO;
import com.smc.smccloud.model.OrderSales.OrderSalesDO;
import com.smc.smccloud.model.OrderSales.OrderSalesRequest;
import com.smc.smccloud.model.orderlog.OrderLogRequest;
import com.smc.smccloud.model.orderlog.OrderLogVO;
import com.smc.smccloud.model.orderstate.OrderStateRequest;
import com.smc.smccloud.model.orderstate.OrderStateVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author smc
 * @since 2021-10-29
 */
public interface OrderLogService extends IService<OrderLogDO> {

    int addLog(OrderLogDO orderLogDO);

    PageInfo<OrderLogVO> findAll(OrderLogRequest request, Page page);

    // 推送日志到MQ
    ResultVo<String> sendOrderLogMsgToMQ(OrderLogVO orderLogVO);

    ResultVo<String> sendOrderLog(OrderLogVO orderLogVO);

}
