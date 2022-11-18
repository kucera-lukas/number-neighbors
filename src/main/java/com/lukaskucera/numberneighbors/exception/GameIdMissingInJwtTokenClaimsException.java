package com.lukaskucera.numberneighbors.exception;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public class GameIdMissingInJwtTokenClaimsException extends RuntimeException {

  public GameIdMissingInJwtTokenClaimsException(
    JwtAuthenticationToken jwtToken
  ) {
    super(
      "game ID is missing in the JWT token claims: " +
      jwtToken.getToken().getClaims()
    );
  }
}
