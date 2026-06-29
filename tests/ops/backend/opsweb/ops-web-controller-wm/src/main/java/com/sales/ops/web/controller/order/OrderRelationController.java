package com.sales.ops.web.controller.order;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.dto.order.OrderRelationExceptionDto;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.serviceimpl.event.v3.order.entity.Order;
import com.sales.ops.serviceimpl.event.v3.order.service.OrderRelationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2024/10/16 13:44
 */

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/order")
public class OrderRelationController {

    @Autowired
    private OrderRelationService orderRelationService;

    /**
     * 每次调用500个单子
     * bugid：15461
     * @param orderFnoList
     * @return
     */
    @PostMapping("/relation/exception")
    public CommonResult<List<OrderRelationExceptionDto>> getOrderRelationException(@RequestBody List<String> orderFnoList) {
        List<OrderRelationExceptionDto> list = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(orderFnoList)){
            if(orderFnoList.size() > 500){
                CommonResult.failure("调用不能超过500条");
            }
            for(String orderFno : orderFnoList){
                try {
                    String[] split = orderFno.split("-");
                    Order order = orderRelationService.getOrder(split[0], Integer.valueOf(split[1]));
                } catch (OpsException e) {
                    String msg = "数据查询失败";
                    if(StringUtils.isNotBlank(e.getMessage()) && e.getMessage().length() < 200){
                        msg = e.getMessage();
                    }
                    list.add(new OrderRelationExceptionDto(orderFno,msg));
                } catch (Exception ex){//todo 测试数据
                    list.add(new OrderRelationExceptionDto(orderFno,"数据查询失败"));
                }
            }
        }
        return CommonResult.success(list);
    }
}
