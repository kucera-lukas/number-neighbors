package com.lukaskucera.numberneighbors.dto;

import com.lukaskucera.numberneighbors.entity.GameEntity;
import java.util.List;

public record GameDTO(Long id, List<PlayerDTO> players, boolean ready) {
  public static GameDTO fromGame(GameEntity game) {
    return new GameDTO(
      game.getId(),
      game.getPlayers().stream().map(PlayerDTO::fromPlayer).toList(),
      game.isReady()
    );
  }
}
