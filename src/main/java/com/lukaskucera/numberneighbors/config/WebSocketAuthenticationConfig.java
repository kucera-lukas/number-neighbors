package com.lukaskucera.numberneighbors.config;

import com.lukaskucera.numberneighbors.security.StompHeaderAccessorBearerTokenResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class WebSocketAuthenticationConfig
  implements WebSocketMessageBrokerConfigurer {

  private static final Logger logger = LoggerFactory.getLogger(
    WebSocketAuthenticationConfig.class
  );

  private final StompHeaderAccessorBearerTokenResolver stompHeaderAccessorBearerTokenResolver;

  private final JwtDecoder jwtDecoder;

  private final JwtAuthenticationConverter jwtAuthenticationConverter;

  public WebSocketAuthenticationConfig(
    StompHeaderAccessorBearerTokenResolver stompHeaderAccessorBearerTokenResolver,
    JwtDecoder jwtDecoder,
    JwtAuthenticationConverter jwtAuthenticationConverter
  ) {
    this.stompHeaderAccessorBearerTokenResolver =
      stompHeaderAccessorBearerTokenResolver;
    this.jwtDecoder = jwtDecoder;
    this.jwtAuthenticationConverter = jwtAuthenticationConverter;
  }

  @Override
  public void configureClientInboundChannel(ChannelRegistration registration) {
    registration.interceptors(
      new ChannelInterceptor() {
        @Override
        @Nullable
        public Message<?> preSend(Message<?> message, MessageChannel channel) {
          final StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(
            message,
            StompHeaderAccessor.class
          );

          if (
            accessor != null &&
            StompCommand.CONNECT.equals(accessor.getCommand())
          ) {
            final String token = stompHeaderAccessorBearerTokenResolver.resolve(
              accessor
            );
            final Jwt jwt = jwtDecoder.decode(token);
            final Authentication authentication = jwtAuthenticationConverter.convert(
              jwt
            );

            accessor.setUser(authentication);

            logger.info("Player \"{}\" connected", authentication.getName());
          }

          return message;
        }
      }
    );
  }
}
