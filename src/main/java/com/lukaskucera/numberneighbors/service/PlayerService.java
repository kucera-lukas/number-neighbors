package com.lukaskucera.numberneighbors.service;

import com.lukaskucera.numberneighbors.dto.AuthDTO;
import com.lukaskucera.numberneighbors.dto.PlayerDTO;
import com.lukaskucera.numberneighbors.dto.UserGameDTO;
import java.util.Set;

public interface PlayerService {
  PlayerDTO getPlayerById(AuthDTO auth, Long id);

  Set<PlayerDTO> getPlayersByGameId(AuthDTO auth, Long gameId);

  UserGameDTO getPlayerUserGameByGameId(AuthDTO auth, Long gameId);

  PlayerDTO newPlayer(Long gameId, String name);

  void deletePlayerById(AuthDTO auth, Long id);

  void checkPlayerAccess(AuthDTO auth, Long playerId);
}
