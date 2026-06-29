package com.smc.smccloud.config.websocket;//package com.smc.smccloud.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MessageService
{
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    /**
     * 模拟 向客户端发送数据
     */
    @Scheduled(fixedDelay = 3000) // spring的计划任务，这里表示每五秒执行一次，需要在配置类或者启动类上加一个注解@EnableScheduling，这样才能生效
    public void mes() {
        // 第一个参数是表示向那个通道发送、第二个参数是发送的内容这样客户端就可以连接了。
        messagingTemplate.convertAndSend("/topic","wqeqweqeqw");
    }
}
