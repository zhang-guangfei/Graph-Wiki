package com.sales.ops;

import com.sales.ops.db.extdao.OrderAssignResultFnoDao;
import com.sales.ops.service.order.BaseCustomerOrderService;
import com.sales.ops.service.order.BaseOrderAssignResultService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class EventBusTest extends BaseTest {

    @Autowired
    private OrderAssignResultFnoDao orderAssignResultFnoDao;
    @Autowired
    private BaseCustomerOrderService baseCustomerOrderService;
    @Autowired
    private BaseOrderAssignResultService baseOrderAssignResultService;

    @Test
    public void test1() {

    }
}
