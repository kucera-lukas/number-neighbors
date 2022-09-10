package com.lukaskucera.numberneighbors.service;

import com.lukaskucera.numberneighbors.config.JwtConfig;
import com.nimbusds.jose.JWSAlgorithm;
import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
  private static final JWSAlgorithm JWS_ALGORITHM = JWSAlgorithm.HS256;
  private static final String TYPE_HEADER = "JWT";
  private final JwtEncoder jwtEncoder;

  public JwtService(JwtEncoder jwtEncoder) {
    this.jwtEncoder = jwtEncoder;
  }

  public String generatePlayerToken(String name, Long playerId, Long gameId) {
    return generate(name, Map.of("playerId", playerId, "gameId", gameId, "authorities", "player"));
  }

  private String generate(String subject, Map<String, Object> claims) {
    Instant instant = new Date().toInstant();

    JwtClaimsSet.Builder claimsSetBuilder =
        JwtClaimsSet.builder()
            .subject(subject)
            .issuer(JwtConfig.ISSUER)
            .audience(Collections.singletonList(JwtConfig.AUDIENCE))
            .notBefore(instant)
            .issuedAt(instant);

    claims.forEach(claimsSetBuilder::claim);

    JwsHeader jwsHeader = JwsHeader.with(JWS_ALGORITHM::getName).type(TYPE_HEADER).build();
    return jwtEncoder
        .encode(JwtEncoderParameters.from(jwsHeader, claimsSetBuilder.build()))
        .getTokenValue();
  }
}
