package com.lukaskucera.numberneighbors.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

@Configuration
public class WebSocketSecurityConfig
  extends AbstractSecurityWebSocketMessageBrokerConfigurer {

  @Override
  protected boolean sameOriginDisabled() {
    return true;
  }

  @Override
  protected void configureInbound(
    @NotNull MessageSecurityMetadataSourceRegistry messages
  ) {
    messages
      .nullDestMatcher()
      .authenticated()
      .simpDestMatchers("/games/turn")
      .hasRole("player")
      .simpSubscribeDestMatchers("/games/play")
      .hasRole("player")
      .simpTypeMatchers(SimpMessageType.MESSAGE, SimpMessageType.SUBSCRIBE)
      .denyAll()
      .anyMessage()
      .denyAll();
  }
}
