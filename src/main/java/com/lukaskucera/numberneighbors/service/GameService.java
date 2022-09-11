package com.lukaskucera.numberneighbors.service;

import com.lukaskucera.numberneighbors.entity.Game;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public interface GameService {
  Game getGameById(Long id);

  Game newGame(String hostName);

  void deleteGameById(Long id);

  void checkGameAccess(Long id, JwtAuthenticationToken jwtToken);
}
