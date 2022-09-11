package com.lukaskucera.numberneighbors.service;

import com.lukaskucera.numberneighbors.entity.Player;
import java.util.Set;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public interface PlayerService {
  Player getPlayerById(Long id);

  Set<Player> getPlayersByGameId(Long id);

  Player newPlayer(Long gameId, String name);

  void deletePlayerById(Long id);

  void checkPlayerAccess(Long id, JwtAuthenticationToken jwtToken);
}
