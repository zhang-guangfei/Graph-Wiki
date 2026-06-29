package com.sales.ops.serviceimpl.event.v3.manage.threadPool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

@Service
public class ThreadPoolExecutorMonitor {

    @Autowired
    @Qualifier("EventOrderStatusTaskExecutor")
    private ThreadPoolTaskExecutor eventOrderStatusTaskExecutor;
    @Autowired
    @Qualifier("EventDeliveryPlanTaskExecutor")
    private ThreadPoolTaskExecutor eventDeliveryPlanTaskExecutor;


    public Map<String, Object> getThreadPoolInfo(String poolName) {
        ThreadPoolExecutor threadPoolExecutor = null;
        if(poolName.equals("EventOrderStatusTaskExecutor")){
            threadPoolExecutor=eventOrderStatusTaskExecutor.getThreadPoolExecutor();
        }else if(poolName.equals("EventDeliveryPlanTaskExecutor")){
            threadPoolExecutor=eventDeliveryPlanTaskExecutor.getThreadPoolExecutor();
        }
        int activeCount = threadPoolExecutor.getActiveCount();
        long completedTaskCount = threadPoolExecutor.getCompletedTaskCount();
        int corePoolSize = threadPoolExecutor.getCorePoolSize();
        int largestPoolSize = threadPoolExecutor.getLargestPoolSize();
        int maximumPoolSize = threadPoolExecutor.getMaximumPoolSize();
        int poolSize = threadPoolExecutor.getPoolSize();
        int queueSize = threadPoolExecutor.getQueue().size();
        long taskCount = threadPoolExecutor.getTaskCount();
        return MapUtil.of(
                "活跃线程数", activeCount,// 返回当前正在执行任务的线程数
                "线程池大小", poolSize,// 返回当前线程池中存活线程的总数
                "核心线程数", corePoolSize,// 返回线程池的核心线程数，即使线程池没有任务执行，也会保持这个数量的线程存活
                "允许的最大线程池数", maximumPoolSize,// 返回线程池允许的最大线程数
                "最大线程数", largestPoolSize,// 返回线程池自创建以来曾经达到过的最大线程数
                "队列大小", queueSize,// 返回线程池任务队列中等待执行的任务数
                "已完成的任务总数", completedTaskCount,// 返回自从线程池创建以来已完成的任务总数
                "任务总数", taskCount// 返回自从线程池创建以来提交到线程池的任务总数，包括已完成的任务、正在执行的任务和队列中等待的任务
        );
    }

    static class MapUtil {

        public static <K, V> Map of(Object... o) {
            Map<K, V> map = new HashMap();
            int length = o.length;
            for (int i = 0; i < o.length - 1; i++) {
                map.put((K) o[i], (V) o[i + 1]);
                i++;
            }
            return map;
        }
    }


}
