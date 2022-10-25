package com.lukaskucera.numberneighbors.exception;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public class PlayerIdMissingInJwtTokenClaimsException extends RuntimeException {

  public PlayerIdMissingInJwtTokenClaimsException(
    JwtAuthenticationToken jwtToken
  ) {
    super(
      String.format(
        "Player ID is missing in the JWT token claims: %s",
        jwtToken.getToken().getClaims()
      )
    );
  }
}
