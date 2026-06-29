package com.smc.smccloud.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.rabbitmq.RabbitMqMessage;
import com.smc.smccloud.core.rabbitmq.constants.Constants;
import com.smc.smccloud.core.utils.PublicUtil;
import com.smc.smccloud.mapper.OrderLogMapper;
import com.smc.smccloud.model.orderlog.OrderLogDO;
import com.smc.smccloud.model.orderlog.OrderLogVO;
import com.smc.smccloud.rabbitmq.SendRcvOrderMqMsg;
import com.smc.smccloud.service.OrderLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author lyc
 * @Date 2023/9/12 10:11
 * @Descripton TODO
 */
@Service
public class OrderLogServiceImpl extends ServiceImpl<OrderLogMapper, OrderLogDO> implements OrderLogService {

    @Resource
    private SendRcvOrderMqMsg sendRcvOrderMqMsg;

    @Override
    public ResultVo<String> sendOrderLogMsgToMQ(OrderLogVO orderLogVO) {
        if (PublicUtil.isEmpty(orderLogVO)) {
            return ResultVo.failure("发送消息失败");
        }
        RabbitMqMessage rabbitMqMessage = new RabbitMqMessage();
        rabbitMqMessage.setContent(JSON.toJSONString(orderLogVO));
        rabbitMqMessage.setFlag(Constants.OPS_ORDER_LOG);
        rabbitMqMessage.setDataType(Constants.OPS_ORDER);
        rabbitMqMessage.setSystem(Constants.OPS);
        boolean aBoolean = sendRcvOrderMqMsg.sendOrderLogMsg(rabbitMqMessage);

        if (aBoolean) {
            return ResultVo.success("发送成功");
        }
        return ResultVo.failure("发送失败");
    }
}
