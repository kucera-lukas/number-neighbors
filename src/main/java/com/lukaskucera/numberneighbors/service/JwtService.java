package com.lukaskucera.numberneighbors.service;

import com.lukaskucera.numberneighbors.config.JwtConfig;
import com.nimbusds.jose.JWSAlgorithm;
import java.time.Instant;
import java.util.Collections;
import java.util.Date;
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

  public String generate() {
    Instant instant = new Date().toInstant();

    JwtClaimsSet claimsSet =
        JwtClaimsSet.builder()
            .subject("playername")
            .issuer(JwtConfig.ISSUER)
            .audience(Collections.singletonList(JwtConfig.AUDIENCE))
            .notBefore(instant)
            .issuedAt(instant)
            .claim("playerId", 1)
            .claim("gameId", 1)
            .claim("authorities", "player")
            .build();

    JwsHeader jwsHeader = JwsHeader.with(JWS_ALGORITHM::getName).type(TYPE_HEADER).build();
    return jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claimsSet)).getTokenValue();
  }
}
