package com.lukaskucera.numberneighbors;

import org.hibernate.dialect.PostgreSQL10Dialect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.nativex.hint.TypeHint;

@SpringBootApplication
@TypeHint(
  types = PostgreSQL10Dialect.class,
  typeNames = "org.hibernate.dialect.PostgreSQLDialect"
)
@EnableCaching(proxyTargetClass = true)
public class NumberNeighborsApplication {

  public static void main(String[] args) {
    SpringApplication.run(NumberNeighborsApplication.class, args);
  }
}
