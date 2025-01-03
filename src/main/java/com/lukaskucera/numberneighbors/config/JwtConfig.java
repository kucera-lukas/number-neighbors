package com.lukaskucera.numberneighbors.config;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

@Configuration
@SuppressWarnings("NullAway.Init")
public class JwtConfig {

  public static final String ISSUER = "https://numberneighbors.lukaskucera.com";
  public static final String AUDIENCE = "numberneighbors";
  public static final String AUTHORITIES_CLAIM_NAME = "authorities";
  public static final String AUTHORITY_PREFIX = "ROLE_";
  public static final String PLAYER_AUTHORITY = "player";

  public static final JWSAlgorithm JWS_ALGORITHM = JWSAlgorithm.RS256;
  public static final String TYPE_HEADER = "JWT";

  @Value("${jwt.public.key}")
  private RSAPublicKey publicKey;

  @Value("${jwt.private.key}")
  private RSAPrivateKey privateKey;

  @Bean
  JwtEncoder jwtEncoder() {
    JWK jwk = new RSAKey.Builder(publicKey).privateKey(privateKey).build();
    JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
    return new NimbusJwtEncoder(jwks);
  }

  @Bean
  JwtDecoder jwtDecoder() {
    final NimbusJwtDecoder decoder = NimbusJwtDecoder
      .withPublicKey(publicKey)
      .build();
    decoder.setJwtValidator(tokenValidator());
    return decoder;
  }

  public OAuth2TokenValidator<Jwt> tokenValidator() {
    final List<OAuth2TokenValidator<Jwt>> validators = List.of(
      new JwtTimestampValidator(),
      new JwtIssuerValidator(ISSUER),
      audienceValidator(),
      new JwtClaimValidator<>("playerId", Long.class::isInstance),
      new JwtClaimValidator<>("gameId", Long.class::isInstance)
    );
    return new DelegatingOAuth2TokenValidator<>(validators);
  }

  public OAuth2TokenValidator<Jwt> audienceValidator() {
    return new JwtClaimValidator<List<String>>(
      OAuth2TokenIntrospectionClaimNames.AUD,
      aud -> aud.contains(AUDIENCE)
    );
  }

  @Bean
  public JwtAuthenticationConverter jwtAuthenticationConverter() {
    final JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
    grantedAuthoritiesConverter.setAuthoritiesClaimName(AUTHORITIES_CLAIM_NAME);
    grantedAuthoritiesConverter.setAuthorityPrefix(AUTHORITY_PREFIX);

    final JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
    jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(
      grantedAuthoritiesConverter
    );
    return jwtAuthenticationConverter;
  }
}
