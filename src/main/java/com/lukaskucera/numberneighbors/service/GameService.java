package com.lukaskucera.numberneighbors.service;

import com.lukaskucera.numberneighbors.entity.GameEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public interface GameService {
  GameEntity getGameById(Long id);

  GameEntity newGame();

  void deleteGameById(Long id);

  void checkGameAccess(Long id, JwtAuthenticationToken jwtToken);

  void checkGameReady(GameEntity game);

  void sendPayloadToPlayers(GameEntity game);
}
