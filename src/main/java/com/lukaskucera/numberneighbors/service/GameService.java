package com.lukaskucera.numberneighbors.service;

import com.lukaskucera.numberneighbors.entity.Game;

public interface GameService {

  Game getGameById(Long id);

  Game newGameWithPlayer(String name);

  Game updateGameWithPlayer(Long id, String name);
}
