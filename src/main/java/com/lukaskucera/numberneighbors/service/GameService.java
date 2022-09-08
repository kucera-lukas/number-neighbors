package com.lukaskucera.numberneighbors.service;

import com.lukaskucera.numberneighbors.entity.Game;

public interface GameService {

  Game getGameById(Long id);

  Game newGame();

  void deleteGameById(Long id);
}
