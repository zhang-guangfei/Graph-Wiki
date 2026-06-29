package com.smc.smccloud.config.websocket;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;


@Configuration
@EnableWebSocketMessageBroker // 注解开启STOMP协议来传输基于代理的消息，此时控制器支持使用@MessageMapping
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic"); // 注册一个队列，主要用来做消息区分的(在我看来)
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // addEndpoint("/order/websocket")就是客户端连接的时候url地址
        registry.addEndpoint("/websocket").setAllowedOrigins("*").addInterceptors().withSockJS();
    }
}
