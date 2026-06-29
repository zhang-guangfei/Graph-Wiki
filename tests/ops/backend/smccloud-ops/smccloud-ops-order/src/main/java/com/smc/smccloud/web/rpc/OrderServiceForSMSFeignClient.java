package com.smc.smccloud.web.rpc;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.adapter.order.OrderDetailDTO;
import com.smc.smccloud.model.adapter.order.OrderDetailVO;
import com.smc.smccloud.model.adapter.order.POOrderNODTO;
import com.smc.smccloud.model.adapter.order.POOrderNOVO;
import com.smc.smccloud.model.orderstate.OrderStateDetailDTO;
import com.smc.smccloud.service.OrderServiceForSMSFeignApi;
import com.smc.smccloud.service.PoInvoiceService;
import com.smc.smccloud.service.SMSOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author edp04
 * @title: OrderServiceForSMSFeignClient
 * @date 2022/05/11 10:02
 */
@RestController
@Slf4j
public class OrderServiceForSMSFeignClient implements OrderServiceForSMSFeignApi {

    @Resource
    private SMSOrderService smsOrderService;

    @Resource
    private PoInvoiceService poInvoiceService;

    @Override
    public ResultVo<List<OrderDetailVO>> listOrderDetailForAgent(OrderDetailDTO detailDTO) {
        return smsOrderService.listOrderDetailForAgent(detailDTO);
    }

    @Override
    public ResultVo<OrderStateDetailDTO> listOrderStateByNo(String order_no) {
        return smsOrderService.listOrderStateByNo(order_no);
    }
    @Override
   public ResultVo<List<POOrderNOVO>> listPOOrder( List<POOrderNODTO> orderNos){

        return poInvoiceService.listPOOrder(orderNos);
    };
}
