package com.lukaskucera.numberneighbors.service;

import com.lukaskucera.numberneighbors.entity.GameEntity;
import com.lukaskucera.numberneighbors.entity.PlayerEntity;
import java.util.Set;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public interface PlayerService {
  Long getPlayerIdFromToken(JwtAuthenticationToken jwtToken);

  PlayerEntity getPlayerById(Long id);

  PlayerEntity getPlayerByJwtToken(JwtAuthenticationToken jwtToken);

  Set<PlayerEntity> getPlayersByGameId(Long id);

  PlayerEntity newPlayer(String name, GameEntity game);

  void deletePlayerById(Long id);

  void checkPlayerAccess(Long id, JwtAuthenticationToken jwtToken);
}
