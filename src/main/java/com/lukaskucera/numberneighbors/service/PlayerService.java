package com.lukaskucera.numberneighbors.service;

import com.lukaskucera.numberneighbors.entity.Player;
import java.util.Set;

public interface PlayerService {
  Player getPlayerById(Long id);

  Set<Player> getPlayersByGameId(Long id);

  Player newPlayer(Long gameId, String name);

  void deletePlayerById(Long id);
}
