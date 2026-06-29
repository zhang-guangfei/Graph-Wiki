package com.sales.ops.job.handler;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.stereotype.Component;

/**
 * @author C12961
 * @date 2022/1/7 12:53
 */
@Component
public class HelloHandler {

    /**
     * 测试job 管理器和执行器的正常连接
     */
    @XxlJob("hello")
    public void hello(){
        System.out.println("Welcome To XXL Job!!!");
        XxlJobHelper.handleFail("错误测试");


    }

    @XxlJob("testMail")
    public void testMail() throws Exception{
        System.out.println("测试邮件");
        int a= 1/0;
    }

}
