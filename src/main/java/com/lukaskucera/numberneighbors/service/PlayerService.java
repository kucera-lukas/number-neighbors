package com.lukaskucera.numberneighbors.service;

import com.lukaskucera.numberneighbors.entity.PlayerEntity;
import java.util.Set;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public interface PlayerService {
  Long getPlayerIdFromToken(JwtAuthenticationToken jwtToken);

  PlayerEntity getPlayerById(Long id);

  Set<PlayerEntity> getPlayersByGameId(Long id);

  PlayerEntity newPlayer(Long gameId, String name);

  void deletePlayerById(Long id);

  PlayerEntity addNumbersToPlayerById(
    Long id,
    int first,
    int second,
    int third
  );

  void checkPlayerAccess(Long id, JwtAuthenticationToken jwtToken);
}
