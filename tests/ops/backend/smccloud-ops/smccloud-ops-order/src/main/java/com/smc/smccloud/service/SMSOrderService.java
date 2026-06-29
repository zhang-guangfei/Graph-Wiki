package com.smc.smccloud.service;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.adapter.order.OrderDetailDTO;
import com.smc.smccloud.model.adapter.order.OrderDetailVO;
import com.smc.smccloud.model.expdetail.ExpdetailSalesVO;
import com.smc.smccloud.model.orderstate.OrderStateDetailDTO;

import java.util.List;

/**
 * @author edp04
 * @title: SMSOrderService
 * @date 2022/05/11 10:20
 */
public interface SMSOrderService {

    ResultVo<List<OrderDetailVO>> listOrderDetailForAgent(OrderDetailDTO detailDTO);

    ResultVo<OrderStateDetailDTO> listOrderStateByNo(String order_no);

    ResultVo<List<ExpdetailSalesVO>> listOrderExpdetailWithSales(OrderDetailDTO detailDTO);
}
