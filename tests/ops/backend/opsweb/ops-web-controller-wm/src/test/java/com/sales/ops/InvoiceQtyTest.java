package com.sales.ops;

import cn.hutool.json.JSONUtil;
import com.sales.ops.dto.order.OpsInvoiceResDto;
import com.sales.ops.service.order.OpsCustomerOrderService;
import com.sales.ops.serviceimpl.event.v3.order.service.CustomerOrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Slf4j
public class InvoiceQtyTest extends BaseTest {


    @Resource
    private CustomerOrderService customerOrderService;
    @Resource
    private OpsCustomerOrderService opsCustomerOrderService;

    @Test
    public void test1() {
        String orderFno = "10075271-8";
        try {
            List<OpsInvoiceResDto> list = opsCustomerOrderService.getInvoiceInfo(Collections.singletonList(orderFno));
            log.info(JSONUtil.toJsonPrettyStr(list));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        int invoicedQty = customerOrderService.getInvoicedQty(orderFno);
        log.info("订单：{}，开票数{}", orderFno, invoicedQty);
    }

}
