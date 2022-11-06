package com.lukaskucera.numberneighbors;

import org.hibernate.dialect.PostgreSQL10Dialect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.nativex.hint.AotProxyHint;
import org.springframework.nativex.hint.NativeHint;
import org.springframework.nativex.hint.ProxyBits;
import org.springframework.nativex.hint.TypeHint;
import org.springframework.scheduling.annotation.EnableAsync;

@NativeHint(
  types = {
    @TypeHint(
      types = PostgreSQL10Dialect.class,
      typeNames = "org.hibernate.dialect.PostgreSQLDialect"
    ),
  },
  aotProxies = {
    @AotProxyHint(
      targetClass = com.lukaskucera.numberneighbors.service.AnswerServiceImpl.class,
      proxyFeatures = ProxyBits.IS_STATIC
    ),
    @AotProxyHint(
      targetClass = com.lukaskucera.numberneighbors.service.GameServiceImpl.class,
      proxyFeatures = ProxyBits.IS_STATIC
    ),
    @AotProxyHint(
      targetClass = com.lukaskucera.numberneighbors.service.NumberServiceImpl.class,
      proxyFeatures = ProxyBits.IS_STATIC
    ),
    @AotProxyHint(
      targetClass = com.lukaskucera.numberneighbors.service.PlayerServiceImpl.class,
      proxyFeatures = ProxyBits.IS_STATIC
    ),
    @AotProxyHint(
      targetClass = com.lukaskucera.numberneighbors.service.ResponseServiceImpl.class,
      proxyFeatures = ProxyBits.IS_STATIC
    ),
    @AotProxyHint(
      targetClass = com.lukaskucera.numberneighbors.service.TurnServiceImpl.class,
      proxyFeatures = ProxyBits.IS_STATIC
    ),
  }
)
@SpringBootApplication
@EnableAsync(proxyTargetClass = true, mode = AdviceMode.PROXY)
public class NumberNeighborsApplication {

  public static void main(String[] args) {
    SpringApplication.run(NumberNeighborsApplication.class, args);
  }
}
