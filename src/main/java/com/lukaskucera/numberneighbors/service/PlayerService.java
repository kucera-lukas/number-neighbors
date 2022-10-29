package com.lukaskucera.numberneighbors.service;

import com.lukaskucera.numberneighbors.entity.PlayerEntity;
import java.util.Set;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public interface PlayerService {
  Long getPlayerIdFromToken(JwtAuthenticationToken jwtToken);

  PlayerEntity getPlayerById(Long id);

  PlayerEntity getPlayerByJwtToken(JwtAuthenticationToken jwtToken);

  Set<PlayerEntity> getPlayersByGameId(Long id);

  PlayerEntity newPlayer(Long gameId, String name);

  void deletePlayerById(Long id);

  PlayerEntity addNumbersToPlayer(
    PlayerEntity player,
    int first,
    int second,
    int third
  );

  void checkPlayerAccess(Long id, JwtAuthenticationToken jwtToken);
}
