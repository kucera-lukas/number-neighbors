package com.lukaskucera.numberneighbors.service;

import com.lukaskucera.numberneighbors.config.JwtConfig;
import com.lukaskucera.numberneighbors.dto.PlayerDTO;
import java.time.Instant;
import java.util.Collections;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

@Service
public final class JwtService {

  private static final Logger logger = LoggerFactory.getLogger(
    JwtService.class
  );

  private final JwtEncoder jwtEncoder;

  public JwtService(JwtEncoder jwtEncoder) {
    this.jwtEncoder = jwtEncoder;
  }

  public String generatePlayerToken(PlayerDTO player) {
    return generate(
      player.id().toString(),
      Map.of(
        "playerId",
        player.id(),
        "gameId",
        player.gameId(),
        "name",
        player.name(),
        JwtConfig.AUTHORITIES_CLAIM_NAME,
        JwtConfig.PLAYER_AUTHORITY
      )
    );
  }

  private String generate(String subject, Map<String, Object> claims) {
    logger.debug("Generating JWT for subject {}", subject);

    final Instant instantNow = Instant.now();

    final JwtClaimsSet.Builder claimsSetBuilder = JwtClaimsSet
      .builder()
      .subject(subject)
      .issuer(JwtConfig.ISSUER)
      .audience(Collections.singletonList(JwtConfig.AUDIENCE))
      .notBefore(instantNow)
      .issuedAt(instantNow);

    claims.forEach((name, value) -> {
      logger.trace("Adding claim {} with value {}", name, value);
      claimsSetBuilder.claim(name, value);
    });

    final JwsHeader jwsHeader = JwsHeader
      .with(JwtConfig.JWS_ALGORITHM::getName)
      .type(JwtConfig.TYPE_HEADER)
      .build();

    return jwtEncoder
      .encode(JwtEncoderParameters.from(jwsHeader, claimsSetBuilder.build()))
      .getTokenValue();
  }
}
