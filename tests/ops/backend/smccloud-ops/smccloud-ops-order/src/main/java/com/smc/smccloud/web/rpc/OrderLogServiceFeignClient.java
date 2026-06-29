package com.smc.smccloud.web.rpc;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.rabbitmq.RabbitMqMessage;
import com.smc.smccloud.core.utils.BeanCopyUtil;
import com.smc.smccloud.core.utils.PublicUtil;
import com.smc.smccloud.log.mapper.OpsSysLogMapper;
import com.smc.smccloud.log.model.OpsSysLogDO;
import com.smc.smccloud.model.OrderLog.OrderLogDO;
import com.smc.smccloud.model.orderlog.OrderLogRequest;
import com.smc.smccloud.model.orderlog.OrderLogVO;
import com.smc.smccloud.model.orderstate.OrderStateVO;
import com.smc.smccloud.service.OrderLogFeignApi;
import com.smc.smccloud.service.OrderLogService;
import com.smc.smccloud.rabbitmq.SendMessage;
import com.smc.smccloud.service.OrderStateLogService;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author lyc
 * @Date 2021/12/24 16:32
 * @Descripton TODO
 */
@RestController
public class OrderLogServiceFeignClient implements OrderLogFeignApi {

    @Resource
    private OrderLogService orderLogService;
    @Resource
    private OrderStateLogService orderStateLogService;

    @Resource
    private OpsSysLogMapper opsSysLogMapper;

    @Override
    public ResultVo<String> addOrderLog(OrderLogVO orderLogVO) {
            return orderLogService.sendOrderLog(orderLogVO);
    }

    @Override
    public ResultVo<String> sendOrderLogMsgToMQ(OrderLogVO orderLogVO) {
        return orderLogService.sendOrderLogMsgToMQ(orderLogVO);
    }

    @Override
    public ResultVo<PageInfo<OrderLogVO>> findOrderLog(OrderLogRequest orderLogRequest) {
        PageInfo<OrderLogVO> orderLogDOPageInfo = orderLogService.findAll(orderLogRequest, orderLogRequest.getPage());
        return ResultVo.success(orderLogDOPageInfo);
    }

    @Override
    public ResultVo<String> insertOrderStateLog(OrderStateVO orderStateVO) {
        int i = orderStateLogService.insertOrderStateLog(orderStateVO, orderStateVO.getLogDataProvider());
        if(i != 1) {
            return ResultVo.failure("日志写入失败");
        } else {
            return ResultVo.success("写入货期状态日志成功.");
        }
    }

    @Override
    public ResultVo<String> selectLogByIds(String id) {
        LambdaQueryWrapper<OpsSysLogDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OpsSysLogDO::getId,id);
        Integer count = opsSysLogMapper.selectCount(queryWrapper);
        return ResultVo.success("查询条数:"+count);
    }
}
