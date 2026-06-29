package com.smc.smccloud.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.page.Page;
import com.smc.smccloud.model.orderlog.OrderLogDO;
import com.smc.smccloud.model.orderlog.OrderLogRequest;
import com.smc.smccloud.model.orderlog.OrderLogVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author smc
 * @since 2021-10-29
 */
public interface OrderLogService extends IService<OrderLogDO> {

    // 推送日志到MQ
    ResultVo<String> sendOrderLogMsgToMQ(OrderLogVO orderLogVO);


}
