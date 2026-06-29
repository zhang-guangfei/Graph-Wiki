package com.smc.smccloud.service;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.OrderSales.OrderSalesDTO;
import com.smc.smccloud.model.adapter.order.AdapterOrderResult;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

/**
 * @Author lyc
 * @Date 2023/9/12 9:04
 * @Descripton TODO
 */
public interface RcvOrderService {

    ResultVo<List<AdapterOrderResult>> addOrderSalesFromSMS(OrderSalesDTO dto);

}
