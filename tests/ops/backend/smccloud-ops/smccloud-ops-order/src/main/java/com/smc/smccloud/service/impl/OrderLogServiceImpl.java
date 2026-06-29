package com.smc.smccloud.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.page.Page;
import com.smc.smccloud.core.rabbitmq.RabbitMqMessage;
import com.smc.smccloud.core.rabbitmq.constants.Constants;
import com.smc.smccloud.core.utils.PublicUtil;
import com.smc.smccloud.mapper.OrderLogMapper;
import com.smc.smccloud.model.OrderLog.OrderLogDO;
import com.smc.smccloud.model.orderlog.OrderLogRequest;
import com.smc.smccloud.model.orderlog.OrderLogVO;
import com.smc.smccloud.rabbitmq.SendMessage;
import com.smc.smccloud.service.OrderLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author smc
 * @since 2021-10-29
 */
@Service
public class OrderLogServiceImpl extends ServiceImpl<OrderLogMapper, OrderLogDO> implements OrderLogService {

    @Resource
    private OrderLogMapper orderLogMapper;

    @Resource
    private SendMessage sendMessage;

    @DS("opslog")
    //@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    @Override
    public int addLog(OrderLogDO orderLogDO) {
        int i = orderLogMapper.insert(orderLogDO);
        if (i != 1) {
            throw new RuntimeException("[add OrderLog] 改变行数小于1,操作失败");
        }
        return i;
    }

    @Override
    public PageInfo<OrderLogVO> findAll(OrderLogRequest request, Page page) {

        QueryWrapper<OrderLogDO> query = new QueryWrapper<>();
        query.eq("1",1);
        query.eq(PublicUtil.isNotEmpty(request.getOrderNo()), "orderno", request.getOrderNo());

        return PageHelper.startPage(page.getPageNumber(), page.getPageSize())
                .doSelectPageInfo(() -> orderLogMapper.selectList(query));
    }

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
        boolean aBoolean = sendMessage.sendOrderLogMsg(rabbitMqMessage);

        if (aBoolean) {
            return ResultVo.success("发送成功");
        }
        return ResultVo.failure("发送失败");
    }

    @Override
    public ResultVo<String> sendOrderLog(OrderLogVO orderLogVO) {
        return  this.sendOrderLogMsgToMQ(orderLogVO);
    }

}
