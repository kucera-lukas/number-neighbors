package com.lukaskucera.numberneighbors.service;

import com.lukaskucera.numberneighbors.entity.PlayerEntity;
import com.lukaskucera.numberneighbors.entity.TurnEntity;
import java.util.List;

public interface TurnService {
  List<TurnEntity> getTurnsByGameId(Long id);

  TurnEntity newTurn(PlayerEntity player, int value);
}
