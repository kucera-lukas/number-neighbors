package com.lukaskucera.numberneighbors.exception;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public class PlayerIdMissingInJwtTokenClaimsException extends RuntimeException {

  public PlayerIdMissingInJwtTokenClaimsException(
    JwtAuthenticationToken jwtToken
  ) {
    super(
      "player ID is missing in the JWT token claims: " +
      jwtToken.getToken().getClaims()
    );
  }
}
