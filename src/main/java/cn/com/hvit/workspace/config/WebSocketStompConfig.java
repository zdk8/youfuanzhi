package cn.com.hvit.workspace.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketStompConfig extends AbstractWebSocketMessageBrokerConfigurer {

    private static final Logger logger = LoggerFactory
      .getLogger(WebSocketStompConfig.class);

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    logger.info("注册 StompEndpoints");
    System.out.println("注册 StompEndpoints");
    registry.addEndpoint("/marcopolo").withSockJS();
  }

  @Override
  public void configureMessageBroker(MessageBrokerRegistry registry) {
    logger.info("配置 MessageBroker");
    System.out.println("配置 MessageBroker");
//    registry.enableStompBrokerRelay("/queue", "/topic");
    registry.enableSimpleBroker("/queue", "/topic");
    registry.setApplicationDestinationPrefixes("/app");
  }
  
}
