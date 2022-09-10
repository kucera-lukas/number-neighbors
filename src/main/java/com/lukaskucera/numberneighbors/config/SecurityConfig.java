package com.lukaskucera.numberneighbors.config;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import java.util.List;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenIntrospectionClaimNames;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimValidator;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtIssuerValidator;
import org.springframework.security.oauth2.jwt.JwtTimestampValidator;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  @Value("${secret-key}")
  public String secretKey;

  @Bean
  @Order(Ordered.HIGHEST_PRECEDENCE)
  public SecurityFilterChain filterChain(@NotNull HttpSecurity http) throws Exception {
    http.cors()
        .and()
        .csrf()
        .disable()
        .httpBasic()
        .disable()
        .formLogin()
        .disable()
        .logout()
        .disable()
        .authorizeHttpRequests(
            authorize ->
                authorize
                    // allow unauthorized requests to create a new games and players
                    .antMatchers(HttpMethod.POST, "/games", "/players")
                    .permitAll()
                    .anyRequest()
                    .hasRole("player"))
        .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
    return http.build();
  }

  @Bean
  public JwtEncoder jwtEncoder() {
    SecretKey key = new SecretKeySpec(getSecretKeyBytes(), JwtConfig.SECRET_KEY_ALGORITHM);
    JWKSource<SecurityContext> immutableSecret = new ImmutableSecret<>(key);
    return new NimbusJwtEncoder(immutableSecret);
  }

  private byte[] getSecretKeyBytes() {
    return secretKey.getBytes();
  }

  @Bean
  public JwtDecoder jwtDecoder() {
    SecretKey originalKey = new SecretKeySpec(getSecretKeyBytes(), JwtConfig.SECRET_KEY_ALGORITHM);
    final NimbusJwtDecoder decoder = NimbusJwtDecoder.withSecretKey(originalKey).build();
    decoder.setJwtValidator(tokenValidator());
    return decoder;
  }

  public OAuth2TokenValidator<Jwt> tokenValidator() {
    final List<OAuth2TokenValidator<Jwt>> validators =
        List.of(
            new JwtTimestampValidator(),
            new JwtIssuerValidator(JwtConfig.ISSUER),
            audienceValidator());
    return new DelegatingOAuth2TokenValidator<>(validators);
  }

  public OAuth2TokenValidator<Jwt> audienceValidator() {
    return new JwtClaimValidator<List<String>>(
        OAuth2TokenIntrospectionClaimNames.AUD, aud -> aud.contains(JwtConfig.AUDIENCE));
  }

  @Bean
  public JwtAuthenticationConverter jwtAuthenticationConverter() {
    final JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter =
        new JwtGrantedAuthoritiesConverter();
    grantedAuthoritiesConverter.setAuthoritiesClaimName(JwtConfig.AUTHORITIES_CLAIM_NAME);
    grantedAuthoritiesConverter.setAuthorityPrefix(JwtConfig.AUTHORITY_PREFIX);

    final JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
    jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
    return jwtAuthenticationConverter;
  }
}
