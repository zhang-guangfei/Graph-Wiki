package com.sales.ops.event.processor;

import com.sales.ops.event.repository.OpsEventScanner;
import com.sales.ops.event.repository.entity.OrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Slf4j
@Component
public abstract class SyncEventConsumerStrategy implements EventConsumerStrategy {

    @Autowired
    private OpsEventScanner opsEventScanner;

    protected OpsEventScanner.EventQueue eventQueue;
    private Integer retryCount = 10;

    public SyncEventConsumerStrategy(OpsEventScanner.EventQueue eventQueue) {
        this.eventQueue = eventQueue;
    }


    public Integer getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(Integer retryCount) {
        this.retryCount = retryCount;
    }

    public void handle(List<OrderEvent> orderEvents) {
        for (OrderEvent event : orderEvents) {
            try {
                processEvent(event);
                success(event);
            } catch (Exception e) {
                failure(event, e);
            }
        }
    }


    protected abstract void processEvent(OrderEvent event) throws Exception;

    protected void success(OrderEvent event) {
        opsEventScanner.updateEventQueueToHandled(eventQueue.getName(), event.getId(), event.getVersion());
    }

    protected void failure(OrderEvent event, Exception e) {
        log.error("", e);
        String remark = StringUtils.substring(e.getMessage(), 0, 1000);
        if (e instanceof NullPointerException || StringUtils.isBlank(e.getMessage())) {
            String stackTrace = ThrowableUtil.getStackTrace(e);
            remark = StringUtils.substring(stackTrace, 0, 1000);
        }
        if (event.getVersion() < getRetryCount()) {
            opsEventScanner.updateEventQueueToInit(eventQueue.getName(), event.getId(), remark);
        } else {
            opsEventScanner.updateEventQueueToFailure(eventQueue.getName(), event.getId(), remark);
        }


    }


}
