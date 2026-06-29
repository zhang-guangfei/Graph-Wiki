package com.sales.ops.serviceimpl.dispatch.dodispatch.service;


import cn.hutool.core.collection.CollectionUtil;
import com.sales.ops.common.until.OPSRedisUtils;
import com.sales.ops.db.dao.ExpdetailMapper;
import com.sales.ops.db.dao.OpsDoMapper;
import com.sales.ops.db.entity.Expdetail;
import com.sales.ops.db.entity.ExpdetailExample;
import com.sales.ops.db.entity.OpsDo;
import com.sales.ops.db.entity.OpsDoExample;
import com.sales.ops.dto.tms.OrderRouterParam;
import com.sales.ops.dto.tms.TMSRouteStatusEnum;
import com.sales.ops.dto.tms.TmsTrackingResult;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.event.publisher.enums.EventSourceEnum;
import com.sales.ops.service.feign.TmsFeignApi;
import com.sales.ops.serviceimpl.event.v3.CustomerEventPublisher;
import io.jsonwebtoken.lang.Collections;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TMSRouteHandler {

    @Autowired
    private ExpdetailMapper expdetailMapper;

    private final String redisKey = "ops:order:orderRoute:";
    @Autowired
    private TmsFeignApi tmsFeignApi;
    @Autowired
    private OPSRedisUtils opsRedisUtils;
    @Autowired
    private OpsDoMapper opsDoMapper;
    @Autowired
    private CustomerEventPublisher customerEventPublisher;

    public TmsTrackingResult getOrderRoute(String expressNo) {
        Boolean exists = opsRedisUtils.hasKey(redisKey + expressNo);
        if (exists != null && exists) {
            Object obj = opsRedisUtils.get(redisKey + expressNo);
            if (obj != null && obj instanceof TmsTrackingResult) {
                return (TmsTrackingResult) obj;
            }
        }
        CommonResult<TmsTrackingResult> result = tmsFeignApi.getTmsRoute(expressNo);
        if (result.isSuccess()) {
            opsRedisUtils.set(redisKey + expressNo, result.getData());
            opsRedisUtils.expire(redisKey + expressNo, 2, TimeUnit.MINUTES); // 存二分钟
            return result.getData();
        }
        return null;
    }

    public List<String> sendOrderRoute(OrderRouterParam param) {
        ExpdetailExample example = new ExpdetailExample();
        ExpdetailExample.Criteria criteria = example.createCriteria();
        criteria.andExpressNoEqualTo(param.getExpressNo());
        if (!CollectionUtil.isEmpty(param.getDoId())) {
            criteria.andDeliveryNoIn(param.getDoId());
        }
        // bug 12282 如果状态为已签收时，则用母单号更新expdetail表中的sign_status，sign_time，update_user，update_time
        if (Objects.equals(param.getStateCode(), Integer.valueOf(TMSRouteStatusEnum.SIGNED.getCode()))) {
            // 只更新状态是1的
            criteria.andSignStatusEqualTo((short) 1);
            Expdetail update = new Expdetail();
            update.setSignStatus((short) 2);
            if (param.getActionTime() != null) {
                update.setSignTime(param.getActionTime());
            }
            update.setUpdateUser("TMS_ROUTE");
            update.setUpdateTime(new Date());
            expdetailMapper.updateByExampleSelective(update, example);
        }
        ExpdetailExample ex = new ExpdetailExample();
        ExpdetailExample.Criteria cri = ex.createCriteria();
        cri.andExpressNoEqualTo(param.getExpressNo());
        if (!CollectionUtil.isEmpty(param.getDoId())) {
            cri.andDeliveryNoIn(param.getDoId());
        }


        // 按orderFno去重，并取orderNo,itemNo
        List<Expdetail> expdetail = expdetailMapper.selectByExample(ex);
        Map<String, List<Expdetail>> map = expdetail.stream().filter(exp -> exp.getOrderFno() != null).collect(Collectors.groupingBy(Expdetail::getOrderFno));
        map.forEach((orderFno, list) -> {
            if (!Collections.isEmpty(list)) {
                Expdetail exp = list.get(0);
                customerEventPublisher.customerOrderEvent(EventSourceEnum.TRANSPORT_ROUTE_INFO, exp.getOrderNo(), exp.getItemNo(), param);
                if(param.getExpectedDeliveryDate()!=null && StringUtils.isNotBlank(exp.getDeliveryNo())){
                    updateDoExpectedDeliveryDate(exp.getDeliveryNo(), param.getExpectedDeliveryDate());
                }
            }
        });
        List<String> list = new ArrayList(map.keySet());
        return list;
    }

    private int updateDoExpectedDeliveryDate(String doId, Date date) {
        OpsDoExample ex = new OpsDoExample();
        ex.createCriteria().andDoIdEqualTo(doId).andDelflagEqualTo(0);
        OpsDo update = new OpsDo();
        update.setTmsExpectedDeliveryDate(date);
        return opsDoMapper.updateByExampleSelective(update, ex);

    }


}
