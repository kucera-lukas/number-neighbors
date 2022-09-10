package com.lukaskucera.numberneighbors.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(@NotNull HttpSecurity http)
    throws Exception {
    http
      .cors()
      .and()
      .csrf()
      .disable()
      .httpBasic()
      .disable()
      .formLogin()
      .disable()
      .logout()
      .disable()
      .authorizeHttpRequests(authorize ->
        authorize
          // allow unauthorized requests to create a new games and players
          .antMatchers(HttpMethod.POST, "/games", "/players")
          .permitAll()
          .anyRequest()
          .hasRole("player")
      )
      .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
      .sessionManagement(session ->
        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      )
      .exceptionHandling(exceptions ->
        exceptions
          .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
          .accessDeniedHandler(new BearerTokenAccessDeniedHandler())
      );
    return http.build();
  }
}
