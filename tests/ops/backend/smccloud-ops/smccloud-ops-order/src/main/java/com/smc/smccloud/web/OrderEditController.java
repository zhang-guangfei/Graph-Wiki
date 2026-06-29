package com.smc.smccloud.web;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.adapter.order.OrderDelivery;
import com.smc.smccloud.model.order.UpdateOrderInfoResultVO;
import com.smc.smccloud.model.order.orderEdit.UpMasterInfo;
import com.smc.smccloud.model.order.orderEdit.UpOrderAddressInfo;
import com.smc.smccloud.model.order.orderEdit.UpOrderDlvDateInfo;
import com.smc.smccloud.model.order.orderEdit.UpOrderExpDlvType;
import com.smc.smccloud.service.OrderEditService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Author lyc
 * @Date 2022/10/20 14:20
 * @Descripton TODO
 */

@RestController
@Slf4j
@RequestMapping("/order/rcvorder")
public class OrderEditController {

    @Resource
    private OrderEditService orderEditService;


    /**
     * 批量变更订单交货期
     * @param orderDeliveryDate  操作人,完整订单号,客户期望发货日期 物流发货日期
     * @return
     */
    @RequestMapping(value = "/batchUpDlvDate", method = RequestMethod.POST)
    public ResultVo<Map<String, UpdateOrderInfoResultVO>> batchUpDlvDate(@RequestBody List<UpOrderDlvDateInfo> orderDeliveryDate) {
        return orderEditService.batchUpDlvDate(orderDeliveryDate);
    }


    /**
     * 批量变更订单发货地址
     * @param orderDeliveryDate 操作人,完整订单号,地址实体
     * @return
     */
    @RequestMapping(value = "/batchUpdateAddress", method = RequestMethod.POST)
    public ResultVo<Map<String, UpdateOrderInfoResultVO>> batchUpdateAddress(@RequestBody List<UpOrderAddressInfo> orderDeliveryDate) {
        return orderEditService.batchUpdateAddress(orderDeliveryDate);
    }

    /**
     * 批量变更特发/普通
     * @param orderExpDlvType
     * @return
     */
    @RequestMapping(value = "/batchUpExpDlvType", method = RequestMethod.POST)
    public ResultVo<Map<String, UpdateOrderInfoResultVO>> batchUpExpDlvType(@RequestBody List<UpOrderExpDlvType> orderExpDlvType) {
        return orderEditService.batchUpExpDlvType(orderExpDlvType);
    }


    /**
     * 批量变更主单信息(出货方式,集约方式)
     */
    @RequestMapping(value = "/batchUpMastInfo", method = RequestMethod.POST)
    public ResultVo<Map<String, UpdateOrderInfoResultVO>> batchUpMastInfo(@RequestBody List<UpMasterInfo> upMasterInfos) {
        return orderEditService.batchUpMastInfo(upMasterInfos);
    }


}
