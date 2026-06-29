package com.sales.ops.event.repository;


import com.sales.ops.event.repository.entity.OrderEvent;
import com.sales.ops.event.repository.mapper.OpsEventBusDao;
import com.sales.ops.event.repository.mapper.OpsEventPoolDao;
import com.sales.ops.event.repository.mapper.OpsRouteConfigDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author C12961
 * @date 2023/3/29 14:42
 */
@Slf4j
@Service
public class OpsEventScannerImpl implements OpsEventScanner {

    @Autowired
    private OpsEventBusDao opsEventBusDao;
    @Autowired
    private OpsEventPoolDao opsEventPoolDao;
    @Autowired
    private OpsRouteConfigDao opsRouteConfigDao;


    @Override
    public OrderEvent  scanEventQueueById(String queueName, Long id){
        return opsRouteConfigDao.scanEventId(queueName,id);
    }

    // 查询队列表和更新队列表状态
    @Override
    public List<OrderEvent> scanEventQueueBySize(String queueName, Integer size) {
        return opsRouteConfigDao.scanEventsByCount(queueName, size, 1);
    }

    //查询队列表按照事件组别
    @Override
    public List<OrderEvent> scanEventQueueBySizeAndGroup(String queueName, Integer size, Integer groupId) {
        return opsRouteConfigDao.scanEventsByCount(queueName, size, groupId);
    }

    @Override
    public int updateEventQueueToHandled(String queueName, Long id, Long version) {
        return opsRouteConfigDao.updateEventToHandled(queueName, id, version, new Date());
    }

    @Override
    public int updateEventQueueToFailure(String queueName, Long id, String remark) {
        return opsRouteConfigDao.updateEventDealFlag(queueName, id, remark, new Date(), 2);
    }

    @Override
    public int updateEventQueueToInit(String queueName, Long id, String remark) {
        return opsRouteConfigDao.updateEventDealFlag(queueName, id, remark, new Date(), 0);
    }

    // 插入队列表
    @Override
    public int insertEventQueue(OrderEvent event, String queue) {
        return opsRouteConfigDao.insertIntoEventQueue(event, queue);
    }

    @Override
    public int insertEventQueueBatch(List<OrderEvent> eventList, String queue) {
        return opsRouteConfigDao.insertIntoEventQueueBatch(eventList, queue);
    }

    @Cacheable(cacheNames = "ops.event.queue")
    @Override
    public List<String> selectQueueByEventCode(String eventCode) {
        return opsRouteConfigDao.selectByEventCode(eventCode);
    }

    // 查询pool表和更新pool状态
    @Override
    public List<OrderEvent> scanEventPoolBySize(Integer size) {
        return opsEventPoolDao.scanEventsByCount(size);
    }

    @Override
    public int updateEventPoolToHandled(Long id, Long version) {
        return opsEventPoolDao.updateEventToHandled(id, version, new Date());
    }

    @Override
    public int updateEventPoolToFailure(Long id, String remark) {
        return opsEventPoolDao.updateEventToFailure(id, remark, new Date());
    }

    // 插入pool表
    @Override
    public int insertEventPool(OrderEvent pool) {
        return opsEventPoolDao.insertIntoEventPool(pool);
    }

    @Override
    public int insertEventPool(List<OrderEvent> eventList) {
        return opsEventPoolDao.insertIntoEventPoolBatch(eventList);
    }

    // 读取bus表数据和更新状态
    @Override
    public OrderEvent scanEventBusByBusId(Long busId) {
        return opsEventBusDao.scanEventsByBusId(busId);
    }

    // 读取bus表数据和更新状态
    @Override
    public List<OrderEvent> scanEventBusBySize(Integer size) {
        return opsEventBusDao.scanEventsByCount(size);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateEventBusToHandled(Long id, Long version) {
        return opsEventBusDao.updateEventToHandled(id, version, new Date());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateEventBusToHandling(Long id, Long version) {
        return opsEventBusDao.updateEventToHandling(id, version, new Date());
    }

    @Override
    public int updateEventBusToInit(Long id, String remark) {
        return opsEventBusDao.updateEventToInit(id, remark, new Date());
    }

    @Override
    public int updateEventBusToFailure(Long id, String remark) {
        return opsEventBusDao.updateEventToFailure(id, remark, new Date());
    }

    // 插入bug表
    @Override
    public int insertEventBus(OrderEvent bus) {
        bus.setId(null);
        bus.setBusId(null);
        return opsRouteConfigDao.insertIntoEventQueue(bus, "ops_event_bus");
    }


}
