package com.lukaskucera.numberneighbors.dto;

import com.lukaskucera.numberneighbors.exception.GameIdMissingInJwtTokenClaimsException;
import com.lukaskucera.numberneighbors.exception.PlayerIdMissingInJwtTokenClaimsException;
import java.util.Map;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public record AuthDTO(Long gameId, Long playerId) {
  public static AuthDTO fromJwtToken(JwtAuthenticationToken jwtToken) {
    final Map<String, Object> claims = jwtToken.getToken().getClaims();

    final Long gameId = (Long) claims.get("gameId");

    if (gameId == null) {
      throw new GameIdMissingInJwtTokenClaimsException(jwtToken);
    }

    final Long playerId = (Long) claims.get("playerId");

    if (playerId == null) {
      throw new PlayerIdMissingInJwtTokenClaimsException(jwtToken);
    }

    return new AuthDTO(gameId, playerId);
  }
}
