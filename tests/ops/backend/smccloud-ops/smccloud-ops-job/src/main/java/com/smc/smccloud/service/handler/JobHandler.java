package com.smc.smccloud.service.handler;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.redis.redisson.RedissonUtil;
import com.smc.smccloud.model.orderlog.OrderLogVO;
import com.smc.smccloud.service.OrderLogFeignApi;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * Author: B90034
 * Date: 2021-11-29 11:20
 * Description: 定时任务执行器
 */
@Slf4j
@Component
public class JobHandler {

//    @Autowired
//    private RedissonClient redissonClient;

    @Resource
    private RedissonUtil redissonUtil;

    @Resource
    private OrderLogFeignApi orderLogFeignApi;

    /**
     * 1、简单任务示例（Bean模式）
     */
    @XxlJob("demoJobHandler")
    public void demoJobHandler() throws Exception {
        // 获取传参
        String param = XxlJobHelper.getJobParam();
        // 记录日志
        XxlJobHelper.log("XXL-JOB: Hello World, {}.", param);

        for (int i = 0; i < 5; i++) {
            XxlJobHelper.log("beat at:" + i);
            TimeUnit.SECONDS.sleep(1);
        }
        // default success
    }


    /**
     * 2、当前线程未执行完毕不会再次执行
     */
    @XxlJob("timeJobTest")
    public void timeJobTest() throws Exception {

        RLock testA = redissonUtil.lock("testA");

        try {
            // 记录日志
            XxlJobHelper.log("开始执行测试定时任务");
            // boolean tryLock = redisLockUtil.tryLock("test", 5, 10);
            // XxlJobHelper.log("tryLock = " + tryLock);

            for (int i = 0; i < 5; i++) {
                XxlJobHelper.log("---> " + i);
                TimeUnit.SECONDS.sleep(20);
            }
            XxlJobHelper.log("执行完毕 ");
        } catch (InterruptedException e) {
            XxlJobHelper.log("开始执行测试定时任务 ==> 抛异常");
            XxlJobHelper.log("异常={} ",e);
        } finally {
            redissonUtil.unlock(testA);
            XxlJobHelper.log("释放锁 ");
        }
    }

    /**
     * 2、当前线程未执行完毕不会再次执行
     */
    @XxlJob("testabc")
    public void testabc() throws Exception {

        RLock testA = redissonUtil.lock("test333");

        try {
            // 记录日志
            XxlJobHelper.log("开始执行测试定时任务");
            // boolean tryLock = redisLockUtil.tryLock("test", 5, 10);
            // XxlJobHelper.log("tryLock = " + tryLock);

            for (int i = 0; i < 5; i++) {
                OrderLogVO orderLogVO = new OrderLogVO();
                orderLogVO.setOrderNo("xxxx-" + (i+1));
                orderLogVO.setIp("1212");
                orderLogVO.setOptType(1);
                ResultVo<String> integerResultVo = orderLogFeignApi.addOrderLog(orderLogVO);
                if (!integerResultVo.isSuccess()) {
                    XxlJobHelper.log("add fail => {}",orderLogVO.getOrderNo());
                    return;
                }
                XxlJobHelper.log("---> {} = {}" ,i+1, orderLogVO.getOrderNo());
                TimeUnit.SECONDS.sleep(20);
            }
            XxlJobHelper.log("执行完毕 ");
        } catch (InterruptedException e) {
            XxlJobHelper.log("开始执行测试定时任务 ==> 抛异常");
            XxlJobHelper.log("异常={} ",e);
        } finally {
            redissonUtil.unlock(testA);
            XxlJobHelper.log("释放锁 ");
        }
    }

}
