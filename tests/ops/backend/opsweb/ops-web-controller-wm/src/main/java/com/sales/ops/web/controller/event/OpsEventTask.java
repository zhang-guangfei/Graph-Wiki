package com.sales.ops.web.controller.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OpsEventTask {

    @Autowired
    private OpsEventController opsEventController;

    // @Scheduled(fixedDelay = 5000)
    public void preprocess() {
        opsEventController.preprocess(20);
    }


    // @Scheduled(fixedDelay = 2000)
    public void exchange() {
        opsEventController.exchange(20);
    }


    // @Scheduled(fixedDelay = 10000)
    public void orderStatusHandler() {
        opsEventController.orderStatusQueue(10, 1);
    }

    // @Scheduled(fixedDelay = 10000)
    public void orderAssignHandler() {
        opsEventController.orderStockAssignQueue(10);
    }

    //@Scheduled(fixedDelay = 10 * 1000)
    public void orderPlanHandler() {
        opsEventController.orderDeliveryPlanQueue(100);
    }


}
