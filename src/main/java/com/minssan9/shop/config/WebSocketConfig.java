package com.minssan9.shop.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker       // 메세지 브로커에 의해 웹소켓 메세지 처리
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {

        registry.enableSimpleBroker("/queue","/topic");
        // /queue => 1:1 , /topic => 1:N
        registry.setApplicationDestinationPrefixes("/app");
    }

    // SockJs 에서 접속 할수있게 도와줌
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/websockethandler/**").setAllowedOrigins("*").withSockJS();
    }

}
