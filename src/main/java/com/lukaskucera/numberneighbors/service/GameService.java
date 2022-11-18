package com.lukaskucera.numberneighbors.service;

import com.lukaskucera.numberneighbors.dto.AuthDTO;
import com.lukaskucera.numberneighbors.dto.GameDTO;
import com.lukaskucera.numberneighbors.entity.GameEntity;

public interface GameService {
  GameDTO getGameById(AuthDTO auth, Long id);

  GameDTO newGame();

  void checkGameAccess(AuthDTO auth, Long gameId);

  void sendPayloadToPlayers(GameEntity game);
}
