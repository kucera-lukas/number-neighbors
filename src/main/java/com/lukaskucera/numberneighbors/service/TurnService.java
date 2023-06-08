package com.lukaskucera.numberneighbors.service;

import com.lukaskucera.numberneighbors.dto.AuthDTO;
import com.lukaskucera.numberneighbors.dto.TurnDTO;
import com.lukaskucera.numberneighbors.entity.TurnEntity;
import java.util.List;

public interface TurnService {
  List<TurnDTO> getTurnsByGameId(AuthDTO auth, Long gameId);

  TurnDTO newTurn(AuthDTO auth, Long gameId, int value);

  void sendTurnToPlayers(TurnEntity turn);
}
